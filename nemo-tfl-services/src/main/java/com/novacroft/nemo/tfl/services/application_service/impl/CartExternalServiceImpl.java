package com.novacroft.nemo.tfl.services.application_service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.exception.ApplicationServiceStaleDataException;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.PaymentService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.form_validator.PickUpLocationValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.services.application_service.CartExternalService;
import com.novacroft.nemo.tfl.services.application_service.ItemExternalService;
import com.novacroft.nemo.tfl.services.constant.WebServiceResultAttribute;
import com.novacroft.nemo.tfl.services.converter.CartConverter;
import com.novacroft.nemo.tfl.services.transfer.Cart;
import com.novacroft.nemo.tfl.services.transfer.CheckoutResult;
import com.novacroft.nemo.tfl.services.transfer.Error;
import com.novacroft.nemo.tfl.services.transfer.ErrorResult;
import com.novacroft.nemo.tfl.services.transfer.Item;
import com.novacroft.nemo.tfl.services.transfer.ListResult;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;
import com.novacroft.nemo.tfl.services.util.ErrorUtil;

@Component("cartExternalService")
public class CartExternalServiceImpl extends BaseService implements CartExternalService {

    static final Logger logger = LoggerFactory.getLogger(CartExternalServiceImpl.class);

    @Autowired
    protected CartService cartService;

    @Autowired
    protected CardDataService cardDataService;

    @Autowired
    protected CartDataService cartDataService;

    @Autowired
    protected CartConverter cartConverter;

    @Autowired
    protected CustomerDataService customerDataService;

    @Autowired
    protected ItemExternalService itemExternalService;

    @Autowired
    protected PaymentService paymentService;

    @Autowired
    protected PickUpLocationValidator pickUpLocationValidator;

    @Autowired
    protected CardService cardService;

    @Autowired
    protected OrderDataService orderDataService;

    @Autowired
    protected PaymentCardSettlementDataService paymentCardSettlementDataService;

    @Override
    public Cart createCart(Long externalCardId) {
        Cart cart = new Cart();
        try {
            CardDTO cardDTO = null;
            try {
                cardDTO = cardDataService.findByExternalId(externalCardId);
            } catch (Exception e) {
                cart.setErrors(ErrorUtil.addErrorToList(new ErrorResult(), new Error(null, null, WebServiceResultAttribute.RECORD_NOT_FOUND.name())));
                return cart;
            }
            CartDTO cartDTO = cartService.createCartFromCardId(cardDTO.getId());
            if (cartItemsExist(cartDTO)) {
                cartDTO = cartService.removeExpiredCartItems(cartDTO);
            }
            cart = cartConverter.convert(cartDTO);
        } catch (ApplicationServiceStaleDataException staleDataException) {
            logger.error(PrivateError.STALE_DATA_ERROR.message(), staleDataException);
            cart.setErrors(ErrorUtil.addErrorToList(new ErrorResult(), new Error(null, null, WebServiceResultAttribute.CART_DATA_HAS_EXPIRED.name())));
        } catch (Exception e) {
            logger.error(PrivateError.UNEXPECTED.message(), e);
            cart.setErrors(ErrorUtil.addErrorToList(new ErrorResult(), new Error(null, null, WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.name())));
        }
        return cart;
    }

    protected boolean cartItemsExist(CartDTO cartDTO) {
        return (null != cartDTO && null != cartDTO.getCartItems() && !cartDTO.getCartItems().isEmpty()) ;
    }

