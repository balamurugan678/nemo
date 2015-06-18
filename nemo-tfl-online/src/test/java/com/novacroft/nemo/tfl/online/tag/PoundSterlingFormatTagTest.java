package com.novacroft.nemo.tfl.online.tag;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;

import javax.servlet.jsp.JspException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

/**
 * PoundSterlingFormatTag unit tests
 */
public class PoundSterlingFormatTagTest {
    @Test
    public void shouldFormatPositiveAmountInPenceWithSymbol() throws JspException, UnsupportedEncodingException {
        Float testAmount = 1234F;
        String expectedResult = "&pound;12.34";
        MockServletContext mockServletContext = new MockServletContext();
        MockPageContext mockPageContext = new MockPageContext(mockServletContext);
        PoundSterlingFormatTag tag = new PoundSterlingFormatTag();
        tag.setPageContext(mockPageContext);
        tag.setAmount(testAmount);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        assertEquals(expectedResult.trim(), result.trim());
    }

    @Test
    public void shouldFormatPositiveHighAmountInPenceWithSymbol() throws JspException, UnsupportedEncodingException {
        Float testAmount = 12345678F;
        String expectedResult = "&pound;123&#44;456.78";
        MockServletContext mockServletContext = new MockServletContext();
        MockPageContext mockPageContext = new MockPageContext(mockServletContext);
        PoundSterlingFormatTag tag = new PoundSterlingFormatTag();
        tag.setPageContext(mockPageContext);
        tag.setAmount(testAmount);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        assertEquals(expectedResult.trim(), result.trim());
    }

    @Test
    public void shouldFormatNegativeAmountInPenceWithSymbol() throws JspException, UnsupportedEncodingException {
        Float testAmount = -1234F;
        String expectedResult = "-&pound;12.34";
        MockServletContext mockServletContext = new MockServletContext();
        MockPageContext mockPageContext = new MockPageContext(mockServletContext);
        PoundSterlingFormatTag tag = new PoundSterlingFormatTag();
        tag.setPageContext(mockPageContext);
        tag.setAmount(testAmount);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        assertEquals(expectedResult.trim(), result.trim());
    }

    @Test
    public void shouldFormatPositiveWholePoundAmountInPenceWithSymbol() throws JspException, UnsupportedEncodingException {
        Float testAmount = 5600F;
        String expectedResult = "&pound;56.00";
        MockServletContext mockServletContext = new MockServletContext();
        MockPageContext mockPageContext = new MockPageContext(mockServletContext);
        PoundSterlingFormatTag tag = new PoundSterlingFormatTag();
        tag.setPageContext(mockPageContext);
        tag.setAmount(testAmount);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        assertEquals(expectedResult.trim(), result.trim());
    }

    @Test
    public void shouldFormatNegativeWholePoundAmountInPenceWithSymbol() throws JspException, UnsupportedEncodingException {
        Float testAmount = -5600F;
        String expectedResult = "-&pound;56.00";
        MockServletContext mockServletContext = new MockServletContext();
        MockPageContext mockPageContext = new MockPageContext(mockServletContext);
        PoundSterlingFormatTag tag = new PoundSterlingFormatTag();
        tag.setPageContext(mockPageContext);
        tag.setAmount(testAmount);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        assertEquals(expectedResult.trim(), result.trim());
    }

    @Test
    public void shouldFormatPositiveTenPenceAmountInPenceWithSymbol() throws JspException, UnsupportedEncodingException {
        Float testAmount = 7890F;
        String expectedResult = "&pound;78.90";
        MockServletContext mockServletContext = new MockServletContext();
        MockPageContext mockPageContext = new MockPageContext(mockServletContext);
        PoundSterlingFormatTag tag = new PoundSterlingFormatTag();
        tag.setPageContext(mockPageContext);
        tag.setAmount(testAmount);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        assertEquals(expectedResult.trim(), result.trim());
    }

    @Test
    public void shouldFormatNegativeTenPenceAmountInPenceWithSymbol() throws JspException, UnsupportedEncodingException {
        Float testAmount = -7890F;
        String expectedResult = "-&pound;78.90";
        MockServletContext mockServletContext = new MockServletContext();
        MockPageContext mockPageContext = new MockPageContext(mockServletContext);
        PoundSterlingFormatTag tag = new PoundSterlingFormatTag();
        tag.setPageContext(mockPageContext);
        tag.setAmount(testAmount);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        assertEquals(expectedResult.trim(), result.trim());
    }

