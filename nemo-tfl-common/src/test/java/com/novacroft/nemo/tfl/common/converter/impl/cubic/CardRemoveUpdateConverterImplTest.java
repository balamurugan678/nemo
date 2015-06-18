package com.novacroft.nemo.tfl.common.converter.impl.cubic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import com.novacroft.nemo.test_support.CubicTestUtil;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardRemoveUpdateRequestDTO;

import org.junit.Before;
import org.junit.Test;

public class CardRemoveUpdateConverterImplTest {

    private CardRemoveUpdateConverterImpl converter;

    @Before
    public void setUp() throws Exception {
        converter = new CardRemoveUpdateConverterImpl();
    }

    @Test
    public void convertToModel() {
        CardRemoveUpdateRequestDTO requestDTO = CubicTestUtil.getTestRequestDTO1();
        CardRemoveUpdateRequest request = converter.convertToModel(requestDTO);
        assertNotNull(request);
        assertNotNull(request.getPrestigeId());
        assertEquals(requestDTO.getAction(), request.getAction());
    }

    @Test
    public void convertResponseToDTO() {
        CardRemoveUpdateResponse response = CubicTestUtil.getTestRemoveResponse1();
        CardUpdateResponseDTO responseDTO = converter.convertToDTO(response);
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getPrestigeId());
        assertEquals(response.getPrestigeId(), responseDTO.getPrestigeId());
    }

    @Test
    public void convertFailureToDTO() {
        RequestFailure requestFailure = CubicTestUtil.getTestRequestFailure1();
        CardUpdateResponseDTO responseDTO = converter.convertToDTO(requestFailure);
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getErrorDescription());
        assertEquals(requestFailure.getErrorDescription(), responseDTO.getErrorDescription());
    }

}
