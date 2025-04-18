package it.epicode.gestioneViaggiLavoro.repository;

import it.epicode.gestioneViaggiLavoro.Dipendente;
import it.epicode.gestioneViaggiLavoro.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    Optional<Prenotazione> findByDipendenteAndViaggioData(Dipendente dipendente, LocalDate data);
}
