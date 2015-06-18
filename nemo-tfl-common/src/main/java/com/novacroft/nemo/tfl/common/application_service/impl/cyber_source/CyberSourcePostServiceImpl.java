package com.novacroft.nemo.tfl.common.application_service.impl.cyber_source;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.application_service.cyber_source.CyberSourcePostService;
import com.novacroft.nemo.tfl.common.application_service.cyber_source.CyberSourceSecurityService;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourcePostPaymentMethod;
import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourcePostTransactionType;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourcePostRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.novacroft.nemo.common.utils.CurrencyUtil.formatPenceWithoutCurrencySymbol;

/**
 * CyberSource Secure Acceptance payment gateway application service implementation
 */
@Service("cyberSourcePostService")
public class CyberSourcePostServiceImpl implements CyberSourcePostService {

    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected CyberSourceSecurityService cyberSourceSecurityService;

    @Value("${CyberSource.post.profileId}")
    protected String cyberSourceProfileId;
    @Value("${CyberSource.post.accessKey}")
    protected String cyberSourceAccessKey;
    @Value("${CyberSource.post.reply.url:unknown}")
    protected String replyUrl;

    @Override
    public CyberSourcePostRequestDTO preparePaymentRequestData(OrderDTO orderDTO, PaymentCardSettlementDTO settlementDTO,
                                                               Boolean createToken, Boolean cookiesEnabledOnClient,
                                                               String clientIpAddress) {
        CyberSourcePostRequestDTO request = new CyberSourcePostRequestDTO(this.cyberSourceAccessKey,
                formatPenceWithoutCurrencySymbol(settlementDTO.getAmount()),
                this.systemParameterService.getParameterValue(SystemParameterCode.PAYMENT_GATEWAY_CURRENCY.code()),
                this.systemParameterService.getParameterValue(SystemParameterCode.PAYMENT_GATEWAY_LOCALE.code()),
                this.cyberSourceProfileId, String.valueOf(orderDTO.getOrderNumber()),
                CyberSourcePostTransactionType.SALE.code(), settlementDTO.getTransactionUuid(),
                orderDTO.getOrderNumber().toString(), CyberSourcePostPaymentMethod.CARD.code(),
                String.valueOf(orderDTO.getCustomerId()), clientIpAddress, String.valueOf(cookiesEnabledOnClient),
                this.replyUrl, this.replyUrl);

        if (createToken) {
            request.setTransactionType(CyberSourcePostTransactionType.SALE_AND_CREATE_PAYMENT_TOKEN.code());
        }

        this.cyberSourceSecurityService.signPostRequest(request);

        return request;
    }
}
