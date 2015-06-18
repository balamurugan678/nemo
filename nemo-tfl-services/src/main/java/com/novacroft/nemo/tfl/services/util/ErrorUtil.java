package com.novacroft.nemo.tfl.services.util;

import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.common.utils.StringUtil.SINGLE_QUOTE;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.ObjectError;

import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.services.transfer.Error;
import com.novacroft.nemo.tfl.services.transfer.ErrorResult;

public final class ErrorUtil {

    private static final Map<String, ContentCode> CONTENT_ERROR_CODE_LOOKUP_MAP = new HashMap<String, ContentCode>();

    static {
        for (ContentCode contentCode : EnumSet.allOf(ContentCode.class)){
            CONTENT_ERROR_CODE_LOOKUP_MAP.put(contentCode.errorCode(), contentCode);
        }
    }
    
    
    public static ErrorResult addErrorToList(ErrorResult errorResult, Long id, String field, String description) {
        if (errorResult == null) {
            errorResult = new ErrorResult();
        }
        List<Error> errors = errorResult.getErrors();
        if (errors == null) {
            errors = new ArrayList<Error>();
            errorResult.setErrors(errors);
        }

        Error error = new Error();
        error.setId(id);
        error.setField(field);
        error.setDescription(description);

        errors.add(error);
        return errorResult;
    }

    public static Error getError(String field, String description) {
        Error error = new Error();
        error.setField(field);
        error.setDescription(description);
        return error;
    }

    public static ErrorResult addErrorToList(ErrorResult errorResult, Error error) {
        List<Error> errors = errorResult.getErrors();
        if (errors == null) {
            errors = new ArrayList<Error>();
            errorResult.setErrors(errors);
        }
        errors.add(error);
        return errorResult;
    }

    public static ErrorResult addValidationErrorsToList(ErrorResult errorResult, List<ObjectError> validationErrors) {
        if (validationErrors != null && validationErrors.size() > 0) {
            if (errorResult == null) {
                errorResult = new ErrorResult();
            }
            List<Error> errors = errorResult.getErrors();
            if (errors == null) {
                errors = new ArrayList<Error>();
                errorResult.setErrors(errors);
            }
            ContentCode contentCode = null;
            for (ObjectError objectError : validationErrors) {
                contentCode = getContentCodeEnum(objectError.getCode());
                errors.add(getError(getErrorField(objectError.toString()), null!=contentCode?contentCode.name():objectError.getCode()));
            }
        }
        return errorResult;
    }

    private ErrorUtil() {
    }
    
    private static String getErrorField(String str) {
        String str1 = str.substring(str.indexOf(SINGLE_QUOTE), str.lastIndexOf(SINGLE_QUOTE));
        String str2 = str1.substring(str1.lastIndexOf(SINGLE_QUOTE));
        return str2.replace(SINGLE_QUOTE, EMPTY_STRING);
    }
    
    public static boolean hasErrors(ErrorResult errorResult){
        return null!=errorResult && null!=errorResult.getErrors() && !errorResult.getErrors().isEmpty();
    }
    
    public static ContentCode getContentCodeEnum(String contentErrorCode){
        return CONTENT_ERROR_CODE_LOOKUP_MAP.get(contentErrorCode);
    }
}
