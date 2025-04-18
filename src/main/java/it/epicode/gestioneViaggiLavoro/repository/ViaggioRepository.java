package it.epicode.gestioneViaggiLavoro.repository;

import it.epicode.gestioneViaggiLavoro.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViaggioRepository extends JpaRepository<Viaggio, Long> {}
