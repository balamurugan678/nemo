package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.CartConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.CartDAO;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.data_service.ItemDataService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowDataService;
import com.novacroft.nemo.tfl.common.domain.Cart;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

/**
 * Cart data service implementation
 */
@Service(value = "cartDataService")
@Transactional(readOnly = true)
public class CartDataServiceImpl extends BaseDataServiceImpl<Cart, CartDTO>
	implements CartDataService {
    static final Logger logger = LoggerFactory
	    .getLogger(CartDataServiceImpl.class);
    
    @Autowired
    WorkflowDataService workflowDataService;

    @Autowired 
    protected ItemDataService itemDataService;

    public CartDataServiceImpl() {
    	super();
    }

    @Autowired
    public void setDao(CartDAO dao) {
    	this.dao = dao;
    }

    @Autowired
    public void setConverter(CartConverterImpl converter) {
    	this.converter = converter;
    }

    @Override
    public Cart getNewEntity() {
    	return new Cart();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> findCartListByCustomerId(Long customerId) {
        String hsql = "select id from Cart as cart where customerId = ? or cardId in (select card.id from Card as card where card.customerId = ? )";
        return dao.findByQuery(hsql,customerId,customerId);
    }

    @Override
    @Transactional
    public CartDTO addApprovalId(CartDTO cartDTO) {
        Long approvalId = ((CartDAO) dao).getNextApprovalId();
        if (workflowDataService.isApprovalIdBeingUsed(approvalId)) {
            logger.error("ApprovalId " + approvalId + " already in use within workflow");
            return addApprovalId(cartDTO);
        } else {
            cartDTO.setApprovalId(approvalId);
            return createOrUpdate(cartDTO);
        }
    }

    @Override
    @Transactional
    public CartDTO createOrUpdate(CartDTO dto) {
		dto = setItemDateTime(dto);
		return super.createOrUpdate(dto);
    }
    
    protected CartDTO setItemDateTime(CartDTO cartDTO) {
		for (ItemDTO itemDTO : cartDTO.getCartItems()) {
		    if (itemDTO != null) {
				itemDTO = itemDataService.setDateTime(itemDTO);
				itemDTO = itemDataService.setUserId(itemDTO);
		    }
		}
		return cartDTO;
    }
    
    @Override
    public CartDTO findByWebAccountId(Long webAccountId) {
		Cart cart = new Cart();
		cart.setWebAccountId(webAccountId);
		cart = dao.findByExampleUniqueResult(cart);
		if (cart != null) {
		    return this.converter.convertEntityToDto(cart);
		}
		return null;
    }

    @Override
    public CartDTO findByCardId(Long cardId) {
		Cart cart = new Cart();
		cart.setCardId(cardId);
		cart = dao.findByExampleUniqueResult(cart);
		if (cart != null) {
		    return this.converter.convertEntityToDto(cart);
		}
		return null;
    }

    @Override
    public CartDTO findByCustomerId(Long customerId) {
		Cart cart = new Cart();
		cart.setCustomerId(customerId);
		cart = dao.findByExampleUniqueResult(cart);
		if (cart != null) {
		    return this.converter.convertEntityToDto(cart);
		}
		return null;
    }

	@Override
	public CartDTO findByApprovalId(Long approvalId) {
		Cart cart = new Cart();
		cart.setApprovalId(approvalId);
		cart = dao.findByExampleUniqueResult(cart);
		if (cart != null) {
		    return this.converter.convertEntityToDto(cart);
		}
		return null;
	}

	@Override
	public CartDTO findNotInWorkFlowFlightCartByCustomerId(Long customerId) {
		Cart cart = new Cart();
		cart.setCustomerId(customerId);
		cart.setWorkFlowInFlight(Boolean.FALSE);
		cart = dao.findByExampleUniqueResult(cart);
		if (cart != null) {
		    return this.converter.convertEntityToDto(cart);
		}
		return null;
	}

	@Override
	public CartDTO findNotInWorkFlowFlightCartByCardId(Long cardId) {
		Cart cart = new Cart();
		cart.setCardId(cardId);
		cart.setWorkFlowInFlight(Boolean.FALSE);
		cart = dao.findByExampleUniqueResult(cart);
		if (cart != null) {
		    return this.converter.convertEntityToDto(cart);
		}
		return null;
	}
    
}
