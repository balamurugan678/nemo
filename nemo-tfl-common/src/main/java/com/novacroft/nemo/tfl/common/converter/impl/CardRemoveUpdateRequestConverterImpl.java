package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateRequest;

/**
 * XML (as String) / CardRemoveUpdateRequest converter
 */
@Component(value = "cardRemoveUpdateRequestConverter")
public class CardRemoveUpdateRequestConverterImpl extends BaseTflCommonXmlModelConverter<CardRemoveUpdateRequest> {

}