package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.tfl.common.constant.RefundConstants.INTERMEDIATE_PERIOD_OF_NON_USE;
import com.google.gson.Gson;
import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.tfl.common.application_service.GoodwillService;
import com.novacroft.nemo.tfl.common.data_service.GoodwillReasonDataService;
import com.novacroft.nemo.tfl.common.transfer.GoodwillReasonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.novacroft.nemo.tfl.common.constant.PageAttribute.GOODWILL_REFUND_TYPES;

/**
 * Goodwill service implementation
 */
@Service("goodwillService")
public class GoodwillServiceImpl extends BaseService implements GoodwillService {
    static final Logger logger = LoggerFactory.getLogger(GoodwillServiceImpl.class);

    @Autowired
    protected GoodwillReasonDataService goodwillReasonDataService;

    @Autowired
    protected MessageSource messageSource;

    @Override
    public SelectListDTO getGoodwillRefundTypes(String goodwillReasonType) {
        List<GoodwillReasonDTO> goodwillReasonDTOs = this.goodwillReasonDataService.findByType(goodwillReasonType);
        SelectListDTO selectListDTO = new SelectListDTO(GOODWILL_REFUND_TYPES, GOODWILL_REFUND_TYPES, new ArrayList<SelectListOptionDTO>());
        for (GoodwillReasonDTO goodwillReasonDTO : goodwillReasonDTOs) {
            selectListDTO.getOptions().add(new SelectListOptionDTO(goodwillReasonDTO.getReasonId().toString(), goodwillReasonDTO.getDescription()));
        }
        return selectListDTO;
    }

    @Override
    public String getGoodwillRefundExtraValidationMessages(String goodwillReasonType) {
        Map<String, String> goodwillExtraValidationMessages = new HashMap<String, String>();
        List<GoodwillReasonDTO> goodwillReasonDTOs = this.goodwillReasonDataService.findByType(goodwillReasonType);
        for (GoodwillReasonDTO goodwillReasonDTO : goodwillReasonDTOs) {
            goodwillExtraValidationMessages.put(goodwillReasonDTO.getReasonId().toString(),
                            messageSource.getMessage(goodwillReasonDTO.getExtraValidationCode(), null, "", null));
        }
        return new Gson().toJson(goodwillExtraValidationMessages);
    }

    @Override
    public SelectListDTO getAnonymousGoodwillRefundTypes(String goodwillReasonType) {
        List<GoodwillReasonDTO> goodwillReasonDTOs = this.goodwillReasonDataService.findByType(goodwillReasonType);
        SelectListDTO selectListDTO = new SelectListDTO(GOODWILL_REFUND_TYPES, GOODWILL_REFUND_TYPES, new ArrayList<SelectListOptionDTO>());
        for (GoodwillReasonDTO goodwillReasonDTO : goodwillReasonDTOs) {
            if (!(goodwillReasonDTO.getDescription().toString().equalsIgnoreCase(INTERMEDIATE_PERIOD_OF_NON_USE))) {
                selectListDTO.getOptions().add(
                                new SelectListOptionDTO(goodwillReasonDTO.getReasonId().toString(), goodwillReasonDTO.getDescription()));
            }
        }
        return selectListDTO;
    }
}
