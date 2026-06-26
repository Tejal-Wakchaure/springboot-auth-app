package com.example.auth.service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${resend.api-key}")
    private String apiKey;

    public void sendVerificationEmail(String email, String verificationUrl) {
        try {
            Resend resend = new Resend(apiKey);

            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from("Auth App <onboarding@resend.dev>")
                    .to(email)
                    .subject("Verify your account")
                    .html("""
                        <h2>Welcome!</h2>
                        <p>Click below to verify your account.</p>
                        <a href="%s">Verify Email</a>
                        """.formatted(verificationUrl))
                    .build();

            resend.emails().send(params);

        } catch (ResendException e) {
            throw new RuntimeException("Failed to send verification email", e);
        }
    }

    public void sendPasswordResetEmail(String email, String resetUrl) {
        try {
            Resend resend = new Resend(apiKey);

            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from("Auth App <onboarding@resend.dev>")
                    .to(email)
                    .subject("Reset Password")
                    .html("""
                        <h2>Password Reset</h2>
                        <a href="%s">Reset Password</a>
                        """.formatted(resetUrl))
                    .build();

            resend.emails().send(params);

        } catch (ResendException e) {
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }

    /*
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @PostConstruct
    public void init() {
        System.out.println("Mail username = " + username);
    }
    public void sendVerificationEmail(String email, String verificationUrl) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(email);
        message.setSubject("Verify your account");
        message.setText("Click the link below to verify your account:\n\n" + verificationUrl);

        mailSender.send(message);
    }

    public void sendPasswordResetEmail(String to, String resetUrl) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(username);
        mail.setTo(to);
        mail.setSubject("Reset Password");
        mail.setText(
                """
                Click the link below to reset your password:

                %s
                """
                        .formatted(resetUrl)
        );
        mailSender.send(mail);
    }*/
}