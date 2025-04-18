package it.epicode.gestioneViaggiLavoro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.epicode.gestioneViaggiLavoro.DTO.ErrorResponse;
import it.epicode.gestioneViaggiLavoro.Dipendente;
import it.epicode.gestioneViaggiLavoro.Prenotazione;
import it.epicode.gestioneViaggiLavoro.Viaggio;
import it.epicode.gestioneViaggiLavoro.exeptions.DipendenteNotFoundException;
import it.epicode.gestioneViaggiLavoro.repository.DipendenteRepository;
import it.epicode.gestioneViaggiLavoro.services.ViaggioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Controller per la gestione delle operazioni relative ai dipendenti
 */
@RestController
@RequestMapping("/api/dipendenti")
@RequiredArgsConstructor
@Validated
public class DipendenteController {
    private final DipendenteRepository dipendenteRepo;
    private final ViaggioService viaggioService;

    /**
     * Crea una nuova prenotazione per un viaggio
     * @param username Username del dipendente
     * @param viaggio Dettagli del viaggio da prenotare
     * @param dataRichiesta Data richiesta per il viaggio
     * @return La prenotazione creata
     * @throws DipendenteNotFoundException se il dipendente non viene trovato
     */
    @Operation(summary = "Crea una nuova prenotazione per un dipendente")
    @ApiResponse(responseCode = "201", description = "Prenotazione creata con successo")
    @ApiResponse(responseCode = "404", description = "Dipendente non trovato")
    @ApiResponse(responseCode = "400", description = "Dati non validi")
    @PostMapping("/{username}/prenotazione")
    public ResponseEntity<?> prenotaViaggio(
            @PathVariable @NotBlank String username,
            @Valid @RequestBody Viaggio viaggio,
            @RequestParam LocalDate dataRichiesta,
            HttpServletRequest request) {
        
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (dataRichiesta == null || dataRichiesta.isBefore(LocalDate.now())) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(
                            HttpStatus.BAD_REQUEST.value(),
                            "Data non valida",
                            "La data richiesta non può essere nel passato",
                            request.getRequestURI()
                    ));
        }

        Dipendente dipendente = dipendenteRepo.findById(username)
                .orElseThrow(() -> new DipendenteNotFoundException("Dipendente non trovato con username: " + username));
                
        try {
            Prenotazione prenotazione = viaggioService.prenotaViaggio(dipendente, viaggio, dataRichiesta);
            return new ResponseEntity<>(prenotazione, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Errore interno",
                            e.getMessage(),
                            request.getRequestURI()
                    ));
        }
    }
}
