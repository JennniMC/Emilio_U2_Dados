package mx.edu.utez.JugadorDado.controller;

import mx.edu.utez.JugadorDado.dto.RequestCrearJugadorDto;
import mx.edu.utez.JugadorDado.dto.RequestRecargarSaldoDto;
import mx.edu.utez.JugadorDado.model.Jugador;
import mx.edu.utez.JugadorDado.service.JugadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jugador")
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @PostMapping
    public ResponseEntity<Jugador> crearJugador(@RequestBody @Valid RequestCrearJugadorDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jugadorService.crearJugador(dto));
    }

    @PatchMapping("/recargar")
    public ResponseEntity<Jugador> recargarSaldo(@RequestBody @Valid RequestRecargarSaldoDto dto) {
        return ResponseEntity.ok(jugadorService.recargarSaldo(dto));
    }
}