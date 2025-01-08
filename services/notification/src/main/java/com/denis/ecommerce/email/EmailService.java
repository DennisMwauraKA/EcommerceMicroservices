package com.denis.ecommerce.email;

import com.denis.ecommerce.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    // payment email
    @Async
    public void sendPaymentSuccessfulEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        mimeMessageHelper.setFrom("kariukidennis078@gmail.com");
        final String templateName = EmailTemplates.PAYMENT_CONFIRMATION.getTemplate();
        Map<String, Object> model = new HashMap<>();
        model.put("customerName", customerName);
        model.put("amount", amount);
        model.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(model);
        mimeMessageHelper.setSubject(EmailTemplates.PAYMENT_CONFIRMATION.getSubject());


        try {
            String html = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);

        }catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }



    // OrderEmail

    @Async
    public void sendOrderConfirmEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<Product> products
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        mimeMessageHelper.setFrom("kariukidennis078@gmail.com");
        final String templateName = EmailTemplates.ORDER_CONFIRMATION.getTemplate();
        Map<String, Object> model = new HashMap<>();
        model.put("customerName", customerName);
        model.put("totalAmount", amount);
        model.put("orderReference", orderReference);
        model.put("Products",products);

        Context context = new Context();
        context.setVariables(model);
        mimeMessageHelper.setSubject(EmailTemplates.ORDER_CONFIRMATION.getSubject());


        try {
            String html = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);

        }catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
