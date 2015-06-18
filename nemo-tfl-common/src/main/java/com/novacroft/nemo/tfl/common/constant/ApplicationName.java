package com.novacroft.nemo.tfl.common.constant;

public enum ApplicationName {

NEMO_TFL_ONLINE("nemo-tfl-online"),NEMO_TFL_INNOVATOR("nemo-tfl-innovator");
private String code;

private ApplicationName(String code){
this.code=code;
}

public String code() {
       return code;
   }
}