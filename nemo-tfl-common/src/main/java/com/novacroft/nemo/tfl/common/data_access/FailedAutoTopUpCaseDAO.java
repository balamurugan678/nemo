package com.novacroft.nemo.tfl.common.data_access;

import org.springframework.stereotype.Repository;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.FailedAutoTopUpCase;

@Repository("failedAutoTopUpCaseDAO")
public class FailedAutoTopUpCaseDAO extends BaseDAOImpl<FailedAutoTopUpCase> {

}
