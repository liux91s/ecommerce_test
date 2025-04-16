package it.euris.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ORDINE_ITEM")
@Data
public class OrdineItem {
	
	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	  	@Column(name="id_ordine_item")
	    private Long id;
	    
	    @ManyToOne
	    @JoinColumn(name = "id_prodotto")
	    private Prodotto prodotto;
	    
	    @Column(name="quantita")
	    private Integer quantita;
	    
	    @ManyToOne
	    @JoinColumn(name = "id_ordine")
	    private Ordine ordine;
}
