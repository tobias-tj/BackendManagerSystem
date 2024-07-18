package com.tj.projectmanagersystem.service;

import com.tj.projectmanagersystem.modal.PlanType;
import com.tj.projectmanagersystem.modal.Subscription;
import com.tj.projectmanagersystem.modal.User;

public interface SubscriptionService {

    Subscription createdSubscription(User user);

    Subscription getUserSubscription(Long userId) throws Exception;

    Subscription upgradeSubscription(Long userId, PlanType planType);

    boolean isValid(Subscription subscription);

}
