package it.epicode.gestioneViaggiLavoro;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Destinazione è obbligatoria")
    private String destinazione;

    @NotNull(message = "Data è obbligatoria")
    @Future(message = "La data deve essere futura")
    private LocalDate data;

    @NotBlank(message = "Stato è obbligatorio")
    private String stato = "in programma";  // 'in programma', 'completato'

    @OneToMany(mappedBy = "viaggio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prenotazione> prenotazioni = new ArrayList<>();
}
