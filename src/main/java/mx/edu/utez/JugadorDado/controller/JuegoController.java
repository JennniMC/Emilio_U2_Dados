package mx.edu.utez.JugadorDado.controller;

import mx.edu.utez.JugadorDado.dto.RequestIniciarPartidaDto;
import mx.edu.utez.JugadorDado.dto.RequestRealizarTiroDto;
import mx.edu.utez.JugadorDado.model.Partida;
import mx.edu.utez.JugadorDado.model.Tiro;
import mx.edu.utez.JugadorDado.service.JuegoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/juego")
public class JuegoController {

    private final JuegoService juegoService;

    public JuegoController(JuegoService juegoService) {
        this.juegoService = juegoService;
    }

    @PostMapping("/iniciar")
    public ResponseEntity<Partida> iniciarPartida(@RequestBody @Valid RequestIniciarPartidaDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(juegoService.iniciarPartida(dto));
    }

    @PostMapping("/tiro")
    public ResponseEntity<Tiro> realizarTiro(@RequestBody @Valid RequestRealizarTiroDto dto) {
        return ResponseEntity.ok(juegoService.realizarTiro(dto));
    }

    @PatchMapping("/finalizar/{partidaId}")
    public ResponseEntity<Partida> finalizarPartidaManual(@PathVariable Long partidaId) {
        return ResponseEntity.ok(juegoService.finalizarPartidaManual(partidaId));
    }

    @GetMapping("/historial/{jugadorId}")
    public ResponseEntity<List<Partida>> obtenerHistorialPartida(@PathVariable Long jugadorId) {
        return ResponseEntity.ok(juegoService.obtenerHistorialPartida(jugadorId));
    }
}