    @Override
    public ListResult<Cart> getCartListByCustomerId(Long externalCustomerId) {
        ListResult<Cart> response = new ListResult<Cart>();
        List<Cart> cartList = new ArrayList<Cart>();
        CustomerDTO customer;
        try {
            customer = customerDataService.findByExternalId(externalCustomerId);
        } catch (Exception e) {
            ErrorResult errors = ErrorUtil
                            .addErrorToList(new ErrorResult(), new Error(null, null, WebServiceResultAttribute.RECORD_NOT_FOUND.name()));
            response.setErrors(errors);
            response.setResultList(cartList);
            return response;
        }
        try {
            List<Long> cartIdList = cartDataService.findCartListByCustomerId(customer.getId());
            for (Long cartId : cartIdList) {
                CartDTO cartDTO = cartService.findById(cartId);
                cartList.add(cartConverter.convert(cartDTO));
            }
        } catch (Exception e) {
            ErrorResult errors = ErrorUtil
                            .addErrorToList(new ErrorResult(), new Error(null, null, WebServiceResultAttribute.RECORD_NOT_FOUND.name()));
            response.setErrors(errors);
        }
        response.setResultList(cartList);
        return response;
    }

    @Override
    public Cart retrieveCart(Long cartId) {
        Cart cart = new Cart();
        try {
            CartDTO cartDTO = null;
            try {
                cartDTO = cartDataService.findByExternalId(cartId);
                if (cartItemsExist(cartDTO)) {
                    cartDTO = cartService.removeExpiredCartItems(cartDTO);
                }
            } catch (ApplicationServiceStaleDataException staleDataException) {
                logger.error(PrivateError.STALE_DATA_ERROR.message(), staleDataException);
                cart.setErrors(ErrorUtil.addErrorToList(new ErrorResult(), new Error(null, null, WebServiceResultAttribute.CART_DATA_HAS_EXPIRED.name())));
                return cart;
            } catch (Exception e) {
                cart.setErrors(ErrorUtil.addErrorToList(new ErrorResult(), new Error(null, null, WebServiceResultAttribute.RECORD_NOT_FOUND.name())));
                return cart;
            }
            cart = cartConverter.convert(cartDTO);
        } catch (Exception e) {
            cart.setErrors(ErrorUtil.addErrorToList(new ErrorResult(), new Error(null, null, WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.name())));
        }
        return cart;
    }

    @Override
    public WebServiceResult deleteCart(Long externalCartId, Long externalCustomerId) {
        WebServiceResult result = new WebServiceResult();
        result.setId(externalCartId);
        if (null == externalCartId || null == externalCustomerId) {
            logger.error(String.format(PrivateError.CART_DETAILS_NOT_FOUND_FOR_CUSTOMER_AND_CART.message(), externalCustomerId, externalCartId));
            addErrorMessageToResult(result, WebServiceResultAttribute.INVALID_NULL_PARAMETER.name(),
                            getContent(WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode()));
            return result;
        }

        try {
            CartDTO cartDTO = cartDataService.findByExternalId(externalCartId);

            if (isCartForCustomer(externalCustomerId, result, cartDTO)) {
                try {
                    cartService.deleteCart(cartDTO.getId());
                    result.setResult(WebServiceResultAttribute.SUCCESS.name());
                    result.setMessage(null);
                } catch (ApplicationServiceStaleDataException staleDataException) {
                    logger.error(PrivateError.STALE_DATA_ERROR.message(), staleDataException);
                    addErrorMessageToResult(result, WebServiceResultAttribute.CART_DATA_HAS_EXPIRED.name(), getContent(WebServiceResultAttribute.CART_DATA_HAS_EXPIRED.contentCode()));
                } catch (Exception deleteException) {
                    logger.error(String.format(PrivateError.CART_NOT_DELETED_FOR_CUSTOMER_AND_CART.message(), externalCustomerId, externalCartId),
                                    deleteException);
                    addErrorMessageToResult(result, WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.name(),
                                    getContent(WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.contentCode()));
                }
            }

        } catch (Exception lookupException) {
            logger.error(String.format(PrivateError.CART_DETAILS_NOT_FOUND_FOR_CUSTOMER_AND_CART.message(), externalCustomerId, externalCartId),
                            lookupException);
            addErrorMessageToResult(result, WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name(),
                            getContent(WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode()));
        }

        return result;
    }

