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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.web.util.HtmlUtils.htmlEscape;

public class SelectListTagTest {
    private static final String LIST_HTML_ELEMENT_ID = "test-list-element-id";
    private static final String TEST_PAGE_NAME = "test-page-name";
    private SelectListTag tag;
    private PageContext mockPageContext;
    private JspWriter mockJspWriter;
    private ServletRequest mockServletRequest;

    @Before
    public void setUp() throws JspException {
        this.tag = mock(SelectListTag.class);
        when(this.tag.doStartTag()).thenCallRealMethod();
        setField(this.tag, "showLabel", Boolean.FALSE);
        setField(this.tag, "showPlaceholder", Boolean.FALSE);
        setField(this.tag, "showHint", Boolean.FALSE);
        setField(this.tag, "selectList", getTestSelectListDTO());
        setField(this.tag, "useManagedContentForMeanings", Boolean.FALSE);
        setField(this.tag, "selectedValue", EMPTY);
        setField(this.tag, "id", LIST_HTML_ELEMENT_ID);
        setField(this.tag, "mandatory", Boolean.FALSE);

        this.mockPageContext = mock(PageContext.class);
        setField(this.tag, "pageContext", this.mockPageContext);

        this.mockJspWriter = mock(JspWriter.class);
        when(this.mockPageContext.getOut()).thenReturn(this.mockJspWriter);

        this.mockServletRequest = mock(ServletRequest.class);
        when(this.mockPageContext.getRequest()).thenReturn(this.mockServletRequest);
        when(this.mockServletRequest.getAttribute(anyString())).thenReturn(TEST_PAGE_NAME);
    }

    @Test(expected = JspException.class)
    public void doStartTagShouldError() throws IOException, JspException {
        doThrow(new IOException()).when(this.mockJspWriter).println(anyString());
        setField(this.tag, "showLabel", Boolean.TRUE);
        this.tag.doStartTag();
    }

    @Test
    public void doStartTagShouldShowLabel() throws JspException, IOException {
        when(this.tag.getLabelMarkUp(anyString(), anyBoolean(), anyString())).thenReturn(EMPTY);
        setField(this.tag, "showLabel", Boolean.TRUE);
        this.tag.doStartTag();
        verify(this.tag).getLabelMarkUp(anyString(), anyBoolean(), anyString());
    }

    @Test
    public void doStartTagShouldNotShowLabel() throws IOException, JspException {
        when(this.tag.getLabelMarkUp(anyString(), anyBoolean(), anyString())).thenReturn(EMPTY);
        this.tag.doStartTag();
        verify(this.tag, never()).getLabelMarkUp(anyString(), anyBoolean(), anyString());
    }

    @Test
    public void doStartTagShouldShowPlaceHolder() throws JspException, IOException {
        final String placeHolderContentCode =
                String.format("%s.%s.selectList.placeholder", TEST_PAGE_NAME, LIST_HTML_ELEMENT_ID);
        when(this.tag.getContent(eq(placeHolderContentCode))).thenReturn(EMPTY);
        setField(this.tag, "showPlaceholder", Boolean.TRUE);
        this.tag.doStartTag();
        verify(this.tag).getContent(eq(placeHolderContentCode));
    }

    @Test
    public void doStartTagShouldNotShowPlaceHolder() throws JspException, IOException {
        final String placeHolderContentCode =
                String.format("%s.%s.selectList.placeholder", TEST_PAGE_NAME, LIST_HTML_ELEMENT_ID);
        when(this.tag.getContent(eq(placeHolderContentCode))).thenReturn(EMPTY);
        this.tag.doStartTag();
        verify(this.tag, never()).getContent(eq(placeHolderContentCode));
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
        assertTrue(markUp.toString().contains(String.format("value=\"%s\" selected", TEST_OPTION_2_VALUE)));
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
        assertFalse(markUp.toString().contains("selected"));
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
        assertTrue(markUp.toString().contains(TEST_OPTION_VALUE_WITH_MARKUP));
        assertTrue(markUp.toString().contains(htmlEscape(TEST_OPTION_MEANING_WITH_MARKUP)));
        assertFalse(markUp.toString().contains(TEST_OPTION_MEANING_WITH_MARKUP));
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

}
