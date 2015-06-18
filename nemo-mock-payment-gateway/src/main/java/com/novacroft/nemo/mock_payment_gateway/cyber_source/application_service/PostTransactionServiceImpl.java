package com.novacroft.nemo.mock_payment_gateway.cyber_source.application_service;

import com.novacroft.nemo.common.application_service.TokenGenerator;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.command.PostCmd;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.configuration.PostConfiguration;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.configuration.PostProfile;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.PostReplyParameterName;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.PostRequestParameterName;
import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourcePostTransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.novacroft.nemo.mock_payment_gateway.cyber_source.util.DateUtil.dateToString;

/**
 * CyberSource Secure Acceptance standard transaction service specification
 */
@Service(value = "postTransactionService")
public class PostTransactionServiceImpl implements PostTransactionService {
    private static final String DEFAULT_REASON_CODE = "100";
    private static final String X = "X";
    private static final int NUMBER_OF_CLEAR_CARD_DIGITS = 4;
    private static Map<PostRequestParameterName, PostReplyParameterName> requestReplyMap;
    @Autowired
    protected TokenGenerator tokenGenerator;
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    @Qualifier(value = "cyberSourcePostConfiguration")
    protected PostConfiguration cyberSourcePostConfiguration;
    @Autowired
    protected PostSigningService postSigningService;

    static {
        requestReplyMap = new HashMap<PostRequestParameterName, PostReplyParameterName>();
        requestReplyMap.put(PostRequestParameterName.REFERENCE_NUMBER, PostReplyParameterName.REQ_REFERENCE_NUMBER);
        requestReplyMap.put(PostRequestParameterName.TRANSACTION_UUID, PostReplyParameterName.REQ_TRANSACTION_UUID);
        requestReplyMap.put(PostRequestParameterName.TRANSACTION_TYPE, PostReplyParameterName.REQ_TRANSACTION_TYPE);
        requestReplyMap.put(PostRequestParameterName.CARD_EXPIRY_DATE, PostReplyParameterName.REQ_CARD_EXPIRY_DATE);
        requestReplyMap.put(PostRequestParameterName.BILL_TO_FORENAME, PostReplyParameterName.REQ_BILL_TO_FORENAME);
        requestReplyMap.put(PostRequestParameterName.BILL_TO_SURNAME, PostReplyParameterName.REQ_BILL_TO_SURNAME);
        requestReplyMap.put(PostRequestParameterName.BILL_TO_ADDRESS_LINE1, PostReplyParameterName.REQ_BILL_TO_ADDRESS_LINE1);
        requestReplyMap.put(PostRequestParameterName.BILL_TO_ADDRESS_LINE2, PostReplyParameterName.REQ_BILL_TO_ADDRESS_LINE2);
        requestReplyMap.put(PostRequestParameterName.BILL_TO_ADDRESS_CITY, PostReplyParameterName.REQ_BILL_TO_ADDRESS_CITY);
        requestReplyMap
                .put(PostRequestParameterName.BILL_TO_ADDRESS_POSTCODE, PostReplyParameterName.REQ_BILL_TO_ADDRESS_POSTAL_CODE);
        requestReplyMap
                .put(PostRequestParameterName.BILL_TO_ADDRESS_COUNTRY, PostReplyParameterName.REQ_BILL_TO_ADDRESS_COUNTRY);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PostCmd getRequestAndConfiguration(Map model) {
        PostCmd cmd = new PostCmd();
        cmd.setRequestModel(model);
        cmd.setPostProfile(getProfile(cmd.getRequestModelAttribute(PostRequestParameterName.PROFILE_ID.code())));
        initialiseReplyValues(cmd);
        generateReplyValues(cmd);
        copyRequestToReplyValues(cmd);
        tokeniseCard(cmd);
        this.postSigningService.signPostReply(cmd.getReplyModel());
        return cmd;
    }

    protected PostProfile getProfile(String profileId) {
        for (PostProfile postProfile : this.cyberSourcePostConfiguration.getPostProfiles()) {
            if (profileId.equals(postProfile.getId())) {
                return postProfile;
            }
        }
        throw new ApplicationServiceException(String.format("Error: no configuration for profile [%s]", profileId));
    }

    protected void initialiseReplyValues(PostCmd cmd) {
        for (PostReplyParameterName postReplyParameterName : PostReplyParameterName.values()) {
            cmd.setReplyModelAttribute(postReplyParameterName.code(), "");
        }
    }

    protected void generateReplyValues(PostCmd cmd) {
        Date now = new Date();
        cmd.setReplyModelAttribute(PostReplyParameterName.AUTH_AMOUNT.code(),
                cmd.getRequestModelAttribute(PostRequestParameterName.AMOUNT.code()));
        cmd.setReplyModelAttribute(PostReplyParameterName.AUTH_TIME.code(), dateToString(now));
        cmd.setReplyModelAttribute(PostReplyParameterName.AUTH_TRANS_REF_NO.code(), String.valueOf(now.getTime()));
        cmd.setReplyModelAttribute(PostReplyParameterName.REASON_CODE.code(), DEFAULT_REASON_CODE);
        cmd.setReplyModelAttribute(PostReplyParameterName.TRANSACTION_ID.code(), String.valueOf(now.getTime()));
    }

    protected void copyRequestToReplyValues(PostCmd cmd) {
        for (PostRequestParameterName postRequestParameterName : requestReplyMap.keySet()) {
            cmd.setReplyModelAttribute(requestReplyMap.get(postRequestParameterName).code(),
                    cmd.getRequestModelAttribute(postRequestParameterName.code()));
        }
    }

    protected void tokeniseCard(PostCmd cmd) {
        if (cmd.getRequestModelAttribute(PostRequestParameterName.TRANSACTION_TYPE.code())
                .contains(CyberSourcePostTransactionType.CREATE_PAYMENT_TOKEN.code())) {
            cmd.setReplyModelAttribute(PostReplyParameterName.PAYMENT_TOKEN.code(),
                    this.tokenGenerator.createUrlSafeToken(32).substring(0, 26));
            cmd.setReplyModelAttribute(PostReplyParameterName.REQ_CARD_NUMBER.code(),
                    obfuscateCardNumber(cmd.getRequestModelAttribute(PostRequestParameterName.CARD_NUMBER.code())));
        }
    }

    protected String obfuscateCardNumber(String cardNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < cardNumber.length() - NUMBER_OF_CLEAR_CARD_DIGITS; i++) {
            stringBuilder.append(X);
        }
        stringBuilder.append(cardNumber.substring(cardNumber.length() - NUMBER_OF_CLEAR_CARD_DIGITS, cardNumber.length()));
        return stringBuilder.toString();
    }
}
