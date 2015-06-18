package com.novacroft.nemo.tfl.common.converter.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.converter.PrePaidTicketConverter;
import com.novacroft.nemo.tfl.common.data_access.DiscountTypeDAO;
import com.novacroft.nemo.tfl.common.data_access.DurationDAO;
import com.novacroft.nemo.tfl.common.data_access.PassengerTypeDAO;
import com.novacroft.nemo.tfl.common.data_access.PriceDAO;
import com.novacroft.nemo.tfl.common.data_access.ZoneDAO;
import com.novacroft.nemo.tfl.common.domain.DiscountType;
import com.novacroft.nemo.tfl.common.domain.Duration;
import com.novacroft.nemo.tfl.common.domain.PassengerType;
import com.novacroft.nemo.tfl.common.domain.PrePaidTicket;
import com.novacroft.nemo.tfl.common.domain.Price;
import com.novacroft.nemo.tfl.common.domain.Zone;
import com.novacroft.nemo.tfl.common.transfer.DiscountTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.DurationDTO;
import com.novacroft.nemo.tfl.common.transfer.PassengerTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;
import com.novacroft.nemo.tfl.common.transfer.PriceDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ZoneDTO;

@Component(value = "prePaidTicketConverter")
public class PrePaidTicketConverterImpl extends BaseDtoEntityConverterImpl<PrePaidTicket, PrePaidTicketDTO> implements PrePaidTicketConverter {

    @Override
    protected PrePaidTicketDTO getNewDto() {
        return new PrePaidTicketDTO();
    }

    @Autowired
    protected ZoneConverterImpl zoneConverter;

    @Autowired
    protected PassengerTypeConverterImpl passengerTypeConverter;

    @Autowired
    protected DurationConverterImpl durationConverter;

    @Autowired
    protected DiscountTypeConverterImpl discountTypeConverter;

    @Autowired
    protected PriceConverterImpl priceConverter;

    @Autowired
    protected ZoneDAO zoneDAO;

    @Autowired
    protected DurationDAO durationDAO;

    @Autowired
    protected PassengerTypeDAO passengerTypeDAO;

    @Autowired
    protected DiscountTypeDAO discountTypeDAO;

    @Autowired
    protected PriceDAO priceDAO;

    @Override
    public PrePaidTicketDTO convertEntityToDto(PrePaidTicket prePaidTicket) {
        PrePaidTicketDTO prePaidTicketDTO = super.convertEntityToDto(prePaidTicket);
        setUpDiscountTypetDTO(prePaidTicketDTO, prePaidTicket);
        setUpDPricestDTO(prePaidTicketDTO, prePaidTicket);
        setUpFromDurationtDTO(prePaidTicketDTO, prePaidTicket);
        setUpToDurationtDTO(prePaidTicketDTO, prePaidTicket);
        setUpPassengerTypetDTO(prePaidTicketDTO, prePaidTicket);
        setUpPassengerTypetDTO(prePaidTicketDTO, prePaidTicket);
        setUpStartZoneDTO(prePaidTicketDTO, prePaidTicket);
        setUpEndZoneDTO(prePaidTicketDTO, prePaidTicket);
        return prePaidTicketDTO;
    }

    @Override
    public PrePaidTicket convertDtoToEntity(PrePaidTicketDTO prePaidTicketDTO, PrePaidTicket prePaidTicket) {
        final ZoneDTO startZoneDTO = prePaidTicketDTO.getStartZoneDTO();
        final ZoneDTO endZoneDTO = prePaidTicketDTO.getEndZoneDTO();
        final DurationDTO fromDurationDTO = prePaidTicketDTO.getFromDurationDTO();
        final DurationDTO toDurationDTO = prePaidTicketDTO.getToDurationDTO();
        final DiscountTypeDTO discountTypeDTO = prePaidTicketDTO.getDiscountTypeDTO();
        final PassengerTypeDTO passengerTypeDTO = prePaidTicketDTO.getPassengerTypeDTO();

        setDiscountType(prePaidTicket, discountTypeDTO);

        setPassengerType(prePaidTicket, passengerTypeDTO);

        setFromDuration(prePaidTicket, fromDurationDTO);

        setToDuration(prePaidTicket, fromDurationDTO, toDurationDTO);

        setStartZone(prePaidTicket, startZoneDTO);

        setEndZone(prePaidTicket, startZoneDTO, endZoneDTO);

        setUpPrices(prePaidTicket, prePaidTicketDTO.getPrices());

        return super.convertDtoToEntity(prePaidTicketDTO, prePaidTicket);
    }

