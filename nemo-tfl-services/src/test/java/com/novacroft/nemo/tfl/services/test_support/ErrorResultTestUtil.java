package com.novacroft.nemo.tfl.services.test_support;

import java.util.ArrayList;
import java.util.List;

import com.novacroft.nemo.tfl.services.transfer.ErrorResult;
import com.novacroft.nemo.tfl.services.transfer.Error;

public class ErrorResultTestUtil {
    public static String ERROR_FIELD_1 = "testOne";
    public static final String ERROR_DESCRIPTION_1 = "Error Description for Test One";
    public static final Long ERROR_ID = 1l;
    
    public static ErrorResult getTestErrorResult1(){
        ErrorResult result = new ErrorResult();
        result.setErrors(getErrorsList());
        return result;
    }
    
    public static List<Error> getErrorsList(){
        List<Error> errorsList = new ArrayList<>();
        errorsList.add(getTestError1());
        
        return errorsList;
    }
    
    public static Error getTestError1(){
        return getError(ERROR_DESCRIPTION_1, ERROR_FIELD_1);
    }
    
    public static Error getError(String description, String field){
        Error error = new Error();
        error.setDescription(description);;
        error.setField(field);
        return error;
    }
    
}
