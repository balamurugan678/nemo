package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.tfl.common.constant.PageAttribute.HOTLIST_REASONS;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.tfl.common.application_service.HotlistCardService;
import com.novacroft.nemo.tfl.common.constant.HotlistedCardExportStatus;
import com.novacroft.nemo.tfl.common.constant.HotlistReasonTypes;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.HotlistReasonDataService;
import com.novacroft.nemo.tfl.common.domain.HotlistReason;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.HotlistReasonDTO;

@Service(value = "refundSummaryService")
public class HotlistCardServiceImpl implements HotlistCardService {
    static final Logger logger = LoggerFactory.getLogger(HotlistCardServiceImpl.class);

    @Autowired
    protected CardDataService cardDataService;

    @Autowired
    protected HotlistReasonDataService hotlistReasonDataService;

    @Override
    @Transactional
    public void toggleCardHotlisted(String cardNumber, Integer hotlistReasonId) {
        final CardDTO card = cardDataService.findByCardNumber(cardNumber);
        if (card != null) {
            if (hotlistReasonId == 0) {
                card.setHotlistReason(null);
                card.setHotlistDateTime(null);
                card.setHotlistStatus(null);
                card.setNullable(true);
            } else {
                HotlistReason hotlistReason = new HotlistReason();
                hotlistReason.setId(Long.valueOf(hotlistReasonId));
                card.setHotlistReason(hotlistReason);
                card.setHotlistDateTime(new DateTime().toDate());
                card.setHotlistStatus(HotlistedCardExportStatus.HOTLIST_STATUS_READYTOEXPORT.getCode());
            }
            cardDataService.createOrUpdate(card);
        } else {
            logger.warn("Card number " + cardNumber + " was not found and could not be hotlisted");
        }

    }

    @Override
    public SelectListDTO getHotlistReasonSelectList() {
        List<HotlistReasonDTO> hotlistReasonDTOs = this.hotlistReasonDataService.findAll();
        SelectListDTO selectListDTO = new SelectListDTO(HOTLIST_REASONS, HOTLIST_REASONS, new ArrayList<SelectListOptionDTO>());
        for (HotlistReasonDTO hotlistReasonDTO : hotlistReasonDTOs) {
            for (HotlistReasonTypes hotlistReasonType : HotlistReasonTypes.values()) {
                if (hotlistReasonDTO.getId() == Long.valueOf(hotlistReasonType.getCode())) {
                    selectListDTO.getOptions().add(new SelectListOptionDTO(hotlistReasonDTO.getId().toString(), hotlistReasonDTO.getDescription()));
                }
            }
        }
        return selectListDTO;
    }

}
