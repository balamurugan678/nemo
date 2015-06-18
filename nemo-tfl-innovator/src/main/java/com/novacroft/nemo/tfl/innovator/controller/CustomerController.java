package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.PageAttribute.CUSTOMER_DEACTIVATION_REASONS;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.REFUNDS;
import static com.novacroft.nemo.tfl.common.constant.PageCommand.PERSONAL_DETAILS;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.CARD_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.ID;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_CUSTOMER;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.transfer.ApplicationEventDTO;
import com.novacroft.nemo.tfl.common.application_service.AgentService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.PersonalDetailsService;
import com.novacroft.nemo.tfl.common.application_service.cubic.CubicCardService;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageParameter;
import com.novacroft.nemo.tfl.common.constant.PageParameterValue;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.controller.BaseController;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.form_validator.DeactivateCustomerValidator;
import com.novacroft.nemo.tfl.common.form_validator.PersonalDetailsValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchArgumentsDTO;

/**
 * Application Controller is used in the Innovator System to handle any interaction with a Customers Application. The view
 * method will load a Customer dependent on the id passed in the request object.
 * The save method will store the updated information.
 */
@Controller
@RequestMapping(value = Page.INV_CUSTOMER)
public class CustomerController extends BaseController{
    private static final int MAX_RETURNED_RECORDS = 10;

    static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    protected PersonalDetailsService personalDetailsService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected CustomerService customerService;
    @Autowired
    protected ApplicationEventService applicationEventService;
    @Autowired
    protected PersonalDetailsValidator personalDetailsValidator;
    @Autowired
    protected AgentService agentService;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected CubicCardService cubicCardService;
    @Autowired
    protected LocationSelectListService locationSelectListService;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected DeactivateCustomerValidator deactivateCustomerValidator;
    @Autowired
    protected CountrySelectListService countrySelectListService;

    public static final String FAIL = "FAIL";
    public static final String SUCCESS_TOKEN = "SUCCESS?TOKEN=";

    @ModelAttribute
    public void populateLocationsSelectList(Model model) {
        model.addAttribute(PageAttribute.LOCATIONS, this.locationSelectListService.getLocationSelectList());
        model.addAttribute(PageAttribute.COUNTRIES, countrySelectListService.getSelectList());
    }

    @ModelAttribute
    public void addOnlineServer(Model model) {
        model.addAttribute(PageParameterValue.ONLINE_SYSTEM_BASE_URI,
                systemParameterService.getParameterValue(SystemParameterCode.ONLINE_SYSTEM_BASE_URI.code()));
    }

    @ModelAttribute
    public void populateRefundsSelectList(Model model) {
        model.addAttribute(REFUNDS, selectListService.getSelectList(PageSelectList.REFUNDS));
    }

    @ModelAttribute
    public void populateTitlesSelectList(Model model) {
        model.addAttribute(PageAttribute.TITLES, selectListService.getSelectList(PageSelectList.TITLES));
    }

