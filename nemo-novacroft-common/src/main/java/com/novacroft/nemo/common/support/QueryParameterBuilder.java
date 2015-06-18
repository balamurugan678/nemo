package com.novacroft.nemo.common.support;

import java.util.HashMap;
import java.util.Map;

public class QueryParameterBuilder {
    protected Map<String, Object> parameters = new HashMap<String, Object>();
    protected static final String LIKE_WILDCARD = "%";

    public QueryParameterBuilder addParameter(String name, Object value) {
        this.parameters.put(name, value);
        return this;
    }

    public QueryParameterBuilder addParameter(Boolean condition, String name, Object value) {
        if (condition) {
            addParameter(name, value);
        }
        return this;
    }

    public QueryParameterBuilder addParameter(Boolean condition, String name, String value, Boolean useLike) {
        if (condition) {
            addParameter(name, useLike ? value + LIKE_WILDCARD : value);
        }
        return this;
    }

    public QueryParameterBuilder addParameterSurroundedByWildcard(Boolean condition, String name, String value, Boolean useLike) {
        if (condition) {
            addParameter(name, useLike ? LIKE_WILDCARD + value + LIKE_WILDCARD : value);
        }
        return this;
    }

    public Map<String, Object> toMap() {
        return this.parameters;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : this.parameters.keySet()) {
            stringBuilder.append(key);
            stringBuilder.append(" = ");
            stringBuilder.append(this.parameters.get(key).toString());
            stringBuilder.append("; ");
        }
        return stringBuilder.toString();
    }
}
