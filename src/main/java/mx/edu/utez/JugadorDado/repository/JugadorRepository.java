package mx.edu.utez.JugadorDado.repository;

import mx.edu.utez.JugadorDado.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {
}
