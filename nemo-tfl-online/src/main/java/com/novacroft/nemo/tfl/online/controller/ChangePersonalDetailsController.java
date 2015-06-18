package com.novacroft.nemo.tfl.online.controller;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.application_service.PAFService;
import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.common.validator.PostcodeValidator;
import com.novacroft.nemo.tfl.common.application_service.PersonalDetailsService;
import com.novacroft.nemo.tfl.common.application_service.SingleSignOnTokenService;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.PersonalDetailsValidator;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnResponseDTO;
import com.novacroft.nemo.tfl.common.util.SingleSignOnUtil;

/**
 * controller for changing personal details
 */
@Controller
@RequestMapping(value = PageUrl.CHANGE_PERSONAL_DETAILS)
public class ChangePersonalDetailsController extends OnlineBaseController {
    protected static final Logger logger = LoggerFactory.getLogger(ChangePersonalDetailsController.class);

    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected PersonalDetailsService personalDetailsService;
    @Autowired
    protected PAFService pafService;
    @Autowired
    protected CountrySelectListService countrySelectListService;
    @Autowired
    protected PostcodeValidator postcodeValidator;
    @Autowired
    protected PersonalDetailsValidator personalDetailsValidator;
    @Autowired
    protected SingleSignOnTokenService singleSignOnTokenService;
    @Autowired
    protected CountryDataService countryDataService;

    @ModelAttribute
    public void populateCountrySelectList(Model model) {
        model.addAttribute(PageAttribute.COUNTRIES, this.countrySelectListService.getSelectList());
    }

    @ModelAttribute
    public void populateTitlesSelectList(Model model) {
        model.addAttribute(PageAttribute.TITLES, selectListService.getSelectList(PageSelectList.TITLES));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showChangePersonalDetails(@ModelAttribute(PageAttribute.LOGGED_IN_USERNAME) String username) {
        ModelAndView modelAndView = new ModelAndView(PageView.CHANGE_PERSONAL_DETAILS);
        PersonalDetailsCmdImpl cmd = this.personalDetailsService.getPersonalDetails(username);
        modelAndView.addObject(PageCommand.PERSONAL_DETAILS, cmd);
        return modelAndView;
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_FIND_ADDRESS, method = RequestMethod.POST)
    public ModelAndView findAddressesForPostcode(@ModelAttribute(PageCommand.PERSONAL_DETAILS) PersonalDetailsCmdImpl cmd, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView(PageView.CHANGE_PERSONAL_DETAILS, PageCommand.PERSONAL_DETAILS, cmd);
        this.postcodeValidator.validate(cmd, result);
        if (!result.hasErrors()) {
        	SelectListDTO listDTO =  this.pafService.getAddressesForPostcodeSelectList(cmd.getPostcode());
        	List<SelectListOptionDTO> listOptionDTOs = listDTO.getOptions();
        	if(listOptionDTOs.size()==0) {
        		cmd.setInvalidPostCodeCheckFlag(true);
                postcodeValidator.validate(cmd, result);
                if (!result.hasErrors()) {
                    return new ModelAndView(PageView.CHANGE_PERSONAL_DETAILS, PageCommand.PERSONAL_DETAILS, cmd);
                }
        	} else {
        		modelAndView.addObject("addressesForPostcode", listDTO);
        		return modelAndView;
        	}
        }
        return new ModelAndView(PageView.CHANGE_PERSONAL_DETAILS, PageCommand.PERSONAL_DETAILS, cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_SELECT_ADDRESS, method = RequestMethod.POST)
    public ModelAndView selectAddress(@ModelAttribute(PageCommand.PERSONAL_DETAILS) PersonalDetailsCmdImpl cmd) {
        if (isNotBlank(cmd.getAddressForPostcode())) {
            cmd = (PersonalDetailsCmdImpl) this.pafService.populateAddressFromJson(cmd, cmd.getAddressForPostcode().trim());
        }
        return new ModelAndView(PageView.CHANGE_PERSONAL_DETAILS, PageCommand.PERSONAL_DETAILS, cmd);
    }

    
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_SAVE_CHANGES, method = RequestMethod.POST)
    public ModelAndView saveChanges(@ModelAttribute(PageCommand.PERSONAL_DETAILS) PersonalDetailsCmdImpl cmd, BindingResult result, final RedirectAttributes redirectAttributes) {
        this.personalDetailsValidator.validate(cmd, result);
        if (result.hasErrors()) {
            return new ModelAndView(PageView.CHANGE_PERSONAL_DETAILS, PageCommand.PERSONAL_DETAILS, cmd);
        } else {
            if (isSingleSignOnAuthenticationOn()) {
                CountryDTO countryDto = countryDataService.findCountryByCode(cmd.getCountry().getCode());
                cmd.setCountry(countryDto);
                Map<String, String> changeSet = SingleSignOnUtil.generateSingleSignOnChangeSet(cmd);
                Object singleSignOnResponse = singleSignOnTokenService.updateMasterCustomerData(changeSet);
                SingleSignOnResponseDTO responseDTO = SingleSignOnUtil.createSingleSignOnResponseDTO(singleSignOnResponse);
                if (!responseDTO.getIsValid()) {
                    return new ModelAndView(PageView.CHANGE_PERSONAL_DETAILS, PageCommand.PERSONAL_DETAILS, cmd);
                }
            }
            
            this.personalDetailsService.updatePersonalDetails(cmd);
            setFlashStatusMessage(redirectAttributes, ContentCode.CHANGE_PERSONAL_DETAILS_UPDATE_SUCCESSFUL.textCode());
            return new ModelAndView(new RedirectView(PageUrl.DASHBOARD));
        }
    }
}
