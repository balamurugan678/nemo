package com.novacroft.nemo.tfl.services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.novacroft.nemo.tfl.services.application_service.CartExternalService;
import com.novacroft.nemo.tfl.services.transfer.Cart;
import com.novacroft.nemo.tfl.services.transfer.CheckoutRequest;
import com.novacroft.nemo.tfl.services.transfer.CheckoutResult;
import com.novacroft.nemo.tfl.services.transfer.ListResult;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

@Controller
public class CartController extends BaseServicesController {

    @Autowired
    protected CartExternalService cartExternalService;
    
    @RequestMapping(value="/cart/{cardId}", method=RequestMethod.POST, produces=JSON_MEDIA)
    @ResponseBody
    public Cart createCart(@PathVariable Long cardId) {
        return cartExternalService.createCart(cardId);
    }
    
    @RequestMapping(value="/cart/{cartId}", method=RequestMethod.GET, produces=JSON_MEDIA)
    @ResponseBody
    public Cart retrieveCart(@PathVariable Long cartId) {
        return cartExternalService.retrieveCart(cartId);
    }
    
    @RequestMapping(value="/cart/{cartId}", method=RequestMethod.PUT, produces=JSON_MEDIA)
    @ResponseBody
    public Cart updateCart(@RequestBody Cart cart, @PathVariable Long cartId) {
        return cartExternalService.updateCart(cart,cartId);
    }

    @RequestMapping(value = "/carts/customer/{customerId}", method = RequestMethod.GET, produces = JSON_MEDIA)
    @ResponseBody
    public ListResult<Cart> getCartListByCustomerId(@PathVariable Long customerId) {
        return cartExternalService.getCartListByCustomerId(customerId);
    }

    @RequestMapping(value = "/cart/{cartId}/customer/{customerId}", method = RequestMethod.DELETE, produces = JSON_MEDIA)
    @ResponseBody
    public WebServiceResult deleteCart(@PathVariable Long cartId, @PathVariable Long customerId) {
        return cartExternalService.deleteCart(cartId, customerId);
    }

    @RequestMapping(value = "/cart/{cartId}/checkout/start", method = RequestMethod.POST, consumes = JSON_MEDIA, produces = JSON_MEDIA)
    @ResponseBody
    public CheckoutResult beginCheckout(@PathVariable Long cartId, @RequestBody CheckoutRequest checkoutRequest) {
        return cartExternalService.beginCheckout(cartId, checkoutRequest.getStationId());
    }

    @RequestMapping(value = "/cart/{cartId}/checkout/authorise", method = RequestMethod.POST, consumes = JSON_MEDIA, produces = JSON_MEDIA)
    @ResponseBody
    public CheckoutResult authoriseCheckoutPayment(@PathVariable Long cartId, @RequestBody CheckoutRequest checkoutRequest) {
        return cartExternalService.authoriseCheckoutPayment(cartId, checkoutRequest.getOrderId(), checkoutRequest.getPaymentCardSettlementId(), checkoutRequest.getPaymentAuthoristationReference());
    }

    @RequestMapping(value = "/cart/{cartId}/checkout/authorise/fail", method = RequestMethod.PUT, consumes = JSON_MEDIA, produces = JSON_MEDIA)
    @ResponseBody
    public CheckoutResult failCheckoutPayment(@PathVariable Long cartId, @RequestBody CheckoutRequest checkoutRequest) {
        return cartExternalService.updatePaymentCardSettlementStatusToFailed(cartId, checkoutRequest.getOrderId(), checkoutRequest.getPaymentCardSettlementId());
    }

    @RequestMapping(value = "/cart/{cartId}/checkout/complete", method = RequestMethod.POST, consumes = JSON_MEDIA, produces = JSON_MEDIA)
    @ResponseBody
    public CheckoutResult completeCheckout(@PathVariable Long cartId, @RequestBody CheckoutRequest checkoutRequest) {
        return cartExternalService.completeCheckout(cartId, checkoutRequest.getStationId(), checkoutRequest.getOrderId(), checkoutRequest.getPaymentCardSettlementId());
    }

}