package com.novacroft.nemo.common.validator;

import static com.novacroft.nemo.common.constant.CommonContentCode.INVALID_POSTCODE;
import static com.novacroft.nemo.common.constant.CommonPageCommandAttribute.FIELD_POSTCODE;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.command.CommonPostcodeCmd;

/**
 * Validate UK postcode
 * <p>
 * The Royal Mail PAF programmers' guide (edition 7, version 5.0) provides a specification of the post code. UK postcodes are in two parts with the parts separated by a space. The first part is known
 * as the outward code and the second part is known as the inward code.
 * </p>
 * 
 * @see <a href="http://www.royalmail.com/sites/default/files/docs/pdf/programmers_guide_edition_7_v5 .pdf">programmers_guide_edition_7_v5.pdf</a>
 */
@Component("postcodeValidator")
public class PostcodeValidator extends BaseValidator {
    protected static final String OUTWARD_1_PATTERN = "[a-zA-Z]{1}[0-9]{1}";
    protected static final String OUTWARD_2_PATTERN = "[a-zA-Z]{1}[0-9]{2}";
    protected static final String OUTWARD_3_PATTERN = "[a-zA-Z]{2}[0-9]{1}";
    protected static final String OUTWARD_4_PATTERN = "[a-zA-Z]{2}[0-9]{2}";
    protected static final String OUTWARD_5_PATTERN = "[a-zA-Z]{1}[0-9]{1}[a-zA-Z]{1}";
    protected static final String OUTWARD_6_PATTERN = "[a-zA-Z]{2}[0-9]{1}[a-zA-Z]{1}";

    protected static final String INWARD_PATTERN = "[0-9]{1}[a-zA-Z]{2}";

    protected static final String POSTCODE_1_PATTERN = OUTWARD_1_PATTERN + " " + INWARD_PATTERN;
    protected static final String POSTCODE_2_PATTERN = OUTWARD_2_PATTERN + " " + INWARD_PATTERN;
    protected static final String POSTCODE_3_PATTERN = OUTWARD_3_PATTERN + " " + INWARD_PATTERN;
    protected static final String POSTCODE_4_PATTERN = OUTWARD_4_PATTERN + " " + INWARD_PATTERN;
    protected static final String POSTCODE_5_PATTERN = OUTWARD_5_PATTERN + " " + INWARD_PATTERN;
    protected static final String POSTCODE_6_PATTERN = OUTWARD_6_PATTERN + " " + INWARD_PATTERN;
    protected static final String POSTCODE_1_PATTERN_WITHOUT_SPACE = OUTWARD_1_PATTERN + INWARD_PATTERN;
    protected static final String POSTCODE_2_PATTERN_WITHOUT_SPACE = OUTWARD_2_PATTERN + INWARD_PATTERN;
    protected static final String POSTCODE_3_PATTERN_WITHOUT_SPACE = OUTWARD_3_PATTERN + INWARD_PATTERN;
    protected static final String POSTCODE_4_PATTERN_WITHOUT_SPACE = OUTWARD_4_PATTERN + INWARD_PATTERN;
    protected static final String POSTCODE_5_PATTERN_WITHOUT_SPACE = OUTWARD_5_PATTERN + INWARD_PATTERN;
    protected static final String POSTCODE_6_PATTERN_WITHOUT_SPACE = OUTWARD_6_PATTERN + INWARD_PATTERN;

    protected static final String[] POSTCODE_PATTERNS = new String[] { POSTCODE_1_PATTERN, POSTCODE_2_PATTERN, POSTCODE_3_PATTERN, POSTCODE_4_PATTERN, POSTCODE_5_PATTERN, POSTCODE_6_PATTERN, POSTCODE_1_PATTERN_WITHOUT_SPACE, POSTCODE_2_PATTERN_WITHOUT_SPACE, POSTCODE_3_PATTERN_WITHOUT_SPACE, POSTCODE_4_PATTERN_WITHOUT_SPACE, POSTCODE_5_PATTERN_WITHOUT_SPACE, POSTCODE_6_PATTERN_WITHOUT_SPACE };

    @Override
    public boolean supports(Class<?> targetClass) {
        return CommonPostcodeCmd.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        rejectIfMandatoryFieldEmpty(errors, FIELD_POSTCODE);
        if (null == errors.getFieldError(FIELD_POSTCODE)) {
            String postcode = ((CommonPostcodeCmd) target).getPostcode();
            validate(errors, postcode);
        }
    }

	public boolean validate(String postcode) {
        return validatePostcode(postcode);
    }

    protected boolean validatePostcode(String postcode) {
        if (StringUtils.isBlank(postcode)) {
            return false;
        }
        for (String postcodePattern : POSTCODE_PATTERNS) {
            if (postcode.matches(postcodePattern)) {
                return true;
            }
        }
        return false;
    }
    
    public void validate(Errors errors, String postcode){
        if (!validate(postcode)) {
            errors.rejectValue(FIELD_POSTCODE, INVALID_POSTCODE.errorCode());
        }
    }
}