package com.novacroft.nemo.tfl.services.application_service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.utils.Converter;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.BusPassService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.PayAsYouGoService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.command.decorator.CartItemCmdImplDecorator;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.CubicConstant;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.form_validator.BusPassValidator;
import com.novacroft.nemo.tfl.common.form_validator.PayAsYouGoValidator;
import com.novacroft.nemo.tfl.common.form_validator.TravelCardValidator;
import com.novacroft.nemo.tfl.common.form_validator.UserCartValidator;
import com.novacroft.nemo.tfl.common.form_validator.ZoneMappingValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.services.application_service.ItemExternalService;
import com.novacroft.nemo.tfl.services.transfer.Cart;
import com.novacroft.nemo.tfl.services.transfer.Item;

@Component("itemExternalService")
public class ItemExternalServiceImpl extends BaseService implements ItemExternalService {

    static final Logger logger = LoggerFactory.getLogger(ItemExternalServiceImpl.class);

    @Autowired
    protected CartService cartService;

    @Autowired
    protected CardDataService cardDataService;

    @Autowired
    protected CartDataService cartDataService;

    @Autowired
    protected TravelCardService travelCardService;

    @Autowired
    protected BusPassService busPassService;

    @Autowired
    protected PayAsYouGoService payAsYouGoService;

    @Autowired
    protected UserCartValidator userCartValidator;

    @Autowired
    protected TravelCardValidator travelCardValidator;

    @Autowired
    @Qualifier("externalzoneMappingValidator")
    protected ZoneMappingValidator zoneMappingValidator;

    @Autowired
    protected PayAsYouGoValidator payAsYouGoValidator;

    @Autowired
    protected BusPassValidator busPassValidator;

    @Autowired
    protected CardService cardService;

    @Autowired
    protected PrePaidTicketDataService prePaidTicketDataService;

    @Override
    public Errors addBusPassItemToCart(CartDTO cartDTO, Item item, Cart cart, Integer totalItemsToAdd) {
    	  CartItemCmdImplDecorator cartItemCmd = new CartItemCmdImplDecorator();

        Converter.convert(item, cartItemCmd);
        cartItemCmd.setTravelCardType(item.getDuration());
        cartItemCmd.setEmailReminder(formatEmailReminder(item.getReminderDate()));
        cartItemCmd.setTicketType(ProductItemType.BUS_PASS.databaseCode());
        cartItemCmd.setCardId(cartDTO.getCardId());
        cartItemCmd.setCartId(cartDTO.getId());
        cartItemCmd.setStartDate(DateUtil.formatDate(item.getStartDate()));
        cartItemCmd.setEndDate(DateUtil.formatDate(item.getEndDate()));
        cartItemCmd.setItemsInThisCart(totalItemsToAdd);
        setPassengerTypeAndDiscountType(cart, cartItemCmd);
        Errors errors = new BeanPropertyBindingResult(cartItemCmd, "cartItemCmd");
        busPassValidator.validate(cartItemCmd, errors);
        userCartValidator.validate(cartItemCmd, errors);
        if (errors.hasErrors()) {
            return errors;
        }
        cartDTO = busPassService.addCartItemForExistingCard(cartDTO, cartItemCmd);
        return errors;
    }

    @Override

    public Errors addPayAsYouGoItemToCart(CartDTO cartDTO, Item item, Integer totalItemsToAdd) {
        CartItemCmdImplDecorator cartItemCmd = new CartItemCmdImplDecorator();

        Converter.convert(item, cartItemCmd);
        cartItemCmd.setTravelCardType(item.getDuration());
        cartItemCmd.setEmailReminder(item.getReminderDate());
        cartItemCmd.setCreditBalance(item.getPrice());
        cartItemCmd.setCardId(cartDTO.getCardId());
        cartItemCmd.setAutoTopUpAmt(item.getAutoTopUpAmount() == null ? 0 : item.getAutoTopUpAmount());
        cartItemCmd.setCartId(cartDTO.getId());
        cartItemCmd.setItemsInThisCart(totalItemsToAdd);
        Errors errors = new BeanPropertyBindingResult(cartItemCmd, "cartItemCmd");
        payAsYouGoValidator.validate(cartItemCmd, errors);
        userCartValidator.validate(cartItemCmd, errors);
        if (errors.hasErrors()) {
            return errors;
        }
        cartDTO = payAsYouGoService.addCartItemForExistingCard(cartDTO, cartItemCmd);
        return errors;
    }

