package mx.edu.utez.JugadorDado.service;

import mx.edu.utez.JugadorDado.dto.RequestIniciarPartidaDto;
import mx.edu.utez.JugadorDado.dto.RequestRealizarTiroDto;
import mx.edu.utez.JugadorDado.exception.CustomException;
import mx.edu.utez.JugadorDado.model.*;
import mx.edu.utez.JugadorDado.repository.JugadorRepository;
import mx.edu.utez.JugadorDado.repository.PartidaRepository;
import mx.edu.utez.JugadorDado.repository.TiroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class JuegoService {

    private final JugadorRepository jugadorRepository;
    private final PartidaRepository partidaRepository;
    private final TiroRepository tiroRepository;
    private final Random random = new Random();

    public JuegoService(JugadorRepository jugadorRepository,
                        PartidaRepository partidaRepository,
                        TiroRepository tiroRepository) {
        this.jugadorRepository = jugadorRepository;
        this.partidaRepository = partidaRepository;
        this.tiroRepository = tiroRepository;
    }

    @Transactional
    public Partida iniciarPartida(RequestIniciarPartidaDto dto) {
        Jugador jugador = jugadorRepository.findById(dto.getJugadorId())
                .orElseThrow(() -> new CustomException("El jugador con ID " + dto.getJugadorId() + " no existe"));

        if (!jugador.getActivo()) {
            throw new CustomException("El jugador no está activo para jugar");
        }

        if (jugador.getSaldo() < dto.getApuesta()) {
            throw new CustomException("Saldo insuficiente para realizar la apuesta");
        }

        jugador.setSaldo(jugador.getSaldo() - dto.getApuesta());
        jugadorRepository.save(jugador);

        Partida partida = new Partida();
        partida.setJugador(jugador);
        partida.setApuesta(dto.getApuesta());
        partida.setEstado(EstadoPartida.EN_JUEGO);

        return partidaRepository.save(partida);
    }

    @Transactional
    public Tiro realizarTiro(RequestRealizarTiroDto dto) {
        Partida partida = partidaRepository.findById(dto.getPartidaId())
                .orElseThrow(() -> new CustomException("La partida con ID " + dto.getPartidaId() + " no existe"));

        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new CustomException("La partida ya se encuentra " + partida.getEstado());
        }

        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        int suma = dado1 + dado2;

        Tiro tiro = new Tiro();
        tiro.setPartida(partida);
        tiro.setValorDado1(dado1);
        tiro.setValorDado2(dado2);
        tiro.setSuma(suma);

        Jugador jugador = partida.getJugador();

        if (suma == 7 || suma == 11) {
            tiro.setEsGanador(true);
            partida.setEstado(EstadoPartida.FINALIZADA);
            jugador.setSaldo(jugador.getSaldo() + (partida.getApuesta() * 2));
            jugadorRepository.save(jugador);
        } else if (suma == 2 || suma == 3 || suma == 12) {
            tiro.setEsGanador(false);
            partida.setEstado(EstadoPartida.FINALIZADA);
        } else {
            tiro.setEsGanador(false);
            partida.setEstado(EstadoPartida.EN_JUEGO);
        }

        partidaRepository.save(partida);
        return tiroRepository.save(tiro);
    }

    @Transactional
    public Partida finalizarPartidaManual(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new CustomException("La partida con ID " + partidaId + " no existe"));

        if (partida.getEstado() == EstadoPartida.FINALIZADA) {
            throw new CustomException("La partida ya está FINALIZADA");
        }

        partida.setEstado(EstadoPartida.FINALIZADA);
        return partidaRepository.save(partida);
    }

    public List<Partida> obtenerHistorialPartida(Long jugadorId) {
        if (!jugadorRepository.existsById(jugadorId)) {
            throw new CustomException("El jugador con ID " + jugadorId + " no existe");
        }
        return partidaRepository.findByJugadorId(jugadorId);
    }
}