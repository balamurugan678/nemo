package com.novacroft.nemo.tfl.common.transfer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.novacroft.nemo.common.transfer.PeriodicAbstractBaseDTO;

public class PrePaidTicketDTO extends PeriodicAbstractBaseDTO {

    private static final long serialVersionUID = -1818694177058301210L;
    protected String adHocPrePaidTicketCode;
    protected String description;
    protected String cubicReference;
    protected DurationDTO fromDurationDTO;
    protected DurationDTO toDurationDTO;
    protected PassengerTypeDTO passengerTypeDTO;
    protected ZoneDTO startZoneDTO;
    protected ZoneDTO endZoneDTO;
    protected DiscountTypeDTO discountTypeDTO;
    protected List<PriceDTO> prices = new ArrayList<>();
    protected String type;

    public PrePaidTicketDTO() {

    }

    public PrePaidTicketDTO(Long id, Date effectiveFrom, Date effectiveTo, String cubicReference, String adHocPrePaidTicketCode, String description,
                    DurationDTO fromDuration, DurationDTO toDuration, PassengerTypeDTO passengerType, ZoneDTO fromZone, ZoneDTO toZone,
                    DiscountTypeDTO discountType, List<PriceDTO> prices) {
        this.id = id;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.cubicReference = cubicReference;
        this.adHocPrePaidTicketCode = adHocPrePaidTicketCode;
        this.description = description;
        this.fromDurationDTO = fromDuration;
        this.toDurationDTO = toDuration;
        this.passengerTypeDTO = passengerType;
        this.startZoneDTO = fromZone;
        this.endZoneDTO = toZone;
        this.discountTypeDTO = discountType;
        this.prices = prices;
    }

    public String getAdHocPrePaidTicketCode() {
        return adHocPrePaidTicketCode;
    }

    public void setAdHocPrePaidTicketCode(String adHocPrePaidTicketCode) {
        this.adHocPrePaidTicketCode = adHocPrePaidTicketCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getCubicReference() {
        return cubicReference;
    }

    @Override
    public void setCubicReference(String cubicReference) {
        this.cubicReference = cubicReference;
    }

    public DurationDTO getFromDurationDTO() {
        return fromDurationDTO;
    }

    public void setFromDurationDTO(DurationDTO fromDurationDTO) {
        this.fromDurationDTO = fromDurationDTO;
    }

    public DurationDTO getToDurationDTO() {
        return toDurationDTO;
    }

    public void setToDurationDTO(DurationDTO toDurationDTO) {
        this.toDurationDTO = toDurationDTO;
    }

    public PassengerTypeDTO getPassengerTypeDTO() {
        return passengerTypeDTO;
    }

    public void setPassengerTypeDTO(PassengerTypeDTO passengerTypeDTO) {
        this.passengerTypeDTO = passengerTypeDTO;
    }

    public ZoneDTO getStartZoneDTO() {
        return startZoneDTO;
    }

    public void setStartZoneDTO(ZoneDTO startZoneDTO) {
        this.startZoneDTO = startZoneDTO;
    }

    public ZoneDTO getEndZoneDTO() {
        return endZoneDTO;
    }

    public void setEndZoneDTO(ZoneDTO endZoneDTO) {
        this.endZoneDTO = endZoneDTO;
    }

    public List<PriceDTO> getPrices() {
        return prices;
    }

    public void setPrices(List<PriceDTO> prices) {
        this.prices = prices;
    }

    public DiscountTypeDTO getDiscountTypeDTO() {
        return discountTypeDTO;
    }

    public void setDiscountTypeDTO(DiscountTypeDTO discountTypeDTO) {
        this.discountTypeDTO = discountTypeDTO;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    

}
