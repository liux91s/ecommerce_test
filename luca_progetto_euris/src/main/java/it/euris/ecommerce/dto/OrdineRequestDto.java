package it.euris.ecommerce.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrdineRequestDto {
	private Long clienteId;
	private List<OrdineItemDto> items;
}
