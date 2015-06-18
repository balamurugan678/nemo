package com.novacroft.nemo.common.support;

import static com.novacroft.nemo.common.utils.ObjectUtil.isObjectArgsNotBlank;

/**
 * Build a string by appending strings and formatted strings.  Provides a fluent style API.
 *
 * <p>This is likely to be useful when putting together data for the information attribute on events.</p>
 */
public class InformationBuilder {
    protected StringBuilder stringBuilder = new StringBuilder();

    /**
     * Format a string, using <code>String.format</code> and append the result.
     *
     * @param format Format string.
     * @param args   Arguments referenced by the format specifiers in the format string.
     * @return Reference to this object.
     */
    public InformationBuilder append(String format, Object... args) {
        this.stringBuilder.append(String.format(format, args));
        return this;
    }

    /**
     * Format a string, using <code>String.format</code> and append the result if the arguments are not blank.
     *
     * @param format Format string.
     * @param args   Arguments referenced by the format specifiers in the format string.
     * @return Reference to this object.
     */
    public InformationBuilder appendIfNotBlank(String format, Object... args) {
        if (isObjectArgsNotBlank(args)) {
            this.stringBuilder.append(String.format(format, args));
        }
        return this;
    }

    /**
     * Append a string.
     *
     * @param str String to append.
     * @return Reference to this object.
     */
    public InformationBuilder append(String str) {
        this.stringBuilder.append(str);
        return this;
    }

    /**
     * Get the built string.
     *
     * @return Built string.
     */
    public String toString() {
        return this.stringBuilder.toString();
    }

}
