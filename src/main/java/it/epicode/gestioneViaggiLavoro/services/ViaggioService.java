package it.epicode.gestioneViaggiLavoro.services;

import it.epicode.gestioneViaggiLavoro.Dipendente;
import it.epicode.gestioneViaggiLavoro.Prenotazione;
import it.epicode.gestioneViaggiLavoro.Viaggio;
import it.epicode.gestioneViaggiLavoro.exeptions.ViaggioNotFoundException;
import it.epicode.gestioneViaggiLavoro.repository.PrenotazioneRepository;
import it.epicode.gestioneViaggiLavoro.repository.ViaggioRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Servizio per la gestione dei viaggi e delle prenotazioni
 */
@Service
@Validated
@RequiredArgsConstructor
public class ViaggioService {
    private final ViaggioRepository viaggioRepo;
    private final PrenotazioneRepository prenotazioneRepo;

    /**
     * Crea un nuovo viaggio
     * @param viaggio Il viaggio da creare
     * @return Il viaggio creato
     */
    @Transactional
    public Viaggio createViaggio(@Valid @NotNull Viaggio viaggio) {
        return viaggioRepo.save(viaggio);
    }

    /**
     * Modifica lo stato di un viaggio
     * @param viaggioId ID del viaggio
     * @param stato Nuovo stato del viaggio
     * @throws ViaggioNotFoundException se il viaggio non viene trovato
     */
    @Transactional
    public void changeStatoViaggio(@NotNull Long viaggioId, @NotNull String stato) {
        Objects.requireNonNull(stato, "Lo stato non può essere null");
        
        Viaggio viaggio = viaggioRepo.findById(viaggioId)
                .orElseThrow(() -> new ViaggioNotFoundException("Viaggio non trovato con ID: " + viaggioId));
        
        viaggio.setStato(stato);
        viaggioRepo.save(viaggio);
    }

    /**
     * Crea una nuova prenotazione per un viaggio
     * @param dipendente Il dipendente che effettua la prenotazione
     * @param viaggio Il viaggio da prenotare
     * @param dataRichiesta La data richiesta per il viaggio
     * @return La prenotazione creata
     */
    @Transactional
    public Prenotazione prenotaViaggio(
            @NotNull Dipendente dipendente,
            @Valid @NotNull Viaggio viaggio,
            @NotNull LocalDate dataRichiesta) {
        
        Objects.requireNonNull(dipendente, "Il dipendente non può essere null");
        Objects.requireNonNull(dataRichiesta, "La data richiesta non può essere null");
        
        if (dataRichiesta.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La data richiesta non può essere nel passato");
        }


        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setDipendente(dipendente);
        prenotazione.setViaggio(viaggio);
        prenotazione.setDataRichiesta(dataRichiesta);
        
        return prenotazioneRepo.save(prenotazione);
    }
}
