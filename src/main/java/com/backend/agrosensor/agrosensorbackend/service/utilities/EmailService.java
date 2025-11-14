package com.backend.agrosensor.agrosensorbackend.service.utilities;

import com.backend.agrosensor.agrosensorbackend.entity.base.AbstractUser;
import com.backend.agrosensor.agrosensorbackend.repository.users.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender emailSender;
    private final IUserRepository  userRepository;

    public void sendEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        AbstractUser to = userRepository.findByEmail(email).orElseThrow();
        message.setTo(to.getEmail());
        message.setFrom("enviarcorreo162@gmail.com");
        message.setSubject("RECUPERACION DE CONTRASEÑA");
        message.setText("\"Haz clic en el siguiente enlace para restablecer tu contraseña:\\n\"");
        emailSender.send(message);
    }
}
