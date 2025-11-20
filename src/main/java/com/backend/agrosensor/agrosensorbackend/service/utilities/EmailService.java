package com.backend.agrosensor.agrosensorbackend.service.utilities;


import java.util.Optional;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.backend.agrosensor.agrosensorbackend.entity.base.AbstractUser;
import com.backend.agrosensor.agrosensorbackend.repository.users.IUserRepository;
import com.backend.agrosensor.agrosensorbackend.service.auth.AuthService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender emailSender;

    private final IUserRepository  userRepository;
    private final AuthService authService;
    private static final String CLIENT_URL = "http://localhost:4200/reset-password";

    private String createUrl(AbstractUser user) {
        String token = authService.generatePasswordResetToken(user);
        return CLIENT_URL + "?tk=" + token + "&usr=" + user.getUsername() + "&role=USER";
    }

    public boolean sendEmail(String email) {
        Optional<AbstractUser> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return false;
        }

        AbstractUser to = optionalUser.get();

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            String url = createUrl(to);

            String htmlMsg = """
            <p>Haz clic en el siguiente enlace para restablecer tu contraseña:</p>
            <p>
                <a href="%s" target="_blank">Restablecer contraseña</a>
            </p>
        """.formatted(url);

            helper.setTo(to.getEmail());
            helper.setFrom("enviarcorreo162@gmail.com");
            helper.setSubject("RECUPERACION DE CONTRASEÑA");
            helper.setText(htmlMsg, true);

            emailSender.send(message);
            return true;

        } catch (MessagingException e) {
            throw new RuntimeException("No se pudo enviar el email", e);
        }
    }

}
