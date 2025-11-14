package com.backend.agrosensor.agrosensorbackend.controller.utilities;

import com.backend.agrosensor.agrosensorbackend.service.utilities.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send/{email}")
    public void sendEmail(@PathVariable String email) {
        this.emailService.sendEmail(email);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword() {
        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }
}
