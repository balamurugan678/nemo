package com.novacroft.nemo.test_support;

import com.novacroft.nemo.common.domain.SelectList;
import com.novacroft.nemo.common.domain.SelectListOption;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilities for select list unit tests
 */
public final class SelectListTestUtil {
    public final static Long TEST_LIST_1_ID = 8L;
    public final static String TEST_LIST_1_NAME = "test-select-list-1-name";
    public final static String TEST_LIST_1_DESCRIPTION = "test-select-list-1-description";

    public final static Long TEST_LIST_2_ID = 16L;
    public final static String TEST_LIST_2_NAME = "test-select-list-2-name";
    public final static String TEST_LIST_2_DESCRIPTION = "test-select-list-2-description";

    public final static Long TEST_LIST_3_ID = 32L;
    public final static String TEST_LIST_3_NAME = "test-select-list-3-name";
    public final static String TEST_LIST_3_DESCRIPTION = "test-select-list-3-description";
    public final static String TEST_LIST_3_MEANING = "Other";

    public final static Long TEST_OPTION_1_ID = 16L;
    public final static String TEST_OPTION_1_VALUE = "test-option-value-1";
    public final static Integer TEST_OPTION_1_ORDER = 10;

    public final static Long TEST_OPTION_2_ID = 32L;
    public final static String TEST_OPTION_2_VALUE = "test-option-value-2";
    public final static Integer TEST_OPTION_2_ORDER = 20;

    public final static Long TEST_OPTION_3_ID = 64L;
    public final static String TEST_OPTION_3_VALUE = "test-option-value-3";
    public final static Integer TEST_OPTION_3_ORDER = 10;

    public final static Long TEST_OPTION_4_ID = 128L;
    public final static String TEST_OPTION_4_VALUE = "test-option-value-4";
    public final static Integer TEST_OPTION_4_ORDER = 20;
    
    public final static Long TEST_LIST_ADD_ACTION_ID = 256L;
    public final static String TEST_LIST_ADD_ACTION_NAME = "test-select-list-add-action-name";
    public final static String TEST_LIST_ADD_ACTION_DESCRIPTION = "test-select-list-add-action-description";

    public final static String TEST_OPTION_ALPHA_VALUE = "ALPHA";
    public final static String TEST_OPTION_ALPHA_MEANING = "Alpha";

    public final static String TEST_OPTION_BRAVO_VALUE = "BRAVO";
    public final static String TEST_OPTION_BRAVO_MEANING = "Bravo";

    public final static String TEST_OPTION_CHARLIE_VALUE = "CHARLIE";
    public final static String TEST_OPTION_CHARLIE_MEANING = "Charlie";

    public final static String TEST_OPTION_OYSTER_GOODWILL_REASON_SERVICE_DELAY_VALUE = "8";
    public final static String TEST_OPTION_OYSTER_GOODWILL_REASON_SERVICE_DELAY_MEANING = "Service Delay";

    public final static String TEST_OPTION_OYSTER_GOODWILL_REASON_OTHER_VALUE = "9";
    public final static String TEST_OPTION_OYSTER_GOODWILL_REASON_OTHER_MEANING = "Other";
    
    public final static String TEST_OPTION_ADD_ACTION_VALUE = "add";
    public final static String TEST_OPTION_ADD_ACTION_MEANING = "add payment action";

    public final static String TEST_OPTION_VALUE_WITH_MARKUP = "<script>alert(\"test-value\");</script>";
    public final static String TEST_OPTION_MEANING_WITH_MARKUP = "<script>alert(\"test-meaning\");</script>";

    public static SelectList getTestSelectList1() {
        SelectList entity = new SelectList();
        entity.setId(TEST_LIST_1_ID);
        entity.setName(TEST_LIST_1_NAME);
        entity.setDescription(TEST_LIST_1_DESCRIPTION);
        entity.getOptions().add(getTestSelectListOption1());
        entity.getOptions().add(getTestSelectListOption2());
        return entity;
    }

    public static SelectList getTestSelectList2() {
        SelectList entity = new SelectList();
        entity.setId(TEST_LIST_2_ID);
        entity.setName(TEST_LIST_2_NAME);
        entity.setDescription(TEST_LIST_2_DESCRIPTION);
        entity.getOptions().add(getTestSelectListOption3());
        entity.getOptions().add(getTestSelectListOption4());
        return entity;
    }

    public static SelectListOption getTestSelectListOption1() {
        return getTestSelectListOption(TEST_OPTION_1_ID, TEST_OPTION_1_VALUE, TEST_OPTION_1_ORDER);
    }

    public static SelectListOption getTestSelectListOption2() {
        return getTestSelectListOption(TEST_OPTION_2_ID, TEST_OPTION_2_VALUE, TEST_OPTION_2_ORDER);
    }

    public static SelectListOption getTestSelectListOption3() {
        return getTestSelectListOption(TEST_OPTION_3_ID, TEST_OPTION_3_VALUE, TEST_OPTION_3_ORDER);
    }

    public static SelectListOption getTestSelectListOption4() {
        return getTestSelectListOption(TEST_OPTION_4_ID, TEST_OPTION_4_VALUE, TEST_OPTION_4_ORDER);
    }

    public static SelectListOption getTestSelectListOption(Long id, String value, Integer displayOrder) {
        SelectListOption entity = new SelectListOption();
        entity.setId(id);
        entity.setValue(value);
        entity.setDisplayOrder(displayOrder);
        return entity;
    }

