// RequestRealizarTiroDto.java
package mx.edu.utez.JugadorDado.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestRealizarTiroDto {
    @NotNull(message = "El ID de la partida es obligatorio")
    private Long partidaId;
}