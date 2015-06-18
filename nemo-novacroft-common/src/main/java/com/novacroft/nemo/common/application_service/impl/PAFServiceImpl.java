package com.novacroft.nemo.common.application_service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.google.gson.Gson;
import com.novacroft.nemo.common.application_service.PAFService;
import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;
import com.novacroft.nemo.common.constant.LocaleConstant;
import com.novacroft.nemo.common.data_service.PAFDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.CommonAddressDTO;
import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.common.utils.StringUtil;

/**
 * Postcode Address File (PAF) service implementation
 */
@Service("pafService")
public class PAFServiceImpl implements PAFService {
    private static final int POST_CODE_FIRST_PART_LENGTH = 3;
    
    @Autowired
    protected PAFDataService pafDataService;

    @Override
    public String[] getAddressesForPostcode(String postcode) {
        List<CommonAddressDTO> addressDTOs = findAddresses(checkAndAddDelimiter(postcode));
        String[] addresses = new String[addressDTOs.size()];
        int i = 0;
        for (CommonAddressDTO addressDTO : addressDTOs) {
            addresses[i] = getDisplayAddress(addressDTO);
            i++;
        }
        return addresses;
    }
    

    public CommonOrderCardCmd populateAddressFromJson(CommonOrderCardCmd cmd , String addressForPostCode) {
        Gson gson = new Gson();
        AddressDTO addressDTO = gson.fromJson(addressForPostCode, AddressDTO.class);
        cmd.setHouseNameNumber(addressDTO.getHouseNameNumber());
        cmd.setStreet(addressDTO.getStreet());
        cmd.setTown(addressDTO.getTown());
        cmd.setCountry(addressDTO.getCountry());
        cmd.setPostcode(addressDTO.getPostcode());
        return cmd;
    }

    @Override
    public SelectListDTO getAddressesForPostcodeSelectList(String postcode) {
        CountryDTO defaultCountryDTO = new CountryDTO();
        defaultCountryDTO.setCode(LocaleConstant.DEFAULT_COUNTRY_CODE);
        defaultCountryDTO.setName(LocaleConstant.DEFAULT_COUNTRY_NAME);
        List<CommonAddressDTO> addressDTOs = findAddresses(checkAndAddDelimiter(postcode));
        SelectListDTO selectList = new SelectListDTO();
        selectList.setName("addressesForPostcode");
        selectList.setOptions(new ArrayList<SelectListOptionDTO>());
        int i = 0;
        for (CommonAddressDTO addressDTO : addressDTOs) {
            addressDTO.setCountry(defaultCountryDTO);
            selectList.getOptions().add(new SelectListOptionDTO(escape(getAddressAsJSON(addressDTO)), i, getDisplayAddress(addressDTO)));
            i++;
        }
        return selectList;
    }

    protected String checkAndAddDelimiter(String postcode) {
        if (postcode.indexOf(StringUtil.SPACE) < 0) {
            String postCodeFirstPart = postcode.substring(0, postcode.length() - POST_CODE_FIRST_PART_LENGTH);
            String postCodeSecondPart = postcode.substring(postcode.length() - POST_CODE_FIRST_PART_LENGTH, postcode.length());
            return postCodeFirstPart + " " + postCodeSecondPart;
        }
        return postcode;
    }

    protected List<CommonAddressDTO> findAddresses(String postcode) {
        return pafDataService.findAddressesForPostcode(postcode);
    }

    protected String getDisplayAddress(CommonAddressDTO addressDTO) {
        return addressDTO.getHouseNameNumber() + ", " + addressDTO.getStreet() + ", " + addressDTO.getTown();
    }

    protected String escape(String value) {
        return HtmlUtils.htmlEscape(value);
    }

    protected String getAddressAsJSON(CommonAddressDTO addressDTO) {
        Gson gson = new Gson();
        return gson.toJson(addressDTO);
    }
}