package it.epicode.gestioneViaggiLavoro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.epicode.gestioneViaggiLavoro.Viaggio;
import it.epicode.gestioneViaggiLavoro.services.ViaggioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller per la gestione delle operazioni sui viaggi
 */
@RestController
@RequestMapping("/api/viaggi")
@RequiredArgsConstructor
@Validated
public class ViaggioController {
    private final ViaggioService viaggioService;

    /**
     * Crea un nuovo viaggio
     * @param viaggio I dettagli del viaggio da creare
     * @return Il viaggio creato
     */
    @Operation(summary = "Crea un nuovo viaggio")
    @ApiResponse(responseCode = "201", description = "Viaggio creato con successo")
    @ApiResponse(responseCode = "400", description = "Dati del viaggio non validi")
    @PostMapping
    public ResponseEntity<Viaggio> creaViaggio(@Valid @RequestBody Viaggio viaggio) {
        Viaggio nuovoViaggio = viaggioService.createViaggio(viaggio);
        return new ResponseEntity<>(nuovoViaggio, HttpStatus.CREATED);
    }

    /**
     * Modifica lo stato di un viaggio
     * @param id ID del viaggio
     * @param stato Nuovo stato del viaggio
     * @return ResponseEntity senza contenuto
     */
    @Operation(summary = "Modifica lo stato di un viaggio")
    @ApiResponse(responseCode = "200", description = "Stato modificato con successo")
    @ApiResponse(responseCode = "404", description = "Viaggio non trovato")
    @ApiResponse(responseCode = "400", description = "Stato non valido")
    @PatchMapping("/{id}/stato")
    public ResponseEntity<Void> cambiaStato(
            @PathVariable Long id,
            @RequestParam @NotBlank(message = "Lo stato non può essere vuoto") String stato) {
        
        if (!stato.equals("in programma") && !stato.equals("completato")) {
            return ResponseEntity.badRequest().build();
        }
        
        viaggioService.changeStatoViaggio(id, stato);
        return ResponseEntity.ok().build();
    }
}
