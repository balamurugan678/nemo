package com.novacroft.nemo.mock_payment_gateway.cyber_source.application_service;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.PostReplyParameterName;
import com.novacroft.nemo.tfl.common.application_service.impl.cyber_source.CyberSourceSecurityServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

import static com.novacroft.nemo.common.utils.CyberSourceDateUtil.formatDateAndTime;

/**
 * CyberSource Secure Acceptance reply signing service
 */
@Service("postSigningService")
public class PostSigningServiceImpl extends CyberSourceSecurityServiceImpl implements PostSigningService {
    static final Logger logger = LoggerFactory.getLogger(PostSigningServiceImpl.class);
    protected static final PostReplyParameterName[] SIGNED_FIELDS =
            new PostReplyParameterName[]{PostReplyParameterName.AUTH_AMOUNT, PostReplyParameterName.AUTH_TIME,
                    PostReplyParameterName.AUTH_TRANS_REF_NO, PostReplyParameterName.REASON_CODE,
                    PostReplyParameterName.REQ_REFERENCE_NUMBER, PostReplyParameterName.REQ_TRANSACTION_UUID,
                    PostReplyParameterName.TRANSACTION_ID, PostReplyParameterName.REQ_TRANSACTION_TYPE,
                    PostReplyParameterName.REQ_TRANSACTION_TYPE};

    @Override
    public void signPostReply(Map<String, String> replyModel) {
        try {
            replyModel
                    .put(PostReplyParameterName.SIGNED_FIELD_NAMES.code(), getSignedFieldsAsCommaSeparatedList(SIGNED_FIELDS));
            replyModel.put(PostReplyParameterName.SIGNED_DATE_TIME.code(), formatDateAndTime(new Date()));
            replyModel.put(PostReplyParameterName.SIGNATURE.code(), sign(replyModel));
        } catch (InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }

    protected String getSignedFieldsAsCommaSeparatedList(PostReplyParameterName[] fields) {
        StringBuilder stringBuilder = new StringBuilder();
        for (PostReplyParameterName postReplyParameterName : fields) {
            if (StringUtils.isNotBlank(stringBuilder.toString())) {
                stringBuilder.append(DELIMITER);
            }
            stringBuilder.append(postReplyParameterName.code());
        }
        return stringBuilder.toString();
    }
}
