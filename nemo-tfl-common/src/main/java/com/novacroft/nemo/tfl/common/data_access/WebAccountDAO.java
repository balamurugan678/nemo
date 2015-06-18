package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.WebAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * TfL Web Account data access class implementation.
 */
@Repository("webAccountDAO")
public class WebAccountDAO extends BaseDAOImpl<WebAccount> {
    protected static final Logger logger = LoggerFactory.getLogger(WebAccountDAO.class);
}
