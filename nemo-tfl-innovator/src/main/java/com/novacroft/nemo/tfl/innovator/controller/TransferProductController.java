package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.PageAttribute.FIELD_SELECT_PICKUP_STATION;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.CARD_ID;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.CARD_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.ID;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_CANCEL;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.domain.cubic.PendingItems;
import com.novacroft.nemo.tfl.common.application_service.AddUnattachedCardService;
import com.novacroft.nemo.tfl.common.application_service.CardPreferencesService;
import com.novacroft.nemo.tfl.common.application_service.CardSelectListService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.RefundSelectListService;
import com.novacroft.nemo.tfl.common.application_service.TransferSourceCardService;
import com.novacroft.nemo.tfl.common.application_service.TransferTargetCardService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.TransferConstants;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CardPreferencesDataService;
import com.novacroft.nemo.tfl.common.form_validator.PickUpLocationValidator;
import com.novacroft.nemo.tfl.common.form_validator.TransferProductValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CardPreferencesDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Transfer Product controller
 */
@Controller
@RequestMapping(value = Page.TRANSFER_PRODUCT)
public class TransferProductController extends BaseController {
    static final Logger logger = LoggerFactory.getLogger(TransferProductController.class);

    @Autowired
    protected CardSelectListService cardSelectListService;
    @Autowired
    protected TransferProductValidator transferProductValidator;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected TransferSourceCardService transferSourceCardService;
    @Autowired
    protected TransferTargetCardService transferTargetCardService;
    @Autowired
    protected RefundSelectListService refundSelectListService;
    @Autowired
    protected CountrySelectListService countrySelectListService;
    @Autowired
    protected CardPreferencesService cardPreferencesService;
    @Autowired
    protected AddUnattachedCardService addUnattachedCardService;
    @Autowired
    protected LocationSelectListService locationSelectListService;
    @Autowired
    protected PickUpLocationValidator pickUpLocationValidator;
    @Autowired
    protected CardService cardService;
    @Autowired
    protected CardPreferencesDataService cardPreferencesDataService;

    @ModelAttribute(PageCommand.CART)
    public CartCmdImpl getCartCmd() {
        return new CartCmdImpl();
    }

    @ModelAttribute
    public void populateStationSelectList(Model model) {
        model.addAttribute(FIELD_SELECT_PICKUP_STATION, locationSelectListService.getLocationSelectList());
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showTransferProduct(HttpSession session, Model model, @RequestParam(CARD_ID) Long cardId,
                    @ModelAttribute(PageAttribute.LOST_STOLEN_MODE) String lostOrStolenMode) {
        CartCmdImpl cartCmd = new CartCmdImpl();
        CardDTO cardDTO = cardDataService.findById(cardId);
        cartCmd.setSourceCardNumber(cardDTO.getCardNumber());
        checkSourceCardEligibility(cartCmd, cardDTO);
        if (!cartCmd.getSourceCardNotEligible()) {
            CartDTO cartDTO = cartService.createCartFromCardId(cardId);
            cartCmd.setCartId(cartDTO.getId());
            setLostOrStolenMode(lostOrStolenMode, cartCmd);
            transferTargetCardService.populateCardsSelectList(cardDTO.getCardNumber(), model);
        }
        createCartSessionData(session, cartCmd);
        return new ModelAndView(PageView.TRANSFER_PRODUCT, PageCommand.CART, cartCmd);
    }

    protected void checkSourceCardEligibility(CartCmdImpl cartCmd, CardDTO cardNumberDTO) {
        List<String> ruleBreachesList = transferSourceCardService.isSourceCardEligible(cardNumberDTO.getCardNumber());
        if (!ruleBreachesList.isEmpty()) {
            cartCmd.setSourceCardNumber(cardNumberDTO.getCardNumber());
            cartCmd.setSourceCardNotEligible(Boolean.TRUE);
            cartCmd.setRuleBreaches(ruleBreachesList);
        } else {
            cartCmd.setSourceCardNotEligible(Boolean.FALSE);
        }
    }

    protected void setLostOrStolenMode(String lostOrStolenMode, CartCmdImpl cartCmd) {
        if (null != lostOrStolenMode && lostOrStolenMode.equalsIgnoreCase(TransferConstants.LOST_STOLEN_MODE)) {
            cartCmd.setLostOrStolenMode(Boolean.TRUE);
        }
    }

    @RequestMapping(value = "/getPreferredStation", method = RequestMethod.POST)
    @ResponseBody
    public String getPreferredStationId(@RequestParam(value = CARD_NUMBER) Long cardId) {
        CartCmdImpl cartCmd = new CartCmdImpl();
        checkPendingItems(cardId, cartCmd);
        Long preferredStationId = cardPreferencesService.getPreferencesByCardId(cardId).getStationId();
        return (preferredStationId == null ? (cartCmd.getStationId() == null ? null : cartCmd.getStationId().toString()) : preferredStationId
                        .toString());
    }

    protected void checkPendingItems(Long cardId, CartCmdImpl cartCmd) {
        CardDTO cardDTO = cardDataService.findById(cardId);
        if (cardDTO != null) {
            setPendingLocation(cartCmd, cardDTO);
        }
    }

    protected void setPendingLocation(CartCmdImpl cartCmd, CardDTO cardDTO) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardService.getCard(cardDTO.getCardNumber());
        if (cardInfoResponseV2DTO.getPendingItems() != null) {
            setPendingItemsLocationId(cartCmd, cardInfoResponseV2DTO);
        }
    }

