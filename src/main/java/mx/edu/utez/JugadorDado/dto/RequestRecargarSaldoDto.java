// RequestRecargarSaldoDto.java
package mx.edu.utez.JugadorDado.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestRecargarSaldoDto {
    @NotNull(message = "El ID del jugador es obligatorio")
    private Long jugadorId;

    @NotNull(message = "El monto a recargar es obligatorio")
    @Positive(message = "El monto a recargar debe ser mayor a 0")
    private Double monto;
}