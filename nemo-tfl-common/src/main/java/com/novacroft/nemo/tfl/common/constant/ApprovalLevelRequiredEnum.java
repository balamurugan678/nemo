package com.novacroft.nemo.tfl.common.constant;

public enum  ApprovalLevelRequiredEnum{
    
    NONE(0)
    ,FIRST_STAGE(1)
    ,SECOND_STAGE(2)
    ;

    private Integer code;

    ApprovalLevelRequiredEnum(Integer code) {
        this.code = code;
    }

    public Integer code() {
        return code;
    }
    
    public static ApprovalLevelRequiredEnum find(Integer code) {
        if (code != null) {
            for (ApprovalLevelRequiredEnum priority : ApprovalLevelRequiredEnum.values()) {
                if (code == priority.code) {
                    return priority;
                }
            }
        }
        return null;
    }


}