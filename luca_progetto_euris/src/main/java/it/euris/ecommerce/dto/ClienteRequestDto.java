package it.euris.ecommerce.dto;

import java.sql.Date;

import lombok.Data;
@Data
public class ClienteRequestDto {
	private String nome;
	private String cognome;
	private Date dataDiNascita;
	private String codiceFiscale;
	private String email;

}