    protected boolean isCartForCustomer(Long externalCustomerId, WebServiceResult result, CartDTO cartDTO) {
        boolean customerBelongsToCustomer = true;
        try {

            CustomerDTO customerDTO = lookupCustomerDTO(cartDTO);
            if (!customerDTO.getExternalId().equals(externalCustomerId)) {
                addErrorMessageToResult(result, WebServiceResultAttribute.RECORD_NOT_FOUND.name(),
                                getContent(WebServiceResultAttribute.RECORD_NOT_FOUND.contentCode()));
                customerBelongsToCustomer = false;
            }

        } catch (Exception customerLookupException) {
            logger.error(String.format(PrivateError.CART_DETAILS_NOT_FOUND_FOR_CUSTOMER_AND_CART.message(), externalCustomerId, cartDTO.getId()),
                            customerLookupException);
            addErrorMessageToResult(result, WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name(),
                            getContent(WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode()));
            customerBelongsToCustomer = false;
        }
        return customerBelongsToCustomer;
    }

    protected void addErrorMessageToResult(WebServiceResult result, String resultCode, String externalErrorMessage) {
        result.setResult(resultCode);
        result.setMessage(externalErrorMessage);
    }

    protected boolean resultHasErrorMessage(WebServiceResult result) {
        return StringUtils.isNotBlank(result.getMessage());
    }

    @Override
    public CheckoutResult beginCheckout(Long externalCartId, Long stationId) {
        CheckoutResult result = new CheckoutResult();
        result.setId(externalCartId);

        if (null == externalCartId || null == stationId) {
            logger.error(String.format(PrivateError.INVALID_INPUT_PARAMETER.message()));
            addErrorMessageToResult(result, WebServiceResultAttribute.INVALID_NULL_PARAMETER.name(),
                            getContent(WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode()));
            return result;
        }

        try {
            CartDTO cartDTO = cartDataService.findByExternalId(externalCartId);

            CartCmdImpl cmd = createCartCmdForBeginCheckout(cartDTO, stationId);
            validateBeginCheckoutInputs(cmd, result);

            if (!resultHasErrorMessage(result)) {
                try {
                    createOrderAndSettlement(cartDTO, cmd, result);
                    result.setResult(WebServiceResultAttribute.SUCCESS.name());
                    result.setMessage(null);
                } catch (Exception createOrderSettlementException) {
                    logger.error(String.format(PrivateError.CHECKOUT_CREATE_ORDER_SETTLEMENT_NOT_COMPLETED.message(), externalCartId, stationId),
                                    createOrderSettlementException);
                    addErrorMessageToResult(result, WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.name(),
                                    getContent(WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.contentCode()));
                }
            }

        } catch (Exception lookupException) {
            logger.error(String.format(PrivateError.CART_DETAILS_NOT_FOUND_FOR_CART.message(), externalCartId, stationId), lookupException);
            addErrorMessageToResult(result, WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name(),
                            getContent(WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode()));
        }

        return result;
    }

    protected void validateBeginCheckoutInputs(CartCmdImpl cmd, CheckoutResult result) {
        Errors errors = new BeanPropertyBindingResult(cmd, CartCmdImpl.class.getName());
        pickUpLocationValidator.validate(cmd, errors);
        if (errors.hasErrors()) {
            String resultCode = WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.name();
            String errorMessageCode = WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.contentCode();
            ErrorResult errorResult = ErrorUtil.addValidationErrorsToList(null, errors.getAllErrors());
            if (ErrorUtil.hasErrors(errorResult)) {
                resultCode = errorResult.getErrors().get(0).getDescription();
                ContentCode contentCodeEnum = ContentCode.valueOf(errorResult.getErrors().get(0).getDescription());
                if (null != contentCodeEnum) {
                    errorMessageCode = contentCodeEnum.errorCode();
                }
            }
            addErrorMessageToResult(result, resultCode, getContent(errorMessageCode));
        }
    }

    protected void createOrderAndSettlement(CartDTO cartDTO, CartCmdImpl cmd, CheckoutResult result) {
        paymentService.createOrderAndSettlementsFromCart(cartDTO, cmd);
        result.setOrderId(cartDTO.getOrder().getExternalId());
        result.setPaymentSettlementId(cartDTO.getPaymentCardSettlement().getExternalId());
    }

