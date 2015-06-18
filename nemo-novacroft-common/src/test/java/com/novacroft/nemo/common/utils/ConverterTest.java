package com.novacroft.nemo.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.novacroft.nemo.common.transfer.ConverterNullable;

public class ConverterTest {
    private static final String TEST_STRING_VALUE = "string type value";
    
    @Test(expected=IllegalArgumentException.class)
    public void convertShouldThrowExceptionIfFirstParameterNull() {
        Converter.convert(null, new Object());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void convertShouldThrowExceptionIfSecondParameterNull() {
        Converter.convert(new Object(), null);
    }
    
    @Test
    public void shouldConvertSuccessfully() {
        MockConverterTestObject convertSource = new MockConverterTestObject();
        convertSource.setTestFieldString(TEST_STRING_VALUE);
        convertSource.setTestFieldBoolean(Boolean.TRUE);
        convertSource.setNullable(Boolean.TRUE);
        MockConverterTestObject convertTarget = new MockConverterTestObject();
        
        Converter.convert(convertSource, convertTarget);
        
        assertEquals(TEST_STRING_VALUE, convertTarget.getTestFieldString());
        assertTrue(convertTarget.isTestFieldBoolean());
    }
    
    private class MockConverterTestObject implements ConverterNullable{
        private String testFieldString;
        private Boolean testFieldBoolean;
        private Boolean nullable;
        
        public String getTestFieldString() {
            return testFieldString;
        }
        
        public void setTestFieldString(String testFieldString) {
            this.testFieldString = testFieldString;
        }
        
        public Boolean isTestFieldBoolean() {
            return testFieldBoolean;
        }
        
        public void setTestFieldBoolean(Boolean testFieldBoolean) {
            this.testFieldBoolean = testFieldBoolean;
        }

        @Override
        public void setNullable(Boolean nullablle) {
            this.nullable = nullablle;
        }

        @Override
        public Boolean isNullable() {
            return nullable;
        }
    }
}
