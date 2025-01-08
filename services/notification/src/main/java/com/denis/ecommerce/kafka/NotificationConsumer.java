package com.denis.ecommerce.kafka;

import com.denis.ecommerce.email.EmailService;
import com.denis.ecommerce.kafka.order.OrderConfirmation;
import com.denis.ecommerce.kafka.payment.PaymentConfirmation;
import com.denis.ecommerce.notification.Notification;
import com.denis.ecommerce.notification.NotificationRepository;
import com.denis.ecommerce.notification.NotificationType;
import jakarta.mail.MessagingException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;


    public NotificationConsumer(NotificationRepository notificationRepository, EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
    }

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        notificationRepository.save(Notification.builder()
                .type(NotificationType.PAYMENT_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .paymentConfirmation(paymentConfirmation)
                .build());
// send payment  success email
        var customerName = paymentConfirmation.customerFirstName() + " " + paymentConfirmation.customerLastName();
        emailService.sendPaymentSuccessfulEmail(paymentConfirmation.email(), customerName, paymentConfirmation.amount(), paymentConfirmation.orderReference());
    }


    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        notificationRepository.save(Notification.builder()
                .type(NotificationType.ORDER_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .orderConfirmation(orderConfirmation)
                .build());
// send order placement successful email
        var customerName = orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName();
        emailService.sendOrderConfirmEmail(orderConfirmation.customer().email(), customerName, orderConfirmation.amount(), orderConfirmation.orderReference(), orderConfirmation.products());
    }

}
