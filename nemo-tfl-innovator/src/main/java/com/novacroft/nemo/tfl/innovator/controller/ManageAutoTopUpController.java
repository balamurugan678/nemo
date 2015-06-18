package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.AutoLoadState.NO_TOP_UP;

import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.command.impl.CardPreferencesCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;
import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.controller.BaseManageAutoTopUpController;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

@Controller
@RequestMapping(value = PageUrl.MANAGE_AUTO_TOP_UP)
public class ManageAutoTopUpController extends BaseManageAutoTopUpController {

    @Autowired
    protected MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView postViewAutoTopUp(@ModelAttribute(PageCommand.MANAGE_CARD) ManageCardCmd cmd,
                                          @RequestParam(value = CartAttribute.CUSTOMER_ID) Long customerId,
                                          @RequestParam(value = CartAttribute.CARD_NUMBER) String cardNumber,
                                          HttpSession session) {

        cmd.setCardNumber(cardNumber);
        ModelAndView modelAndView = new ModelAndView(PageView.MANAGE_AUTO_TOP_UP);
        modelAndView.addObject(PageAttribute.PAYMENT_CARDS, this.paymentCardSelectListService.getPaymentCardSelectList(customerId));
        cardService.populateManageCardCmdWithCubicCardDetails(cardNumber, customerId, cmd);
        CardPreferencesCmdImpl cardPreferences = new CardPreferencesCmdImpl();
        cardPreferences.setCardId(cmd.getCardId());
        cmd.setStationId(cardPreferencesService.getPreferredStationIdByCardId(cardPreferences));
        cmd.setCustomerId(customerId);
        cmd.setOrderNumber(null);

        if (StringUtil.isNotEmpty(cmd.getCardNumber())) {
            modelAndView.addObject(PageCommand.MANAGE_CARD, cmd);
            return modelAndView;
        } else {
            return completeView(cmd);
        }
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_UPDATE_AUTO_TOP_UP, method = RequestMethod.POST)

    public ModelAndView manageAutoTopUp(@ModelAttribute(PageCommand.MANAGE_CARD) ManageCardCmd cmd, BindingResult result,
                                        HttpSession session) {

        this.selectStationValidator.validate(cmd, result);
        if (result.hasErrors() || checkManageCardCmdForChanges(cmd)) {
            ModelAndView modelAndView = new ModelAndView(PageView.MANAGE_AUTO_TOP_UP, PageCommand.MANAGE_CARD, cmd);
            modelAndView.addObject("message", messageSource.getMessage(ContentCode.NO_CHANGE_IN_AMOUNT_SET_FOR_AUTOTOPUP.textCode(), null, "", null));
            return modelAndView;
        } else {
            if (cmd.getAutoTopUpState() > 0) {
                cardUpdateService.requestCardAutoLoadChange(cmd.getCardId(), cmd.getStationId(),
                        AutoLoadState.lookUpState(cmd.getAutoTopUpState()));
            } else {
                cardUpdateService.requestCardAutoLoadChange(cmd.getCardId(), cmd.getStationId(), NO_TOP_UP.state());
            }
            persistCardPreferences(cmd);
            return completeView(cmd);
        }
    }

    @Override
    protected ModelAndView completeView(ManageCardCmd manageCardCmd) {
        return createRedirectView(manageCardCmd);
    }

    @Override
    protected ModelAndView cancelView(ManageCardCmd manageCardCmd) {
        return createRedirectView(manageCardCmd);
    }

    protected ModelAndView createRedirectView(ManageCardCmd manageCardCmd) {
        ModelAndView modelAndView = new ModelAndView(new RedirectView(PageUrl.INV_CARD_ADMIN));
        modelAndView.addObject(CartAttribute.CARD_NUMBER, manageCardCmd.getCardNumber());
        modelAndView.addObject(CartAttribute.CUSTOMER_ID, manageCardCmd.getCustomerId());
        return modelAndView;
    }

    protected CardPreferencesCmdImpl persistCardPreferences(ManageCardCmd cmd) {
        CardPreferencesCmdImpl cardPreferences = new CardPreferencesCmdImpl();
        cardPreferences.setStationId(cmd.getStationId());
        cardPreferences.setCardId(cmd.getCardId());
        cardPreferences.setCardNumber(cmd.getCardNumber());
        cardPreferences.setCreatedUserId(getLoggedInUsername());
        cardPreferences.setCreatedDateTime(Calendar.getInstance().getTime());
        cardPreferences = cardPreferencesService.updatePreferences(cardPreferences);
        return cardPreferences;
    }

    @Override
    protected CustomerDTO getCustomer() {
        return customerDataService.findById(this.securityService.getLoggedInCustomer().getId());
    }

}