    protected CartCmdImpl createCartCmdForBeginCheckout(CartDTO cartDTO, Long stationId) {
        CartCmdImpl cmd = new CartCmdImpl();
        cmd.setCartId(cartDTO.getId());
        cmd.setCardId(cartDTO.getCardId());
        cmd.setCustomerId(determineCustomerId(cartDTO));
        cmd.setStationId(stationId);
        cmd.setTotalAmt(cartDTO.getCartTotal());
        cmd.setToPayAmount(cartDTO.getCartTotal());
        cmd.setAutoTopUpAmount(cartDTO.getAutoTopUpAmount());
        return cmd;
    }

    protected Long determineCustomerId(CartDTO cartDTO) {
        Long customerId = cartDTO.getCustomerId();
        if (null == customerId) {
            CardDTO card = cardService.getCardDTOById(cartDTO.getCardId());
            customerId = card.getCustomerId();
        }
        return customerId;
    }

    protected CustomerDTO lookupCustomerDTO(CartDTO cartDTO) {
        Long customerId = determineCustomerId(cartDTO);
        return customerDataService.findById(customerId);
    }

    @Override
    public CheckoutResult authoriseCheckoutPayment(Long externalCartId, Long externalOrderId, Long externalPaymentCardSettlementId,
                    String paymentAuthorisationReference) {
        CheckoutResult result = new CheckoutResult();
        result.setId(externalCartId);
        if (null == externalCartId || null == externalOrderId || null == externalPaymentCardSettlementId
                        || StringUtils.isBlank(paymentAuthorisationReference)) {
            logger.error(String.format(PrivateError.CART_CHECKOUT_AUTHORISATION_ERROR.message(), externalCartId, externalOrderId,
                            externalPaymentCardSettlementId, paymentAuthorisationReference));
            addErrorMessageToResult(result, WebServiceResultAttribute.INVALID_NULL_PARAMETER.name(),
                            getContent(WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode()));
            return result;
        }

        try {

            cartDataService.findByExternalId(externalCartId);
            List<PaymentCardSettlementDTO> orderCardSettlements = paymentCardSettlementDataService.findByOrderId(orderDataService
                            .getInternalIdFromExternalId(externalOrderId));
            try {
                PaymentCardSettlementDTO paymentCardSettlmentDTO = getPaymentCardSettlementDTO(orderCardSettlements, externalPaymentCardSettlementId);
                if (null != paymentCardSettlmentDTO) {
                    paymentCardSettlmentDTO.setAuthorisationTransactionReferenceNumber(paymentAuthorisationReference);
                    paymentCardSettlementDataService.createOrUpdate(paymentCardSettlmentDTO);
                    result.setResult(WebServiceResultAttribute.SUCCESS.name());
                    result.setMessage(null);
                } else {
                    addErrorMessageToResult(result, WebServiceResultAttribute.RECORD_NOT_FOUND.name(),
                                    getContent(WebServiceResultAttribute.RECORD_NOT_FOUND.contentCode()));
                }
            } catch (Exception updatePaymentSettlementException) {
                logger.error(String.format(PrivateError.CART_CHECKOUT_AUTHORISATION_ERROR.message(), externalCartId, externalOrderId,
                                externalPaymentCardSettlementId, paymentAuthorisationReference), updatePaymentSettlementException);
                addErrorMessageToResult(result, WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.name(),
                                getContent(WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.contentCode()));
            }

        } catch (Exception lookupException) {
            logger.error(String.format(PrivateError.CART_CHECKOUT_AUTHORISATION_ERROR.message(), externalCartId, externalOrderId,
                            externalPaymentCardSettlementId, paymentAuthorisationReference), lookupException);
            addErrorMessageToResult(result, WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name(),
                            getContent(WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode()));
        }

        return result;
    }

