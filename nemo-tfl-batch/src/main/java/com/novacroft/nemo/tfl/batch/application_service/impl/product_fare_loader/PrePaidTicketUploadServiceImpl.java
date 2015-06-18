package com.novacroft.nemo.tfl.batch.application_service.impl.product_fare_loader;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.batch.application_service.product_fare_loader.PrePaidTicketUploadService;
import com.novacroft.nemo.tfl.batch.domain.product_fare_loader.PrePaidTicketRecord;
import com.novacroft.nemo.tfl.common.data_service.DiscountTypeDataService;
import com.novacroft.nemo.tfl.common.data_service.DurationDataService;
import com.novacroft.nemo.tfl.common.data_service.PassengerTypeDataService;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.data_service.ZoneDataService;
import com.novacroft.nemo.tfl.common.transfer.DiscountTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.DurationDTO;
import com.novacroft.nemo.tfl.common.transfer.PassengerTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;
import com.novacroft.nemo.tfl.common.transfer.PriceDTO;
import com.novacroft.nemo.tfl.common.transfer.ZoneDTO;

@Service("prePaidTicketUploadService")
public class PrePaidTicketUploadServiceImpl implements PrePaidTicketUploadService {

    @Autowired
    protected PrePaidTicketDataService prePaidTicketDataService;
    @Autowired
    protected ZoneDataService zoneDataService;
    @Autowired
    protected DurationDataService durationDataService;
    @Autowired
    protected DiscountTypeDataService discountTypeDataService;
    @Autowired
    protected PassengerTypeDataService passengerTypeDataService;

    @Override
    public void updatePrePaidDataForRecord(PrePaidTicketRecord prePaidTicketRecord) {
        PrePaidTicketDTO prePaidTicketDTO = getPrePaidTicketByCodeAndEffectiveDate(prePaidTicketRecord.getAdhocPrePaidTicketCode(),
                         prePaidTicketRecord.getActiveFlag());
        if (prePaidTicketDTO == null) {
            createPrePaidTicket(prePaidTicketRecord);
        } else {
            updatePrePaidTicket(prePaidTicketRecord, prePaidTicketDTO);
        }
    }

    protected PrePaidTicketDTO getPrePaidTicketByCodeAndEffectiveDate(String adHocCode, Boolean isActive) {
        PrePaidTicketDTO prePaidTicketDTO = prePaidTicketDataService.findByProductCode(adHocCode);
        if (prePaidTicketDTO != null) {
            if (prePaidTicketDTO.getEffectiveTo() == null && !isActive) {
                prePaidTicketDTO.setEffectiveTo(getTodaysDate());
            } else if (prePaidTicketDTO.getEffectiveTo() != null && isActive) {
                prePaidTicketDTO.setEffectiveTo(null);
            }
        }
        return prePaidTicketDTO;
    }

    protected void createPrePaidTicket(PrePaidTicketRecord prePaidTicketRecord) {
        PrePaidTicketDTO prePaidTicketDTO = new PrePaidTicketDTO();
        prePaidTicketDTO.setAdHocPrePaidTicketCode(prePaidTicketRecord.getAdhocPrePaidTicketCode());
        prePaidTicketDTO.setEffectiveFrom(prePaidTicketRecord.getEffectiveDate());
        prePaidTicketDTO.setDescription(prePaidTicketRecord.getProductDescription());
        prePaidTicketDTO.setType(prePaidTicketRecord.getType());

        prePaidTicketDTO.setStartZoneDTO(getOrCreateZoneByCode(prePaidTicketRecord.getFromZonecode(), prePaidTicketRecord.getEffectiveDate()));
        if (prePaidTicketRecord.getFromZonecode().equals(prePaidTicketRecord.getToZoneCode())) {
            prePaidTicketDTO.setEndZoneDTO(prePaidTicketDTO.getStartZoneDTO());
        } else {
            prePaidTicketDTO.setEndZoneDTO(getOrCreateZoneByCode(prePaidTicketRecord.getToZoneCode(), prePaidTicketRecord.getEffectiveDate()));
        }

        prePaidTicketDTO.setFromDurationDTO(getOrCreateDurationByCode(prePaidTicketRecord.getFromDurationCode(),
                        prePaidTicketRecord.getEffectiveDate()));
        if (prePaidTicketRecord.getFromDurationCode().equals(prePaidTicketRecord.getToDurationCode())) {
            prePaidTicketDTO.setToDurationDTO(prePaidTicketDTO.getFromDurationDTO());
        } else {
            prePaidTicketDTO.setToDurationDTO(getOrCreateDurationByCode(prePaidTicketRecord.getToDurationCode(),
                            prePaidTicketRecord.getEffectiveDate()));
        }

        prePaidTicketDTO.setPassengerTypeDTO(getOrCreatePassengerTypeByCode(prePaidTicketRecord.getPassengerTypeCode(),
                        prePaidTicketRecord.getEffectiveDate()));
        prePaidTicketDTO.setDiscountTypeDTO(getOrCreateDiscountTypeByCode(prePaidTicketRecord.getDiscountDescription(),
                        prePaidTicketRecord.getEffectiveDate()));

        if (prePaidTicketRecord.getPriceInPence() != null) {
            prePaidTicketDTO.getPrices().add(createPriceDTO(prePaidTicketRecord.getPriceInPence(), prePaidTicketRecord.getPriceEffectiveDate()));
        }

        prePaidTicketDataService.createOrUpdate(prePaidTicketDTO);
    }

