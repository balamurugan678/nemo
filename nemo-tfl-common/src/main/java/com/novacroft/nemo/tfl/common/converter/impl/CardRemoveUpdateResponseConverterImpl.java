package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateResponse;

/**
 * XML (as String) / CardRemoveUpdateResponse converter
 */
@Component(value = "cardRemoveUpdateResponseConverter")
public class CardRemoveUpdateResponseConverterImpl extends BaseTflCommonXmlModelConverter<CardRemoveUpdateResponse> {

}