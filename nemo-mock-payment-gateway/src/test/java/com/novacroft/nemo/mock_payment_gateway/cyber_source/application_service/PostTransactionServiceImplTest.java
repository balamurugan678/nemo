package com.novacroft.nemo.mock_payment_gateway.cyber_source.application_service;

import com.novacroft.nemo.mock_payment_gateway.cyber_source.command.PostCmd;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.configuration.PostConfiguration;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.PostRequestParameterName;
import org.junit.Test;

import static com.novacroft.nemo.test_support.CyberSourceTestUtil.*;
import static com.novacroft.nemo.test_support.ProfileTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * PostTransactionService unit tests
 */
public class PostTransactionServiceImplTest {
    @Test
    public void shouldGetProfile() {
        PostConfiguration mockPostConfiguration = mock(PostConfiguration.class);
        when(mockPostConfiguration.getPostProfiles()).thenReturn(getTestProfileList2());
        PostTransactionServiceImpl service = new PostTransactionServiceImpl();
        service.cyberSourcePostConfiguration = mockPostConfiguration;
        service.postSigningService = mock(PostSigningService.class);
        assertEquals(getTestProfile2(), service.getProfile(TEST_ID_2));
    }

    @Test
    public void shouldGetRequestAndConfiguration() {
        PostConfiguration mockPostConfiguration = mock(PostConfiguration.class);
        when(mockPostConfiguration.getPostProfiles()).thenReturn(getTestProfileList2());
        PostTransactionServiceImpl service = new PostTransactionServiceImpl();
        service.cyberSourcePostConfiguration = mockPostConfiguration;
        service.postSigningService = mock(PostSigningService.class);
        PostCmd result = service.getRequestAndConfiguration(getTestRequestMap());
        assertEquals(TEST_ACCESS_KEY, result.getRequestModelAttribute(PostRequestParameterName.ACCESS_KEY.code()));
        assertEquals(TEST_PROFILE_ID, result.getRequestModelAttribute(PostRequestParameterName.PROFILE_ID.code()));
    }
}
