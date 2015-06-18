package com.novacroft.nemo.test_support;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

public class ZoneServiceTestUtil {
    private static final Integer ZONE_1 = 1;
    private static final Integer ZONE_2 = 2;
    private static final Integer ZONE_3 = 3;
    private static final Integer ZONE_4 = 4;
    private static final Integer ZONE_5 = 5;
    private static final Integer ZONE_6 = 6;
    private static final Integer ZONE_7 = 7;
    private static final Integer ZONE_8 = 8;
    private static final Integer ZONE_9 = 9;
    
    public static ProductItemDTO getTravelcardSurrounding(){
        return getTravelCard(ZONE_1, ZONE_6, "28/10/2014", "03/11/2014");
    }
    
    public static ProductItemDTO getTravelcardSurrounding2(){
        return getTravelCard(ZONE_3, ZONE_6, "28/10/2014", "03/11/2014");
    }
    
    public static ProductItemDTO getTravelcardSame1(){
        return getTravelCard(ZONE_2, ZONE_5, "28/10/2014", "03/11/2014");
    }
    
    public static ProductItemDTO getTravelcardStartZone1() {
        return getTravelCard(ZONE_1, ZONE_4,"28/10/2014", "06/11/2014");
    }
    
    public static ProductItemDTO getTravelcardStartZone2() {
        return getTravelCard(ZONE_1, ZONE_2,"28/10/2014", "06/11/2014");
    }
    
    public static ProductItemDTO getTravelcardEndZone1() {
        return getTravelCard(ZONE_4, ZONE_6,"31/10/2014", "06/11/2014");
    }
    
    public static ProductItemDTO getTravelcardEndZone2() {
        return getTravelCard(ZONE_3, ZONE_6,"28/10/2014", "08/11/2014");
    }
    
    public static ProductItemDTO getTravelcardInBetweenZone1() {
        return getTravelCard(ZONE_3, ZONE_4,"28/10/2014", "06/11/2014");
    }
    public static ProductItemDTO getTravelcardInBetweenZone2() {
        return getTravelCard(ZONE_2, ZONE_3,"28/10/2014", "06/11/2014");
    }
    
    public static ProductItemDTO getTravelcardEndZone3() {
        return getTravelCard(ZONE_8, ZONE_9,"28/10/2014", "06/11/2014");
    }
    
    public static ProductItemDTO getTravelcardStartDate1() {
        return getTravelCard(ZONE_3, ZONE_4,"28/10/2014", "29/10/2014");
    }
    
    public static ProductItemDTO getTravelcardEndDate1() {
        return getTravelCard(ZONE_3, ZONE_4,"08/11/2014", "29/11/2014");
    }
    
    public static CartItemCmdImpl getCartItemCmd1(){
        return getCartItemCmd(ZONE_2, ZONE_5, "30/10/2014", "07/11/2014");
    }
    
    public static CartItemCmdImpl getCartItemCmd2(){
        return getCartItemCmd(ZONE_1, ZONE_4, "29/10/2014", "04/11/2014");
    }
    
    public static CartItemCmdImpl getCartItemCmd3(){
        return getCartItemCmd(ZONE_3, ZONE_6, "26/10/2014", "02/11/2014");
    }
    
    public static CartItemCmdImpl getCartItemCmd4(){
        return getCartItemCmd(ZONE_7, ZONE_9, "28/10/2014", "03/11/2014");
    }
    public static CartItemCmdImpl getCartItemCmd5(){
        return getCartItemCmd(ZONE_2, ZONE_6, "28/10/2014", "03/11/2014");
    }
    
    public static CartItemCmdImpl getCartItemCmd(Integer startZone, Integer endZone, String startDate, String endDate){
        CartItemCmdImpl cartItemCmd = new CartItemCmdImpl();
        cartItemCmd.setStartZone(startZone);
        cartItemCmd.setEndZone(endZone);
        cartItemCmd.setStartDate(startDate);
        cartItemCmd.setEndDate(endDate);
        return cartItemCmd;
    }
    
    public static ProductItemDTO getTravelCard(Integer startZone, Integer endZone, String startDate, String endDate){
        ProductItemDTO productItemDTO = new ProductItemDTO();
        productItemDTO.setStartZone(startZone);
        productItemDTO.setEndZone(endZone);
        productItemDTO.setStartDate(DateUtil.parse(startDate));
        productItemDTO.setEndDate(DateUtil.parse(endDate));
        return productItemDTO;
    }
}
