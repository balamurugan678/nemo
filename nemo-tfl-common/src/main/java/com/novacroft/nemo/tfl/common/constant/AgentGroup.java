package com.novacroft.nemo.tfl.common.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Agent Group
 */
public enum AgentGroup {
    AGENT("AgentList", "Agent"),
    FIRST_STAGE_APPROVER("ApprovalList", "FirstStageApprovers"),
    SECOND_STAGE_APPROVER("ApprovalListStage2", "SecondStageApprovers"),
    SUPERVISOR("SupervisorList", "Supervisor"),
    EXCEPTIONS("ExceptionList", "ExceptionApprovers");
  
    private AgentGroup(String listCode, String code) {
        this.listCode = listCode;
        this.code = code;
    }

    private String listCode;
    private String code;
    
    public String listCode() {
        return this.listCode;
    }

    public String code() {
        return this.code;
    }
    
    public List<String> getValues() {
        List<String> values = new ArrayList<String>();
        values.add(this.listCode);
        values.add(this.code);
        return values;
    }
    
    public static AgentGroup find(String s) {
        for(AgentGroup agentGroup: AgentGroup.values()) {
            if(agentGroup.getValues().contains(s)) {
                return agentGroup;
            }
        }
        return null;
    }
}
