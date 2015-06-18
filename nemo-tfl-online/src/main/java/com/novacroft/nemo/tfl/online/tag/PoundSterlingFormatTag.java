package com.novacroft.nemo.tfl.online.tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Currency;

import static com.novacroft.nemo.common.constant.LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE;
import static com.novacroft.nemo.common.constant.LocaleConstant.UNITED_KINGDOM_CURRENCY_CODE;
import static com.novacroft.nemo.common.utils.CurrencyUtil.convertPenceToPounds;

/**
 * Format Pound Sterling (GBP) currency amounts
 */
public class PoundSterlingFormatTag extends TagSupport {
    private static final Logger logger = LoggerFactory.getLogger(PoundSterlingFormatTag.class);
    protected static final String POUND_HTML_SYMBOL = "'&pound;'";
    protected static final String COMMA = ",";
    protected static final String COMMA_HTML_SYMBOL = "&#44;";
    protected static final String BASE_PATTERN = "%1$s###,###,###,##0.00;-%1$s###,###,###,##0.00";
    protected Float amount;
    protected Boolean amountInPence = Boolean.TRUE;
    protected Boolean showPoundSign = Boolean.TRUE;

    public int doStartTag() throws JspException {
        if (this.amount == null) {
            return SKIP_BODY;
        }
        try {
            DecimalFormat poundSterlingCurrencyFormatter =
                    (DecimalFormat) DecimalFormat.getInstance(ENGLISH_UNITED_KINGDOM_LOCALE);
            poundSterlingCurrencyFormatter.setCurrency(Currency.getInstance(UNITED_KINGDOM_CURRENCY_CODE));
            poundSterlingCurrencyFormatter.applyPattern(getPattern());
            pageContext.getOut().print(replaceCommasWithCommaHtmlEntities(
                    poundSterlingCurrencyFormatter.format(getAmountInPoundsAndPence(this.amount))));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new JspException(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

    protected Float getAmountInPoundsAndPence(Float value) {
        return (this.amountInPence) ? convertPenceToPounds(value) : value;
    }

    protected String getPattern() {
        if (this.showPoundSign) {
            return String.format(BASE_PATTERN, POUND_HTML_SYMBOL);
        } else {
            return String.format(BASE_PATTERN, "");
        }
    }

    protected String replaceCommasWithCommaHtmlEntities(String value) {
        return value.replaceAll(COMMA, COMMA_HTML_SYMBOL);
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setAmountInPence(Boolean amountInPence) {
        this.amountInPence = amountInPence;
    }

    public void setShowPoundSign(Boolean showPoundSign) {
        this.showPoundSign = showPoundSign;
    }
}
