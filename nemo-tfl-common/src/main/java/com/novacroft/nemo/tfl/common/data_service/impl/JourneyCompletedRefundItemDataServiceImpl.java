package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.JourneyCompletedRefundItemConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.JourneyCompletedRefundItemDAO;
import com.novacroft.nemo.tfl.common.data_service.JourneyCompletedRefundItemDataService;
import com.novacroft.nemo.tfl.common.domain.JourneyCompletedRefundItem;
import com.novacroft.nemo.tfl.common.transfer.JourneyCompletedRefundItemDTO;

@Service
public class JourneyCompletedRefundItemDataServiceImpl extends BaseDataServiceImpl<JourneyCompletedRefundItem, JourneyCompletedRefundItemDTO> implements JourneyCompletedRefundItemDataService {

	@Override
	public JourneyCompletedRefundItem getNewEntity() {
		
		return new JourneyCompletedRefundItem();
	}

	@Autowired
	public void setDao(JourneyCompletedRefundItemDAO dao) {
	        this.dao = dao;
	}

	@Autowired
	public void setConverter(JourneyCompletedRefundItemConverterImpl converter) {
	        this.converter = converter;
	}

	@Override
	public List<JourneyCompletedRefundItemDTO>  findByCardId(Long cardId) {
		final JourneyCompletedRefundItem journeyCompletedRefundItem = new JourneyCompletedRefundItem();
		journeyCompletedRefundItem.setCardId(cardId);
		final List<JourneyCompletedRefundItem>  refunditemList =  this.dao.findByExample(journeyCompletedRefundItem);
		return this.convert(refunditemList);
	}

}
