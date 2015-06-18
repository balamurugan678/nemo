package com.novacroft.nemo.tfl.common.transfer;


import static com.novacroft.nemo.tfl.common.constant.CubicConstant.DEFAULT_PAYMENT_METHOD;
import static com.novacroft.nemo.tfl.common.constant.CubicConstant.DEFAULT_REAL_TIME_FLAG;


public class CardUpdateRequestDTO {
    
        protected String realTimeFlag = DEFAULT_REAL_TIME_FLAG;
        protected String prestigeId;
        protected String action;
        protected Long pickupLocation;
        protected Integer paymentMethod = DEFAULT_PAYMENT_METHOD;
        protected String userId;
        protected String password;
        
        public CardUpdateRequestDTO() {
            
        }
        
        public CardUpdateRequestDTO(String prestigeId, String action, Long pickupLocation,  String userId, String password) {
            this.prestigeId = prestigeId;
            this.action = action;
            this.pickupLocation = pickupLocation;
            this.userId = userId;
            this.password = password;
        }
        
        public CardUpdateRequestDTO(String realTimeFlag, String prestigeId, String action,  Long pickupLocation, Integer paymentMethod, String userId, String password) {
            this.realTimeFlag = realTimeFlag;
            this.prestigeId = prestigeId;
            this.action = action;
            this.pickupLocation = pickupLocation;
            this.paymentMethod = paymentMethod;
            this.userId = userId;
            this.password = password;
        }
        
  
        public String getRealTimeFlag() {
            return realTimeFlag;
        }
        public void setRealTimeFlag(String realTimeFlag) {
            this.realTimeFlag = realTimeFlag;
        }
        public String getPrestigeId() {
            return prestigeId;
        }
        public void setPrestigeId(String prestigeId) {
            this.prestigeId = prestigeId;
        }
        public String getAction() {
            return action;
        }
        public void setAction(String action) {
            this.action = action;
        }
      
        public Long getPickupLocation() {
            return pickupLocation;
        }
        public void setPickupLocation(Long pickupLocation) {
            this.pickupLocation = pickupLocation;
        }
        public Integer getPaymentMethod() {
            return paymentMethod;
        }
        public void setPaymentMethod(Integer paymentMethod) {
            this.paymentMethod = paymentMethod;
        }
        public String getUserId() {
            return userId;
        }
        public void setUserId(String userId) {
            this.userId = userId;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        
}