    protected void setPendingItemsLocationId(CartCmdImpl cmd, CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        PendingItems pendingItems = cardInfoResponseV2DTO.getPendingItems();
        if (checkPpv(pendingItems)) {
            cmd.setStationId(new Long(pendingItems.getPpvs().get(0).getPickupLocation().intValue()));
        } else if (checkPpt(pendingItems)) {
            cmd.setStationId(new Long(pendingItems.getPpts().get(0).getPickupLocation().intValue()));
        } else if (checkStationPreference(cmd, pendingItems)) {
            setStationFromPreference(cmd, cardInfoResponseV2DTO);
        }
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_ADD_EXISTING_CARD_TO_ACCOUNT, method = RequestMethod.POST)
    public ModelAndView showAddExistingCardToAccount(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
        ModelAndView mav = new ModelAndView(new RedirectView(PageUrl.INV_ADD_UNATTACHED_CARD));
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        PersonalDetailsCmdImpl personalDetailsByCustomerId = addUnattachedCardService.retrieveOysterDetails(cartSessionData.getSourceCardNumber());
        mav.addObject("CustomerId", personalDetailsByCustomerId.getCustomerId());
        return mav;
    }

    protected boolean checkStationPreference(CartCmdImpl cmd, PendingItems pendingItems) {
        return pendingItems.getPpts() != null && pendingItems.getPpts().size() == 0;
    }

    protected void setStationFromPreference(CartCmdImpl cmd, CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        CardPreferencesDTO cardPreferencesDTO = cardPreferencesDataService.findByCardId(cardDataService.findByCardNumber(
                        cardInfoResponseV2DTO.getPrestigeId()).getId());
        if (null != cardPreferencesDTO) {
            cmd.setStationId(cardPreferencesDTO.getStationId());
        }
    }

    protected boolean checkPpt(PendingItems pendingItems) {
        return pendingItems.getPpts() != null && pendingItems.getPpts().size() > 0 && pendingItems.getPpts().get(0) != null
                        && pendingItems.getPpts().get(0).getPickupLocation() != null;
    }

    protected boolean checkPpv(PendingItems pendingItems) {
        return pendingItems.getPpvs() != null && pendingItems.getPpvs().size() > 0 && pendingItems.getPpvs().get(0) != null
                        && pendingItems.getPpvs().get(0).getPickupLocation() != null;
    }

    @RequestMapping(params = TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancelTransferProduct(HttpSession session) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        PersonalDetailsCmdImpl personalDetailsByCustomerId = addUnattachedCardService.retrieveOysterDetails(cartSessionData.getSourceCardNumber());
        ModelAndView redirectView = new ModelAndView(new RedirectView(PageUrl.INV_CUSTOMER));
        redirectView.addObject(ID, personalDetailsByCustomerId.getCustomerId());
        return redirectView;
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CONTINUE, method = RequestMethod.POST)
    public ModelAndView showTransferSummary(HttpSession session, Model model, @ModelAttribute(PageCommand.CART) CartCmdImpl cartCmd,
                    BindingResult result) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        ModelAndView modelAndView = new ModelAndView(PageView.TRANSFER_PRODUCT);
        cartSessionData.setStationId(null);
        transferProductValidator.validate(cartCmd, result);
        if (!result.hasErrors()) {
            pickUpLocationValidator.validate(cartCmd, result);
            if (!result.hasErrors()) {
                return setTargetCardAndStation(cartCmd, cartSessionData);
            } else {
                redirectTransferProduct(session, model, cartCmd, cartSessionData);
            }
        } else {
            redirectTransferProduct(session, model, cartCmd, cartSessionData);
        }
        return modelAndView;
    }

    protected ModelAndView setTargetCardAndStation(CartCmdImpl cartCmd, CartSessionData cartSessionData) {
        CardDTO cardNumberDTO = cardDataService.findById(cartCmd.getCardId());
        cartSessionData.setTargetCardNumber(cardNumberDTO.getCardNumber());
        cartSessionData.setStationId(cartCmd.getStationId());
        return new ModelAndView(new RedirectView(PageUrl.TRANSFER_SUMMARY), PageCommand.CART, cartCmd);
    }

    protected void redirectTransferProduct(HttpSession session, Model model, CartCmdImpl cartCmd, CartSessionData cartSessionData) {
        transferTargetCardService.populateCardsSelectList(cartSessionData.getSourceCardNumber(), model);
        CartDTO cartDTO = cartService.createCartFromCardId(cardService.getCardIdFromCardNumber(cartSessionData.getSourceCardNumber()));
        cartCmd.setCardId(cartCmd.getCardId());
        cartCmd.setCartId(cartDTO.getId());
        cartCmd.setSourceCardNumber(cartSessionData.getSourceCardNumber());
        createCartSessionData(session, cartCmd);
    }

    protected void createCartSessionData(HttpSession session, CartCmdImpl cmd) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        if (null == cartSessionData) {
            cartSessionData = new CartSessionData(cmd.getCartId());
            cartSessionData.setPageName(Page.TRANSFER_PRODUCT);
            cartSessionData.setTransferProductMode(Boolean.TRUE);
            cartSessionData.setCartId(cmd.getCartId());
            cartSessionData.setSourceCardNumber(cmd.getSourceCardNumber());
            cartSessionData.setLostOrStolenMode(cmd.isLostOrStolenMode());
            CartUtil.addCartSessionDataDTOToSession(session, cartSessionData);
        } else {
            cartSessionData.setSourceCardNumber(cmd.getSourceCardNumber());
            cartSessionData.setLostOrStolenMode(cmd.isLostOrStolenMode());
            cartSessionData.setPageName(Page.TRANSFER_PRODUCT);
            CartUtil.addCartSessionDataDTOToSession(session, cartSessionData);
            cmd.setCardId(cardService.getCardIdFromCardNumber(cartSessionData.getTargetCardNumber()));
        }
    }

}
