package com.novacroft.nemo.common.data_access;

import com.novacroft.nemo.common.domain.SystemParameter;
import org.springframework.stereotype.Repository;

/**
 * System Parameter data access class
 */
@Repository("systemParameterDAO")
public class SystemParameterDAO extends BaseDAOImpl<SystemParameter> {
}
