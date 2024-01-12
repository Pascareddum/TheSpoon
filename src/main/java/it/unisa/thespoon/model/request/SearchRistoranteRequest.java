package it.unisa.thespoon.model.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SearchRistoranteRequest {

    @Size(max = 3, message = "Il numero civico deve avere una lunghezza massima di 3")
    private String N_Civico;

    private Integer Cap;

    @Size(max = 30, message = "La via deve avere una lunghezza massima di 30 caratteri")
    private String Via;

    @Size(max = 2, message = "La provincia deve avere una lunghezza massima di 2 caratteri")
    private String Provincia;

    @Pattern(regexp = "^(\\((00|\\+)39\\)|(00|\\+)39)?(38[890]|34[24-90]|36[680]|33[13-90]|32[890]|35[013]|37[0139]|39[23])\\d{6,7}$", message = "Numero di telefono non valido, inserire un numero italiano")
    private String Telefono;

    public SearchRistoranteRequest(String n_Civico, Integer cap, String via, String provincia, String telefono) {
        N_Civico = n_Civico;
        Cap = cap;
        Via = via;
        Provincia = provincia;
        Telefono = telefono;
    }
}