    @Test
    public void shouldFormatPositiveAmountInPenceWithoutSymbol() throws JspException, UnsupportedEncodingException {
        Float testAmount = 1234F;
        String expectedResult = "12.34";
        MockServletContext mockServletContext = new MockServletContext();
        MockPageContext mockPageContext = new MockPageContext(mockServletContext);
        PoundSterlingFormatTag tag = new PoundSterlingFormatTag();
        tag.setPageContext(mockPageContext);
        tag.setAmount(testAmount);
        tag.setShowPoundSign(Boolean.FALSE);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        assertEquals(expectedResult.trim(), result.trim());
    }

    @Test
    public void shouldFormatNegativeAmountInPenceWithoutSymbol() throws JspException, UnsupportedEncodingException {
        Float testAmount = -1234F;
        String expectedResult = "-12.34";
        MockServletContext mockServletContext = new MockServletContext();
        MockPageContext mockPageContext = new MockPageContext(mockServletContext);
        PoundSterlingFormatTag tag = new PoundSterlingFormatTag();
        tag.setPageContext(mockPageContext);
        tag.setAmount(testAmount);
        tag.setShowPoundSign(Boolean.FALSE);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        assertEquals(expectedResult.trim(), result.trim());
    }

    @Test
    public void shouldFormatPositiveAmountInPoundsAndPenceWithSymbol() throws JspException, UnsupportedEncodingException {
        Float testAmount = 12.34F;
        String expectedResult = "&pound;12.34";
        MockServletContext mockServletContext = new MockServletContext();
        MockPageContext mockPageContext = new MockPageContext(mockServletContext);
        PoundSterlingFormatTag tag = new PoundSterlingFormatTag();
        tag.setPageContext(mockPageContext);
        tag.setAmount(testAmount);
        tag.setAmountInPence(Boolean.FALSE);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        assertEquals(expectedResult.trim(), result.trim());
    }

    @Test
    public void shouldFormatNegativeAmountInPoundsAndPenceWithSymbol() throws JspException, UnsupportedEncodingException {
        Float testAmount = -12.34F;
        String expectedResult = "-&pound;12.34";
        MockServletContext mockServletContext = new MockServletContext();
        MockPageContext mockPageContext = new MockPageContext(mockServletContext);
        PoundSterlingFormatTag tag = new PoundSterlingFormatTag();
        tag.setPageContext(mockPageContext);
        tag.setAmount(testAmount);
        tag.setAmountInPence(Boolean.FALSE);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        assertEquals(expectedResult.trim(), result.trim());
    }

    @Test
    public void shouldFormatPositiveAmountInPoundsAndPenceWithoutSymbol() throws JspException, UnsupportedEncodingException {
        Float testAmount = 12.34F;
        String expectedResult = "12.34";
        MockServletContext mockServletContext = new MockServletContext();
        MockPageContext mockPageContext = new MockPageContext(mockServletContext);
        PoundSterlingFormatTag tag = new PoundSterlingFormatTag();
        tag.setPageContext(mockPageContext);
        tag.setAmount(testAmount);
        tag.setAmountInPence(Boolean.FALSE);
        tag.setShowPoundSign(Boolean.FALSE);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        assertEquals(expectedResult.trim(), result.trim());
    }

    @Test
    public void shouldFormatNegativeAmountInPoundsAndPenceWithoutSymbol() throws JspException, UnsupportedEncodingException {
        Float testAmount = -12.34F;
        String expectedResult = "-12.34";
        MockServletContext mockServletContext = new MockServletContext();
        MockPageContext mockPageContext = new MockPageContext(mockServletContext);
        PoundSterlingFormatTag tag = new PoundSterlingFormatTag();
        tag.setPageContext(mockPageContext);
        tag.setAmount(testAmount);
        tag.setAmountInPence(Boolean.FALSE);
        tag.setShowPoundSign(Boolean.FALSE);
        tag.doStartTag();
        String result = ((MockHttpServletResponse) mockPageContext.getResponse()).getContentAsString();
        assertEquals(expectedResult.trim(), result.trim());
    }
}
