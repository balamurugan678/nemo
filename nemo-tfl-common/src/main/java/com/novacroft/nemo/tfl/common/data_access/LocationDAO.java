package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.Location;
import org.springframework.stereotype.Repository;

/**
 * Location (station/NLC) DAO
 */
@Repository("locationDAO")
public class LocationDAO extends BaseDAOImpl<Location> {
}