    protected void updatePrePaidTicket(PrePaidTicketRecord prePaidTicketRecord, PrePaidTicketDTO prePaidTicketDTO) {
        checkAndResetExpiryDates(prePaidTicketDTO);
        if (prePaidTicketRecord.getPriceInPence() != null) {
            PriceDTO currentPriceDTO = getCurrentPriceFromList(prePaidTicketDTO.getPrices());
            if (currentPriceDTO == null) {
                prePaidTicketDTO.getPrices().add(createPriceDTO(prePaidTicketRecord.getPriceInPence(), prePaidTicketRecord.getPriceEffectiveDate()));
            } else if (!currentPriceDTO.getpriceInPence().equals(prePaidTicketRecord.getPriceInPence())) {
                PriceDTO newPriceDTO = createPriceDTO(prePaidTicketRecord.getPriceInPence(), prePaidTicketRecord.getPriceEffectiveDate());
                currentPriceDTO.setEffectiveTo(DateUtil.getDayBefore(prePaidTicketRecord.getPriceEffectiveDate()));

                prePaidTicketDTO.getPrices().add(newPriceDTO);
            }
        }
        
        if(StringUtils.isEmpty(prePaidTicketDTO.getType())){
            prePaidTicketDTO.setType(prePaidTicketRecord.getType());
        }

        prePaidTicketDataService.createOrUpdate(prePaidTicketDTO);
    }

    protected void checkAndResetExpiryDates(PrePaidTicketDTO prePaidTicketDTO) {
        if (prePaidTicketDTO.getStartZoneDTO().getEffectiveTo() != null) {
            prePaidTicketDTO.getStartZoneDTO().setEffectiveTo(null);
        }

        if (prePaidTicketDTO.getEndZoneDTO().getEffectiveTo() != null) {
            prePaidTicketDTO.getEndZoneDTO().setEffectiveTo(null);
        }

        if (prePaidTicketDTO.getFromDurationDTO().getEffectiveTo() != null) {
            prePaidTicketDTO.getFromDurationDTO().setEffectiveTo(null);
        }

        if (prePaidTicketDTO.getToDurationDTO().getEffectiveTo() != null) {
            prePaidTicketDTO.getToDurationDTO().setEffectiveTo(null);
        }

        if (prePaidTicketDTO.getDiscountTypeDTO().getEffectiveTo() != null) {
            prePaidTicketDTO.getDiscountTypeDTO().setEffectiveTo(null);
        }

        if (prePaidTicketDTO.getPassengerTypeDTO().getEffectiveTo() != null) {
            prePaidTicketDTO.getPassengerTypeDTO().setEffectiveTo(null);
        }
    }

    protected ZoneDTO getOrCreateZoneByCode(String code, Date effectiveDate) {
        ZoneDTO zoneDTO = zoneDataService.findByCode(code, effectiveDate);
        if (zoneDTO == null) {
            return new ZoneDTO(null, code, code, effectiveDate, null, null);
        } else if (isEffectiveToExpired(zoneDTO.getEffectiveTo())) {
            zoneDTO.setEffectiveTo(null);
        }
        return zoneDTO;
    }

    protected DurationDTO getOrCreateDurationByCode(String code, Date effectiveDate) {
        DurationDTO durationDTO = durationDataService.findByCode(code, effectiveDate);
        if (durationDTO == null) {
            return new DurationDTO(null, 1, "1", code, code, effectiveDate, null, null);
        } else if (isEffectiveToExpired(durationDTO.getEffectiveTo())) {
            durationDTO.setEffectiveTo(null);
        }
        return durationDTO;
    }

    protected PassengerTypeDTO getOrCreatePassengerTypeByCode(String code, Date effectiveDate) {
        PassengerTypeDTO passengerTypeDTO = passengerTypeDataService.findByCode(code, effectiveDate);
        if (passengerTypeDTO == null) {
            return new PassengerTypeDTO(null, code, code, effectiveDate, null, null);
        } else if (isEffectiveToExpired(passengerTypeDTO.getEffectiveTo())) {
            passengerTypeDTO.setEffectiveTo(null);
        }
        return passengerTypeDTO;
    }

    protected DiscountTypeDTO getOrCreateDiscountTypeByCode(String code, Date effectiveDate) {
        DiscountTypeDTO discountTypeDTO = discountTypeDataService.findByCode(code, effectiveDate);
        if (discountTypeDTO == null) {
            return new DiscountTypeDTO(null, code, code, effectiveDate, null, null);
        } else if (isEffectiveToExpired(discountTypeDTO.getEffectiveTo())) {
            discountTypeDTO.setEffectiveTo(null);
        }
        return discountTypeDTO;
    }

    protected PriceDTO createPriceDTO(Integer price, Date effectiveDate) {
        return new PriceDTO(null, price, effectiveDate, null, null);
    }

    protected Date getTodaysDate() {
        return DateUtils.truncate(new Date(), Calendar.DATE);
    }

    protected Boolean isEffectiveToExpired(Date date) {
        return date != null ? true : false;
    }

    protected PriceDTO getCurrentPriceFromList(List<PriceDTO> priceDTOs) {
        if (priceDTOs != null && priceDTOs.size() > 0) {
            for (PriceDTO priceDTO : priceDTOs) {
                if (priceDTO.getEffectiveTo() == null) {
                    return priceDTO;
                }
            }
        }
        return null;

    }

}
