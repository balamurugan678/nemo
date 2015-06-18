package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.tfl.common.constant.TransferConstants.LOST_STOLEN_MODE;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.HotlistCardService;
import com.novacroft.nemo.tfl.common.command.impl.LostOrStolenCardCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.HotlistReasonTypes;
import com.novacroft.nemo.tfl.common.constant.LostStolenOptionType;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameter;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.LostOrStolenValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;

@Controller
@RequestMapping(value = PageUrl.LOST_OR_STOLEN_CARD)
public class LostOrStolenCardController extends OnlineBaseController {
    protected static final Logger logger = LoggerFactory.getLogger(LostOrStolenCardController.class);

    @Autowired
    protected LostOrStolenValidator lostOrStolenValidator;
    @Autowired
    protected HotlistCardService hotlistCardService;
    @Autowired
    protected CardUpdateService cardUpdateService;
    @Autowired
    protected CardDataService cardDataService;

    @ModelAttribute
    public void populateHotlistReasonsSelectList(Model model) {
        model.addAttribute(PageAttribute.HOTLIST_REASONS, this.hotlistCardService.getHotlistReasonSelectList());
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewLostOrStolenCard(@ModelAttribute(PageCommand.LOST_OR_STOLEN_CARD) LostOrStolenCardCmdImpl cmd, HttpSession session) {
        Long cardId = (Long) this.getFromSession(session, CartAttribute.CARD_ID);
        cmd.setCardId(cardId);
        cmd.setCardNumber(cardDataService.findById(cardId).getCardNumber());
        return new ModelAndView(PageView.LOST_OR_STOLEN_CARD, PageCommand.LOST_OR_STOLEN_CARD, cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CONTINUE, method = RequestMethod.POST)
    public ModelAndView saveChanges(HttpSession session, @ModelAttribute(PageCommand.LOST_OR_STOLEN_CARD) LostOrStolenCardCmdImpl cmd,
                    BindingResult result, RedirectAttributes redirectAttributes) {
        setHotListedCardDetails(cmd);
        lostOrStolenValidator.validate(cmd, result);
        if (!result.hasErrors()) {
            if (LostStolenOptionType.REFUND_CARD.equals(LostStolenOptionType.lookUpOptionType(cmd.getLostStolenOptions()))
                            && (HotlistReasonTypes.LOST_CARD.getCode().equals(cmd.getHotlistReasonId()) || HotlistReasonTypes.STOLEN_CARD.getCode().equals(cmd.getHotlistReasonId()))) {
                redirectAttributes.addAttribute(PageParameter.CARD_NUMBER, cmd.getCardNumber());
                redirectAttributes.addAttribute(PageParameter.HOT_LIST_REASON_ID, cmd.getHotlistReasonId());
                return new ModelAndView(new RedirectView(PageUrl.LOST_CARD_REFUND));
            } else if (LostStolenOptionType.TRANSFER_CARD.equals(LostStolenOptionType.lookUpOptionType(cmd.getLostStolenOptions()))) {
                return new ModelAndView(new RedirectView(PageUrl.TRANSFER_PRODUCT), PageAttribute.LOST_STOLEN_MODE, LOST_STOLEN_MODE);
            }
        }
        return new ModelAndView(PageView.LOST_OR_STOLEN_CARD, PageCommand.LOST_OR_STOLEN_CARD, cmd);
    }

    @Override
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel() {
        return new ModelAndView(new RedirectView(PageUrl.VIEW_OYSTER_CARD));
    }

    protected void setHotListedCardDetails(LostOrStolenCardCmdImpl lostOrStolenCardCmd) {
        Long loggedInCustomerId = getLoggedInUserCustomerId();
        CardDTO cardDTO = cardDataService.findByCustomerIdAndCardNumber(loggedInCustomerId, lostOrStolenCardCmd.getCardNumber());
        lostOrStolenCardCmd.setCardId(cardDTO.getId());
        if (null != cardDTO.getHotlistReason()) {
            lostOrStolenCardCmd.setHotlistedCardReasonId(cardDTO.getHotlistReason().getId());
            lostOrStolenCardCmd.setHotlistStatus(cardDTO.getHotlistStatus());
            lostOrStolenCardCmd.setHotlistReasonDescription(cardDTO.getHotlistReason().getDescription());
        }
    }
}
