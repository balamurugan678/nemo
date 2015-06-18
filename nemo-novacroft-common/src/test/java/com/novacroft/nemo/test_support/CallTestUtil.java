package com.novacroft.nemo.test_support;

import com.novacroft.nemo.common.command.impl.CallCmd;
import com.novacroft.nemo.common.domain.Call;
import com.novacroft.nemo.common.domain.CallType;
import com.novacroft.nemo.common.transfer.CallDTO;

import static com.novacroft.nemo.common.utils.Converter.convert;

/**
 * <p> Test support class for junit tests on the Call functionality </p>
 */
public class CallTestUtil {

    public static final Long ID_1 = 1L;
    public static final String TITLE_1 = "Mr";
    public static final String FIRST_NAME_1 = "James";
    public static final String LAST_NAME_1 = "Smith";
    public static final String INITIALS_1 = "B";
    public static final Long ADDRESS_ID_1 = 1L;
    public static final Long CALLTYPE_ID_1 = 1L;
    public static final String DESCRIPTION_1 = "Description";
    public static final String RESOLUTION_1 = "Resolution";
    public static final String NOTES_1 = "Notes";
    public static final String EMAILADDRESS_1 = "james.smith@test-novacroft.com";
    public static final String HOMEPHONE_1 = "01234567890";
    public static final String MOBILEPHONE_1 = "01234567890";

    public static final Long CALLTYPE_ID = 1L;
    public static final String CALLTYPE_DESCRIPTION = "Description";
    public static final Integer CALLTYPE_ACTIVE = 1;

    public static final String SELECTLIST_NAME = "Titles";
    public static final String SELECTLIST_DESCRIPTION = "Description";

    public static Call getCall1() {
        return createCall(ID_1, TITLE_1, FIRST_NAME_1, LAST_NAME_1, ADDRESS_ID_1, CALLTYPE_ID_1, DESCRIPTION_1, RESOLUTION_1,
                NOTES_1, EMAILADDRESS_1, HOMEPHONE_1, MOBILEPHONE_1);
    }

    public static CallType getCallType1() {
        return createCallType(CALLTYPE_ID, CALLTYPE_DESCRIPTION, CALLTYPE_ACTIVE);
    }

    public static Call createCall(Long id, String title, String firstName, String lastName, Long addressId, Long callTypeId,
                                  String description, String resolution, String notes, String emailAddress, String homePhone,
                                  String mobilePhone) {
        Call call = new Call();
        call.setTitle(title);
        call.setFirstName(firstName);
        call.setLastName(lastName);
        call.setAddressId(addressId);
        call.setCallTypeId(callTypeId);
        call.setDescription(description);
        call.setResolution(resolution);
        call.setNotes(notes);
        call.setEmailAddress(emailAddress);
        call.setHomePhone(homePhone);
        call.setMobilePhone(mobilePhone);
        return call;
    }

    public static CallDTO getCallDTO1() {
        Call call = getCall1();
        CallDTO callDTO = new CallDTO();
        convert(call, callDTO);
        return callDTO;
    }

    public static CallType createCallType(Long id, String description, Integer active) {
        CallType callType = new CallType();
        callType.setId(id);
        callType.setDescription(description);
        callType.setActive(active);
        return callType;
    }

    public static CallCmd getCallCmd1() {
        CallCmd callCmd = new CallCmd();
        Call call = getCall1();
        convert(call, callCmd);
        return callCmd;
    }

}
