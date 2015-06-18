package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.domain.AlternativeZoneMapping;
import com.novacroft.nemo.tfl.common.transfer.AlternativeZoneMappingDTO;

/**
 * Utilities for alternative zone mapping tests
 */
public final class AlternativeZoneMappingTestUtil {
    public static final Integer START_ZONE_1 = 6;
    public static final Integer END_ZONE_1 = 9;
    public static final Integer ALTERNATIVE_START_ZONE_1 = 4;
    public static final Integer ALTERNATIVE_END_ZONE_1 = 9;

    public static AlternativeZoneMappingDTO getTestAlternativeZoneMappingDTO1() {
        return getTestAlternativeZoneMappingDTO(START_ZONE_1, END_ZONE_1, ALTERNATIVE_START_ZONE_1, ALTERNATIVE_END_ZONE_1);
    }

	public static AlternativeZoneMappingDTO getTestAlternativeZoneMappingDTO(Integer startZone, Integer endZone, Integer alternativeStartZone,
			Integer alternativeEndZone) {
    	AlternativeZoneMappingDTO alternativeZoneMappingDTO = new AlternativeZoneMappingDTO();
    	alternativeZoneMappingDTO.setStartZone(startZone);
    	alternativeZoneMappingDTO.setEndZone(endZone);
    	alternativeZoneMappingDTO.setAlternativeStartZone(alternativeStartZone);
    	alternativeZoneMappingDTO.setAlternativeEndZone(alternativeEndZone);
        return alternativeZoneMappingDTO;
    }
	
	public static AlternativeZoneMapping getTestAlternativeZoneMapping1() {
        return getTestAlternativeZoneMapping(START_ZONE_1, END_ZONE_1, ALTERNATIVE_START_ZONE_1, ALTERNATIVE_END_ZONE_1);
    }

	public static AlternativeZoneMapping getTestAlternativeZoneMapping(Integer startZone, Integer endZone, Integer alternativeStartZone,
			Integer alternativeEndZone) {
    	AlternativeZoneMapping alternativeZoneMapping = new AlternativeZoneMapping();
    	alternativeZoneMapping.setStartZone(startZone);
    	alternativeZoneMapping.setEndZone(endZone);
    	alternativeZoneMapping.setAlternativeStartZone(alternativeStartZone);
    	alternativeZoneMapping.setAlternativeEndZone(alternativeEndZone);
        return alternativeZoneMapping;
    }

}
