package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.HotlistReason;
import com.novacroft.nemo.tfl.common.transfer.HotlistReasonDTO;

/**
 * HotlistReason data service specification
 */
public interface HotlistReasonDataService extends BaseDataService<HotlistReason, HotlistReasonDTO> {
	HotlistReasonDTO findByDescription(String description);
}
