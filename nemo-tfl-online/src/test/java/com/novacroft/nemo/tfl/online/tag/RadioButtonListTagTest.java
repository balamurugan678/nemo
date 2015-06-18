package com.novacroft.nemo.tfl.online.tag;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.IOException;

import static com.novacroft.nemo.test_support.SelectListTestUtil.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.web.util.HtmlUtils.htmlEscape;

public class RadioButtonListTagTest {
    private RadioButtonListTag tag;
    private PageContext mockPageContext;
    private JspWriter mockJspWriter;
    private ServletRequest mockServletRequest;

    @Before
    public void setUp() throws JspException {
        this.tag = mock(RadioButtonListTag.class);
        when(this.tag.doStartTag()).thenCallRealMethod();
        setField(this.tag, "showHeadingLabel", Boolean.FALSE);
        setField(this.tag, "showHint", Boolean.FALSE);
        setField(this.tag, "selectList", getTestSelectListDTO());
        setField(this.tag, "useManagedContentForMeanings", Boolean.FALSE);
        setField(this.tag, "selectedValue", EMPTY);

        this.mockPageContext = mock(PageContext.class);
        setField(this.tag, "pageContext", this.mockPageContext);

        this.mockJspWriter = mock(JspWriter.class);
        when(this.mockPageContext.getOut()).thenReturn(this.mockJspWriter);

        this.mockServletRequest = mock(ServletRequest.class);
        when(this.mockPageContext.getRequest()).thenReturn(this.mockServletRequest);
        when(this.mockServletRequest.getAttribute(anyString())).thenReturn("test-page");
    }

    @Test(expected = JspException.class)
    public void doStartTagShouldError() throws IOException, JspException {
        doThrow(new IOException()).when(this.mockJspWriter).println(anyString());
        setField(this.tag, "showHeadingLabel", Boolean.TRUE);
        this.tag.doStartTag();
    }

    @Test
    public void doStartTagShouldShowHeading() throws JspException, IOException {
        when(this.tag.getLabelMarkUp(anyString(), anyBoolean(), anyString())).thenReturn(EMPTY);
        setField(this.tag, "showHeadingLabel", Boolean.TRUE);
        this.tag.doStartTag();
        verify(this.tag).getLabelMarkUp(anyString(), anyBoolean(), anyString());
    }

    @Test
    public void doStartTagShouldNotShowHeading() throws IOException, JspException {
        when(this.tag.getLabelMarkUp(anyString(), anyBoolean(), anyString())).thenReturn(EMPTY);
        this.tag.doStartTag();
        verify(this.tag, never()).getLabelMarkUp(anyString(), anyBoolean(), anyString());
    }

    @Test
    public void doStartTagShouldShowHint() throws JspException, IOException {
        when(this.tag.getHintMarkUp(anyString())).thenReturn(EMPTY);
        setField(this.tag, "showHint", Boolean.TRUE);
        this.tag.doStartTag();
        verify(this.tag).getHintMarkUp(anyString());
    }

    @Test
    public void doStartTagShouldNotShowHint() throws JspException, IOException {
        when(this.tag.getHintMarkUp(anyString())).thenReturn(EMPTY);
        this.tag.doStartTag();
        verify(this.tag, never()).getHintMarkUp(anyString());
    }

    @Test
    public void doStartTagShouldUseSelectedValue() throws JspException, IOException {
        final StringBuilder markUp = new StringBuilder();
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                markUp.append(args[0]);
                return args[0];
            }
        }).when(this.mockJspWriter).print(anyString());
        setField(this.tag, "selectedValue", TEST_OPTION_2_VALUE);
        this.tag.doStartTag();
        assertEquals(TEST_OPTION_2_VALUE, getField(this.tag, "selectedValue"));
        assertTrue(markUp.toString().contains(String.format("value=\"%s\" checked=\"checked\"", TEST_OPTION_2_VALUE)));
    }

    @Test
    public void doStartTagShouldNotUseSelectedValue() throws JspException, IOException {
        final StringBuilder markUp = new StringBuilder();
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                markUp.append(args[0]);
                return args[0];
            }
        }).when(this.mockJspWriter).print(anyString());
        this.tag.doStartTag();
        assertNotEquals(EMPTY, getField(this.tag, "selectedValue"));
        assertTrue(markUp.toString().contains(String.format("value=\"%s\" checked=\"checked\"", TEST_OPTION_1_VALUE)));
    }

    @Test
    public void doStartTagShouldUseManagedContent() throws JspException, IOException {
        when(this.tag.getContent(anyString())).thenReturn(EMPTY);
        setField(this.tag, "useManagedContentForMeanings", Boolean.TRUE);
        this.tag.doStartTag();
        verify(this.tag, atLeastOnce()).getContent(anyString());
    }

    @Test
    public void doStartTagShouldNotUseManagedContent() throws JspException, IOException {
        when(this.tag.getContent(anyString())).thenReturn(EMPTY);
        this.tag.doStartTag();
        verify(this.tag, never()).getContent(anyString());
    }

    @Test
    public void doStartTagShouldEscapeMarkUp() throws JspException, IOException {
        setField(this.tag, "selectList", getTestSelectListDTOWithMarkUp());
        final StringBuilder markUp = new StringBuilder();
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                markUp.append(args[0]);
                return args[0];
            }
        }).when(this.mockJspWriter).print(anyString());
        this.tag.doStartTag();
        assertNotEquals(EMPTY, getField(this.tag, "selectedValue"));
        assertTrue(markUp.toString().contains(htmlEscape(TEST_OPTION_VALUE_WITH_MARKUP)));
        assertTrue(markUp.toString().contains(htmlEscape(TEST_OPTION_MEANING_WITH_MARKUP)));
        assertFalse(markUp.toString().contains(TEST_OPTION_VALUE_WITH_MARKUP));
        assertFalse(markUp.toString().contains(TEST_OPTION_MEANING_WITH_MARKUP));
    }
}
