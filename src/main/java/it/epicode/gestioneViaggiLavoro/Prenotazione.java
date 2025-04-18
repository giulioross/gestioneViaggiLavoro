package it.epicode.gestioneViaggiLavoro;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data richiesta è obbligatoria")
    private LocalDate dataRichiesta;

    private String note;
    private String preferenze;

    @ManyToOne(optional = false)
    @JoinColumn(name = "viaggio_id", nullable = false)
    @NotNull(message = "Viaggio è obbligatorio")
    private Viaggio viaggio;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dipendente_username", nullable = false)
    @NotNull(message = "Dipendente è obbligatorio")
    private Dipendente dipendente;
}
