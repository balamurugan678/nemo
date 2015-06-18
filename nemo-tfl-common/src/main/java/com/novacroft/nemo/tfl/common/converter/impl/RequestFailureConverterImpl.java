package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import org.springframework.stereotype.Component;

/**
 * XML (as String) / RequestFailure converter
 */
@Component(value = "requestFailureConverter")
public class RequestFailureConverterImpl extends BaseTflCommonXmlModelConverter<RequestFailure> {
}
