package com.tj.projectmanagersystem.controller;

import com.stripe.model.checkout.Session;
import com.stripe.model.Customer;
import com.stripe.model.PaymentLink;
import com.tj.projectmanagersystem.modal.PlanType;
import com.tj.projectmanagersystem.modal.User;
import com.tj.projectmanagersystem.response.PaymentLinkResponse;
import com.tj.projectmanagersystem.service.StripeClientService;
import com.tj.projectmanagersystem.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private StripeClientService stripeClientService;
    @Autowired
    private UserService userService;

    @PostMapping("/{planType}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @PathVariable PlanType planType,
            @RequestHeader("Authorization") String jwt,
            @RequestParam String successUrl
            ) throws Exception{

        User user = userService.findUserProfileByJwt(jwt);
        long amount = 199*100L; // $199.00 en centavos
        if(planType.equals(PlanType.ANNUALLY)){
            amount = amount * 12; // Convertir a precio anual
            amount = (long) (amount*0.7); // Aplicar un descuento del 30%
        }

        // Crear cliente en Stripe
        Customer customer = stripeClientService.createCustomer(user.getFullName(), user.getEmail());

        // Crear enlace de pago en Stripe
        Session session = stripeClientService.createPaymentLink(customer.getId(), amount, successUrl);

        PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();
        paymentLinkResponse.setPayment_link_id(session.getId());
        paymentLinkResponse.setPayment_link_url(session.getUrl());

        return new ResponseEntity<>(paymentLinkResponse, HttpStatus.CREATED);
    }

}
