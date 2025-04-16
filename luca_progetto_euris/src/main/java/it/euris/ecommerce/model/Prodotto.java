package it.euris.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "PRODOTTO")
@Data
public class Prodotto {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_prodotto")
    private Long idProdotto;
    
    @Column(name="codice")
	String codice;
    
    @Column(name="nome")
	String nome;
    
    @Column(name="stock")
	Long stock;

}
