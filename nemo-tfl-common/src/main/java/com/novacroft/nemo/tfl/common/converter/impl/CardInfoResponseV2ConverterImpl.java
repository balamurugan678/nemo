package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoResponseV2;

/**
 * XML (as String) / CardInfoResponseV2 converter
 */
@Component(value = "cardInfoResponseV2Converter")
public class CardInfoResponseV2ConverterImpl extends BaseTflCommonXmlModelConverter<CardInfoResponseV2> {
}