    protected PaymentCardSettlementDTO getPaymentCardSettlementDTO(List<PaymentCardSettlementDTO> orderCardSettlements,
                    Long externalPaymentCardSettlementId) {
        PaymentCardSettlementDTO matchedSettlementRecord = null;

        if (null != orderCardSettlements && !orderCardSettlements.isEmpty()) {
            for (PaymentCardSettlementDTO paymentCardSettlementDTO : orderCardSettlements) {
                if (externalPaymentCardSettlementId.equals(paymentCardSettlementDTO.getExternalId())) {
                    matchedSettlementRecord = paymentCardSettlementDTO;
                    break;
                }
            }
        }
        return matchedSettlementRecord;
    }

    @Override
    public CheckoutResult updatePaymentCardSettlementStatusToFailed(Long externalCartId, Long externalOrderId, Long externalPaymentCardSettlementId) {
        CheckoutResult result = new CheckoutResult();
        result.setId(externalCartId);
        if (null == externalCartId || null == externalOrderId || null == externalPaymentCardSettlementId) {
            logger.error(String.format(PrivateError.CART_CHECKOUT_AUTHORISATION_ERROR.message(), externalCartId, externalOrderId,
                            externalPaymentCardSettlementId, ""));
            addErrorMessageToResult(result, WebServiceResultAttribute.INVALID_NULL_PARAMETER.name(),
                            getContent(WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode()));
            return result;
        }

        try {

            cartDataService.findByExternalId(externalCartId);
            List<PaymentCardSettlementDTO> orderCardSettlements = paymentCardSettlementDataService.findByOrderId(orderDataService
                            .getInternalIdFromExternalId(externalOrderId));
            try {
                PaymentCardSettlementDTO paymentCardSettlmentDTO = getPaymentCardSettlementDTO(orderCardSettlements, externalPaymentCardSettlementId);
                if (null != paymentCardSettlmentDTO) {
                    paymentCardSettlmentDTO.setStatus(SettlementStatus.FAILED.code());
                    paymentCardSettlementDataService.createOrUpdate(paymentCardSettlmentDTO);
                    result.setResult(WebServiceResultAttribute.SUCCESS.name());
                    result.setMessage(null);
                } else {
                    addErrorMessageToResult(result, WebServiceResultAttribute.RECORD_NOT_FOUND.name(),
                                    getContent(WebServiceResultAttribute.RECORD_NOT_FOUND.contentCode()));
                }
            } catch (Exception updatePaymentSettlementException) {
                logger.error(String.format(PrivateError.CART_CHECKOUT_AUTHORISATION_ERROR.message(), externalCartId, externalOrderId,
                                externalPaymentCardSettlementId, ""), updatePaymentSettlementException);
                addErrorMessageToResult(result, WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.name(),
                                getContent(WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.contentCode()));
            }

        } catch (Exception lookupException) {
            logger.error(String.format(PrivateError.CART_CHECKOUT_AUTHORISATION_ERROR.message(), externalCartId, externalOrderId,
                            externalPaymentCardSettlementId, ""), lookupException);
            addErrorMessageToResult(result, WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name(),
                            getContent(WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode()));
        }

        return result;
    }

