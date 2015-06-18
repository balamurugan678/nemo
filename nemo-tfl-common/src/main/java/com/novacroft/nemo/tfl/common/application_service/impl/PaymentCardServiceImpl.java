package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.tfl.common.util.cyber_source.CyberSourceAddressUtil.extractCountry;
import static com.novacroft.nemo.tfl.common.util.cyber_source.CyberSourceAddressUtil.extractHouseNameNumber;
import static com.novacroft.nemo.tfl.common.util.cyber_source.CyberSourceAddressUtil.extractPostCode;
import static com.novacroft.nemo.tfl.common.util.cyber_source.CyberSourceAddressUtil.extractStreet;
import static com.novacroft.nemo.tfl.common.util.cyber_source.CyberSourceAddressUtil.extractTown;
import static com.novacroft.nemo.tfl.common.util.cyber_source.CyberSourceUtil.isCreatePaymentToken;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.application_service.PaymentCardService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.ManagePaymentCardCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PaymentCardCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourceDecision;
import com.novacroft.nemo.tfl.common.data_service.AutoLoadChangeSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.cyber_source.CyberSourceTransactionDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceAddressDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourcePostReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapRequestDTO;
import com.novacroft.nemo.tfl.common.util.SettlementUtil;

/**
 * Payment Card service
 */
@Service("paymentCardService")
@Transactional(readOnly = true)
public class PaymentCardServiceImpl implements PaymentCardService {
    protected static final Logger logger = LoggerFactory.getLogger(PaymentCardServiceImpl.class);
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected PaymentCardDataService paymentCardDataService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected AutoLoadChangeSettlementDataService autoLoadChangeSettlementDataService;
    @Autowired
    protected AddressDataService addressDataService;
    @Autowired
    protected PaymentCardSettlementDataService paymentCardSettlementDataService;
    @Autowired
    protected CyberSourceTransactionDataService cyberSourceTransactionDataService;
    @Autowired
    protected CountryDataService countryDataService;

    @Override
    @Transactional
    public void createPaymentCardOnTokenRequest(CartCmdImpl cmd) {
        if (isCreateToken(cmd)) {
            PaymentCardDTO paymentCardDTO = createTokenisedPaymentCard(cmd);
            updateSettlementWithPaymentCard(cmd.getCartDTO().getOrder(), paymentCardDTO);
        }
    }

    @Override
    public ManagePaymentCardCmdImpl getPaymentCards(Long customerId) {
        ManagePaymentCardCmdImpl cmd = new ManagePaymentCardCmdImpl(new ArrayList<PaymentCardCmdImpl>());
        for (PaymentCardDTO paymentCardDTO : this.paymentCardDataService.findByCustomerId(customerId)) {
            if (null != paymentCardDTO.getAddressId()) {
                paymentCardDTO.setAddressDTO(this.addressDataService.findById(paymentCardDTO.getAddressId()));
            }
            cmd.getPaymentCards().add(new PaymentCardCmdImpl(paymentCardDTO, isCardInUse(paymentCardDTO.getId())));
        }
        return cmd;
    }

    @Override
    @Transactional
    public ManagePaymentCardCmdImpl updatePaymentCards(Long customerId, ManagePaymentCardCmdImpl cmd) {
        if (CollectionUtils.isNotEmpty(cmd.getPaymentCards())) {
            for (PaymentCardCmdImpl paymentCardCmd : cmd.getPaymentCards()) {
                updatePaymentCard(paymentCardCmd);
            }
        }
        return getPaymentCards(customerId);
    }

    @Override
    public Boolean isCardInUse(Long paymentCardId) {
        List<CardDTO> cards = this.cardDataService.findByPaymentCardId(paymentCardId);
        return (isPaymentCardLinkedToCard(cards) && (isAutoLoadOnInCubic(cards) || isAutoLoadRequested(cards)));
    }

    @Override
    @Transactional
    public void linkPaymentCardToCardOnAutoLoadOrder(OrderDTO orderDTO) {
        if (isAutoLoadRequested(orderDTO)) {
            for (PaymentCardSettlementDTO paymentCardSettlementDTO : this.paymentCardSettlementDataService
                    .findByOrderId(orderDTO.getId())) {
                linkPaymentCardToCard(orderDTO,
                        this.paymentCardDataService.findById(paymentCardSettlementDTO.getPaymentCardId()));
            }
        }
    }

    @Override
    @Transactional
    public void updateSettlementWithPaymentCard(OrderDTO orderDTO, Long paymentCardId) {
        updateSettlementWithPaymentCard(orderDTO, this.paymentCardDataService.findById(paymentCardId));
    }

