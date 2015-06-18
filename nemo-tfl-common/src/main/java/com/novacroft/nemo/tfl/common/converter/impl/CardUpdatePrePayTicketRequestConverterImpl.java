package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayTicketRequest;


/**
 * XML (as String) / CardUpdateRequest converter
 */
@Component(value = "cardUpdatePrePayTicketRequestConverter")
public class CardUpdatePrePayTicketRequestConverterImpl extends BaseTflCommonXmlModelConverter<CardUpdatePrePayTicketRequest> {

}
