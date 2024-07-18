package com.tj.projectmanagersystem.service;

import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;


public interface StripeClientService {

    Customer createCustomer(String name, String email) throws Exception;

    Session createPaymentLink(String customerId, Long amount, String successUrl) throws Exception;

}
