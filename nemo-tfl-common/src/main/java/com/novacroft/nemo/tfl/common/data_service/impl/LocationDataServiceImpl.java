package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_cache.CachingDataService;
import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.LocationConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.LocationDAO;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.domain.Location;
import com.novacroft.nemo.tfl.common.transfer.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Location (station/NLC) data service implementation NLC = National Location Code
 */
@Service(value = "locationDataService")
@Transactional(readOnly = true)
public class LocationDataServiceImpl extends BaseDataServiceImpl<Location, LocationDTO>
        implements LocationDataService, CachingDataService {
    public static final String ACTIVE = "1";

    @Autowired
    public void setDao(LocationDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(LocationConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public Location getNewEntity() {
        return new Location();
    }

    @Override
    public Date findLatestCreatedDateTime() {
        final String hsql = "select max(l.createdDateTime) from Location l";
        return findLatest(hsql);
    }

    @Override
    @Transactional(readOnly = true)
    public Date findLatestModifiedDateTime() {
        final String hsql = "select max(l.modifiedDateTime) from Location l";
        return findLatest(hsql);
    }

    @SuppressWarnings("unchecked")
    protected Date findLatest(final String hsql) {
        List<Date> results = dao.findByQuery(hsql);
        if (results.iterator().hasNext()) {
            return results.iterator().next();
        }
        return null;
    }

    public List<LocationDTO> findAllActiveLocations() {
        Location location = new Location();
        location.setStatus(ACTIVE);
        List<Location> results = dao.findByExample(location);
        return convert(results);
    }

    @Override
    public LocationDTO getActiveLocationById(int stationId) {
        final String hsql = "select l from Location l where l.id = ? and status = 1";
        Location result = dao.findByQueryUniqueResult(hsql, new Long(stationId));
        return this.converter.convertEntityToDto(result);
    }

}
