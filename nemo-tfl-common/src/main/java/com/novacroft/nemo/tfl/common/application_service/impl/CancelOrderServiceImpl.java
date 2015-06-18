package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.CancelOrderService;
import com.novacroft.nemo.tfl.common.application_service.cubic.CardRemoveUpdateService;
import com.novacroft.nemo.tfl.common.constant.CancelOrderResult;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.CancelOrderResultDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.SettlementDTO;

@Service("cancelOrderService")
@Transactional
public class CancelOrderServiceImpl extends BaseService implements CancelOrderService {

    protected static final Integer CUBIC_RETRY_COUNT = 2;

    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected CardRemoveUpdateService cardRemoveUpdateService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected AdHocLoadSettlementDataService adhocLoadSettlementDataService;
    @Autowired
    protected PaymentCardSettlementDataService paymentCardSettlementDataService;

    @Override
    public CancelOrderResultDTO cancelOrderWithExternalOrderIdAndCustomerId(Long externalCustomerId, Long externalOrderId) {
        Long internalCustomerId = customerDataService.getInternalIdFromExternalId(externalCustomerId);
        Long internalOrderId = orderDataService.getInternalIdFromExternalId(externalOrderId);
        CancelOrderResultDTO cancelOrderResultDTO = cancelOrderWithOrderIdAndCustomerId(internalCustomerId, internalOrderId);

        cancelOrderResultDTO.setId(orderDataService.getExternalIdFromInternalId(cancelOrderResultDTO.getId()));
        if (cancelOrderResultDTO.getOriginalId() != null && cancelOrderResultDTO.getOriginalId() > 0) {
            cancelOrderResultDTO.setOriginalId(orderDataService.getExternalIdFromInternalId(cancelOrderResultDTO.getOriginalId()));
        }

        return cancelOrderResultDTO;
    }

    @Override
    @Transactional
    public CancelOrderResultDTO cancelOrderWithOrderIdAndCustomerId(Long customerId, Long orderId) {
        assert (customerId != null && orderId != null);
        CancelOrderResultDTO cancelOrderResultDTO = null;
        OrderDTO orderDTO = orderDataService.findByIdAndCustomerId(orderId, customerId);
        assert (orderDTO != null);
        List<AdHocLoadSettlementDTO> adHocLoadSettlementDTOs = adhocLoadSettlementDataService.findByOrderId(orderDTO.getId());
        String existingCardNumber = getExistingCardNumber(getCardIdFromOrderItems(orderDTO.getOrderItems()));
        if (existingCardNumber != null) {
            cancelOrderResultDTO = processExistingCard(existingCardNumber, orderDTO, adHocLoadSettlementDTOs);
            if (cancelOrderResultDTO.getResult() != null) {
                return cancelOrderResultDTO;
            }
        }
        // Request sequence number on AdhocLoadSettlements has been changed to the remove request sequence number
        return createRefundOrderAndSettlement(orderDTO, adHocLoadSettlementDTOs);
    }

    @Override
    public CancelOrderResultDTO completeCancelOrderWithExternalOrderIdAndCustomerId(Long externalCustomerId, Long externalOrderId) {
        Long internalCustomerId = customerDataService.getInternalIdFromExternalId(externalCustomerId);
        Long internalOrderId = orderDataService.getInternalIdFromExternalId(externalOrderId);

        CancelOrderResultDTO completeCancelOrderResultDTO = completeCancelOrderWithOrderIdAndCustomerId(internalCustomerId, internalOrderId);
        completeCancelOrderResultDTO.setId(orderDataService.getExternalIdFromInternalId(completeCancelOrderResultDTO.getId()));
        if (completeCancelOrderResultDTO.getOriginalId() != null && completeCancelOrderResultDTO.getOriginalId() > 0) {
            completeCancelOrderResultDTO.setOriginalId(orderDataService.getExternalIdFromInternalId(completeCancelOrderResultDTO.getOriginalId()));
        }
        return completeCancelOrderResultDTO;
    }

    @Override
    public CancelOrderResultDTO completeCancelOrderWithOrderIdAndCustomerId(Long customerId, Long orderId) {
        assert (customerId != null && orderId != null);
        OrderDTO orderDTO = orderDataService.findByIdAndCustomerId(orderId, customerId);
        assert (orderDTO != null);
        orderDTO.setStatus(OrderStatus.CANCELLED.code());

        return updateRefundOrderAndSettlement(orderDTO);
    }