    protected void setUpPrices(PrePaidTicket prePaidTicket, List<PriceDTO> prices) {
        for (PriceDTO priceDTO : prices) {
            if (priceDTO.getId() == null) {
                prePaidTicket.addPrice(priceConverter.convertDtoToEntity(priceDTO, new Price()));
            } else {
                prePaidTicket.addPrice(priceConverter.convertDtoToEntity(priceDTO, priceDAO.findById(priceDTO.getId())));
            }

        }

    }

    protected void setDiscountType(PrePaidTicket prePaidTicket, final DiscountTypeDTO discountTypeDTO) {
        if (discountTypeDTO.getId() == null) {
            prePaidTicket.setDiscountType(discountTypeConverter.convertDtoToEntity(discountTypeDTO, new DiscountType()));
        } else {
            discountTypeDTO.setNullable(true);
            prePaidTicket.setDiscountType(discountTypeConverter.convertDtoToEntity(discountTypeDTO, discountTypeDAO.findById(discountTypeDTO.getId())));
        }
    }

    protected void setPassengerType(PrePaidTicket prePaidTicket, final PassengerTypeDTO passengerTypeDTO) {
        if (passengerTypeDTO.getId() == null) {
            prePaidTicket.setPassengerType(passengerTypeConverter.convertDtoToEntity(passengerTypeDTO, new PassengerType()));
        } else {
            passengerTypeDTO.setNullable(true);
            prePaidTicket.setPassengerType(passengerTypeConverter.convertDtoToEntity(passengerTypeDTO,
                            passengerTypeDAO.findById(passengerTypeDTO.getId())));
        }
    }

    protected void setToDuration(PrePaidTicket prePaidTicket, final DurationDTO fromDurationDTO, final DurationDTO toDurationDTO) {
        if (fromDurationDTO.getCode().equalsIgnoreCase(toDurationDTO.getCode())) {
            prePaidTicket.setToDuration(prePaidTicket.getFromDuration());
        } else if (toDurationDTO.getId() == null) {
            prePaidTicket.setToDuration(durationConverter.convertDtoToEntity(toDurationDTO, new Duration()));
        } else {
            toDurationDTO.setNullable(true);
            prePaidTicket.setToDuration(durationConverter.convertDtoToEntity(toDurationDTO, durationDAO.findById(toDurationDTO.getId())));
        }
    }

    protected void setFromDuration(PrePaidTicket prePaidTicket, final DurationDTO fromDurationDTO) {
        if (fromDurationDTO.getId() == null) {
            prePaidTicket.setFromDuration(durationConverter.convertDtoToEntity(fromDurationDTO, new Duration()));
        } else {
            fromDurationDTO.setNullable(true);
            prePaidTicket.setFromDuration(durationConverter.convertDtoToEntity(fromDurationDTO, durationDAO.findById(fromDurationDTO.getId())));
        }
    }

    protected void setEndZone(PrePaidTicket prePaidTicket, final ZoneDTO startZoneDTO, final ZoneDTO endZoneDTO) {
        if (startZoneDTO.getCode().equalsIgnoreCase(endZoneDTO.getCode())) {
            prePaidTicket.setEndZone(prePaidTicket.getStartZone());
        } else if (endZoneDTO.getId() == null) {
            prePaidTicket.setEndZone(zoneConverter.convertDtoToEntity(endZoneDTO, new Zone()));
        } else {
            endZoneDTO.setNullable(true);
            prePaidTicket.setEndZone(zoneConverter.convertDtoToEntity(endZoneDTO, zoneDAO.findById(endZoneDTO.getId())));
        }
    }

    protected void setStartZone(PrePaidTicket prePaidTicket, final ZoneDTO startZoneDTO) {
        if (startZoneDTO.getId() == null) {
            prePaidTicket.setStartZone(zoneConverter.convertDtoToEntity(startZoneDTO, new Zone()));
        } else {
            startZoneDTO.setNullable(true);
            prePaidTicket.setStartZone(zoneConverter.convertDtoToEntity(startZoneDTO, zoneDAO.findById(startZoneDTO.getId())));
        }
    }

    protected void setUpStartZoneDTO(PrePaidTicketDTO prePaidTicketDTO, PrePaidTicket prePaidTicket) {
        if (prePaidTicket.getStartZone() != null) {
            prePaidTicketDTO.setStartZoneDTO(zoneConverter.convertEntityToDto(prePaidTicket.getStartZone()));
        }

    }

    protected void setUpEndZoneDTO(PrePaidTicketDTO prePaidTicketDTO, PrePaidTicket prePaidTicket) {
        if (prePaidTicket.getEndZone() != null) {
            prePaidTicketDTO.setEndZoneDTO(zoneConverter.convertEntityToDto(prePaidTicket.getEndZone()));
        }

    }

