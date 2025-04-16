package it.euris.ecommerce.mapper;

import it.euris.ecommerce.dto.OrdineRequestDto;
import it.euris.ecommerce.dto.OrdineResponseDto;
import it.euris.ecommerce.model.Ordine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrdineMapper {
	Ordine toEntity(OrdineRequestDto dto);

	@Mapping(target = "clienteNome", source = "cliente.nomeCompleto")
	OrdineResponseDto toDto(Ordine entity);
}