    protected String getExistingCardNumber(Long cardId) {
        CardDTO cardDTO = cardDataService.findById(cardId);
        return (cardDTO != null && cardDTO.getCardNumber() != null) ? cardDTO.getCardNumber() : null;
    }

    protected Long getCardIdFromOrderItems(List<ItemDTO> orderItems) {
        Long cardId = (orderItems != null && orderItems.get(0) != null) ? orderItems.get(0).getCardId() : null;
        for (ItemDTO item : orderItems) {
            Long tempCardId = item.getCardId();
            if (null == cardId && tempCardId != null) {
                cardId = tempCardId;
            }
            if ((cardId != null && cardId.equals(tempCardId)) || null == tempCardId) {
                continue;
            } else {
                throw new ApplicationServiceException(getContent(ContentCode.MULTIPLE_CARDS_ON_ORDER.errorCode()));
            }
        }
        return cardId;
    }

    protected CancelOrderResultDTO processExistingCard(String cardNumber, OrderDTO orderDTO, List<AdHocLoadSettlementDTO> adHocLoadSettlementDTOs) {
        CancelOrderResultDTO cancelOrderResultDTO = new CancelOrderResultDTO(orderDTO.getId());
        if (isBeforeCutOffTime(orderDTO.getOrderDate())) {
            CardUpdateResponseDTO cubicRemovalResponseDTO = removePendingUpdates(cardNumber, adHocLoadSettlementDTOs);
            if (cubicRemovalResponseDTO != null && cubicRemovalResponseDTO.getErrorCode() != null) {
                return new CancelOrderResultDTO(orderDTO.getId(), CancelOrderResult.UNABLE_TO_CANCEL_ORDER.name(),
                                (cubicRemovalResponseDTO.getErrorDescription() != null ? cubicRemovalResponseDTO.getErrorDescription()
                                                : getContent(CancelOrderResult.UNABLE_TO_CANCEL_ORDER.contentCode())));
            }
        } else {
            return new CancelOrderResultDTO(orderDTO.getId(), CancelOrderResult.UNABLE_TO_CANCEL_ORDER_AFTER_CUT_OFF_TIME.name(),
                            getContent(CancelOrderResult.UNABLE_TO_CANCEL_ORDER_AFTER_CUT_OFF_TIME.contentCode()));
        }
        return cancelOrderResultDTO;
    }

    protected boolean isBeforeCutOffTime(Date orderDate) {
        return DateUtil.isBeforeBusinessDayCutOffHoursOnTheSameDay(orderDate) || DateUtil.isBeforeBusinessDayCutOffHoursForTheNextDay(orderDate);
    }

    protected CardUpdateResponseDTO removePendingUpdates(String cardNumber, List<AdHocLoadSettlementDTO> settlementDTOs) {
        CardUpdateResponseDTO cardRemoveUpdateResponseDTO = null;
        assert (settlementDTOs != null);
        for (SettlementDTO settlementDTO : settlementDTOs) {
            AdHocLoadSettlementDTO adhocLoadSettlement = (AdHocLoadSettlementDTO) settlementDTO;
            if (hasAdhocLoadRequestBeenRegisteredInCubic(adhocLoadSettlement.getRequestSequenceNumber())) {
                cardRemoveUpdateResponseDTO = removePendingUpdate(cardNumber, adhocLoadSettlement.getRequestSequenceNumber());
                if (cardRemoveUpdateResponseDTO != null) {
                    if (cardRemoveUpdateResponseDTO.getErrorDescription() != null) {
                        break;
                    } else {
                        adhocLoadSettlement.setRequestSequenceNumber(cardRemoveUpdateResponseDTO.getRequestSequenceNumber());
                    }
                }
            }
        }
        return cardRemoveUpdateResponseDTO;
    }

    protected boolean hasAdhocLoadRequestBeenRegisteredInCubic(Integer requestSequenceNumber) {
        return null != requestSequenceNumber;
    }

