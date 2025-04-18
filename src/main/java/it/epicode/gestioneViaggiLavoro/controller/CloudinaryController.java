package it.epicode.gestioneViaggiLavoro.controller;

import com.cloudinary.Cloudinary;
import it.epicode.gestioneViaggiLavoro.services.EmailSenderService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class CloudinaryController {
    private final Cloudinary cloudinary;
    private final EmailSenderService emailSenderService;
    
    private static final String ALLOWED_FILE_TYPES = "image/jpeg,image/png,image/gif";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    /**
     * Carica un'immagine su Cloudinary e invia una notifica email
     * @param file Il file immagine da caricare
     * @return ResponseEntity con l'URL dell'immagine caricata o un messaggio di errore
     */
    @PostMapping(path="/uploadme", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(@RequestPart("file") MultipartFile file) {
        // Validazione del file
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Il file è vuoto");
        }
        
        if (file.getSize() > MAX_FILE_SIZE) {
            return ResponseEntity.badRequest().body("Il file supera la dimensione massima consentita di 5MB");
        }
        
        if (!ALLOWED_FILE_TYPES.contains(Objects.requireNonNull(file.getContentType()))) {
            return ResponseEntity.badRequest().body("Tipo di file non supportato. Sono ammessi solo JPEG, PNG e GIF");
        }

        try {
            Map result = cloudinary.uploader()
                    .upload(file.getBytes(), Map.of(
                            "folder", "FS1024",
                            "public_id", file.getOriginalFilename(),
                            "resource_type", "auto"
                    ));

            String url = result.get("secure_url").toString();
            
            emailSenderService.sendEmail(
                "giuliorossini501@gmail.com",
                "Salvataggio immagine completato",
                String.format("<h3>Immagine salvata con successo</h3><p>L'immagine è disponibile al seguente URL: <img src='%s'/></p>", url)
            );
            
            return ResponseEntity.ok().body(Map.of(
                "message", "Immagine caricata con successo",
                "url", url
            ));
            
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Errore durante il caricamento del file: " + e.getMessage());
        } catch (MessagingException e) {
            return ResponseEntity.ok().body(Map.of(
                "message", "Immagine caricata ma errore nell'invio dell'email",
                "error", e.getMessage()
            ));
        }
    }
}