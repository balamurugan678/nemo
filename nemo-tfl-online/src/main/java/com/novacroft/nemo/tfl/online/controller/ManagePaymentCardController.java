package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.tfl.common.constant.PageAttribute.ADDRESSES_FOR_POSTCODE;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.application_service.PAFService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.validator.PostcodeLookUpValidator;
import com.novacroft.nemo.common.validator.PostcodeValidator;
import com.novacroft.nemo.tfl.common.application_service.PaymentCardService;
import com.novacroft.nemo.tfl.common.command.impl.ManagePaymentCardCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PaymentCardCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.PaymentCardDeleteValidator;
import com.novacroft.nemo.tfl.common.form_validator.PaymentCardEditValidator;

/**
 * Manage Payment Card Controller
 */
@Controller
@RequestMapping(PageUrl.MANAGE_PAYMENT_CARD)
public class ManagePaymentCardController extends OnlineBaseController {
    @Autowired
    protected PaymentCardService paymentCardService;
    @Autowired
    protected PaymentCardDeleteValidator paymentCardDeleteValidator;
    @Autowired
    protected PaymentCardEditValidator paymentCardEditValidator;
    @Autowired
    protected PAFService pafService;
    @Autowired
    protected CountrySelectListService countrySelectListService;
    @Autowired
    protected PostcodeValidator postcodeValidator;
    @Autowired
    protected PostcodeLookUpValidator postcodeLookUpValidator;

    @ModelAttribute
    public void populateCountrySelectList(Model model) {
        model.addAttribute(PageAttribute.COUNTRIES, this.countrySelectListService.getSelectList());
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_EDIT_PAYMENT_CARD, method = RequestMethod.POST)
    public ModelAndView selectPaymentCard(@ModelAttribute(PageCommand.MANAGE_PAYMENT_CARD) ManagePaymentCardCmdImpl cmd) {
        if (null == cmd.getPaymentCardId()) {
            return showPage();
        }

        return new ModelAndView(PageView.EDIT_PAYMENT_CARD, PageCommand.PAYMENT_CARD,
                this.paymentCardService.getPaymentCard(cmd.getPaymentCardId()));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showPage() {
        return new ModelAndView(PageView.MANAGE_PAYMENT_CARD, PageCommand.MANAGE_PAYMENT_CARD,
                this.paymentCardService.getPaymentCards(getLoggedInUserCustomerId()));
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_FIND_ADDRESS, method = RequestMethod.POST)
    public ModelAndView findAddressesForPostcode(@ModelAttribute(PageCommand.PAYMENT_CARD) PaymentCardCmdImpl cmd,
                                                 BindingResult result) {
        this.postcodeValidator.validate(cmd, result);
        this.postcodeLookUpValidator.validate(cmd, result);
        if (result.hasErrors()) {
            return new ModelAndView(PageView.EDIT_PAYMENT_CARD, PageCommand.PAYMENT_CARD, cmd);
        }
        ModelAndView modelAndView = new ModelAndView(PageView.EDIT_PAYMENT_CARD, PageCommand.PAYMENT_CARD, cmd);
        postcodeToUppercase(cmd);
        modelAndView.addObject(ADDRESSES_FOR_POSTCODE, this.pafService.getAddressesForPostcodeSelectList(cmd.getPostcode()));
        return modelAndView;
    }

    protected void postcodeToUppercase(PaymentCardCmdImpl paymentCardCmd) {
        paymentCardCmd.getPaymentCardDTO().getAddressDTO()
                .setPostcode(paymentCardCmd.getPaymentCardDTO().getAddressDTO().getPostcode().toUpperCase());
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_SELECT_ADDRESS, method = RequestMethod.POST)
    public ModelAndView selectAddress(@ModelAttribute(PageCommand.PAYMENT_CARD) PaymentCardCmdImpl cmd) {
        if (isNotBlank(cmd.getAddressForPostcode())) {
            Gson gson = new Gson();
            AddressDTO addressDTO = gson.fromJson(cmd.getAddressForPostcode(), AddressDTO.class);
            cmd.getPaymentCardDTO().getAddressDTO().setHouseNameNumber(addressDTO.getHouseNameNumber());
            cmd.getPaymentCardDTO().getAddressDTO().setStreet(addressDTO.getStreet());
            cmd.getPaymentCardDTO().getAddressDTO().setTown(addressDTO.getTown());
        }
        return new ModelAndView(PageView.EDIT_PAYMENT_CARD, PageCommand.PAYMENT_CARD, cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_DELETE, method = RequestMethod.POST)
    public ModelAndView deletePaymentCard(@ModelAttribute(PageCommand.PAYMENT_CARD) PaymentCardCmdImpl cmd,
                                          BindingResult result) {
        this.paymentCardDeleteValidator.validate(cmd, result);
        if (result.hasErrors()) {
            return new ModelAndView(PageView.EDIT_PAYMENT_CARD, PageCommand.PAYMENT_CARD, cmd);
        }
        this.paymentCardService.deletePaymentCard(cmd);
        return showPage();
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_SAVE_CHANGES, method = RequestMethod.POST)
    public ModelAndView updatePaymentCard(@ModelAttribute(PageCommand.PAYMENT_CARD) PaymentCardCmdImpl cmd,
                                          BindingResult result) {
        this.paymentCardEditValidator.validate(cmd, result);
        if (result.hasErrors()) {
            return new ModelAndView(PageView.EDIT_PAYMENT_CARD, PageCommand.PAYMENT_CARD, cmd);
        }
        this.paymentCardService.updatePaymentCard(cmd);
        return showPage();
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL_EDIT, method = RequestMethod.POST)
    public ModelAndView cancelEdit() {
        return showPage();
    }
}
