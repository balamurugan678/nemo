package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.tfl.common.constant.PageAttribute.BACKDATED_REFUND_TYPES;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.tfl.common.application_service.BackdatedRefundReasonService;
import com.novacroft.nemo.tfl.common.data_service.BackdatedRefundReasonDataService;
import com.novacroft.nemo.tfl.common.transfer.BackdatedRefundReasonDTO;

/**
 * Backdated RefundReason service implementation
 */
@Service("backdatedRefundReasonService")
public class BackdatedRefundReasonServiceImpl extends BaseService implements BackdatedRefundReasonService {
    static final Logger logger = LoggerFactory.getLogger(BackdatedRefundReasonServiceImpl.class);

    @Autowired
    protected BackdatedRefundReasonDataService backdatedRefundReasonDataService;

    @Autowired
    protected MessageSource messageSource;

    @Override
    public SelectListDTO getBackdatedRefundTypes() {
        List<BackdatedRefundReasonDTO> backdatedRefundReasonDTOs = this.backdatedRefundReasonDataService.findAll();
        SelectListDTO selectListDTO = new SelectListDTO(BACKDATED_REFUND_TYPES, BACKDATED_REFUND_TYPES, new ArrayList<SelectListOptionDTO>());
        for (BackdatedRefundReasonDTO backdatedRefundReasonDTO : backdatedRefundReasonDTOs) {
            selectListDTO.getOptions().add(new SelectListOptionDTO(backdatedRefundReasonDTO.getReasonId().toString(), backdatedRefundReasonDTO.getDescription()));
        }
        return selectListDTO;
    }

}
