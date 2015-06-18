package com.novacroft.nemo.tfl.online.controller;

import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.tfl.common.application_service.CardSelectListService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.TransferSourceCardService;
import com.novacroft.nemo.tfl.common.application_service.TransferTargetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.TransferConstants;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.TransferProductValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Transfer Product controller
 */
@Controller
@RequestMapping(value = PageUrl.TRANSFER_PRODUCT)
public class TransferProductController extends OnlineBaseController {
    static final Logger logger = LoggerFactory.getLogger(TransferProductController.class);

    @Autowired
    protected CardSelectListService cardSelectListService;
    @Autowired
    protected TransferProductValidator transferProductValidator;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected TransferSourceCardService transferSourceCardService;
    @Autowired
    protected TransferTargetCardService transferTargetCardService;

    @ModelAttribute(PageCommand.CART)
    public CartCmdImpl getCartCmd() {
        return new CartCmdImpl();
    }

    public void populateCardsSelectList(HttpSession session, Model model) {
        Long cardId = (Long) this.getFromSession(session, CartAttribute.CARD_ID);
        CardDTO cardNumberDTO = cardDataService.findById(cardId);
        String sourceCardNumber = cardNumberDTO.getCardNumber();
        List<CardDTO> cardDTOs = cardDataService.getAllCardsFromUserExceptCurrent(sourceCardNumber);
        SelectListDTO selectListDTO = new SelectListDTO();
        selectListDTO.setName(PageAttribute.CARDS);
        selectListDTO.setOptions(new ArrayList<SelectListOptionDTO>());
        for (CardDTO cardDTO : cardDTOs) {
            if (null != cardDTO.getCardNumber() && transferTargetCardService.isEligibleAsTargetCard(sourceCardNumber, cardDTO.getCardNumber())) {
                selectListDTO.getOptions().add(new SelectListOptionDTO(cardDTO.getId().toString(), cardDTO.getCardNumber()));
            }
        }
        if (!selectListDTO.getOptions().isEmpty()) {
            model.addAttribute(PageAttribute.CARDS, selectListDTO);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showTransferProduct(HttpSession session, Model model, @ModelAttribute(PageAttribute.CARDS) SelectListDTO cardSelectList,
                    @ModelAttribute(PageAttribute.SOURCE_CARD_NUMBER) String sourceCardNumber,
                    @ModelAttribute(PageAttribute.LOST_STOLEN_MODE) String lostOrStolenMode) {
        CartCmdImpl cartCmd = new CartCmdImpl();
        Long cardId = (Long) this.getFromSession(session, CartAttribute.CARD_ID);
        CardDTO cardNumberDTO = cardDataService.findById(cardId);
        cartCmd.setSourceCardNumber(cardNumberDTO.getCardNumber());
        checkSourceCardEligibility(cartCmd, cardNumberDTO);
        if (!cartCmd.getSourceCardNotEligible()) {
            populateCardsSelectList(session, model);
            CartDTO cartDTO = cartService.createCartFromCardId(cardId);
            setLostOrStolenMode(lostOrStolenMode, cartCmd);
            cartCmd.setCardId(cardId);
            cartCmd.setCartId(cartDTO.getId());
            createCartSessionData(session, cartCmd);
        }
        return new ModelAndView(PageView.TRANSFER_PRODUCT, PageCommand.CART, cartCmd);
    }

    protected void setLostOrStolenMode(String lostOrStolenMode, CartCmdImpl cartCmd) {
        if (null != lostOrStolenMode && lostOrStolenMode.equalsIgnoreCase(TransferConstants.LOST_STOLEN_MODE)) {
            cartCmd.setLostOrStolenMode(Boolean.TRUE);
        }
    }

    private void checkSourceCardEligibility(CartCmdImpl cartCmd, CardDTO cardNumberDTO) {
        List<String> ruleBreachesList = transferSourceCardService.isSourceCardEligible(cardNumberDTO.getCardNumber());
        if (!ruleBreachesList.isEmpty()) {
            cartCmd.setSourceCardNumber(cardNumberDTO.getCardNumber());
            cartCmd.setSourceCardNotEligible(Boolean.TRUE);
            cartCmd.setRuleBreaches(ruleBreachesList);
        } else {
            cartCmd.setSourceCardNotEligible(Boolean.FALSE);
        }
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CONTINUE, method = RequestMethod.POST)
    public ModelAndView showCollectPurchase(HttpSession session, Model model, @ModelAttribute(PageCommand.CART) CartCmdImpl cartCmd, BindingResult result) {
        transferProductValidator.validate(cartCmd, result);
        ModelAndView modelAndView = new ModelAndView(PageView.TRANSFER_PRODUCT);
        Long cardId = (Long) this.getFromSession(session, CartAttribute.CARD_ID);
        if (!result.hasErrors()) {
            CardDTO sourceCardDTO = cardDataService.findById(cardId);
            cartCmd.setSourceCardNumber(sourceCardDTO.getCardNumber());
            CardDTO cardNumberDTO = cardDataService.findById(cartCmd.getCardId());
            cartCmd.setTargetCardNumber(cardNumberDTO.getCardNumber());
            setTransferProductParameters(session, cartCmd);
            return new ModelAndView(new RedirectView(PageUrl.COLLECT_PURCHASE), PageCommand.CART, cartCmd);
        } else {
            populateCardsSelectList(session, model);
            CartDTO cartDTO = cartService.createCartFromCardId(cardId);
            cartCmd.setCardId(cartDTO.getCardId());
            cartCmd.setCartId(cartDTO.getId());
            createCartSessionData(session, cartCmd);
        }
        return modelAndView;
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_ADD_EXISTING_CARD_TO_ACCOUNT, method = RequestMethod.POST)
    public ModelAndView showAddExistingCardToAccount(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
        return new ModelAndView(new RedirectView(PageUrl.ADD_EXISTING_CARD_TO_ACCOUNT));
    }

    @Override
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel() {
        return new ModelAndView(new RedirectView(PageUrl.VIEW_OYSTER_CARD));
    }

    private void setCardCmdParameters(CartCmdImpl cmd, CartDTO cartDTO) {
        cmd.setCartId(cartDTO.getId());
        cmd.setPageName(Page.TRANSFER_PRODUCT);
        cmd.setCartType(CartType.TRANSFER_PRODUCT.code());
    }

    private void setTransferProductParameters(HttpSession session, CartCmdImpl cmd) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = null;
        cartDTO = cartService.findById(cartSessionData.getCartId());
        cartDTO.setCartType(CartType.TRANSFER_PRODUCT.code());
        cmd.setLostOrStolenMode(cartSessionData.getLostOrStolenMode());
        setCardCmdParameters(cmd, cartDTO);
        createCartSessionData(session, cmd);
    }

    protected void createCartSessionData(HttpSession session, CartCmdImpl cmd) {
        CartSessionData cartSessionData = new CartSessionData(cmd.getCartId());
        cartSessionData.setPageName(Page.TRANSFER_PRODUCT);
        cartSessionData.setTransferProductMode(Boolean.TRUE);
        cartSessionData.setSourceCardNumber(cmd.getSourceCardNumber());
        cartSessionData.setTargetCardNumber(cmd.getTargetCardNumber());
        cartSessionData.setLostOrStolenMode(cmd.isLostOrStolenMode());
        CartUtil.addCartSessionDataDTOToSession(session, cartSessionData);
    }
}
