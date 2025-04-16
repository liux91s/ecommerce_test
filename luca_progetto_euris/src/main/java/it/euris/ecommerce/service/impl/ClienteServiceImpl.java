package it.euris.ecommerce.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.euris.ecommerce.dto.ClienteRequestDto;
import it.euris.ecommerce.dto.ClienteResponseDto;
import it.euris.ecommerce.mapper.ClienteMapper;
import it.euris.ecommerce.model.Cliente;
import it.euris.ecommerce.repository.ClienteRepository;
import it.euris.ecommerce.service.ClienteService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

	private final ClienteRepository clienteRepository;
	private final ClienteMapper clienteMapper;

	//Recupero della lista cliente task 1
	@Override
	public Page<ClienteResponseDto> getAllClienti(Pageable pageable) {
		return clienteRepository.findAll(pageable).map(clienteMapper::toDto);
	}

	
	//Aggiunta di un cliente task 1
	@Override
    @Transactional
    public ClienteResponseDto creaCliente(@Valid ClienteRequestDto clienteDto) {
        Cliente cliente = clienteMapper.toEntity(clienteDto);
        return clienteMapper.toDto(clienteRepository.save(cliente));
	}

	public ClienteRepository getClienteRepository() {
		return clienteRepository;
	}

	public ClienteMapper getClienteMapper() {
		return clienteMapper;
	}
	
	

}
