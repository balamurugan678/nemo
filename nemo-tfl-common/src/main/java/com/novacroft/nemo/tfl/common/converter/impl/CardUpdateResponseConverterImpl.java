package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdateResponse;


/**
 * XML (as String) / CardUpdateResponse converter
 */
@Component(value = "cardUpdateResponseConverter")
public class CardUpdateResponseConverterImpl extends BaseTflCommonXmlModelConverter<CardUpdateResponse> {

}
