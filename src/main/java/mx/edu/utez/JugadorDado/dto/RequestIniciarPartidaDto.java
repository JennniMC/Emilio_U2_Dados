// RequestIniciarPartidaDto.java
package mx.edu.utez.JugadorDado.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestIniciarPartidaDto {
    @NotNull(message = "El ID del jugador es obligatorio")
    private Long jugadorId;

    @NotNull(message = "La apuesta es obligatoria")
    @Positive(message = "La apuesta debe ser un valor positivo")
    private Double apuesta;
}