    @Override
    public CheckoutResult completeCheckout(Long externalCartId, Long stationId, Long externalOrderId, Long externalPaymentCardSettlementId) {
        CheckoutResult result = new CheckoutResult();
        result.setId(externalCartId);
        if (null == externalCartId || null == stationId || null == externalOrderId || null == externalPaymentCardSettlementId) {
            logger.error(String.format(PrivateError.CART_CHECKOUT_COMPLETION_ERROR.message(), externalCartId, stationId, externalOrderId,
                            externalPaymentCardSettlementId));
            addErrorMessageToResult(result, WebServiceResultAttribute.INVALID_NULL_PARAMETER.name(),
                            getContent(WebServiceResultAttribute.INVALID_NULL_PARAMETER.contentCode()));
            return result;
        }

        try {

            CartDTO cartDTO = cartDataService.findByExternalId(externalCartId);
            List<PaymentCardSettlementDTO> orderCardSettlements = paymentCardSettlementDataService.findByOrderId(orderDataService
                            .getInternalIdFromExternalId(externalOrderId));
            try {
                PaymentCardSettlementDTO paymentCardSettlmentDTO = getPaymentCardSettlementDTO(orderCardSettlements, externalPaymentCardSettlementId);
                if (null != paymentCardSettlmentDTO) {
                    result.setResult(WebServiceResultAttribute.SUCCESS.name());
                    result.setMessage(null);
                    CartCmdImpl cmd = createCartCmdForCompleteCheckout(cartDTO, stationId, externalOrderId, paymentCardSettlmentDTO);

                    paymentService.processPaymentGatewayReply(cmd);
                    setPaymentStatusToCompleted(cartDTO);

                    result.setResult(WebServiceResultAttribute.SUCCESS.name());
                    result.setMessage(null);
                } else {
                    addErrorMessageToResult(result, WebServiceResultAttribute.RECORD_NOT_FOUND.name(),
                                    getContent(WebServiceResultAttribute.RECORD_NOT_FOUND.contentCode()));
                }
            } catch (ApplicationServiceStaleDataException staleDataException) {
                logger.error(PrivateError.STALE_DATA_ERROR.message(), staleDataException);
                addErrorMessageToResult(result, WebServiceResultAttribute.CART_DATA_HAS_EXPIRED.name(), getContent(WebServiceResultAttribute.CART_DATA_HAS_EXPIRED.contentCode()));
            } catch (Exception checkoutCompleteException) {
                logger.error(String.format(PrivateError.CART_CHECKOUT_COMPLETION_ERROR.message(), externalCartId, stationId, externalOrderId,
                                externalPaymentCardSettlementId), checkoutCompleteException);
                addErrorMessageToResult(result, WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.name(),
                                getContent(WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.contentCode()));
            }

        } catch (Exception lookupException) {
            logger.error(String.format(PrivateError.CART_CHECKOUT_COMPLETION_ERROR.message(), externalCartId, stationId, externalOrderId,
                            externalPaymentCardSettlementId), lookupException);
            addErrorMessageToResult(result, WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name(),
                            getContent(WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode()));
        }

        return result;
    }

    protected void setPaymentStatusToCompleted(CartDTO cartDTO) {
        PaymentCardSettlementDTO settlementDTO = cartDTO.getPaymentCardSettlement();
        settlementDTO.setStatus(SettlementStatus.COMPLETE.code());
        paymentCardSettlementDataService.createOrUpdate(settlementDTO);
    }

    protected CartCmdImpl createCartCmdForCompleteCheckout(CartDTO cartDTO, Long stationId, Long externalOrderId,
                    PaymentCardSettlementDTO paymentCardSettlementDTO) {
        CartCmdImpl cmd = new CartCmdImpl();
        cmd.setCartId(cartDTO.getId());
        cmd.setCardId(cartDTO.getCardId());
        cmd.setCustomerId(determineCustomerId(cartDTO));
        cmd.setStationId(stationId);
        cmd.setTotalAmt(0);
        cmd.setToPayAmount(0);
        cmd.setAutoTopUpAmount(cartDTO.getAutoTopUpAmount());
        cartDTO.setOrder(orderDataService.findByExternalId(externalOrderId));
        cartDTO.setPaymentCardSettlement(paymentCardSettlementDTO);
        cmd.setCartDTO(cartDTO);
        return cmd;
    }

