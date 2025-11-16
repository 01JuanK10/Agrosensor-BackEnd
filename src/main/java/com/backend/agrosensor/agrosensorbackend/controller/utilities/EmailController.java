package com.backend.agrosensor.agrosensorbackend.controller.utilities;

import com.backend.agrosensor.agrosensorbackend.service.utilities.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/utilities")
public class EmailController {
    private final EmailService emailService;

    @GetMapping("/send/{email}")
    public ResponseEntity<String> sendEmail(@PathVariable String email) {
        try {
            boolean sent = emailService.sendEmail(email);

            if (!sent) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existe un usuario con el correo: " + email);
            }

            return ResponseEntity.ok("Correo enviado correctamente a: " + email);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar el correo: " + e.getMessage());
        }
    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword() {
        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }
}
