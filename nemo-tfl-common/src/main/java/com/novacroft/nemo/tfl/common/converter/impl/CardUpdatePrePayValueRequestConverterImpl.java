package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayValueRequest;

/**
 * XML (as String) / CardUpdateRequest converter
 */
@Component(value = "cardUpdatePrePayValueRequestConverter")
public class CardUpdatePrePayValueRequestConverterImpl extends BaseTflCommonXmlModelConverter<CardUpdatePrePayValueRequest> {

}
