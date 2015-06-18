package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;
import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.DateUtil.getBusinessDay;
import static com.novacroft.nemo.tfl.common.constant.AutoTopUpActivityType.CONFIG_CHANGE;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.USER_PRODUCT_START_AFTER_DAYS;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.common.utils.TravelCardDurationUtil;
import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;
import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.controller.BaseManageAutoTopUpController;
import com.novacroft.nemo.tfl.common.form_validator.AutoTopUpValidator;
import com.novacroft.nemo.tfl.common.form_validator.ChoosePaymentCardValidator;
import com.novacroft.nemo.tfl.common.form_validator.PaymentTermsValidator;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderItemsDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

@Controller
@RequestMapping(value = PageUrl.MANAGE_AUTO_TOP_UP)
public class ManageAutoTopUpController extends BaseManageAutoTopUpController {

    @Autowired
    protected ChoosePaymentCardValidator choosePaymentCardValidator;
    @Autowired
    protected PaymentTermsValidator paymentTermsValidator;
    @Autowired
    protected AutoTopUpValidator autoTopUpValidator;

    @ModelAttribute
    public void populatePaymentcardSelectList(Model model) {
        model.addAttribute(PageAttribute.PAYMENT_CARDS, paymentCardSelectListService.getPaymentCardSelectListForAdHocLoad(getCustomer().getId()));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView postViewAutoTopUp(@ModelAttribute(PageCommand.MANAGE_CARD) ManageCardCmd cmd, HttpSession session) {
        Long cardId = (Long) this.getFromSession(session, CartAttribute.CARD_ID);
        CardDTO cardNumberDTO = cardDataService.findById(cardId);
        String cardNumber = cardNumberDTO.getCardNumber();
        cmd.setCardNumber(cardNumber);
        ModelAndView modelAndView = new ModelAndView(PageView.MANAGE_AUTO_TOP_UP);
        cardService.populateManageCardCmdWithCubicCardDetails(cardNumber, getCustomerId(cardId), cmd);
        cmd.setStationId(customerService.getPreferredStationId(getCustomerId(cardId)));
        cmd.setCustomerId(getCustomerId(cardId));
        cmd.setOrderNumber(null);
        setAutoTopUpAmountStatus(cmd);
        if (StringUtil.isNotEmpty(cmd.getCardNumber())) {
            modelAndView.addObject(PageCommand.MANAGE_CARD, cmd);
            return modelAndView;
        } else {
            return completeView(cmd);
        }
    }

    protected void setAutoTopUpAmountStatus(ManageCardCmd cmd) {
        List<OrderItemsDTO> orderItems = orderService.findOrderItemsByCustomerId(getLoggedInUserCustomerId());
        for (OrderItemsDTO orderItemsDTO : orderItems) {
            List<ItemDTO> itemDTOList = orderItemsDTO.getItems();
            getAutoTopStatus(cmd, orderItemsDTO, itemDTOList);
        }
    }

    private void getAutoTopStatus(ManageCardCmd cmd, OrderItemsDTO orderItemsDTO, List<ItemDTO> itemDTOList) {
        cmd.setDisableAutoTopUpConfigurationChange(true);
        for (ItemDTO itemDTO : itemDTOList) {
            if (itemDTO.getClass().equals(AutoTopUpConfigurationItemDTO.class)
                            && ((OrderStatus.COMPLETE.code().equalsIgnoreCase(orderItemsDTO.getOrder().getStatus())))
                            || (OrderStatus.CANCELLED.code().equalsIgnoreCase(orderItemsDTO.getOrder().getStatus()))
                            || (OrderStatus.ERROR.code().equalsIgnoreCase(orderItemsDTO.getOrder().getStatus()))) {
                cmd.setDisableAutoTopUpConfigurationChange(false);
            }
        }
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_UPDATE_AUTO_TOP_UP, method = RequestMethod.POST)
    public ModelAndView manageAutoTopUp(@ModelAttribute(PageCommand.MANAGE_CARD) ManageCardCmd cmd, BindingResult result, HttpSession session) {
        validateAutoTopUpStatePendingAmount(cmd, session);
        autoTopUpValidator.validate(cmd, result);
        paymentTermsValidator.validate(cmd, result);
        if (result.hasErrors()) {
            setAutoTopUpAmountStatus(cmd);
            return new ModelAndView(PageView.MANAGE_AUTO_TOP_UP, PageCommand.MANAGE_CARD, cmd);
        }
        String startDate = DateUtil.formatDate(
                        getBusinessDay(new Date(), systemParameterService.getIntegerParameterValue(USER_PRODUCT_START_AFTER_DAYS)),
                        SHORT_DATE_PATTERN);
        String endDate = formatDate(TravelCardDurationUtil.getEndDate(startDate, Durations.SEVEN_DAYS.getDurationType()));
        cmd.setStartDateforAutoTopUpCardActivate(startDate);
        cmd.setEndDateforAutoTopUpCardActivate(endDate);
        Long cardId = (Long) this.getFromSession(session, CartAttribute.CARD_ID);
        cmd.setStationId(customerService.getPreferredStationId(getCustomerId(cardId)));
        cmd.setPaymentCardID(cmd.getPaymentCardID());
        cmd.setAutoTopUpState(null != cmd.getAutoTopUpState() ? cmd.getAutoTopUpState() : AutoLoadState.NO_TOP_UP.topUpAmount());
        cmd.setPaymentMethod(CartAttribute.PAY_AS_YOU_GO_PAYMENT_METHOD);
        cmd.setAutoTopUpActivity(CONFIG_CHANGE.getCode());
        if (cmd.getAutoTopUpStateExistingPendingAmount().equals(cmd.getAutoTopUpState())) {
            return new ModelAndView(new RedirectView(PageUrl.AUTO_TOP_UP_CONFIRMATION_ON_PAYMENT_CARD_CHANGE));
        } else {
            return new ModelAndView(PageView.ACTIVATE_CHANGES_TO_AUTO_TOP_UP, PageCommand.MANAGE_CARD, cmd);
        }
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CONFIRM_NEW_PAYMENT_CARD_PAYMENT, method = RequestMethod.POST)
    public RedirectView addNewPaymentCard(@ModelAttribute(PageCommand.MANAGE_CARD) ManageCardCmd cmd, BindingResult result, HttpSession session) {
        String startDate = DateUtil.formatDate(
                        getBusinessDay(new Date(), systemParameterService.getIntegerParameterValue(USER_PRODUCT_START_AFTER_DAYS)),
                        SHORT_DATE_PATTERN);
        String endDate = formatDate(TravelCardDurationUtil.getEndDate(startDate, Durations.SEVEN_DAYS.getDurationType()));
        cmd.setStartDateforAutoTopUpCardActivate(startDate);
        cmd.setEndDateforAutoTopUpCardActivate(endDate);
        Long cardId = (Long) this.getFromSession(session, CartAttribute.CARD_ID);
        cmd.setStationId(customerService.getPreferredStationId(getCustomerId(cardId)));
        cmd.setPaymentMethod(CartAttribute.PAY_AS_YOU_GO_PAYMENT_METHOD);
        RedirectView redirectView = new RedirectView(PageUrl.TOP_UP_TICKET);
        redirectView.addStaticAttribute(PageCommand.CART_ITEM, cmd);
        return redirectView;
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_VIEW_AUTO_TOP_UP, method = RequestMethod.POST)
    public ModelAndView mycardAutoTopUpView(@ModelAttribute(PageCommand.MANAGE_CARD) ManageCardCmd cmd, HttpSession session) {
        Long cardId = cmd.getCardId();
        this.addAttributeToSession(session, CartAttribute.CARD_ID, cardId);
        CardDTO cardNumberDTO = cardDataService.findById(cardId);
        String cardNumber = cardNumberDTO.getCardNumber();
        cmd.setCardNumber(cardNumber);
        ModelAndView modelAndView = new ModelAndView(PageView.MANAGE_AUTO_TOP_UP);
        cardService.populateManageCardCmdWithCubicCardDetails(cardNumber, getCustomerId(cardId), cmd);
        cmd.setStationId(customerService.getPreferredStationId(getCustomerId(cardId)));
        cmd.setCustomerId(getCustomerId(cardId));
        cmd.setOrderNumber(null);
        if (StringUtil.isNotEmpty(cmd.getCardNumber())) {
            modelAndView.addObject(PageCommand.MANAGE_CARD, cmd);
            return modelAndView;
        } else {
            return new ModelAndView(new RedirectView(PageUrl.VIEW_OYSTER_CARD));
        }
    }

    @Override
    protected ModelAndView completeView(ManageCardCmd manageCardCmd) {
        return new ModelAndView(new RedirectView(PageUrl.VIEW_OYSTER_CARD));
    }

    @Override
    protected ModelAndView cancelView(ManageCardCmd manageCardCmd) {
        return new ModelAndView(new RedirectView(PageUrl.VIEW_OYSTER_CARD));
    }

    @Override
    protected CustomerDTO getCustomer() {
        return customerDataService.findById(this.securityService.getLoggedInCustomer().getId());
    }

    protected void validateAutoTopUpStatePendingAmount(ManageCardCmd cmd, HttpSession session) {
        Long cardId = (Long) this.getFromSession(session, CartAttribute.CARD_ID);
        CardDTO cardNumberDTO = cardDataService.findById(cardId);
        CardInfoResponseV2DTO cubicCard = getCardService.getCard(cardNumberDTO.getCardNumber());
        Integer autoLoadStatePendingAmount = AutoLoadState.lookUpAmount(cubicCard.getAutoLoadState());
        if (StringUtils.isEmpty(cmd.getAutoTopUpState())) {
            cmd.setAutoTopUpState(autoLoadStatePendingAmount);
        }
        cmd.setAutoTopUpStateExistingPendingAmount(autoLoadStatePendingAmount);
    }
}
