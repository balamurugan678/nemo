package com.novacroft.nemo.mock_cubic.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.mock_cubic.domain.card.CubicCardResponse;
import org.springframework.stereotype.Repository;

/**
 * TfL Contact data access class implementation.
 */
@Repository("cubicCardResponseDAO")
public class CubicCardResponseDAO extends BaseDAOImpl<CubicCardResponse> {
    public Long getNextRequestSequenceNumber() {
        return getNextSequenceNumber("mock_cubic_request_seq_num_seq");
    }
}
