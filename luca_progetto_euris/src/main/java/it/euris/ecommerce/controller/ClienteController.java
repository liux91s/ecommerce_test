package it.euris.ecommerce.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.euris.ecommerce.dto.ClienteRequestDto;
import it.euris.ecommerce.dto.ClienteResponseDto;
import it.euris.ecommerce.model.Cliente;
import it.euris.ecommerce.service.ClienteService;
import jakarta.validation.Valid;
import lombok.Data;

@RestController
@RequestMapping("/clienti")
@Data
public class ClienteController {

	@Autowired
    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDto> createCliente(
            @Valid @RequestBody ClienteRequestDto clienteDto) {
        ClienteResponseDto response = clienteService.creaCliente(clienteDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllClienti(
            @PageableDefault(size = 10, sort = "codice_fiscale") Pageable pageable) {
        Page<?> clienti = clienteService.getAllClienti(pageable);
        return ResponseEntity.ok(clienti);
    }
}