    protected CardUpdateResponseDTO removePendingUpdate(String cardNumber, Integer requestSequenceNumber) {
        CardUpdateResponseDTO cardRemoveUpdateResponseDTO = null;
        int errorCount = 0;
        do {
            try {
                cardRemoveUpdateResponseDTO = cardRemoveUpdateService.removePendingUpdate(cardNumber, Long.valueOf(requestSequenceNumber));
                if (cardRemoveUpdateResponseDTO == null || cardRemoveUpdateResponseDTO.getErrorCode() != null) {
                    errorCount++;
                } else {
                    break;
                }
            } catch (Exception e) {
                errorCount++;
            }
        } while (errorCount <= CUBIC_RETRY_COUNT && (cardRemoveUpdateResponseDTO != null && cardRemoveUpdateResponseDTO.getErrorCode() != null));
        return cardRemoveUpdateResponseDTO;
    }

    protected CancelOrderResultDTO createRefundOrderAndSettlement(OrderDTO orderDTO, List<AdHocLoadSettlementDTO> adHocLoadSettlementDTOs) {
        CancelOrderResultDTO cancelOrderResultDTO = new CancelOrderResultDTO(CancelOrderResult.SUCCESS_AWAITING_REFUND_PAYMENT.name(),
                        getContent(CancelOrderResult.SUCCESS_AWAITING_REFUND_PAYMENT.contentCode()));
        cancelOrderResultDTO.setOriginalId(orderDTO.getId());
        OrderDTO refundOrderDTO = updateRefundOrder(orderDTO);
        if (null == refundOrderDTO || null == refundOrderDTO.getId() || refundOrderDTO.getId() <= 0) {
            cancelOrderResultDTO.setResult(CancelOrderResult.UNABLE_TO_CREATE_OR_UPDATE_REFUND_ORDER.name());
            cancelOrderResultDTO.setMessage(getContent(CancelOrderResult.UNABLE_TO_CREATE_OR_UPDATE_REFUND_ORDER.contentCode()));
            return cancelOrderResultDTO;
        }
        PaymentCardSettlementDTO refundSettlementDTOs = createRefundSettlement(getPaymentCardSettlementFromSettlementDTOs(orderDTO.getId()),
                        refundOrderDTO.getId());
        if (null == refundSettlementDTOs) {
            cancelOrderResultDTO.setResult(CancelOrderResult.UNABLE_TO_CREATE_OR_UPDATE_REFUND_SETTLEMENT.name());
            cancelOrderResultDTO.setMessage(getContent(CancelOrderResult.UNABLE_TO_CREATE_OR_UPDATE_REFUND_SETTLEMENT.contentCode()));
            return cancelOrderResultDTO;
        }

        createRefundAdHocLoadSettlements(adHocLoadSettlementDTOs, refundOrderDTO);

        cancelOrderResultDTO.setId(refundOrderDTO.getId());
        return cancelOrderResultDTO;
    }

    protected List<AdHocLoadSettlementDTO> createRefundAdHocLoadSettlements(List<AdHocLoadSettlementDTO> originalAdHocLoadSettlementDTOs,
                    OrderDTO orderDTO) {
        ArrayList<AdHocLoadSettlementDTO> refundAdHocLoadSettlementDTOs = new ArrayList<AdHocLoadSettlementDTO>();
        for (AdHocLoadSettlementDTO adHocLoadSettlementDTO : originalAdHocLoadSettlementDTOs) {
            if (hasAdhocLoadRequestBeenRegisteredInCubic(adHocLoadSettlementDTO.getRequestSequenceNumber())) {
                refundAdHocLoadSettlementDTOs.add(createRefundAdHocLoadSettlement(adHocLoadSettlementDTO, orderDTO));
            }
        }
        return refundAdHocLoadSettlementDTOs;
    }