    public static SelectListDTO getTestSelectListDTO() {
        SelectListDTO dto = new SelectListDTO();
        dto.setId(TEST_LIST_1_ID);
        dto.setName(TEST_LIST_1_NAME);
        dto.setDescription(TEST_LIST_1_DESCRIPTION);
        dto.getOptions().add(getTestSelectListOptionDTO1());
        dto.getOptions().add(getTestSelectListOptionDTO2());
        return dto;
    }

    public static SelectListDTO getTestSelectListDTO3() {
        SelectListDTO dto = new SelectListDTO();
        dto.setId(TEST_LIST_3_ID);
        dto.setName(TEST_LIST_3_NAME);
        dto.setDescription(TEST_LIST_3_DESCRIPTION);
        dto.getOptions().add(getTestSelectListOptionDTOOysterGoodwillReasonServiceDelay());
        dto.getOptions().add(getTestSelectListOptionDTOOysterGoodwillReasonOther());
        return dto;
    }

    public static SelectListDTO getTestSelectListDTOWithMarkUp() {
        SelectListDTO dto = new SelectListDTO();
        dto.setId(TEST_LIST_1_ID);
        dto.setName(TEST_LIST_1_NAME);
        dto.setDescription(TEST_LIST_1_DESCRIPTION);
        dto.getOptions().add(getTestSelectListOptionDTOWithMarkUp());
        return dto;
    }

    public static SelectListDTO getTestSelectListDTOWithAddOption() {
        SelectListDTO dto = new SelectListDTO();
        dto.setId(TEST_LIST_ADD_ACTION_ID);
        dto.setName(TEST_LIST_ADD_ACTION_NAME);
        dto.setDescription(TEST_LIST_ADD_ACTION_DESCRIPTION);
        dto.getOptions().add(getTestSelectListOptionDTOAddAction());
        return dto;
    }

    public static SelectListOptionDTO getTestSelectListOptionDTO1() {
        return getTestSelectListOptionDTO(TEST_OPTION_1_ID, TEST_OPTION_1_VALUE, TEST_OPTION_1_ORDER);
    }

    public static SelectListOptionDTO getTestSelectListOptionDTO2() {
        return getTestSelectListOptionDTO(TEST_OPTION_2_ID, TEST_OPTION_2_VALUE, TEST_OPTION_2_ORDER);
    }

    public static SelectListOptionDTO getTestSelectListOptionDTOAlpha() {
        return getTestSelectListOptionDTO(TEST_OPTION_ALPHA_VALUE, TEST_OPTION_ALPHA_MEANING);
    }

    public static SelectListOptionDTO getTestSelectListOptionDTOBravo() {
        return getTestSelectListOptionDTO(TEST_OPTION_BRAVO_VALUE, TEST_OPTION_BRAVO_MEANING);
    }

    public static SelectListOptionDTO getTestSelectListOptionDTOCharlie() {
        return getTestSelectListOptionDTO(TEST_OPTION_CHARLIE_VALUE, TEST_OPTION_CHARLIE_MEANING);
    }

    public static SelectListOptionDTO getTestSelectListOptionDTOOysterGoodwillReasonServiceDelay() {
        return getTestSelectListOptionDTO(TEST_OPTION_OYSTER_GOODWILL_REASON_SERVICE_DELAY_VALUE,
                        TEST_OPTION_OYSTER_GOODWILL_REASON_SERVICE_DELAY_MEANING);
    }

    public static SelectListOptionDTO getTestSelectListOptionDTOOysterGoodwillReasonOther() {
        return getTestSelectListOptionDTO(TEST_OPTION_OYSTER_GOODWILL_REASON_OTHER_VALUE, TEST_OPTION_OYSTER_GOODWILL_REASON_OTHER_MEANING);
    }
    
    public static SelectListOptionDTO getTestSelectListOptionDTOAddAction() {
        return getTestSelectListOptionDTO(TEST_OPTION_ADD_ACTION_VALUE, TEST_OPTION_ADD_ACTION_MEANING);
    }

    public static SelectListOptionDTO getTestSelectListOptionDTOWithMarkUp() {
        return getTestSelectListOptionDTO(TEST_OPTION_1_ID, TEST_OPTION_VALUE_WITH_MARKUP, TEST_OPTION_MEANING_WITH_MARKUP,
                TEST_OPTION_1_ORDER);
    }
    public static List<SelectListOptionDTO> getTestSelectListOptionDTOs() {
        List<SelectListOptionDTO> options = new ArrayList<SelectListOptionDTO>();
        options.add(getTestSelectListOptionDTO1());
        options.add(getTestSelectListOptionDTO2());
        return options;
    }

    public static SelectListOptionDTO getTestSelectListOptionDTO(Long id, String value, Integer displayOrder) {
        SelectListOptionDTO dto = new SelectListOptionDTO();
        dto.setId(id);
        dto.setValue(value);
        dto.setDisplayOrder(displayOrder);
        return dto;
    }

    public static SelectListOptionDTO getTestSelectListOptionDTO(String value, String meaning) {
        SelectListOptionDTO dto = new SelectListOptionDTO();
        dto.setValue(value);
        dto.setMeaning(meaning);
        return dto;
    }

    public static SelectListOptionDTO getTestSelectListOptionDTO(Long id, String value, String meaning, Integer displayOrder) {
        SelectListOptionDTO dto = new SelectListOptionDTO();
        dto.setId(id);
        dto.setValue(value);
        dto.setMeaning(meaning);
        dto.setDisplayOrder(displayOrder);
        return dto;
    }

}