    protected void setUpFromDurationtDTO(PrePaidTicketDTO prePaidTicketDTO, PrePaidTicket prePaidTicket) {
        if (prePaidTicket.getFromDuration() != null) {
            prePaidTicketDTO.setFromDurationDTO(durationConverter.convertEntityToDto(prePaidTicket.getFromDuration()));
        }

    }

    protected void setUpToDurationtDTO(PrePaidTicketDTO prePaidTicketDTO, PrePaidTicket prePaidTicket) {
        if (prePaidTicket.getToDuration() != null) {
            prePaidTicketDTO.setToDurationDTO(durationConverter.convertEntityToDto(prePaidTicket.getToDuration()));
        }

    }

    protected void setUpPassengerTypetDTO(PrePaidTicketDTO prePaidTicketDTO, PrePaidTicket prePaidTicket) {
        if (prePaidTicket.getPassengerType() != null) {
            prePaidTicketDTO.setPassengerTypeDTO(passengerTypeConverter.convertEntityToDto(prePaidTicket.getPassengerType()));
        }
    }

    protected void setUpDiscountTypetDTO(PrePaidTicketDTO prePaidTicketDTO, PrePaidTicket prePaidTicket) {
        if (prePaidTicket.getDiscountType() != null) {
            prePaidTicketDTO.setDiscountTypeDTO(discountTypeConverter.convertEntityToDto(prePaidTicket.getDiscountType()));
        }
    }

    protected void setUpDPricestDTO(PrePaidTicketDTO prePaidTicketDTO, PrePaidTicket prePaidTicket) {
        if (!prePaidTicket.getPrices().isEmpty()) {
            List<PriceDTO> priceList = new ArrayList<>();
            for (Price price : prePaidTicket.getPrices()) {
                priceList.add(priceConverter.convertEntityToDto(price));
            }
            prePaidTicketDTO.setPrices(priceList);
        }
    }

    @Override
    public ProductDTO convertToProductDto(PrePaidTicketDTO prePaidTicketDTO, Date effectiveDate) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCreatedDateTime(prePaidTicketDTO.getCreatedDateTime());
        productDTO.setCreatedUserId(prePaidTicketDTO.getCreatedUserId());
        productDTO.setDuration(prePaidTicketDTO.getFromDurationDTO().getCode().equals(prePaidTicketDTO.getToDurationDTO().getCode()) ? prePaidTicketDTO
                        .getFromDurationDTO().getCode() : Durations.OTHER.getDurationType());
        productDTO.setEndZone(prePaidTicketDTO.getEndZoneDTO() != null ? Integer.parseInt(prePaidTicketDTO.getEndZoneDTO().getCode()) : null);
        productDTO.setStartZone(prePaidTicketDTO.getStartZoneDTO() != null ? Integer.parseInt(prePaidTicketDTO.getStartZoneDTO().getCode()) : null);
        productDTO.setExternalId(prePaidTicketDTO.getExternalId());
        productDTO.setId(prePaidTicketDTO.getId());
        productDTO.setModifiedDateTime(prePaidTicketDTO.getModifiedDateTime());
        productDTO.setModifiedUserId(prePaidTicketDTO.getModifiedUserId());
        productDTO.setProductCode(prePaidTicketDTO.getAdHocPrePaidTicketCode());
        productDTO.setProductName(prePaidTicketDTO.getDescription());
        productDTO.setRate(prePaidTicketDTO.getPassengerTypeDTO() != null ? prePaidTicketDTO.getPassengerTypeDTO().getName() : null);
        productDTO.setTicketPrice(getEffectivePriceForDate(prePaidTicketDTO, effectiveDate));
        productDTO.setType(prePaidTicketDTO.getType());
        return productDTO;
    }

    private Integer getEffectivePriceForDate(PrePaidTicketDTO prePaidTicketDTO, Date effectiveDate) {
        if (prePaidTicketDTO.getPrices() != null) {
            for (PriceDTO priceDto : prePaidTicketDTO.getPrices()) {
                if (isCurrentPrice(effectiveDate, priceDto)) {
                    return priceDto.getpriceInPence();
                }
            }
        }
        return null;
    }

    protected boolean isCurrentPrice(Date effectiveDate, PriceDTO priceDTO) {
        Date truncatedEffectiveDate = DateUtil.truncateAtDay(effectiveDate);
        return DateUtil.isAfterOrEqual(truncatedEffectiveDate, priceDTO.getEffectiveFrom())
                        && (priceDTO.getEffectiveTo() == null || DateUtil.isBeforeOrEqual(truncatedEffectiveDate, priceDTO.getEffectiveTo()));
    }
}