    protected AdHocLoadSettlementDTO createRefundAdHocLoadSettlement(AdHocLoadSettlementDTO originalAdHocLoadSettlementDTO, OrderDTO orderDTO) {
        AdHocLoadSettlementDTO refundAdHocLoadSettlementDTO = new AdHocLoadSettlementDTO();
        refundAdHocLoadSettlementDTO.setItem(originalAdHocLoadSettlementDTO.getItem());
        refundAdHocLoadSettlementDTO.setRequestSequenceNumber(originalAdHocLoadSettlementDTO.getRequestSequenceNumber());
        refundAdHocLoadSettlementDTO.setOrderId(orderDTO.getId());
        refundAdHocLoadSettlementDTO.setAmount(-originalAdHocLoadSettlementDTO.getAmount());
        refundAdHocLoadSettlementDTO.setStatus(SettlementStatus.COMPLETE.code());
        refundAdHocLoadSettlementDTO.setVersion(originalAdHocLoadSettlementDTO.getVersion());
        refundAdHocLoadSettlementDTO.setSettlementDate(new Date());
        return adhocLoadSettlementDataService.createOrUpdate(refundAdHocLoadSettlementDTO);
    }

    protected PaymentCardSettlementDTO getPaymentCardSettlementFromSettlementDTOs(Long orderId) {
        List<PaymentCardSettlementDTO> paymentCardSettlementDTO = paymentCardSettlementDataService.findByOrderId(orderId);
        return (paymentCardSettlementDTO != null && paymentCardSettlementDTO.size() > 0) ? paymentCardSettlementDTO.get(0) : null;
    }

    protected CancelOrderResultDTO updateRefundOrderAndSettlement(OrderDTO orderDTO) {
        CancelOrderResultDTO cancelOrderResultDTO = new CancelOrderResultDTO(CancelOrderResult.SUCCESS.name(),
                        getContent(CancelOrderResult.SUCCESS.contentCode()));
        OrderDTO refundOrderDTO = orderDataService.createOrUpdate(orderDTO);
        if (null == refundOrderDTO || null == refundOrderDTO.getId() || refundOrderDTO.getId() <= 0) {
            cancelOrderResultDTO.setResult(CancelOrderResult.UNABLE_TO_CREATE_OR_UPDATE_REFUND_ORDER.name());
            cancelOrderResultDTO.setMessage(getContent(CancelOrderResult.UNABLE_TO_CREATE_OR_UPDATE_REFUND_ORDER.contentCode()));
            return cancelOrderResultDTO;
        }
        PaymentCardSettlementDTO paymentCardSettlementDTO = getPaymentCardSettlementFromSettlementDTOs(refundOrderDTO.getId());
        paymentCardSettlementDTO.setStatus(SettlementStatus.COMPLETE.code());
        SettlementDTO refundSettlementDTO = createOrUpdateRefundSettlement(paymentCardSettlementDTO);
        if (null == refundSettlementDTO) {
            cancelOrderResultDTO.setResult(CancelOrderResult.UNABLE_TO_CREATE_OR_UPDATE_REFUND_SETTLEMENT.name());
            cancelOrderResultDTO.setMessage(getContent(CancelOrderResult.UNABLE_TO_CREATE_OR_UPDATE_REFUND_SETTLEMENT.contentCode()));
            return cancelOrderResultDTO;
        }

        cancelOrderResultDTO.setId(refundOrderDTO.getId());
        return cancelOrderResultDTO;
    }

    protected OrderDTO updateRefundOrder(OrderDTO orderDTO) {
        orderDTO.setStatus(OrderStatus.CANCEL_REQUESTED.code());
        orderDTO.setRefundDate(new Date());
        return orderDataService.createOrUpdate(orderDTO);
    }

    protected PaymentCardSettlementDTO createRefundSettlement(PaymentCardSettlementDTO paymentCardSettlementDTO, Long refundOrderId) {
        assert (paymentCardSettlementDTO != null);
        PaymentCardSettlementDTO refundPaymentSettlementDTO = new PaymentCardSettlementDTO();
        refundPaymentSettlementDTO.setAmount(-paymentCardSettlementDTO.getAmount());
        refundPaymentSettlementDTO.setOrderId(refundOrderId);
        refundPaymentSettlementDTO.setStatus(SettlementStatus.NEW.code());
        refundPaymentSettlementDTO.setVersion(paymentCardSettlementDTO.getVersion());
        refundPaymentSettlementDTO.setSettlementDate(new Date());
        return createOrUpdateRefundSettlement(refundPaymentSettlementDTO);
    }

    protected PaymentCardSettlementDTO createOrUpdateRefundSettlement(PaymentCardSettlementDTO paymentCardSettlementDTO) {
        return paymentCardSettlementDataService.createOrUpdate(paymentCardSettlementDTO);
    }

}