    @Override
    public PaymentCardCmdImpl getPaymentCard(Long paymentCardId) {
        PaymentCardDTO paymentCardDTO = this.paymentCardDataService.findById(paymentCardId);
        if (null != paymentCardDTO.getAddressId()) {
            paymentCardDTO.setAddressDTO(this.addressDataService.findById(paymentCardDTO.getAddressId()));
        }
        return new PaymentCardCmdImpl(paymentCardDTO, isCardInUse(paymentCardDTO.getId()));
    }

    @Override
    @Transactional
    public void updatePaymentCard(PaymentCardCmdImpl paymentCardCmd) {
        AddressDTO addressdto = paymentCardCmd.getPaymentCardDTO().getAddressDTO();
        addressdto.setId(paymentCardCmd.getPaymentCardDTO().getAddressId());
        this.addressDataService.createOrUpdate(addressdto);
        this.paymentCardDataService.createOrUpdate(paymentCardCmd.getPaymentCardDTO());
    }

    @Override
    @Transactional
    public void deletePaymentCard(PaymentCardCmdImpl paymentCardCmd) {
        AddressDTO addressdto = paymentCardCmd.getPaymentCardDTO().getAddressDTO();
        this.paymentCardDataService.delete(paymentCardCmd.getPaymentCardDTO());
        this.addressDataService.delete(addressdto);
        deleteCyberSourceToken(paymentCardCmd);
    }

    protected void deleteCyberSourceToken(PaymentCardCmdImpl paymentCardCmd) {
        CyberSourceSoapRequestDTO cyberSourceSoapRequestDTO = prepareDeleteTokenRequest(paymentCardCmd);
        CyberSourceSoapReplyDTO cyberSourceSoapReplyDTO =
                this.cyberSourceTransactionDataService.deleteToken(cyberSourceSoapRequestDTO);
        if (isNotSuccessReply(cyberSourceSoapReplyDTO)) {
            logNotSuccessReply(cyberSourceSoapRequestDTO, cyberSourceSoapReplyDTO);
        }
    }

    protected CyberSourceSoapRequestDTO prepareDeleteTokenRequest(PaymentCardCmdImpl paymentCardCmd) {
        return new CyberSourceSoapRequestDTO(paymentCardCmd.getPaymentCardDTO().getToken(),
                paymentCardCmd.getPaymentCardDTO().getCustomerId(), paymentCardCmd.getPaymentCardDTO().getReferenceCode());
    }

    protected boolean isNotSuccessReply(CyberSourceSoapReplyDTO cyberSourceSoapReplyDTO) {
        return !isSuccessReply(cyberSourceSoapReplyDTO);
    }

    protected boolean isSuccessReply(CyberSourceSoapReplyDTO cyberSourceSoapReplyDTO) {
        return CyberSourceDecision.ACCEPT.code().equals(cyberSourceSoapReplyDTO.getDecision());
    }

    protected void logNotSuccessReply(CyberSourceSoapRequestDTO cyberSourceSoapRequestDTO,
                                      CyberSourceSoapReplyDTO cyberSourceSoapReplyDTO) {
        logger.error(String.format(PrivateError.CYBER_SOURCE_PAYMENT_CARD_TOKEN_ERROR.message(), "delete",
                cyberSourceSoapRequestDTO.toString(), cyberSourceSoapReplyDTO.toString()));
    }

    protected void linkPaymentCardToCard(OrderDTO orderDTO, PaymentCardDTO paymentCardDTO) {
        for (AutoLoadChangeSettlementDTO autoLoadChangeSettlementDTO : this.autoLoadChangeSettlementDataService
                .findByOrderId(orderDTO.getId())) {
            linkPaymentCardToCard(autoLoadChangeSettlementDTO, paymentCardDTO);
        }
    }

    protected void linkPaymentCardToCard(AutoLoadChangeSettlementDTO autoLoadChangeSettlementDTO,
                                         PaymentCardDTO paymentCardDTO) {
        if (isAutoLoadOn(autoLoadChangeSettlementDTO)) {
            CardDTO cardDTO = this.cardDataService.findById(autoLoadChangeSettlementDTO.getCardId());
            cardDTO.setPaymentCardId(paymentCardDTO.getId());
            this.cardDataService.createOrUpdate(cardDTO);
        }
    }

    protected boolean isAutoLoadOn(AutoLoadChangeSettlementDTO autoLoadChangeSettlementDTO) {
        return SettlementUtil.isAutoLoadOn(autoLoadChangeSettlementDTO);
    }

