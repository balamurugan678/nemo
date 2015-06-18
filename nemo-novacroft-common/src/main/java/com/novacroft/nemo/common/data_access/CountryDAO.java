package com.novacroft.nemo.common.data_access;

import com.novacroft.nemo.common.domain.Country;
import org.springframework.stereotype.Repository;

/**
 * Country DAO
 */
@Repository("countryDAO")
public class CountryDAO extends BaseDAOImpl<Country> {
}
