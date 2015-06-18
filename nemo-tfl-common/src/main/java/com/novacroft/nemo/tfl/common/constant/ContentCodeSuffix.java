package com.novacroft.nemo.tfl.common.constant;

/**
 * Content (message) code suffix constants.
 */
public enum ContentCodeSuffix {
    LABEL("label"),
    TEXT("text"),
    ERROR("error"),
    HEADING("heading"),
    BUTTON("button"),
    TIP("tip"),
    ABBR("abbr"),
    TITLE("title"),
    HINT("hint"),
    URL("url"),
    LINK("link"),
    POPUP("popup"),
    PLACEHOLDER("placeholder"),
    OPTION("option"),
    BREADCRUMB("breadcrumb");

    private ContentCodeSuffix(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return this.code;
    }
}
