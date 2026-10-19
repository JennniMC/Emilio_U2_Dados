package mx.edu.utez.JugadorDado.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tiros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partida_id", nullable = false)
    private Partida partida;

    private Integer valorDado1;
    private Integer valorDado2;
    private Integer suma;
    private Boolean esGanador;
}
