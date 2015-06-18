package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadResponse;
import org.springframework.stereotype.Component;

/**
 * XML (as String) / AutoLoadResponse converter
 */
@Component(value = "autoLoadResponseConverter")
public class AutoLoadResponseConverterImpl extends BaseTflCommonXmlModelConverter<AutoLoadResponse> {
}
