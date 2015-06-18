package com.novacroft.nemo.tfl.online.controller;

import java.util.Date;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.domain.cubic.PendingItems;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CardPreferencesDataService;
import com.novacroft.nemo.tfl.common.form_validator.PickUpLocationValidator;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CardPreferencesDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Collect product purchase controller
 */
@Controller
@RequestMapping(value = PageUrl.COLLECT_PURCHASE)
public class CollectPurchaseController extends OnlineBaseController {
    static final Logger logger = LoggerFactory.getLogger(CollectPurchaseController.class);

    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected LocationSelectListService locationSelectListService;
    @Autowired
    protected PickUpLocationValidator pickUpLocationValidator;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected CardService cardService;
    @Autowired
    protected CardPreferencesDataService cardPreferencesDataService;
    
    @ModelAttribute(PageCommand.CART)
    public CartCmdImpl getCartCmd() {
        return new CartCmdImpl();
    }

    @ModelAttribute
    public void populateLocationsSelectList(Model model) {
        model.addAttribute(PageAttribute.LOCATIONS, this.locationSelectListService.getLocationSelectList());
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showCollectPurchase(HttpSession session, Model model) {
        CartDTO cartDTO = null;
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        cartDTO = cartService.findById(cartSessionData.getCartId());
        addCartDTOToModel(model, cartDTO);
        CartCmdImpl cmd = new CartCmdImpl();
        cmd.setPageName(cartSessionData.getPageName());
        cartSessionData.setPageName(null);
        cmd.setCardId(getCardIdForQuickBuyTopupTicketMode(session, cartSessionData, cmd));
        getTicketType(cartDTO,cartSessionData);
        checkPendingItems(cmd);
        setCollectPurchaseStartAndEndDates(cartDTO, cmd);
        return new ModelAndView(PageView.COLLECT_PURCHASE, PageCommand.CART, cmd);
    }
 
    protected void getTicketType(CartDTO cartDTO, CartSessionData cartSessionData) {
        boolean payAsYouItem = false;
        boolean autoTopUpItem = false;
        for (ItemDTO itemDTOObj : cartDTO.getCartItems()) {
            if (itemDTOObj.getClass().equals(PayAsYouGoItemDTO.class)) {
                payAsYouItem = true;
            }
            if (itemDTOObj.getClass().equals(AutoTopUpConfigurationItemDTO.class)) {
                autoTopUpItem = true;
            }
        }
        if (payAsYouItem && autoTopUpItem) {
            cartSessionData.setTicketType(TicketType.PAY_AS_YOU_GO_AUTO_TOP_UP.code());
        }
    }

    protected void setCollectPurchaseStartAndEndDates(CartDTO cartDTO, CartCmdImpl cartCmd) {
        if (Page.TRANSFER_PRODUCT.equalsIgnoreCase(cartCmd.getPageName())) {
            setPickWindowStartAndEndDates(cartCmd);
        } else {
            for (ItemDTO itemDTOObj : cartDTO.getCartItems()) {
                if (itemDTOObj.getClass().equals(ProductItemDTO.class)) {
                    ProductItemDTO productItemDTOObj = (ProductItemDTO) itemDTOObj;
                    setPickWindowStartAndEndDates(cartCmd, productItemDTOObj.getStartDate());
                } else if (itemDTOObj.getClass().equals(PayAsYouGoItemDTO.class)) {
                    setPickWindowStartAndEndDates(cartCmd);
                }
            }
        }
    }
    
    protected void setPickWindowStartAndEndDates(CartCmdImpl cartCmd) {
        Date tomorrow = DateUtil.getTomorrowDate();
        cartCmd.setPickUpStartDate(tomorrow);
        cartCmd.setPickUpEndDate(DateUtil.addDaysToDate(tomorrow, DateUtil.SEVEN_DAYS));
    }

    protected void setPickWindowStartAndEndDates(CartCmdImpl cartCmd, Date startDate) {
        Date fiveDaysBeforeStartDate = DateUtil.getFiveDaysBefore(startDate);
        Date tomorrow = DateUtil.getTomorrowDate();
        cartCmd.setPickUpStartDate(DateUtil.isAfter(fiveDaysBeforeStartDate, tomorrow) ? fiveDaysBeforeStartDate
                        : tomorrow);
        cartCmd.setPickUpEndDate(DateUtil.getTwoDaysAfter(startDate));
    }

    protected Long getCardIdForQuickBuyTopupTicketMode(HttpSession session, CartSessionData cartSessionData, CartCmdImpl cmd) {
        Long cardId = null;
        if (cartSessionData.getQuickBuyMode()) {
            cardId = (Long) getFromSession(session, CartAttribute.CARD_ID);
        }
        else if(cartSessionData.getTransferProductMode()){
            cardId = cardService.getCardIdFromCardNumber(cartSessionData.getTargetCardNumber());
        }
        if (cardId == null) {
            CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
            cardId = cartDTO.getCardId();
        }
        return cardId;
    }

    protected Long getCardIdFromSessionObjects(HttpSession session, CartSessionData cartSessionData) {
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        if (null != cartDTO) {
            return cartDTO.getCardId();
        } else {
            return (Long) getFromSession(session, CartAttribute.CARD_ID);
        }
    }

    protected void checkPendingItems(CartCmdImpl cmd) {
        if (null != cmd.getCardId()) {
            CardDTO cardDTO = cardDataService.findById(cmd.getCardId());
            if (cardDTO != null) {
                CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardService.getCard(cardDTO.getCardNumber());
                if (cardInfoResponseV2DTO.getPendingItems() != null) {
                    setPendingItemsLocationId(cmd, cardInfoResponseV2DTO);
                }
            }
        }
    }

    protected void setPendingItemsLocationId(CartCmdImpl cmd, CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        PendingItems pendingItems = cardInfoResponseV2DTO.getPendingItems();
        if (pendingItems.getPpvs() != null && pendingItems.getPpvs().size() > 0 && pendingItems.getPpvs().get(0) != null
                        && pendingItems.getPpvs().get(0).getPickupLocation() != null) {
            cmd.setStationId(new Long(pendingItems.getPpvs().get(0).getPickupLocation().intValue()));
        } else if (pendingItems.getPpts() != null && pendingItems.getPpts().size() > 0 && pendingItems.getPpts().get(0) != null
                        && pendingItems.getPpts().get(0).getPickupLocation() != null) {
            cmd.setStationId(new Long(pendingItems.getPpts().get(0).getPickupLocation().intValue()));
        }else if (pendingItems.getPpts() != null && pendingItems.getPpts().size() == 0 && Page.TRANSFER_PRODUCT.equalsIgnoreCase(cmd.getPageName())) {
            CardPreferencesDTO cardPreferencesDTO = cardPreferencesDataService.findByCardId(cardDataService.findByCardNumber(cardInfoResponseV2DTO.getPrestigeId()).getId());
            if(null!= cardPreferencesDTO){
                cmd.setStationId(cardPreferencesDTO.getStationId());
            }
        }
    }
 
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CONTINUE, method = RequestMethod.POST)
    public ModelAndView collectPurchase(HttpSession session, Model model, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        cmd.setCardId(getCardIdForQuickBuyTopupTicketMode(session, cartSessionData, cmd));
        ModelAndView modelAndView = new ModelAndView(PageView.COLLECT_PURCHASE);
        cartSessionData.setStationId(null);
        pickUpLocationValidator.validate(cmd, result);
        if (!result.hasErrors()) {
            cartSessionData.setStationId(cmd.getStationId());
            return setRedirectViewCheckOrTransferSummary(cmd, cartSessionData);
        } else {
            CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
            setCollectPurchaseStartAndEndDates(cartDTO, cmd);
            addCartDTOToModel(model, cartDTO);
        }
        return modelAndView;
    }

