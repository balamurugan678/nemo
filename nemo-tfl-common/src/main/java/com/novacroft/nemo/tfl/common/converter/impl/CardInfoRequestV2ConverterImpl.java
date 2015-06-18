package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoRequestV2;
import org.springframework.stereotype.Component;

/**
 * XML (as String) / CardInfoRequestV2 converter
 */
@Component(value = "cardInfoRequestV2Converter")
public class CardInfoRequestV2ConverterImpl extends BaseTflCommonXmlModelConverter<CardInfoRequestV2> {
}
