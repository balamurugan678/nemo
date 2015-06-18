package com.novacroft.nemo.tfl.common.application_service;

import java.util.List;

public interface TransferSourceCardService {

    List<String> isSourceCardEligible(String cardNumber);

}
