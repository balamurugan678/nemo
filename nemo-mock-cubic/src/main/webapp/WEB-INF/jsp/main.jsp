<style type="text/css">
    /* label{float:left; clear:both;}
    input{float:left; clear:both;} */
    .clear{clear:both}
    .area{float:left; clear:both}
    #inputArea{float:left; min-width:300px;}
    #outputArea{float:left; border-left:1px solid #aaa; width:300px;}
    #response{ float:left; border:1px solid #bbb; width: 900px;overflow: scroll;}
    #personalDetails label{ width:150px;    margin-top: 3px;    display:inline-block;    float:left;    padding:3px;}
    #personalDetails input{height:20px; 
    width:220px; 
    padding:5px 8px;} 
    ul {list-style:none;}
    .contact_form ul {
    width:750px;
    list-style-type:none;
    list-style-position:outside;
    margin:0px;
    padding:0px;
}
.contact_form ul li{
    padding:12px; 
    border-bottom:1px solid #eee;
    position:relative;
}
</style>
<body ng-controller="CallController as callCtrl">
    <div id="inputArea">
    <select ng-model="callCtrl.myCall" ng-options="call.name for call in callCtrl.callOptions"></select>
    <p>{{callCtrl.myCall.text}}</p>
    <form ng-submit="callCtrl.callMethod()">
    <input type="hidden" ng-model="callCtrl.myCall.name" />
    <div id="getCardTab" style="float: left;">
        <label>PrestigeId</label> 
        <input type="text" ng-model="callCtrl.product.prestigeId" />
    </div>
    <div id="stationArea" ng-hide="callCtrl.myCall.value === 'GET_CARD'">
        <label>Station</label>
	    <input type="text" ng-model="callCtrl.product.stationId" />
    </div>
    <div id="payAsYouGo" style="float: left;clear:both" ng-show="callCtrl.myCall.value === 'ADD_PAYG'">
        <label>Pre Pay Value</label>
        <input type="text" ng-model="callCtrl.product.prePayValue" />
    </div>
    <div id="product" class="area" ng-show="callCtrl.myCall.value === 'ADD_PRODUCT'">
        <label>Product Code</label>
        <input type="text" ng-model="callCtrl.product.productCode" />
        <label>Start Date</label>
        <input type="date" ng-model="callCtrl.product.startDate" />
        <label>Expiry Date</label>
        <input type="date" ng-model="callCtrl.product.expiryDate" />
        <label>Price in pence</label>
        <input type="number" ng-model="callCtrl.product.productPrice" />
    </div>
    <div class="area" >
        <input type="submit" value="{{callCtrl.myCall.buttonCall}}" />
    </div>
    </form>
    </div>
    <div id="outputArea">
    <div class="area">
        <p>Call to make : <br />
        PrestigeId : {{callCtrl.product.prestigeId}} <br />
        StationId : {{callCtrl.product.stationId}}
        </p>
        <p id="response">{{callCtrl.serviceResponse}}</p>
        <div id="getCardResponse" ng-show="callCtrl.myCall.value === 'GET_CARD'" class="area">
            <div ng-show="callCtrl.serviceResponse.CardInfoResponseV2">
                <div id="personalDetails" class="contact_form">
                  <ul >
				    <li>
				         <h2>Personal Details</h2>
				         <span class="required_notification">* Denotes Required Field</span>
				    </li>
				    <li><label>FirstName</label><input type="text" value="{{callCtrl.serviceResponse.CardInfoResponseV2.CardHolderDetails.FirstName}}"/></li>
                    <li><label>MiddleInitial</label><input type="text" value="{{callCtrl.serviceResponse.CardInfoResponseV2.CardHolderDetails.MiddleInitial}}"/></li>
                    <li><label>LastName</label><input type="text" value="{{callCtrl.serviceResponse.CardInfoResponseV2.CardHolderDetails.LastName}}"/></li>
                    <li><label>HouseName</label><input type="text" value="{{callCtrl.serviceResponse.CardInfoResponseV2.CardHolderDetails.HouseName}}"/></li>
                    <li><label>HouseNumber</label><input type="text" value="{{callCtrl.serviceResponse.CardInfoResponseV2.CardHolderDetails.HouseNumber}}"/></li>
                    <li><label>Street</label><input type="text" value="{{callCtrl.serviceResponse.CardInfoResponseV2.CardHolderDetails.Street}}"/></li>
                    <li><label>Town</label><input type="text" value="{{callCtrl.serviceResponse.CardInfoResponseV2.CardHolderDetails.Town}}"/></li>
                    <li><label>County</label><input type="text" value="{{callCtrl.serviceResponse.CardInfoResponseV2.CardHolderDetails.County}}"/></li>
                    <li><label>Postcode</label><input type="text" value="{{callCtrl.serviceResponse.CardInfoResponseV2.CardHolderDetails.Postcode}}"/></li>
                    <li><label>Password</label><input type="text" value="{{callCtrl.serviceResponse.CardInfoResponseV2.CardHolderDetails.Password}}"/></li>
                    <li><label>DayTimePhoneNumber</label><input type="text" value="{{callCtrl.serviceResponse.CardInfoResponseV2.CardHolderDetails.DayTimePhoneNumber}}"/></li>
                    <li><label>EmailAddress</label><input type="text" value="{{callCtrl.serviceResponse.CardInfoResponseV2.CardHolderDetails.EmailAddress}}"/></li>
                  </ul>
                </div>
                <div class="clear"></div>
                <div class="clear"></div>
                <h3>Balance</h3>
                <span>{{callCtrl.serviceResponse.CardInfoResponseV2.PPVDetails.Balance}}</span>
                <h3>Current Products</h3>
                    <ul ng-repeat="ticket in callCtrl.serviceResponse.CardInfoResponseV2.PPTDetails.PPTSlot">
                        <li>SlotNumber : {{ticket.SlotNumber}}</li>
                        <li>StartDate : {{ticket.Zone}}</li>
                        <li>ExpiryDate : {{ticket.StartDate}}</li>
                        <li>Expiry Date : {{ticket.ExpiryDate}}</li>
                        <li>State : {{ticket.State}}</li>
                    </ul>
                    
                <h3>Pending Products</h3>
					<h4>Tickets</h4>
					<ul ng-repeat="pendingItem in callCtrl.serviceResponse.CardInfoResponseV2.PendingItems.PPT">
						<li>Code : {{pendingItem.ProductCode}}</li>
						<li>Price : {{pendingItem.ProductPrice}}</li>
						<li>Start date : {{pendingItem.Startdate}}</li>
						<li>Expiry Date : {{pendingItem.ExpiryDate}}</li>
					</ul>
					<h4>Pay As You Go</h4>
					<ul
						ng-repeat="pendingItem in callCtrl.serviceResponse.CardInfoResponseV2.PendingItems.PPV">
						<li>Price : {{pendingItem.PrepayValue}}</li>
					</ul>
				</div>
        </div>
    </div>
    </div>
</body>
