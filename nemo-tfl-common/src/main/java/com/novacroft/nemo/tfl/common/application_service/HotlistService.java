package com.novacroft.nemo.tfl.common.application_service;

public interface HotlistService {
	
    boolean isCardHotlisted(String cardNumber);
    boolean isCardHotListedInCubic(String cardNumber);

}
