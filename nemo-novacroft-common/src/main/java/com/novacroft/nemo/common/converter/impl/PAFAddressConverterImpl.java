package com.novacroft.nemo.common.converter.impl;

import static com.novacroft.nemo.common.utils.StringUtil.WHITE_SPACE;
import static com.novacroft.nemo.common.utils.StringUtil.isAlphabetic;
import static com.novacroft.nemo.common.utils.StringUtil.isInteger;
import static com.novacroft.nemo.common.utils.StringUtil.mixCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.comparator.FullAddressHouseNoNameComparator;
import com.novacroft.nemo.common.converter.PAFAddressConverter;
import com.novacroft.nemo.common.transfer.CommonAddressDTO;
import com.novacroft.phoenix.service.paf.bean.PAFFullAddress;

/**
 * PAF (Postcode Address File) address converter implementation
 */
@Component("pafAddressConverter")
public class PAFAddressConverterImpl implements PAFAddressConverter {
    @Override
    public CommonAddressDTO convertEntityToDto(PAFFullAddress address) {
        return formatFullAddress(address);
    }

    @Override
    public List<CommonAddressDTO> convertEntitiesToDtos(PAFFullAddress[] addresses) {
        List<CommonAddressDTO> addressDTOs = new ArrayList<CommonAddressDTO>();
        for (PAFFullAddress address : addresses) {
            addressDTOs.add(convertEntityToDto(address));
        }
        Collections.sort(addressDTOs, new FullAddressHouseNoNameComparator());
        return addressDTOs;
    }

    protected CommonAddressDTO formatFullAddress(PAFFullAddress address) {
        CommonAddressDTO addressDTO = new CommonAddressDTO();

        final String lineAddress = buildLineAddress(address);
        String buildingNoOrName = "";
        String street="";

        if (address.getBuildingNumber() > 0) {
            buildingNoOrName = getBuildingNoOrNameFromBuildingNumber(address);
            if (address.getBuildingName().length() > 0 || address.getSubBuildingName().length() > 0 || address.getOrganisationName().length() > 0) {
                street = Integer.toString(address.getBuildingNumber());
            } else {
                buildingNoOrName += Integer.toString(address.getBuildingNumber());
            }
        } else if (address.getBuildingName().length() > 0) {
            buildingNoOrName = getBuildingNoOrNameFromBuildingName(address);
        } else if (getLeadingDigits(address.getSubBuildingName()).length() > 0) {
            buildingNoOrName = getBuildingNoOrNameFromSubBuildingName(address);
        } else {
            String digits = firstNumericAlpha(lineAddress);
            if (digits.length() > 0) {
                buildingNoOrName = digits;
                if (address.getDepThoroughfareName().length() > 0 || address.getDepThoroughfareDescriptor().length() > 0) {
                    buildingNoOrName = buildingNoOrName + WHITE_SPACE + address.getDepThoroughfareName() + WHITE_SPACE +
                            address.getDepThoroughfareDescriptor();
                }
            } else {
                buildingNoOrName = lineAddress;
            }
        }

        addressDTO.setHouseNameNumber(mixCase(buildingNoOrName));
        addressDTO.setStreet(street+ WHITE_SPACE + mixCase(address.getThoroughfareName() + WHITE_SPACE + address.getThoroughfareDescriptor()));
        addressDTO.setTown(mixCase(address.getPostTown()));
        addressDTO.setPostcode(address.getPostcode());

        return addressDTO;
    }

    /**
     * @param address
     * @return
     */
    protected String getBuildingNoOrNameFromSubBuildingName(PAFFullAddress address) {
        String buildingNoOrName;
        buildingNoOrName = address.getSubBuildingName();
        if (address.getDepThoroughfareName().length() > 0 || address.getDepThoroughfareDescriptor().length() > 0) {
            buildingNoOrName = buildingNoOrName + WHITE_SPACE + address.getDepThoroughfareName() + WHITE_SPACE +
                    address.getDepThoroughfareDescriptor();
        }
        return buildingNoOrName;
    }

