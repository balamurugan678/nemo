package com.novacroft.nemo.tfl.common.transfer.cyber_source;

import java.util.HashMap;
import java.util.Map;

/**
 * Base implementation for CyberSource payment gateway API request and reply that uses a map for the arguments
 */
public abstract class BaseCyberSourceApiDTO {
    protected Map<String, String> arguments = new HashMap<String, String>();

    protected String get(String argumentName) {
        return (this.arguments.containsKey(argumentName)) ? this.arguments.get(argumentName) : "";
    }

    protected void set(String argumentName, String argumentValue) {
        this.arguments.put(argumentName, argumentValue);
    }

    public Map<String, String> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, String> arguments) {
        this.arguments = arguments;
    }
}