    @ModelAttribute
    public void populateCustomerDeactivationReasonsSelectList(Model model) {
        model.addAttribute(CUSTOMER_DEACTIVATION_REASONS,
                selectListService.getSelectList(PageSelectList.CUSTOMER_DEACTIVATION_REASONS));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView load(@RequestParam(value = ID, required = false) Long customerId,
                             @RequestParam(value = CARD_NUMBER, required = false) String cardNumber,
                             HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(INV_CUSTOMER);
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        cmd.setCardNumber(cardNumber);
        if (customerId != null && personalDetailsService.getPersonalDetailsByCustomerId(customerId) != null) {
            cmd = personalDetailsService.getPersonalDetailsByCustomerId(customerId);
            cmd.setShowWebAccountDeactivationEnableFlag(true);
            cmd.setCardNumber(cardNumber);
            loadCardForCustomer(cmd, modelAndView);
            loadApplicationEventsForCustomer(cmd, modelAndView);
        } else {
            modelAndView.addObject("error", "customerid.parameter.error");
            modelAndView.addObject(PERSONAL_DETAILS, cmd);
            return modelAndView;
        }
        deleteCartSessionData(request);
        modelAndView.addObject(PERSONAL_DETAILS, cmd);
        modelAndView.addObject(PageAttribute.LOGGED_IN_USERNAME, cmd.getUsername());
        request.setAttribute(ID, customerId);
        return modelAndView;
    }

    protected void deleteCartSessionData(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && this.getFromSession(session, CartAttribute.SESSION_ATTRIBUTE_SHOPPING_CART_DATA) != null) {
            this.deleteAttributeFromSession(session, CartAttribute.SESSION_ATTRIBUTE_SHOPPING_CART_DATA);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute(PERSONAL_DETAILS) PersonalDetailsCmdImpl cmd, BindingResult result,
                             HttpServletRequest request) {
        this.personalDetailsValidator.validate(cmd, result);

        if (cmd.getCustomerId() != null) {
            this.deactivateCustomerValidator.validate(cmd, result);
            if (!result.hasErrors()) {
                this.personalDetailsService.updatePersonalDetails(cmd);
            }
        } else {
            if (!result.hasErrors()) {
                this.customerService.customerExists(cmd, result);
            }
        }
        ModelAndView modelAndView = new ModelAndView(INV_CUSTOMER);
        if (!result.hasErrors()) {
            if (cmd.getCustomerId() == null) {
                boolean isGhost = false;
                if (StringUtils.isEmpty(cmd.getEmailAddress())) {
                    cmd.setEmailAddress(customerService.createGhostEmail(cmd));
                    isGhost = true;
                }
                CustomerDTO customer = customerService.addCustomer(cmd);
                cmd.setCustomerId(customer.getId());
                request.setAttribute(ID, customer.getId());
                // TODO: send email to customer asking them to log in
                if (!isGhost) {
                    URI uri = URI.create(
                            systemParameterService.getParameterValue(SystemParameterCode.ONLINE_SYSTEM_BASE_URI.code()));
                }

            } else {
                this.personalDetailsService.updatePersonalDetails(cmd);
                request.setAttribute(ID, cmd.getCustomerId());
            }
        }
        loadCardForCustomer(cmd, modelAndView);
        loadApplicationEventsForCustomer(cmd, modelAndView);

        modelAndView.addObject(PERSONAL_DETAILS, cmd);
        return modelAndView;
    }

    private void loadCardForCustomer(PersonalDetailsCmdImpl cmd, ModelAndView modelAndView) {
        if (cmd.getCustomerId() != null) {
            List<CardDTO> cards = cardDataService.findByCustomerId(cmd.getCustomerId());
            modelAndView.addObject(PageAttribute.CUSTOMER_CARDS, cards);
        }
    }

    private void loadApplicationEventsForCustomer(PersonalDetailsCmdImpl cmd, ModelAndView modelAndView) {
        List<ApplicationEventDTO> eventsForCustomer =
                applicationEventService.findApplicationEventsForCustomer(cmd.getCustomerId());
        modelAndView.addObject(PageAttribute.CUSTOMER_EVENTS, eventsForCustomer);
    }

    @RequestMapping(value = "/checkEmailAvailable", method = RequestMethod.POST)
    @ResponseBody
    public String checkEmailAvailable(String email, HttpServletRequest request) {
        String result = "false";

        CustomerDTO usernameOrEmail = customerDataService.findByUsernameOrEmail(email);
        if (usernameOrEmail != null) {
            result = usernameOrEmail.getId().toString();
        }
        return result;
    }

    @RequestMapping(value = "/checkCustomer", method = RequestMethod.POST)
    @ResponseBody
    public String checkCustomer(String firstName, String lastName, String postcode) {
        if (StringUtils.isNotEmpty(firstName) && StringUtils.isNotEmpty(lastName) && StringUtils.isNotEmpty(postcode)) {
            return new Gson().toJson(
                 customerDataService.findByCriteria(
                      new CustomerSearchArgumentsDTO(true, null, null, firstName, lastName, postcode, null, null, 1, MAX_RETURNED_RECORDS)
                 )
            );
        }
        return StringUtils.EMPTY;
    }

    /**
     * Log into the account by the agent
     */
    @RequestMapping(method = RequestMethod.GET, value = "agentLogon.htm")
    @ResponseBody
    public String agentLogon(HttpServletRequest request) {
        String customerId =
                request.getParameter(PageParameter.CUSTOMER_ID) != null ? request.getParameter(PageParameter.CUSTOMER_ID) :
                        "-1";
        // pick details from existing session
        HttpSession session = request.getSession(false);
        String agentId = session != null ? (String) session.getAttribute("userId") : "";
        agentId = "SK11";
        String token = "";

        if (agentId != null && agentId.length() > 0 && !customerId.equals("-1")) {
            try {
                token = agentService.allowAgent(agentId, Integer.parseInt(customerId));
            } catch (Exception e) {
                return FAIL;
            }
        } else {
            return FAIL;
        }
        if (token != null && token.length() > 0) {
            return SUCCESS_TOKEN + token;
        } else {
            return FAIL;
        }
    }

    @RequestMapping(value = "/checkCardStatus", method = RequestMethod.POST)
    @ResponseBody
    public String checkCardStatus(String prestigeId) {
        return new Gson().toJson(cubicCardService.checkCardStatusReturnMessage(prestigeId));
    }

}
