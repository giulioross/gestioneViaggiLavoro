package it.epicode.gestioneViaggiLavoro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Dipendente {
    @Id
    @NotBlank(message = "Username è obbligatorio")
    private String username;

    @NotBlank(message = "Nome è obbligatorio")
    private String nome;

    @NotBlank(message = "Cognome è obbligatorio")
    private String cognome;

    @Email(message = "Email non valida")
    @NotBlank(message = "Email è obbligatoria")
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "dipendente")
    private List<Prenotazione> prenotazioni;

    @Column(nullable = true)
    private String immagineProfilo;  // URL immagine su Cloudinary
}
