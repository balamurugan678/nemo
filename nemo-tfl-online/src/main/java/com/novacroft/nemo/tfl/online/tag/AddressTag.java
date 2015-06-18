package com.novacroft.nemo.tfl.online.tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novacroft.nemo.common.transfer.CountryDTO;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.util.List;

import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.appendPrefixAndValue;
import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.formatAddress;

/**
 * JSP Tag to render a formatted address
 */
public class AddressTag extends TagSupport {
    private static final long serialVersionUID = -9009063706539524855L;
    static final Logger logger = LoggerFactory.getLogger(AddressTag.class);
    protected static final String LINE_BREAK = "<br/>";
    protected String houseNameNumber;
    protected String street;
    protected String town;
    protected CountryDTO country;
    protected String postcode;

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            StringBuilder formattedAddress = new StringBuilder();
            List<String> addressLines = formatAddress(houseNameNumber, street, town, postcode, country);
            for (String addressLine : addressLines) {
                appendPrefixAndValue(formattedAddress, addressLine, LINE_BREAK);
            }
            out.print(formattedAddress.toString());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new JspException(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

    public void setHouseNameNumber(String houseNameNumber) {
        this.houseNameNumber = houseNameNumber;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
