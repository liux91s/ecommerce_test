package it.euris.ecommerce.mapper;

import org.mapstruct.Mapper;

import it.euris.ecommerce.dto.ClienteRequestDto;
import it.euris.ecommerce.dto.ClienteResponseDto;
import it.euris.ecommerce.model.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    Cliente toEntity(ClienteRequestDto dto);
    ClienteResponseDto toDto(Cliente entity);
}