    protected boolean isAutoLoadRequested(OrderDTO orderDTO) {
        for (AutoLoadChangeSettlementDTO autoLoadChangeSettlementDTO : this.autoLoadChangeSettlementDataService
                .findByOrderId(orderDTO.getId())) {
            if (isAutoLoadRequested(autoLoadChangeSettlementDTO)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isPaymentCardLinkedToCard(List<CardDTO> cards) {
        return !cards.isEmpty();
    }

    protected boolean isAutoLoadOnInCubic(List<CardDTO> cards) {
        for (CardDTO cardDTO : cards) {
            CardInfoResponseV2DTO response = this.getCardService.getCard(cardDTO.getCardNumber());
            if (isAutoLoadOn(response.getAutoLoadState())) {
                return true;
            }
        }
        return false;
    }

    protected boolean isAutoLoadOn(Integer autoLoadState) {
        return SettlementUtil.isAutoLoadOn(autoLoadState);
    }

    protected boolean isAutoLoadRequested(List<CardDTO> cards) {
        for (CardDTO cardDTO : cards) {
            if (isAutoLoadRequested(cardDTO)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isAutoLoadRequested(CardDTO cardDTO) {
        return isAutoLoadRequested(this.autoLoadChangeSettlementDataService.findLatestByCardId(cardDTO.getId()));
    }

    protected boolean isAutoLoadRequested(AutoLoadChangeSettlementDTO autoLoadChangeSettlementDTO) {
        return SettlementUtil.isRequested(autoLoadChangeSettlementDTO) &&
                SettlementUtil.isAutoLoadOn(autoLoadChangeSettlementDTO);
    }

    protected boolean isCreateToken(CartCmdImpl cmd) {
        if (cmd.getCartDTO().getCyberSourceReply() instanceof CyberSourcePostReplyDTO) {
            CyberSourcePostReplyDTO cyberSourcePostReply = (CyberSourcePostReplyDTO) cmd.getCartDTO().getCyberSourceReply();
            return isCreatePaymentToken(cyberSourcePostReply.getRequestTransactionType());
        }
        return false;
    }

    protected PaymentCardDTO createTokenisedPaymentCard(CartCmdImpl cmd) {
        assert (cmd.getCustomerId() != null);
        CyberSourcePostReplyDTO cyberSourcePostReply = (CyberSourcePostReplyDTO) cmd.getCartDTO().getCyberSourceReply();

        AddressDTO addressDTO = createAddressForPaymentCard(cyberSourcePostReply);

        PaymentCardDTO paymentCardDTO = new PaymentCardDTO(cmd.getCustomerId(), addressDTO.getId(),
                cyberSourcePostReply.getRequestCardNumber(), cyberSourcePostReply.getRequestCardExpiryDate(),
                cyberSourcePostReply.getRequestBillToForename(), cyberSourcePostReply.getRequestBillToSurname(),
                cyberSourcePostReply.getPaymentToken(), cyberSourcePostReply.getReqReferenceNumber());
        return this.paymentCardDataService.createOrUpdate(paymentCardDTO);
    }

    protected AddressDTO createAddressForPaymentCard(CyberSourcePostReplyDTO cyberSourcePostReply) {
        CyberSourceAddressDTO cyberSourceAddressDTO =
                new CyberSourceAddressDTO(cyberSourcePostReply.getRequestBillToAddressLine1(),
                        cyberSourcePostReply.getRequestBillToAddressLine2(), cyberSourcePostReply.getRequestBillToAddressCity(),
                        cyberSourcePostReply.getRequestBillToAddressPostCode(),
                        cyberSourcePostReply.getRequestBillToAddressCountry());
        return this.addressDataService.createOrUpdate(
                new AddressDTO(extractHouseNameNumber(cyberSourceAddressDTO), extractStreet(cyberSourceAddressDTO),
                        extractTown(cyberSourceAddressDTO), extractPostCode(cyberSourceAddressDTO),
                        countryDataService.findCountryByCode(extractCountry(cyberSourceAddressDTO))));
    }

    protected void updateSettlementWithPaymentCard(OrderDTO orderDTO, PaymentCardDTO paymentCardDTO) {
        for (PaymentCardSettlementDTO paymentCardSettlementDTO : this.paymentCardSettlementDataService
                .findByOrderId(orderDTO.getId())) {
            paymentCardSettlementDTO.setPaymentCardId(paymentCardDTO.getId());
            this.paymentCardSettlementDataService.createOrUpdate(paymentCardSettlementDTO);
        }
    }
}
