<%@include file="/WEB-INF/jspf/pageCommon.jspf" %>
<nemo-tfl:breadcrumbs pageNames='<%=new String[]{Page.DASHBOARD, Page.VIEW_OYSTER_CARD, Page.MANAGE_AUTO_TOP_UP}%>'/>
<to:headLine/>
<div class="r" id="full-width-content">
    <div class="main">
        <div>
            <form:form commandName="<%=PageCommand.MANAGE_CARD %>" cssClass="oo-responsive-form">
                <div class="page-heading with-border">
	            	<to:oysterCardImageAndCardNumber oysterCardNumber="${manageCardCmd.cardNumber}"/>
	            </div>
	            <div>
                    <h2 class="styled-heading emphasis">Change your Auto top-up amount or the payment card used</h2>
                </div>
                <div class=" csc-module form-control-wrap" >
                    <nemo-tfl:radioButtonList id="autoTopUpState" path="autoTopUpState"                                            
                    						  selectList="${payAsYouGoAutoTopUpAmounts}"
                    						  fieldsetCssClass = "bottom-stroke-container  first-column"
                                              mandatory="true" selectedValue="${manageCardCmd.autoTopUpState}"
                                              showHeadingLabel="false"/>
                    <form:errors path="autoTopUpState" cssClass="field-validation-error"/>
                </div>

                <div class='form-control-wrap with-message expandable-box'>
                    <!--contains the label, input and error messaging-->
                    <label class="visually-hidden" for="card">Change your card</label>

                    <div class="form-control-wrap with-message expandable-box">
                        <nemo-tfl:selectList id="PaymentCards" path="paymentCardID" selectList="${paymentCards}"
                                             mandatory="true"
                                             selectedValue="${manageCardCmd.paymentCardID}"
                                             useManagedContentForMeanings="false"/>
						<c:choose>
 							<c:when test="${manageCardCmd.paymentCardID==null || paymentCards.options.size()=='0'}">
								<to:primaryButton id="newPaymentCard" buttonType="submit"
											 targetAction="<%= PageParameterValue.CONFIRM_NEW_PAYMENT_CARD_PAYMENT %>"/>
							</c:when> 
						</c:choose>
                        <form:errors path="paymentCardID" cssClass="field-validation-error"/>
                    </div>

                </div>
                
 				<div>
					<to:linkButtonAndPopUp id="paymentTermsNotice" />
					<to:checkbox id="paymentTermsAccepted" />
				</div>

                <div class="oo-button-group-spaced">
                	<to:secondaryButton id="cancel" buttonType="submit" targetAction="<%= PageParameterValue.CANCEL %>"/>
                    <to:primaryButton id="continue" buttonType="submit" targetAction="<%= PageParameterValue.UPDATE_AUTO_TOP_UP %>"/>
                </div>
                <input type="hidden" id="autoTopUpOrderStatus" value="${manageCardCmd.disableAutoTopUpConfigurationChange}"/>
                <input type="hidden" id="autoTopUpStateAmount" value="${manageCardCmd.autoTopUpState}"/>
            </form:form>
        </div>
    </div>
    <tiles:insertAttribute name="myAccount"/>
</div>

<script src="scripts/manageAutoTopUp.js" type="text/javascript"></script>