package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadRequest;
import org.springframework.stereotype.Component;

/**
 * XML (as String) / AutoLoadRequest converter
 */
@Component(value = "autoLoadRequestConverter")
public class AutoLoadRequestConverterImpl extends BaseTflCommonXmlModelConverter<AutoLoadRequest> {
}
