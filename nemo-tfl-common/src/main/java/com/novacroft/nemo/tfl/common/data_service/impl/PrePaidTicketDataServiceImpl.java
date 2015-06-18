package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.converter.impl.PrePaidTicketConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.PrePaidTicketDAO;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.domain.PrePaidTicket;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;

@Service(value = "prePaidTicketDataService")
@Transactional(readOnly = true)
public class PrePaidTicketDataServiceImpl extends BaseDataServiceImpl<PrePaidTicket, PrePaidTicketDTO> implements
		PrePaidTicketDataService {

	protected static final String CURRENT_DATE_PLACE_HOLDER = " AND $.effectiveFrom <= CURRENT_DATE() and ($.effectiveTo is null or $.effectiveTo >= CURRENT_DATE()) ";

	protected static final String NAMED_DATE_PLACE_HOLDER = " AND $.effectiveFrom <= :effectiveDate and ($.effectiveTo is null or $.effectiveTo >= :effectiveDate) ";

	protected static final String DEFAULT_PASSENGERTYPE_DISCOUNT_APPEND = " AND p.passengerType.code = :passengerType  and p.discountType.code = :discountType";

	private static final String FROM_DURATION = "fromDuration";
	private static final String TO_DURATION = "toDuration";
	private static final String START_ZONE = "startZone";
	private static final String END_ZONE = "endZone";
	private static final String EFFECTIVE_DATE = "effectiveDate";
	private static final String PASSENGER_TYPE = "passengerType";
	private static final String DISCOUNT_TYPE = "discountType";
	private static final String PRODUCT_CODE = "productCode";
	private static final String TYPE = "type";

	@Autowired
	public void setDao(PrePaidTicketDAO dao) {
		this.dao = dao;
	}

	@Autowired
	public void setConverter(PrePaidTicketConverterImpl converter) {
		this.converter = converter;
	}

	@Override
	public PrePaidTicket getNewEntity() {
		return new PrePaidTicket();
	}

	@Override
	public PrePaidTicketDTO findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(String fromDuration,
			String toDuration, Integer startZone, Integer endZone, Date effectiveDate, String passengerType,
			String discountType, String type) {
		final String hsql = "from PrePaidTicket p where p.startZone.code = :startZone  AND p.endZone.code = :endZone "
				+ getPeriodClause(effectiveDate, "p", "p.fromDuration", "p.toDuration", "p.startZone", "p.endZone",
						"p.passengerType", "p.discountType")
				+ " and  p.fromDuration.code = :fromDuration and  p.toDuration.code = :toDuration and p.type = :type "
				+ DEFAULT_PASSENGERTYPE_DISCOUNT_APPEND;

		final Map<String, Object> namedParameterMap = new HashMap<>();
		namedParameterMap.put(FROM_DURATION, fromDuration);
		namedParameterMap.put(TO_DURATION, toDuration);
		namedParameterMap.put(TYPE, type);
		namedParameterMap.put(START_ZONE, startZone.toString());
		namedParameterMap.put(END_ZONE, endZone.toString());
		namedParameterMap.put(PASSENGER_TYPE, passengerType);
		namedParameterMap.put(DISCOUNT_TYPE, discountType);

		setUpEffectiveDateParameterIfNotNull(effectiveDate, namedParameterMap);
		PrePaidTicket prePaidTicket = dao.findByQueryUniqueResultUsingNamedParameters(hsql, namedParameterMap);
		return (prePaidTicket != null) ? this.converter.convertEntityToDto(prePaidTicket) : null;
	}

	private void setUpEffectiveDateParameterIfNotNull(Date effectiveDate, final Map<String, Object> namedParameterMap) {
		if (effectiveDate != null) {
			namedParameterMap.put(EFFECTIVE_DATE, DateUtil.truncateAtDay(effectiveDate));
		}
	}

	@Override
	public List<PrePaidTicketDTO> findAllByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(
			String fromDuration, String toDuration, Integer startZone, Integer endZone, String passengerType,
			String discountType, String type) {
		final String hsql = "Select distinct p from PrePaidTicket p where p.startZone.code = :startZone  AND p.endZone.code = :endZone "
				+ " and  p.fromDuration.code = :fromDuration and  p.toDuration.code = :toDuration and p.type = :type "
				+ DEFAULT_PASSENGERTYPE_DISCOUNT_APPEND;

		final Map<String, Object> namedParameterMap = new HashMap<>();
		namedParameterMap.put(FROM_DURATION, fromDuration);
		namedParameterMap.put(TO_DURATION, toDuration);
		namedParameterMap.put(TYPE, type);
		namedParameterMap.put(START_ZONE, startZone.toString());
		namedParameterMap.put(END_ZONE, endZone.toString());
		namedParameterMap.put(PASSENGER_TYPE, passengerType);
		namedParameterMap.put(DISCOUNT_TYPE, discountType);

		@SuppressWarnings("unchecked")
		List<PrePaidTicketDTO> prePaidTicketList = dao.findByQueryUsingNamedParameters(hsql, namedParameterMap);
		return convert(prePaidTicketList);
	}

	@Override
	public PrePaidTicketDTO findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(String fromDuration,
			Integer startZone, Integer endZone, Date effectiveDate, String passengerType, String discountType,
			String type) {
		final String hsql = "from PrePaidTicket p where p.startZone.code = :startZone  AND p.endZone.code = :endZone"
				+ getPeriodClause(effectiveDate, "p", "p.fromDuration", "p.toDuration", "p.startZone", "p.endZone",
						"p.passengerType", "p.discountType")
				+ " and  p.fromDuration.code = :fromDuration and  p.toDuration.code = :toDuration and p.type = :type "
				+ DEFAULT_PASSENGERTYPE_DISCOUNT_APPEND;

		final Map<String, Object> namedParameterMap = new HashMap<>();
		namedParameterMap.put(FROM_DURATION, fromDuration);
		namedParameterMap.put(TO_DURATION, fromDuration);
		namedParameterMap.put(START_ZONE, startZone.toString());
		namedParameterMap.put(END_ZONE, endZone.toString());
		namedParameterMap.put(PASSENGER_TYPE, passengerType);
		namedParameterMap.put(DISCOUNT_TYPE, discountType);
		namedParameterMap.put(TYPE, type);

		setUpEffectiveDateParameterIfNotNull(effectiveDate, namedParameterMap);
		PrePaidTicket prePaidTicket = dao.findByQueryUniqueResultUsingNamedParameters(hsql, namedParameterMap);
		return (prePaidTicket != null) ? this.converter.convertEntityToDto(prePaidTicket) : null;
	}

	@Override
	public PrePaidTicketDTO findByProductCode(String productCode, Date effectiveDate) {
		final String hsql = "from PrePaidTicket p where p.adHocPrePaidTicketCode = :productCode "
				+ getPeriodClause(effectiveDate, "p");
		final Map<String, Object> namedParameterMap = new HashMap<>();
		namedParameterMap.put(PRODUCT_CODE, productCode);
		setUpEffectiveDateParameterIfNotNull(effectiveDate, namedParameterMap);
		PrePaidTicket paidTicket = dao.findByQueryUniqueResultUsingNamedParameters(hsql, namedParameterMap);
		return (paidTicket != null) ? this.converter.convertEntityToDto(paidTicket) : null;
	}

	@Override
	public PrePaidTicketDTO findByProductCode(String productCode) {
		final String hsql = "from PrePaidTicket p where p.adHocPrePaidTicketCode = :productCode "
				+ getPeriodClause(null, "p");
		final Map<String, Object> namedParameterMap = new HashMap<>();
		namedParameterMap.put(PRODUCT_CODE, productCode);
		PrePaidTicket paidTicket = dao.findByQueryUniqueResultUsingNamedParameters(hsql, namedParameterMap);
		return (paidTicket != null) ? this.converter.convertEntityToDto(paidTicket) : null;
	}

	@Override
	public List<PrePaidTicketDTO> findAllByExample(PrePaidTicket example) {
		List<PrePaidTicket> items = dao.findByExample(example);
		return convert(items);
	}

	@Override
	public List<PrePaidTicketDTO> findProductsByDurationAndStartZone(String duration, Integer startZone,
			Date effectiveDate) {

		final Map<String, Object> namedParameterMap = new HashMap<>();
		namedParameterMap.put("startZone", startZone.toString());
		String hsql = "from PrePaidTicket p where p.fromDuration.code = :fromDuration and p.toDuration.code = :fromDuration  "
				+ "and p.startZone.code = :startZone" + getPeriodClause(effectiveDate, "p", "p.startZone");
		namedParameterMap.put(FROM_DURATION, duration);

		setUpEffectiveDateParameterIfNotNull(effectiveDate, namedParameterMap);

		@SuppressWarnings("unchecked")
		List<PrePaidTicket> prePaidTickets = dao.findByQueryUsingNamedParameters(hsql, namedParameterMap);
		return convert(prePaidTickets);

	}

	@Override
	public List<PrePaidTicketDTO> findProductsByStartZoneAndTypeForOddPeriod(Integer startZone, Date effectiveDate,
			String type) {

		final Map<String, Object> namedParameterMap = new HashMap<>();
		namedParameterMap.put("startZone", startZone.toString());
		namedParameterMap.put(TYPE, type);
		String hsql = "from PrePaidTicket p where p.fromDuration.code !=  p.toDuration.code and p.startZone.code = :startZone and p.type = :type "
				+ getPeriodClause(effectiveDate, "p", "p.startZone");

		setUpEffectiveDateParameterIfNotNull(effectiveDate, namedParameterMap);

		@SuppressWarnings("unchecked")
		List<PrePaidTicket> prePaidTickets = dao.findByQueryUsingNamedParameters(hsql, namedParameterMap);
		return convert(prePaidTickets);

	}

	@Override
	protected String getPeriodClause(final Date effectiveDate, String... entities) {
		StringBuilder dateClauseBuilder = new StringBuilder();
		if (effectiveDate == null) {
			for (String namedEntity : entities) {
				dateClauseBuilder.append(StringUtils.replace(CURRENT_DATE_PLACE_HOLDER, "$", namedEntity));
			}

		} else {
			for (String namedEntity : entities) {
				dateClauseBuilder.append(StringUtils.replace(NAMED_DATE_PLACE_HOLDER, "$", namedEntity));
			}
		}
		return dateClauseBuilder.toString();
	}
}
