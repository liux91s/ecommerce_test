package it.euris.ecommerce.dto;

import java.util.List;

import it.euris.ecommerce.model.Ordine.OrdineStato;
import lombok.Data;
@Data
public class OrdineResponseDto {
	private Long id;
	private String clienteNome;
	private List<OrdineItemResponseDto> items;
	private OrdineStato stato;

}
