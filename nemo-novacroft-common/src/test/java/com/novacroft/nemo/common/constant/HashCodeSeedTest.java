package com.novacroft.nemo.common.constant;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HashCodeSeed unit tests
 */
public class HashCodeSeedTest {
	private static final Logger logger = LoggerFactory
			.getLogger(HashCodeSeedTest.class);

	@Test
	public void generatePrimes() {
		for (Integer i : CommonHashCodeSeed.generatePrimes(194, 500)) {
			logger.info(String.format("%s ", i));
		}
	}
}
