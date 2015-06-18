package com.novacroft.nemo.tfl.common.constant;

public enum RefundDepartmentEnum {

    OYSTER("Oyster")
    , LU("Lu")
    , CCS("Ccs")
    , RCL("Rcl")
    ;

    private String code;

    RefundDepartmentEnum(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
    
    public static RefundDepartmentEnum find(String code) {
        if (code != null) {
            for (RefundDepartmentEnum dept : RefundDepartmentEnum.values()) {
                if (code.equalsIgnoreCase(dept.code)) {
                    return dept;
                }
            }
        }
        return null;
    }
}