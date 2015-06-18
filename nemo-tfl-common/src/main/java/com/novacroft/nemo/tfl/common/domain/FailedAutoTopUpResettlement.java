package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * TfL resettlement (failed auto top up) item single table inheritance domain definition
 */
@Entity
@DiscriminatorValue("FailedAutoTopUpResettlement")
public class FailedAutoTopUpResettlement extends Item {
	private static final long serialVersionUID = -5300618883673721092L;
}
