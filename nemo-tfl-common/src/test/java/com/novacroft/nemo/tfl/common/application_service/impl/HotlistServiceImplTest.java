package com.novacroft.nemo.tfl.common.application_service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.tfl.common.application_service.impl.cubic.GetCardServiceImpl;

public class HotlistServiceImplTest {
	
	private HotlistServiceImpl mockService;
	private CardServiceImpl mockCardService;
	private GetCardServiceImpl mockGetCardService;
	
	@Before
	public void setup() {
		mockService = mock(HotlistServiceImpl.class);
		mockCardService = mock(CardServiceImpl.class);
		mockGetCardService = mock(GetCardServiceImpl.class);
		mockService.cardService = mockCardService;
		mockService.getCardService = mockGetCardService;
	}
	
	@Test
	public  void testIfCardIsHotlisted() {
	    when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(CardTestUtil.getTestCardDTO1().getId());
	    when(mockCardService.getCardDTOById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
	    when(mockService.isCardHotListedInCubic(anyString())).thenReturn(true);
	    when(mockService.isCardHotlisted(anyString())).thenCallRealMethod();
	    assertTrue(mockService.isCardHotlisted(anyString()));	     
	}
	
	@Test
	public void testIfCardIsHotlistedInCubic() {
		when(mockGetCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOWithHotlistedCard());
		when(mockService.isCardHotListedInCubic(anyString())).thenCallRealMethod();
		assertTrue(mockService.isCardHotListedInCubic(anyString()));
	}
	
	@Test (expected=Exception.class)
	public void testIfCardIsHotlistedInCubicShouldReturnException() {
		when(mockGetCardService.getCard(anyString())).thenThrow(new Exception());
		when(mockService.isCardHotListedInCubic(anyString())).thenCallRealMethod();
		mockService.isCardHotListedInCubic(anyString());
	}

}
