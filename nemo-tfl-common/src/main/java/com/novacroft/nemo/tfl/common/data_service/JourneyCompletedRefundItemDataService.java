package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.JourneyCompletedRefundItem;
import com.novacroft.nemo.tfl.common.transfer.JourneyCompletedRefundItemDTO;

public interface JourneyCompletedRefundItemDataService extends BaseDataService<JourneyCompletedRefundItem, JourneyCompletedRefundItemDTO> {

	List<JourneyCompletedRefundItemDTO>  findByCardId(Long cardId);
}
