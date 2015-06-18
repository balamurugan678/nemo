package com.novacroft.nemo.tfl.common.converter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.common.utils.Converter;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.ProductService;
import com.novacroft.nemo.tfl.common.data_service.RefundEngineService;
import com.novacroft.nemo.tfl.common.domain.AutoTopUpConfigurationItem;
import com.novacroft.nemo.tfl.common.domain.GoodwillPaymentItem;
import com.novacroft.nemo.tfl.common.domain.Item;
import com.novacroft.nemo.tfl.common.domain.PayAsYouGoItem;
import com.novacroft.nemo.tfl.common.domain.ProductItem;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * Transform between item entity and DTO.
 */
@Component(value = "itemConverter")
public class ItemConverterImpl extends BaseDtoEntityConverterImpl<Item, ItemDTO> {
    	static final Logger logger = LoggerFactory.getLogger(ItemConverterImpl.class);
    
	protected static final String DTO = "DTO";
	protected static final String DOMAIN = "domain";
	protected static final String TRANSFER = "transfer";
	
	@Autowired
	protected GoodwillPaymentItemConverterImpl goodwillPaymentItemConverter;
	
	@Autowired
	protected NemoUserContext nemoUserContext;
	
	@Autowired
	protected ProductService productService;
	
	@Autowired
	protected RefundEngineService refundEngineService;
	
	@Autowired
	protected ProductItemConverterImpl productItemConverter; 
	
	@Autowired
    protected PayAsYouGoItemConverterImpl payAsYouGoItemConverter;
	
	@Autowired
    protected AutoTopUpConfigurationItemConverterImpl autoTopUpConfigurationItemConverter;
	
    @Override
    protected ItemDTO getNewDto() {
        return new ProductItemDTO();
    }
    
    public ItemDTO getNewDto(Item item) {
    	Class<? extends Item> itemClass = item.getClass();
    	String itemClassName = itemClass.getName();
    	String itemDTOClassName = itemClassName + DTO;
    	itemDTOClassName = itemDTOClassName.replace(DOMAIN, TRANSFER);
    	try {
    		Class<?> itemDTOClass = Class.forName(itemDTOClassName);
    		return (ItemDTO) itemDTOClass.newInstance();
    	} catch (ReflectiveOperationException roe) {
    		return new ProductItemDTO();
    	} 
    }
    
    @Override
    public ItemDTO convertEntityToDto(Item item) {
	if (item instanceof GoodwillPaymentItem) {
	    return goodwillPaymentItemConverter.convertEntityToDto(item);
	} else if (item instanceof ProductItem) {
	    return productItemConverter.convertEntityToDto((ProductItem) item);
	} else if (item instanceof PayAsYouGoItem) {
        return payAsYouGoItemConverter.convertEntityToDto((PayAsYouGoItem) item);
    }else if (item instanceof AutoTopUpConfigurationItem) {
        return autoTopUpConfigurationItemConverter.convertEntityToDto((AutoTopUpConfigurationItem) item);
    }
	return (ItemDTO) Converter.convert(item, getNewDto(item));
    }
    
    
    
    @Override
    public Item convertDtoToEntity(ItemDTO dto, Item item) {
        Item newItem = null == item ? getNewEntity(dto) : item;
	if (newItem instanceof GoodwillPaymentItem) {
	    return goodwillPaymentItemConverter.convertDtoToEntity(dto, newItem);
	} if (newItem instanceof ProductItem) {
	    return productItemConverter.convertDtoToEntity((ProductItemDTO) dto, (ProductItem) newItem);
	}if (newItem instanceof PayAsYouGoItem) {
        return payAsYouGoItemConverter.convertDtoToEntity((PayAsYouGoItemDTO) dto, (PayAsYouGoItem) newItem);
    }if (newItem instanceof AutoTopUpConfigurationItem) {
        return autoTopUpConfigurationItemConverter.convertDtoToEntity((AutoTopUpConfigurationItemDTO) dto, (AutoTopUpConfigurationItem) newItem);
    }else {
	    Converter.convert(dto, newItem);
	}
	return newItem;
    }
    
    protected Item getNewEntity(ItemDTO itemDTO) {
    	Class<? extends ItemDTO> itemDTOClass = itemDTO.getClass();
    	String itemDTOClassName = itemDTOClass.getName();
    	String itemClassName = itemDTOClassName.replace(DTO, StringUtil.EMPTY_STRING);
    	itemClassName = itemClassName.replace(TRANSFER, DOMAIN);
    	try {
    		Class<?> itemClass = Class.forName(itemClassName);
    		return (Item) itemClass.newInstance();
    	} catch (ReflectiveOperationException roe) {
    		return new Item();
    	} 
    }
}
