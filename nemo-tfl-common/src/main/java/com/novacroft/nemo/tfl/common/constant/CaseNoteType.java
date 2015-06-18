package com.novacroft.nemo.tfl.common.constant;

public enum CaseNoteType {
    HISTORIC("Historic"),
    AGENT("Agent");
  
    private CaseNoteType(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }
    
    public static CaseNoteType find(String code) {
        if (code != null) {
            for (CaseNoteType caseNoteType : CaseNoteType.values()) {
                if (code.equalsIgnoreCase(caseNoteType.code)) {
                    return caseNoteType;
                }
            }
        }
        return null;
    }
}