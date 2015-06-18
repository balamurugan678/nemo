package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static com.novacroft.nemo.test_support.CardTestUtil.CARD_ID;
import static com.novacroft.nemo.test_support.CartItemTestUtil.PAY_AS_YOU_GO_NAME_1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.PAY_AS_YOU_GO_TICKET_PRICE_DEFAULT;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestPayAsYouGo1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrderDTO1;

import java.util.ArrayList;
import java.util.List;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

public final class CommonRefundPaymentServiceUtil {
	
	public static CartCmdImpl buildCartCmdImp(){
        CartCmdImpl cmd = new CartCmdImpl();
        List<CartItemCmdImpl> items = new ArrayList<CartItemCmdImpl>();
        CartItemCmdImpl item = new CartItemCmdImpl();
        item.setRefundCalculationBasis(RefundCalculationBasis.ORDINARY.name());
        item.setPrice(PAY_AS_YOU_GO_TICKET_PRICE_DEFAULT);
        item.setItem(PAY_AS_YOU_GO_NAME_1);
        item.setStartZone(1);
        item.setEndZone(3);
        items.add(item );
        CartItemCmdImpl testPayAsYouGo1 = getTestPayAsYouGo1();
        testPayAsYouGo1.setPrice(PAY_AS_YOU_GO_TICKET_PRICE_DEFAULT);
        items.add(testPayAsYouGo1);
        cmd.setCardId(CARD_ID);
        cmd.setCardNumber(OYSTER_NUMBER_1);
        cmd.setCartItemList(items);
        cmd.setApprovalId(1L);
        cmd.setPaymentType(PaymentType.AD_HOC_LOAD.code());
        cmd.setTotalAmt(new Integer(12));
        cmd.setCartDTO(buildCartDto());
        cmd.setAdministrationFeeValue(PAY_AS_YOU_GO_TICKET_PRICE_DEFAULT);
        AddressDTO address = getTestAddressDTO1();
        cmd.setPayeeAddress(address);
        return cmd;
    }
	
	private static CartDTO buildCartDto() {

        CartDTO cartDto = new CartDTO();
        ProductItemDTO productItemDto = new ProductItemDTO();
        productItemDto.setName(PAY_AS_YOU_GO_NAME_1);
        productItemDto.setStartZone(1);
        productItemDto.setEndZone(2);
        productItemDto.setRefundCalculationBasis(RefundCalculationBasis.ORDINARY.code());
        productItemDto.setPrice(PAY_AS_YOU_GO_TICKET_PRICE_DEFAULT);
        List<ItemDTO> productItemDtoList = new ArrayList<ItemDTO>();
        productItemDtoList.add(productItemDto);

        PayAsYouGoItemDTO productItemForPayAsYouGoDto = new PayAsYouGoItemDTO();
        productItemForPayAsYouGoDto.setCardId(CARD_ID);
        productItemForPayAsYouGoDto.setPrice(PAY_AS_YOU_GO_TICKET_PRICE_DEFAULT);
        productItemDtoList.add(productItemForPayAsYouGoDto);

        AdministrationFeeItemDTO productItemForAdministrationFeeDto = new AdministrationFeeItemDTO();
        productItemForAdministrationFeeDto.setId(CARD_ID);
        productItemForAdministrationFeeDto.setPrice(PAY_AS_YOU_GO_TICKET_PRICE_DEFAULT);
        productItemDtoList.add(productItemForAdministrationFeeDto);

        cartDto.setId(CARD_ID);
        cartDto.setCartType(PAY_AS_YOU_GO_NAME_1);
        cartDto.setCartItems(productItemDtoList);
        cartDto.setOrder(getTestOrderDTO1());
        
        cartDto.setCustomerId(CUSTOMER_ID_1);
        cartDto.setCardId(CARD_ID);
        return cartDto;
    }
}
