package com.novacroft.nemo.tfl.services.test_support;

import com.novacroft.nemo.tfl.services.transfer.Station;

public class StationDataTestUtil {

    public static final Integer STATION_ID = 714;
    public static final String STATION_NAME = "Stanmore";
    public static final String STATION_STATUS = "1";

    public static Station getTestStation() {
        return new Station(STATION_ID.longValue(), STATION_NAME, STATION_STATUS);
    }
    
}