    /**
     * @param address
     * @return
     */
    protected String getBuildingNoOrNameFromBuildingName(PAFFullAddress address) {
        String buildingNoOrName;
        buildingNoOrName = "";
        if (address.getOrganisationName().length() > 0) {
            buildingNoOrName = buildingNoOrName + address.getOrganisationName() + WHITE_SPACE;
        }
        if (address.getSubBuildingName().length() > 0) {
            buildingNoOrName = buildingNoOrName + address.getSubBuildingName() + WHITE_SPACE + address.getBuildingName();
        } else {
            buildingNoOrName = buildingNoOrName + address.getBuildingName();
        }
        if (address.getDepThoroughfareName().length() > 0 || address.getDepThoroughfareDescriptor().length() > 0) {
            buildingNoOrName = buildingNoOrName + WHITE_SPACE + address.getDepThoroughfareName() + WHITE_SPACE +
                    address.getDepThoroughfareDescriptor();
        }
        return buildingNoOrName;
    }

    /**
     * @param address
     * @return
     */
    protected String getBuildingNoOrNameFromBuildingNumber(PAFFullAddress address) {
        String buildingNoOrName;
        buildingNoOrName = "";
        if (address.getOrganisationName().length() > 0) {
            buildingNoOrName = buildingNoOrName + address.getOrganisationName();
        }
        if (address.getSubBuildingName().length() > 0) {
            buildingNoOrName = buildingNoOrName + WHITE_SPACE + address.getSubBuildingName();
        }
        if (address.getBuildingName().length() > 0) {
            buildingNoOrName = buildingNoOrName + WHITE_SPACE + address.getBuildingName();
        }
        if (buildingNoOrName.length() > 0) {
            buildingNoOrName = buildingNoOrName + WHITE_SPACE;
        }
        if (address.getDepThoroughfareName().length() > 0 || address.getDepThoroughfareDescriptor().length() > 0) {
            buildingNoOrName = buildingNoOrName + WHITE_SPACE + address.getDepThoroughfareName() + WHITE_SPACE +
                    address.getDepThoroughfareDescriptor();
        }
        return buildingNoOrName;
    }

    protected String firstNumericAlpha(String string) {
        StringBuffer newString = new StringBuffer();
        int length = string.length();
        int n = 0;
        // Look for first digit
        while (n < length && !Character.isDigit(string.charAt(n))) {
            n++;
        }
        // Scan remaining digits
        while (n < length && Character.isLetterOrDigit(string.charAt(n))) {
            newString.append(string.charAt(n++));
        }
        return newString.toString();
    }

    protected String buildLineAddress(PAFFullAddress fullAddress) {
        String[] address = buildAddress(true, fullAddress);
        String line = "";
        for (int n = 0; n < address.length; n++) {
            if (n > 0) {
                line += WHITE_SPACE;
            }
            line += address[n];
        }
        return line;
    }

    protected String[] buildAddress(boolean includeOrganisation, PAFFullAddress fullAddress) {
        ArrayList<String> address1 = new ArrayList<String>();
        ArrayList<String> address2 = new ArrayList<String>();
        // Build up address
        if (includeOrganisation && fullAddress.getOrganisationName().length() > 0) {
                address1.add(fullAddress.getOrganisationName());
        }
        if (fullAddress.getOrganisationDepartmentName().length() > 0) {

            address1.add(fullAddress.getOrganisationDepartmentName());

        }
        if (fullAddress.getPoBoxNumber().length() > 0) {
            address1.add("PO Box " + fullAddress.getPoBoxNumber());
        }
        // Previously obtained street and area, not required for metro.
        address2.add("");

        // 7 rules for combinations of sub-building name, building name and building number
        if (fullAddress.getBuildingNumber() > 0 && fullAddress.getBuildingName().length() == 0 && fullAddress.getSubBuildingName().length() == 0) {
            // Rule 1 - building number only
            address2.set(0, fullAddress.getBuildingNumber() + WHITE_SPACE + address2.get(0));
        } else if (fullAddress.getBuildingName().length() > 0 && fullAddress.getBuildingNumber() == 0
                        && fullAddress.getSubBuildingName().length() == 0) {
            // Rule 2 - building name only
            addBuildingName(fullAddress, address2);
        } else if (fullAddress.getBuildingName().length() > 0 && fullAddress.getBuildingNumber() > 0
                        && fullAddress.getSubBuildingName().length() == 0) {
            // Rule 3 - building name and building number
            address2.set(0, fullAddress.getBuildingNumber() + WHITE_SPACE + address2.get(0));
            address2.set(0, fullAddress.getBuildingName());

        } else if (fullAddress.getSubBuildingName().length() > 0 && fullAddress.getBuildingNumber() > 0
                        && fullAddress.getBuildingName().length() == 0) {
            // Rule 4 - sub building name and building number
            addSubBuildingNameAndBuildingNumber(fullAddress, address2);
        } else if (fullAddress.getSubBuildingName().length() > 0 && fullAddress.getBuildingName().length() > 0
                        && fullAddress.getBuildingNumber() == 0) {
            // Rule 5 - sub building name and building name
            addSubBuildingNameAndBuildingName(fullAddress, address2);
        } else if (fullAddress.getBuildingNumber() > 0 && fullAddress.getSubBuildingName().length() > 0 && fullAddress.getBuildingName().length() > 0) {
            // Rule 6 - sub building name, building name and building number
            addBuildingNameNumberAndSubBuildingName(fullAddress, address2);
        }
        for (int n = 0; n < address2.size(); n++) {
            address1.add(address2.get(n));
        }

        String[] address = new String[address1.size()];
        address = address1.toArray(address);
        return address;
    }

