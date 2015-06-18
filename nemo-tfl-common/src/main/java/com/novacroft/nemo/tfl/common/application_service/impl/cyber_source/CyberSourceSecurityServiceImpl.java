package com.novacroft.nemo.tfl.common.application_service.impl.cyber_source;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.application_service.cyber_source.CyberSourceSecurityService;
import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourcePostRequestField;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourcePostReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourcePostRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.novacroft.nemo.common.constant.LocaleConstant.UTF_8_FORMAT;
import static com.novacroft.nemo.common.utils.CyberSourceDateUtil.formatDateAndTime;
import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourcePostRequestField.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * CyberSource payment gateway security
 *
 * <p>
 * The following methods were provided by CyberSource:
 * </p>
 * <ul>
 * <code>String sign(HashMap)</code>
 * <code>String sign(String, String)</code>
 * <code>buildDataToSign(HashMap)</code>
 * <code>String commaSeparate(ArrayList<String>)</code>
 * </ul>
 */
@Service("cyberSourceSecurityService")
public class CyberSourceSecurityServiceImpl implements CyberSourceSecurityService {
    protected static final Logger logger = LoggerFactory.getLogger(CyberSourceSecurityServiceImpl.class);
    protected static final String HMAC_SHA256 = "HmacSHA256";
    protected static final String DELIMITER = ",";
    protected static final String SEPARATOR = "=";
    protected static final String LINE_FEED = "\n";
    protected static final String CARRIAGE_RETURN = "\r";

    protected static final CyberSourcePostRequestField[] SIGNED_FIELDS =
            new CyberSourcePostRequestField[]{ACCESS_KEY, AMOUNT, CURRENCY, LOCALE, PROFILE_ID, REFERENCE_NUMBER,
                    TRANSACTION_TYPE, TRANSACTION_UUID, PAYMENT_METHOD, CONSUMER_ID, CUSTOMER_COOKIES_ACCEPTED,
                    CUSTOMER_IP_ADDRESS, DEVICE_FINGERPRINT_ID, SIGNED_FIELD_NAMES, UNSIGNED_FIELD_NAMES, SIGNED_DATE_TIME,
                    OVERRIDE_CUSTOM_RECEIPT_PAGE};
    protected static final CyberSourcePostRequestField[] UN_SIGNED_FIELDS =
            new CyberSourcePostRequestField[]{CARD_TYPE, CARD_NUMBER, CARD_EXPIRY_DATE, CARD_CVN, BILL_TO_FORENAME,
                    BILL_TO_SURNAME, BILL_TO_EMAIL, BILL_TO_ADDRESS_LINE1, BILL_TO_ADDRESS_LINE2, BILL_TO_ADDRESS_CITY,
                    BILL_TO_ADDRESS_POSTAL_CODE, BILL_TO_ADDRESS_COUNTRY, SIGNATURE, BILL_TO_ADDRESS_STATE, DATE_OF_BIRTH,
                    OVERRIDE_CUSTOM_CANCEL_PAGE};

    @Value("${CyberSource.post.secretKey:unknown}")
    protected String cyberSourceSecretKey;

    @Override
    public void signPostRequest(CyberSourcePostRequestDTO cyberSourcePostRequestDTO) {
        try {
            cyberSourcePostRequestDTO.setSignedFieldNames(getRequestSignedFieldsAsCommaSeparatedList());
            cyberSourcePostRequestDTO.setUnsignedFieldNames(getRequestUnSignedFieldsAsCommaSeparatedList());
            cyberSourcePostRequestDTO.setSignedDateTime(formatDateAndTime(new Date()));
            cyberSourcePostRequestDTO.setSignature(sign(cyberSourcePostRequestDTO.getArguments()));
        } catch (InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Boolean isPostReplySignatureValid(CyberSourcePostReplyDTO cyberSourcePostReplyDTO) {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Signature: reply [%s]; check [%s]", cyberSourcePostReplyDTO.getSignature(),
                        sign(cyberSourcePostReplyDTO.getArguments())));
            }
            return cyberSourcePostReplyDTO.getSignature().equals(sign(cyberSourcePostReplyDTO.getArguments()));
        } catch (InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }

    protected String getRequestSignedFieldsAsCommaSeparatedList() {
        return getRequestFieldsAsCommaSeparatedList(SIGNED_FIELDS);
    }

    protected String getRequestUnSignedFieldsAsCommaSeparatedList() {
        return getRequestFieldsAsCommaSeparatedList(UN_SIGNED_FIELDS);
    }

    protected String getRequestFieldsAsCommaSeparatedList(CyberSourcePostRequestField[] fields) {
        StringBuilder stringBuilder = new StringBuilder();
        for (CyberSourcePostRequestField cyberSourcePostRequestField : fields) {
            if (isNotBlank(stringBuilder.toString())) {
                stringBuilder.append(DELIMITER);
            }
            stringBuilder.append(cyberSourcePostRequestField.code());
        }
        return stringBuilder.toString();
    }

    protected String sign(Map<String, String> parameters)
            throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        return sign(buildDataToSign(parameters), this.cyberSourceSecretKey);
    }

    protected String sign(String data, String secretKey)
            throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), HMAC_SHA256);
        Mac mac = Mac.getInstance(HMAC_SHA256);
        mac.init(secretKeySpec);
        byte[] rawHmac = mac.doFinal(data.getBytes(UTF_8_FORMAT));
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encodeBuffer(rawHmac).replace(LINE_FEED, EMPTY_STRING).replace(CARRIAGE_RETURN, EMPTY_STRING);
    }

    protected String buildDataToSign(Map<String, String> parameters) {
        String[] signedFieldNames =
                String.valueOf(parameters.get(CyberSourcePostRequestField.SIGNED_FIELD_NAMES.code())).split(DELIMITER);
        ArrayList<String> dataToSign = new ArrayList<String>();
        for (String signedFieldName : signedFieldNames) {
            dataToSign.add(signedFieldName + SEPARATOR + String.valueOf(parameters.get(signedFieldName)));
        }
        return commaSeparate(dataToSign);
    }

    protected String commaSeparate(List<String> dataToSign) {
        StringBuilder csv = new StringBuilder();
        for (Iterator<String> it = dataToSign.iterator(); it.hasNext(); ) {
            csv.append(it.next());
            if (it.hasNext()) {
                csv.append(DELIMITER);
            }
        }
        return csv.toString();
    }
}
