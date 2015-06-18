package com.novacroft.nemo.tfl.online.tag;

import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.test_support.TagTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class BaseListTagTest {

    private BaseListTag tag;

    @Before
    public void setUp() {
        this.tag = mock(BaseListTag.class);
    }

    @Test
    public void getLabelMarkUpForMandatoryField() {
        when(this.tag.getLabelMarkUp(anyString(), anyBoolean(), anyString())).thenCallRealMethod();
        assertEquals("<label for='test-element-id' class='emphasis'>test-prompt* : </label>",
                this.tag.getLabelMarkUp(TEST_HTML_ELEMENT_ID_1, Boolean.TRUE, TEST_PROMPT_1));
    }

    @Test
    public void getLabelMarkUpForOptionalField() {
        when(this.tag.getLabelMarkUp(anyString(), anyBoolean(), anyString())).thenCallRealMethod();
        assertEquals("<label for='test-element-id' class='emphasis'>test-prompt : </label>",
                this.tag.getLabelMarkUp(TEST_HTML_ELEMENT_ID_1, Boolean.FALSE, TEST_PROMPT_1));
    }

    @Test
    public void shouldGetHintMarkUp() {
        when(this.tag.getHintMarkUp(anyString())).thenCallRealMethod();
        assertEquals("<span style=\"color: #2070b6;\" >test-hint</span>", this.tag.getHintMarkUp(TEST_HINT_1));
    }
}
