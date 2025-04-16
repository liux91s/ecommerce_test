package it.euris.ecommerce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.euris.ecommerce.dto.ClienteRequestDto;
import it.euris.ecommerce.dto.ClienteResponseDto;
import jakarta.validation.Valid;
public interface ClienteService {

	Page<?> getAllClienti(Pageable pageable);

	ClienteResponseDto creaCliente(@Valid ClienteRequestDto clienteDto);

}
