package it.euris.ecommerce.dto;

import lombok.Data;

@Data
public class ClienteResponseDto {
	private Long id;
	private String nomeCompleto;
	private String codiceFiscale;
	private String email;

}
