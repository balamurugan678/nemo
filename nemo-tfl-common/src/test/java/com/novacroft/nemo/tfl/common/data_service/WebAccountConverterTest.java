package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.tfl.common.converter.impl.WebAccountConverterImpl;
import com.novacroft.nemo.tfl.common.domain.WebAccount;
import com.novacroft.nemo.tfl.common.transfer.WebAccountDTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Web account converter unit tests
 */
public class WebAccountConverterTest {

    static final Long testId = 99L;
    static final String testUsername = "testUser";
    static final String testPassword = "testPW";
    static final String testSalt = "testSalt";
    static final String testEmailAddress = "test@test.co.uk";
    static final String testPhotoCardNumber = "testNumber";
    static final String testUnformattedEmailAddress = "test@test.co.uk";
    static final int testAnonymised = 0;
    static final int testReadOnly = 0;
    static final int testPasswordChangeRequired = 0;

    @Test
    public void shouldConvertEntityToDto() {

        WebAccountConverterImpl converter = new WebAccountConverterImpl();
        WebAccount entity = new WebAccount();

        entity.setId(testId);
        entity.setUsername(testUsername);
        entity.setPassword(testPassword);
        entity.setSalt(testSalt);
        entity.setEmailAddress(testEmailAddress);
        entity.setPhotoCardNumber(testPhotoCardNumber);
        entity.setUnformattedEmailAddress(testUnformattedEmailAddress);
        entity.setAnonymised(testAnonymised);
        entity.setReadOnly(testReadOnly);
        entity.setPasswordChangeRequired(testPasswordChangeRequired);

        WebAccountDTO dto = converter.convertEntityToDto(entity);

        assertEquals(testId, dto.getId());
        assertEquals(testUsername, dto.getUsername());
        assertEquals(testPassword, dto.getPassword());
        assertEquals(testSalt, dto.getSalt());
        assertEquals(testEmailAddress, dto.getEmailAddress());
        assertEquals(testPhotoCardNumber, dto.getPhotoCardNumber());
        assertEquals(testUnformattedEmailAddress, dto.getUnformattedEmailAddress());
        assertEquals(testAnonymised, dto.getAnonymised());
        assertEquals(testReadOnly, dto.getReadOnly());
        assertEquals(testPasswordChangeRequired, dto.getPasswordChangeRequired());
    }

    @Test
    public void shouldConvertDtoToEntity() {
        WebAccountConverterImpl converter = new WebAccountConverterImpl();
        WebAccountDTO dto = new WebAccountDTO();
        dto.setId(testId);
        dto.setUsername(testUsername);
        dto.setPassword(testPassword);
        dto.setSalt(testSalt);
        dto.setEmailAddress(testEmailAddress);
        dto.setPhotoCardNumber(testPhotoCardNumber);
        dto.setUnformattedEmailAddress(testUnformattedEmailAddress);
        dto.setAnonymised(testAnonymised);
        dto.setReadOnly(testReadOnly);
        dto.setPasswordChangeRequired(testPasswordChangeRequired);

        WebAccount entity = new WebAccount();
        entity.setId(testId);
        entity = converter.convertDtoToEntity(dto, entity);

        assertEquals(testId, entity.getId());
        assertEquals(testUsername, entity.getUsername());
        assertEquals(testPassword, entity.getPassword());
        assertEquals(testSalt, entity.getSalt());
        assertEquals(testEmailAddress, entity.getEmailAddress());
        assertEquals(testPhotoCardNumber, entity.getPhotoCardNumber());
        assertEquals(testUnformattedEmailAddress, entity.getUnformattedEmailAddress());
        assertEquals(testAnonymised, entity.getAnonymised());
        assertEquals(testReadOnly, entity.getReadOnly());
        assertEquals(testPasswordChangeRequired, entity.getPasswordChangeRequired());
    }
}