    protected String formatEmailReminder(String emailReminder) {
        String formattedEmailReminder = emailReminder;
        if (!StringUtil.isEmpty(emailReminder)) {
            String[] emailReminderSplitArray = emailReminder.split(CartAttribute.DAYS_BEFORE_EXPIRY);
            if (emailReminderSplitArray != null) {
                formattedEmailReminder = emailReminderSplitArray[0];
            }
        }
        return formattedEmailReminder;
    }

    @Override

    public Errors addTravelCardItemToCart(CartDTO cartDTO, Item item, Cart cart, Integer totalItemsToAdd) {
        CartItemCmdImplDecorator cartItemCmd = new CartItemCmdImplDecorator();

        Converter.convert(item, cartItemCmd);
        cartItemCmd.setTravelCardType(item.getDuration());
        cartItemCmd.setEmailReminder(formatEmailReminder(item.getReminderDate()));
        cartItemCmd.setTicketType(ProductItemType.TRAVEL_CARD.databaseCode());
        cartItemCmd.setStartDate(DateUtil.formatDate(item.getStartDate()));
        cartItemCmd.setEndDate(DateUtil.formatDate(item.getEndDate()));
        cartItemCmd.setCartId(cartDTO.getId());
        cartItemCmd.setCardId(cartDTO.getCardId());
        cartItemCmd.setConcessionEndDate(cart.getConcessionEndDate());
        setPassengerTypeAndDiscountType(cart, cartItemCmd);
        cartItemCmd.setItemsInThisCart(totalItemsToAdd);
        Errors errors = new BeanPropertyBindingResult(cartItemCmd, "cartItemCmd");
        setUpPrePaidTicketId(item, cartItemCmd, errors);
        travelCardValidator.validate(cartItemCmd, errors);
        zoneMappingValidator.validate(cartItemCmd, errors);
        userCartValidator.validate(cartItemCmd, errors);
        if (errors.hasErrors()) {
            return errors;
        }
        cartDTO = travelCardService.addCartItemForExistingCard(cartDTO, cartItemCmd);
        return errors;
    }

    protected void setUpPrePaidTicketId(Item item, CartItemCmdImpl cartItemCmd, Errors errors) {
        if (item.getPrePaidProductReference() != null) {
            cartItemCmd.setPrePaidTicketId(prePaidTicketDataService.getInternalIdFromExternalId(item.getPrePaidProductReference()));
        }
    }

    protected void setPassengerTypeAndDiscountType(Cart cart, CartItemCmdImpl cartItemCmd) {
        if (cart.getPassengerType() != null) {
            cartItemCmd.setPassengerType(cart.getPassengerType());
        } else {
            cartItemCmd.setPassengerType(CubicConstant.PASSENGER_TYPE_ADULT);
        }
        logger.debug(String.format(getContent(ContentCode.PASSENGER_TYPE.textCode()), cartItemCmd.getPassengerType()));

        if (cart.getDiscountType() != null) {
            cartItemCmd.setDiscountType(cart.getDiscountType());
        } else {
            cartItemCmd.setDiscountType(CubicConstant.NO_DISCOUNT_TYPE);
        }
        logger.debug(String.format(getContent(ContentCode.DISCOUNT_TYPE.textCode()), cartItemCmd.getDiscountType()));

    }
}
