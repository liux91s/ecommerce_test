package it.euris.ecommerce.mapper;

import it.euris.ecommerce.dto.ProdottoRequestDto;
import it.euris.ecommerce.dto.ProdottoResponseDto;
import it.euris.ecommerce.model.Prodotto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdottoMapper {
	Prodotto toEntity(ProdottoRequestDto dto);

	ProdottoResponseDto toDto(Prodotto entity);
}