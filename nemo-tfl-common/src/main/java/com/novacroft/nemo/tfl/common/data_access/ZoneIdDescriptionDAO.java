package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.ZoneIdDescription;
import org.springframework.stereotype.Repository;

/**
 * TfL zone id description data access class implementation.
 */
@Repository("zoneIdDescriptionDAO")
public class ZoneIdDescriptionDAO extends BaseDAOImpl<ZoneIdDescription> {

}