    protected ModelAndView setRedirectViewCheckOrTransferSummary(CartCmdImpl cmd, CartSessionData cartSessionData) {
        if (cartSessionData.getTransferProductMode()) {
        	cartSessionData.setTransferProductMode(Boolean.FALSE);
        	return new ModelAndView(new RedirectView(PageUrl.TRANSFER_SUMMARY), PageCommand.CART, cmd);
        } else {
            return new ModelAndView(new RedirectView(PageUrl.CHECKOUT));
        }
    }


    protected void addCartDTOToModel(Model model, CartDTO cartDTO) {
        model.addAttribute(PageAttribute.CART_DTO, cartDTO);
    }
    
    @Override
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel() {
        return new ModelAndView(new RedirectView(PageUrl.USER_CART));
    }
    
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_PAY_AS_YOU_GO_AUTO_TOP_UP_BACK, method = RequestMethod.POST)
    public ModelAndView cancelForPayAsYouGoAutoTopUp() {
        return new ModelAndView(new RedirectView(PageUrl.TOP_UP_TICKET));
    }
    
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_TRANSFER_PRODUCT_BACK, method = RequestMethod.POST)
    public ModelAndView cancelForTransferProduct() {
        return new ModelAndView(new RedirectView(PageUrl.TRANSFER_PRODUCT));
    }
}
