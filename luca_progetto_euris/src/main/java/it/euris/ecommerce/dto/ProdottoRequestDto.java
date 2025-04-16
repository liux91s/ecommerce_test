package it.euris.ecommerce.dto;

import lombok.Data;

@Data
public class ProdottoRequestDto {
	private Long idProdotto;
	private String codice;
	private String nome;
	private Long stock;

}
