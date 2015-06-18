package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.tfl.common.constant.CartAttribute.LINE_NO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.application_service.CardSelectListService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.RenewTravelCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.SelectCardValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * controller for renew product
 */
@Controller
@RequestMapping(value = PageUrl.QUICK_BUY)
public class QuickBuyController extends OnlineBaseController {
    static final Logger logger = LoggerFactory.getLogger(QuickBuyController.class);

    @Autowired
    protected CardSelectListService cardSelectListService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected RenewTravelCardService renewTravelCardService;
    @Autowired
    protected SelectCardValidator selectCardValidator;
	@Autowired
    protected SelectListService selectListService;

    @ModelAttribute(PageCommand.CART)
    public CartCmdImpl getCartCmd() {
        return new CartCmdImpl();
    }

    @ModelAttribute()
    public void populateCardsSelectList(Model model) {
        model.addAttribute(PageAttribute.CARDS,
                cardSelectListService.getCardsSelectList(this.securityService.getLoggedInUsername()));
    }

    public List<SelectListDTO> populateStartDatesSelectList(@ModelAttribute(PageCommand.CART) CartCmdImpl cmd) {
        List<SelectListDTO> startDates = new ArrayList<SelectListDTO>();
        for (CartItemCmdImpl cartItemCmdImpl : cmd.getCartItemList()) {
            startDates.add(renewTravelCardService.getRenewProductStartDateList(cartItemCmdImpl.getStartDate()));
        }
        return startDates;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showQuickBuy(HttpSession session, Model model,
                                     @ModelAttribute(PageAttribute.CARDS) SelectListDTO cardSelectList,
                                     @ModelAttribute(PageCommand.CART) CartCmdImpl cmd) {
        if (cardSelectList.getOptions().size() == 1) {
            Long cardId = new Long(cardSelectList.getOptions().iterator().next().getValue());
            CartDTO cartDTO = cartService.createCartFromCardId(cardId);
            CartCmdImpl cartCmd = renewTravelCardService.getCartItemsFromCubic(cartDTO, cardId);
            cartCmd.setCardId(cardId);
            cartCmd.setCartId(cartDTO.getId());
            createCartSessionData(session, cartCmd);
            addCartDTOToModel(model, cartDTO);
            return addStartDatesSelectListToModelAndView(cartCmd);
        } else {
            return new ModelAndView(PageView.QUICK_BUY, PageCommand.CART, new CartCmdImpl());
        }
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_SELECT_CARD, method = RequestMethod.POST)
    public ModelAndView selectCard(HttpSession session, Model model, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd,
                                   BindingResult result) {
        ModelAndView modelAndView = new ModelAndView(PageView.QUICK_BUY);
        selectCardValidator.validate(cmd, result);
        CartCmdImpl cartCmd = cmd;
        if (!result.hasErrors()) {
            CartDTO cartDTO = cartService.createCartFromCardId(cmd.getCardId());
            cartCmd = renewTravelCardService.getCartItemsFromCubic(cartDTO, cmd.getCardId());
            cartCmd.setCartId(cartDTO.getId());
            cartCmd.setCardId(cartDTO.getCardId());
            createCartSessionData(session, cartCmd);
            cartDTO = cartService.findById(cartDTO.getId());
            addCartDTOToModel(model, cartDTO);
            modelAndView.addObject(PageAttribute.START_DATES, populateStartDatesSelectList(cartCmd));
        }
        modelAndView.addObject(PageCommand.CART, cartCmd);
        return modelAndView;
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CONTINUE, method = RequestMethod.POST)
    public ModelAndView renew(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd) {
        cmd.setPageName(Page.QUICK_BUY);
        cmd.setCartType(CartType.RENEW.code());
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = null;
        cartDTO = cartService.findById(cartSessionData.getCartId());
		cartDTO.setCartType(CartType.RENEW.code());
        renewTravelCardService.renewProducts(cmd, cartDTO);
        cmd.setCartId(cartDTO.getId());
        createCartSessionData(session, cmd);
        return new ModelAndView(new RedirectView(PageUrl.COLLECT_PURCHASE));
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_DELETE, method = RequestMethod.POST)
    public ModelAndView deleteRenewItem(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, @RequestParam(value = LINE_NO) int lineNo) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        ProductItemDTO itemDTO = (ProductItemDTO) cartService.getMatchedProductItemDTOFromCartDTO(cartDTO, ProductItemDTO.class, lineNo);
        Long toBeDeletedItemId = itemDTO.getId();
        cartService.deleteItem(cartDTO, toBeDeletedItemId);
        deleteCartItemCmdFromcartCmd(cmd, toBeDeletedItemId);
        cmd = addRenewItemDTOWithCartItems(cmd, cartDTO);
        cartDTO = cartService.findById(cartSessionData.getCartId());
        cmd.setRenewItemCartDTO(cartDTO);
        return addStartDatesSelectListToModelAndView(cmd);
    }

    protected void deleteCartItemCmdFromcartCmd(CartCmdImpl cmd, Long toBeDeletedItemId) {
        CartItemCmdImpl toBeDeletedCartItemCmd = null;
        Iterator<CartItemCmdImpl> cmdIterator = cmd.getCartItemList().iterator();
        while (cmdIterator.hasNext()) {
            CartItemCmdImpl cartItemCmd = (CartItemCmdImpl) cmdIterator.next();
            if (toBeDeletedItemId.equals(cartItemCmd.getId())) {
                toBeDeletedCartItemCmd = cartItemCmd;
            }
        }
        if (null != toBeDeletedCartItemCmd) {
            cmd.getCartItemList().remove(toBeDeletedCartItemCmd);
        }
    }

    protected ModelAndView addStartDatesSelectListToModelAndView(CartCmdImpl cmd) {
        ModelAndView modelAndView = new ModelAndView(PageView.QUICK_BUY);
        modelAndView.addObject(PageAttribute.START_DATES, populateStartDatesSelectList(cmd));
        modelAndView.addObject(PageAttribute.CART_DTO, cmd.getRenewItemCartDTO());
        modelAndView.addObject(PageCommand.CART, cmd);
        return modelAndView;
    }

    protected void addCartDTOToModel(Model model, CartDTO cartDTO) {
        model.addAttribute(PageAttribute.CART_DTO, cartDTO);
    }

    protected void createCartSessionData(HttpSession session, CartCmdImpl cmd) {
        CartSessionData cartSessionData = new CartSessionData(cmd.getCartId());
        cartSessionData.setQuickBuyMode(Boolean.TRUE);
        cartSessionData.setPageName(Page.QUICK_BUY);
        CartUtil.addCartSessionDataDTOToSession(session, cartSessionData);
    }

    protected CartCmdImpl addRenewItemDTOWithCartItems(CartCmdImpl cmd, CartDTO cartDTO){
    	int updatedCountOfDeletedItemListSize = cartDTO.getCartItems().size();
    	List<CartItemCmdImpl> cartCmdItemDTOList = cmd.getCartItemList();
    	if((cartCmdItemDTOList.size()==0) && (updatedCountOfDeletedItemListSize >0)) {
    		cartDTO.setPpvRenewItemAddFlag(Boolean.TRUE);
    		cmd = renewTravelCardService.getCartItemsFromCubic(cartDTO, cartDTO.getCardId());
    		cmd.setCardId(cartDTO.getCardId());
    	}
    	return cmd;
    }
    
    @ModelAttribute
    public void populateEmailRemindersSelectList(Model model) {
        model.addAttribute(PageAttribute.BASKET_EMAIL_REMINDERS,
                selectListService.getSelectList(PageSelectList.BASKET_EMAIL_REMINDERS));
    }

}