    @Override
    public Cart updateCart(Cart cart, Long cartId) {
        try {
            
            CartDTO cartDTO;
            try {
                cartDTO = cartDataService.findByExternalId(cartId);
                if (hasCartDataExpired(cart, cartDTO)) {
                    cart.setErrors(ErrorUtil.addErrorToList(new ErrorResult(), new Error(null, null, WebServiceResultAttribute.CART_DATA_HAS_EXPIRED.name())));
                    return cart;
                }
            } catch (Exception e) {
                cart.setErrors(ErrorUtil.addErrorToList(new ErrorResult(), new Error(null, null, WebServiceResultAttribute.RECORD_NOT_FOUND.name())));
                return cart;
            }
            List<ItemDTO> originalCartItemsList = new ArrayList<ItemDTO>();
            originalCartItemsList.addAll(cartDTO.getCartItems());
            nullifyExternalIdAndId(originalCartItemsList);
            cartDTO = cartService.emptyCart(cartDTO);
            List<Item> cartItems = cart.getCartItems();
            Integer countItemsToAdd = countItemsToAdd(cartItems);
            addItemsToCart(cartItems, cartDTO, cart, countItemsToAdd);
            cartDTO = cartService.findById(cartDTO.getId());
            if (ErrorUtil.hasErrors(cart.getErrors())) {
                attachOriginalCartItemsListToCartDTO(cartDTO, originalCartItemsList);
                return cart;
            }
            cart = cartConverter.convert(cartDTO);
        } catch (ApplicationServiceStaleDataException staleDataException) {
            logger.error(PrivateError.STALE_DATA_ERROR.message(), staleDataException);
            cart.setErrors(ErrorUtil.addErrorToList(new ErrorResult(), new Error(null, null, WebServiceResultAttribute.CART_DATA_HAS_EXPIRED.name())));
        } catch (Exception e) {
            logger.error(PrivateError.UNEXPECTED.message(), e);
            cart.setErrors(ErrorUtil.addErrorToList(new ErrorResult(), new Error(null, null, WebServiceResultAttribute.OPERATION_NOT_COMPLETED_DUE_TO_EXCEPTION.name())));
        }
        return cart;
    }
    
    protected boolean hasCartDataExpired(Cart cart, CartDTO cartDTO) {
        if (null != cart.getVersion()) {
            return !cart.getVersion().equals(cartDTO.getVersion());
        }
        return false;
    }
        
    protected void addItemsToCart(List<Item> cartItems, CartDTO cartDTO, Cart cart, Integer countItemsToAdd) {
    	Errors errors = null;
    	ErrorResult errorResult = null;
    	for (Item item : cartItems) {
            item.setId(null);
            ProductItemType itemType = ProductItemType.lookUpOptionType(item.getProductType());
            switch (itemType) {
            case TRAVEL_CARD:
                errors = itemExternalService.addTravelCardItemToCart(cartDTO, item, cart,countItemsToAdd);
                break;
            case PAY_AS_YOU_GO:
                errors = itemExternalService.addPayAsYouGoItemToCart(cartDTO, item,countItemsToAdd);
                break;
            case BUS_PASS:
                errors = itemExternalService.addBusPassItemToCart(cartDTO, item, cart,countItemsToAdd);
                break;
            default:
                break;
            }
            if (errors.hasErrors()) {
            	cart.setErrors(ErrorUtil.addValidationErrorsToList(errorResult, errors.getAllErrors()));
                break;
            }
        }
    }

    private Integer countItemsToAdd(List<Item> cartItems) {
    	
    	int count = 0;
    	  for (Item item : cartItems) {
    		  ProductItemType itemType = ProductItemType.lookUpOptionType(item.getProductType());
    		  
    		  if (itemType.equals(ProductItemType.TRAVEL_CARD) || itemType.equals(ProductItemType.PAY_AS_YOU_GO) || itemType.equals(ProductItemType.BUS_PASS)){
    			  count++;
    		  }
    		  
    	  }
              return count;
	}

	protected void attachOriginalCartItemsListToCartDTO(CartDTO cartDTO, List<ItemDTO> originalCartItemsList) {
        cartDTO.getCartItems().clear();
        cartDTO.getCartItems().addAll(originalCartItemsList);
        cartDTO = cartService.updateCart(cartDTO);
    }

    protected void nullifyExternalIdAndId(List<ItemDTO> originalCartItemsList) {
        for (ItemDTO originalItemDTO : originalCartItemsList) {
            originalItemDTO.setId(null);
            originalItemDTO.setExternalId(null);
        }
    }


}
