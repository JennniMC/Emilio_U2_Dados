package mx.edu.utez.JugadorDado.service;

import mx.edu.utez.JugadorDado.dto.RequestCrearJugadorDto;
import mx.edu.utez.JugadorDado.dto.RequestRecargarSaldoDto;
import mx.edu.utez.JugadorDado.exception.CustomException;
import mx.edu.utez.JugadorDado.model.Jugador;
import mx.edu.utez.JugadorDado.repository.JugadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    public Jugador crearJugador(RequestCrearJugadorDto dto) {
        Jugador jugador = new Jugador();
        jugador.setNombre(dto.getNombre());
        jugador.setSaldo(dto.getSaldo());
        jugador.setActivo(true);
        return jugadorRepository.save(jugador);
    }

    public Jugador buscarPorId(Long id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new CustomException("El jugador con ID " + id + " no existe"));
    }

    @Transactional
    public Jugador recargarSaldo(RequestRecargarSaldoDto dto) {
        Jugador jugador = buscarPorId(dto.getJugadorId());

        if (!jugador.getActivo()) {
            throw new CustomException("No se puede recargar saldo a un jugador inactivo");
        }

        jugador.setSaldo(jugador.getSaldo() + dto.getMonto());
        return jugadorRepository.save(jugador);
    }
}