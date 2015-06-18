package com.novacroft.nemo.tfl.common.application_service.impl.cyber_source;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourcePostRequestField;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourcePostRequestDTO;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * CyberSourceSecurityServiceImpl unit tests
 */
public class CyberSourceSecurityServiceImplTest {
    private CyberSourceSecurityServiceImpl service;
    private CyberSourcePostRequestDTO mockCyberSourcePostRequestDTO;

    private String testParameterName1 = CyberSourcePostRequestField.ACCESS_KEY.code();
    private String testParameterValue1 = "test-value-1";
    private String testParameterName2 = CyberSourcePostRequestField.PROFILE_ID.code();
    private String testParameterValue2 = "test-value-2";

    private String[] testParameterNameArray = new String[]{testParameterName1, testParameterName2};
    private List<String> testParameterNameList = Arrays.asList(testParameterNameArray);
    private String testParameterNameCsv = String.format("%s,%s", testParameterNameArray);

    private Map<String, String> testParameterMap;
    private String testParameterData = "access_key=test-value-1,profile_id=test-value-2";

    private String testSecretKey = "test-secret-key";

    @Before
    public void setUp() {
        this.service = mock(CyberSourceSecurityServiceImpl.class);
        this.mockCyberSourcePostRequestDTO = mock(CyberSourcePostRequestDTO.class);

        this.testParameterMap = new HashMap<>();
        this.testParameterMap.put(testParameterName1, testParameterValue1);
        this.testParameterMap.put(testParameterName2, testParameterValue2);
        this.testParameterMap.put(CyberSourcePostRequestField.SIGNED_FIELD_NAMES.code(), testParameterNameCsv);
    }

    @Test
    public void shouldCommaSeparate() {
        when(this.service.commaSeparate(anyList())).thenCallRealMethod();
        assertEquals(testParameterNameCsv, this.service.commaSeparate(this.testParameterNameList));
    }

    @Test
    public void shouldBuildDataToSign() {
        when(this.service.buildDataToSign(anyMap())).thenCallRealMethod();
        when(this.service.commaSeparate(anyList())).thenCallRealMethod();
        assertEquals(testParameterData, this.service.buildDataToSign(this.testParameterMap));
    }

    @Test
    public void shouldSign() {
        try {
            when(this.service.sign(anyString(), anyString())).thenCallRealMethod();
            assertEquals("NF0sO3FY1j39wGa1FQz4PcWkR7r9YwYSdr/B5m7krvo=",
                    this.service.sign(testParameterData, testSecretKey).trim());
        } catch (InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldGetFieldsAsCommaSeparatedList() {
        when(this.service.getRequestFieldsAsCommaSeparatedList(any(CyberSourcePostRequestField[].class))).thenCallRealMethod();
        assertEquals(testParameterNameCsv, this.service.getRequestFieldsAsCommaSeparatedList(
                new CyberSourcePostRequestField[]{CyberSourcePostRequestField.ACCESS_KEY,
                        CyberSourcePostRequestField.PROFILE_ID}
        ));
    }

    @Test
    public void shouldGetSignedFieldsAsCommaSeparatedList() {
        when(this.service.getRequestSignedFieldsAsCommaSeparatedList()).thenCallRealMethod();
        when(this.service.getRequestFieldsAsCommaSeparatedList(any(CyberSourcePostRequestField[].class))).thenReturn("");

        this.service.getRequestSignedFieldsAsCommaSeparatedList();

        verify(this.service).getRequestFieldsAsCommaSeparatedList(any(CyberSourcePostRequestField[].class));
    }

    @Test
    public void shouldGetUnSignedFieldsAsCommaSeparatedList() {
        when(this.service.getRequestUnSignedFieldsAsCommaSeparatedList()).thenCallRealMethod();
        when(this.service.getRequestFieldsAsCommaSeparatedList(any(CyberSourcePostRequestField[].class))).thenReturn("");

        this.service.getRequestUnSignedFieldsAsCommaSeparatedList();

        verify(this.service).getRequestFieldsAsCommaSeparatedList(any(CyberSourcePostRequestField[].class));
    }

    @Test
    public void shouldSignPostRequest() {
        try {
            doCallRealMethod().when(this.service).signPostRequest(any(CyberSourcePostRequestDTO.class));

            when(this.service.getRequestSignedFieldsAsCommaSeparatedList()).thenReturn("");
            when(this.service.getRequestUnSignedFieldsAsCommaSeparatedList()).thenReturn("");
            when(this.service.sign(anyMap())).thenReturn("");

            doNothing().when(this.mockCyberSourcePostRequestDTO).setSignedFieldNames(anyString());
            doNothing().when(this.mockCyberSourcePostRequestDTO).setUnsignedFieldNames(anyString());
            doNothing().when(this.mockCyberSourcePostRequestDTO).setSignature(anyString());
            doNothing().when(this.mockCyberSourcePostRequestDTO).setSignedDateTime(anyString());

            this.service.signPostRequest(this.mockCyberSourcePostRequestDTO);

            verify(this.service).getRequestSignedFieldsAsCommaSeparatedList();
            verify(this.service).getRequestUnSignedFieldsAsCommaSeparatedList();
            verify(this.service).sign(anyMap());

            verify(this.mockCyberSourcePostRequestDTO).setSignedFieldNames(anyString());
            verify(this.mockCyberSourcePostRequestDTO).setUnsignedFieldNames(anyString());
            verify(this.mockCyberSourcePostRequestDTO).setSignature(anyString());
            verify(this.mockCyberSourcePostRequestDTO).setSignedDateTime(anyString());

        } catch (InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = ApplicationServiceException.class)
    public void signPostRequestShouldError()
            throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        doCallRealMethod().when(this.service).signPostRequest(any(CyberSourcePostRequestDTO.class));

        when(this.service.getRequestSignedFieldsAsCommaSeparatedList()).thenReturn("");
        when(this.service.getRequestUnSignedFieldsAsCommaSeparatedList()).thenReturn("");
        when(this.service.sign(anyMap())).thenThrow(new UnsupportedEncodingException("test-error"));

        doNothing().when(this.mockCyberSourcePostRequestDTO).setSignedFieldNames(anyString());
        doNothing().when(this.mockCyberSourcePostRequestDTO).setUnsignedFieldNames(anyString());
        doNothing().when(this.mockCyberSourcePostRequestDTO).setSignature(anyString());
        doNothing().when(this.mockCyberSourcePostRequestDTO).setSignedDateTime(anyString());

        this.service.signPostRequest(this.mockCyberSourcePostRequestDTO);
    }
}
