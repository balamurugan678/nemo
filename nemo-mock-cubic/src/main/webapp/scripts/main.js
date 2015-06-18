var mainApp = angular.module('mainApp', [  ]);
var cardResponse;
/*mainApp.controller('CallCtrl', function ($scope) {
  
  $scope.callOptions = [
    {'name': 'Get Card Information',
     'text': 'Retrieve information on a card from Cubic.',
     'buttonCall': 'Get Card'},
    {'name': 'Add Pay As You Go Balance',
     'text': 'Add pay as you go balance to card.',
     'buttonCall': 'Add PAYG'},
    {'name': 'Add Product',
     'text': 'Add product to card.',
     'buttonCall': 'Add Product'}
  ];
  $scope.myCall = $scope.callOptions[0];
  $scope.Product = { 'prestigeId' : '', 'prePayValue' : '', 'stationId' : '500' };
  
  
  $scope.callMethod =function(product) {    
	  alert(product.prestigeId);
  };
  
});*/


	mainApp.controller("CallController", [ '$http' , function($http) {
	this.callOptions = [ {
		'name' : 'Get Card Information',
		'text' : 'Retrieve information on a card from Cubic.',
		'buttonCall' : 'Get Card',
		'value' : 'GET_CARD'
	}, {
		'name' : 'Add Pay As You Go Balance',
		'text' : 'Add pay as you go balance to card.',
		'buttonCall' : 'Add PAYG',
	    'value' : 'ADD_PAYG'
	}, {
		'name' : 'Add Product',
		'text' : 'Add product to card.',
		'buttonCall' : 'Add Product',
		'value' : 'ADD_PRODUCT'
	} ];
	this.myCall = this.callOptions[0];
	this.product = {};
	this.serviceResponse = '';
	this.callMethod =function() {    
		if (this.myCall.value === 'GET_CARD') {
			getCard($http, this.product, this);	
		}
		
	};
}]);

this.getCard = function (http, product, controller) {
	var getCardRequest = '<CardInfoRequestV2><PrestigeID>' + product.prestigeId +'</PrestigeID><OriginatorInfo><UserID>LTWebUser</UserID><Password>secrets</Password></OriginatorInfo></CardInfoRequestV2>';
	http.post('http://localhost/nemo-mock-cubic/main.htm', getCardRequest, null).success(function(data) {
		controller.serviceResponse = converXMLtoJSON(data);
		this.cardResponse = controller.serviceResponse;
	});
	
}

this.updateProduct = function (http, product, controller) {
	var getCardRequest = '<CardInfoRequestV2><PrestigeID>' + product.prestigeId +'</PrestigeID><OriginatorInfo><UserID>LTWebUser</UserID><Password>secrets</Password></OriginatorInfo></CardInfoRequestV2>';
	http.post('http://localhost/nemo-mock-cubic/main.htm', getCardRequest, null).success(function(data) {controller.serviceResponse = converXMLtoJSON(data);});
}

this.updatePayAsYouGo = function (http, product, controller) {
var updatePayAsYouGoRequest = '<CardUpdateRequest><RealTimeFlag>Y</RealTimeFlag><PrestigeID>123456789</PrestigeID><Action>ADD</Action>' +
'<PPT><ProductCode>'+product.productCode+'</ProductCode><StartDate>'+product.startDate+'</StartDate><ExpiryDate>'+product.expiryDate+'</ExpiryDate><ProductPrice>'+product.price+'</ProductPrice><Currency>0</Currency></PPT>'+
'<PickupLocation>740</PickupLocation><PaymentMethod>32</PaymentMethod><OriginatorInfo><UserID>LTWebUser</UserID><Password>secrets</Password></OriginatorInfo></CardUpdateRequest>';
	http.post('http://localhost/nemo-mock-cubic/main.htm', getCardRequest, null).success(function(data) {controller.serviceResponse = converXMLtoJSON(data); });
}

this.converXMLtoJSON = function(xml){
	var x2js = new X2JS();
	var json = x2js.xml_str2json( xml );
	return json;
}

/*this.handleGetCardResponse = function(getCardResponse){
	angular.forEach(responseObject, function(value, key) {
		  this.push(key + ': ' + value);
	});
	
}*/