    /**
     * @param fullAddress
     * @param address2
     */
    protected void addBuildingName(PAFFullAddress fullAddress, List<String> address2) {
        if (isFormat1BuildingName(fullAddress.getBuildingName())) {
            address2.set(0, fullAddress.getBuildingName() + WHITE_SPACE + address2.get(0));
        } else {
            address2.set(0, fullAddress.getBuildingName());
        }
    }

    /**
     * @param fullAddress
     * @param address2
     */
    protected void addBuildingNameNumberAndSubBuildingName(PAFFullAddress fullAddress, List<String> address2) {
        address2.set(0,fullAddress.getBuildingNumber() + WHITE_SPACE + address2.get(0));
        if (isFormat1BuildingName(fullAddress.getSubBuildingName())) {
            address2.add(0,fullAddress.getSubBuildingName() + WHITE_SPACE + fullAddress.getBuildingName());
        } else {
            address2.add(0,fullAddress.getSubBuildingName());
            address2.add(0,fullAddress.getBuildingName());
         }
    }

    /**
     * @param fullAddress
     * @param address2
     */
    protected void addSubBuildingNameAndBuildingNumber(PAFFullAddress fullAddress, List<String> address2) {
        if (fullAddress.getConcatenationIndicator().equals("Y")) {
            address2.set(0,fullAddress.getBuildingNumber() + fullAddress.getSubBuildingName() + WHITE_SPACE + address2.get(0));
        } else {
            address2.set(0, fullAddress.getBuildingNumber() + WHITE_SPACE + address2.get(0));
            address2.add(0,fullAddress.getSubBuildingName());
        }
    }

    /**
     * @param fullAddress
     * @param address2
     */
    protected void addSubBuildingNameAndBuildingName(PAFFullAddress fullAddress, List<String> address2) {
      if (isFormat1BuildingName(fullAddress.getSubBuildingName())) {
            address2.add(0,fullAddress.getSubBuildingName() + WHITE_SPACE + fullAddress.getBuildingName());
        } else {
            if (isFormat1BuildingName(fullAddress.getBuildingName())) {
                address2.set(0, fullAddress.getBuildingName() + WHITE_SPACE + address2.get(0));
                address2.add(0, fullAddress.getSubBuildingName());
            } else {
                address2.add(0, fullAddress.getBuildingName());
                address2.add(0, fullAddress.getSubBuildingName());
            }
        }
    }

    protected boolean isFormat1BuildingName(String buildingName) {
        if (isInteger(buildingName.substring(0, 1))){
             if (isInteger(buildingName.substring(buildingName.length() - 1))) {
                return true;
            } else {
                if (isInteger(buildingName.substring(buildingName.length() - 2, buildingName.length() - 1)) &&
                        isAlphabetic(buildingName.substring(buildingName.length() - 1))) {
                    return true;
                }
            }   
         }

         return false;

    }

    protected String getLeadingDigits(String number) {
        StringBuffer result = new StringBuffer();
        int l = number.length();
        for (int n = 0; n < l && Character.isDigit(number.charAt(n)); n++) {
            result.append(number.charAt(n));
        }
        return result.toString();
    }
}
