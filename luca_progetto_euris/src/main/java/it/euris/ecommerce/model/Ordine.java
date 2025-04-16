package it.euris.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;

@Entity
@Table(name = "ORDINE")
@Data
public class Ordine {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idOrdine")
    private Long idOrdine;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "id_prodotto")
    private Prodotto prodotto;
    
    @Version  // Aggiungi questa annotazione per il lock ottimistico
    private Long concorrenzaEvadibile;
    
    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
    private List<OrdineItem> listaOrdini = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    private OrdineStato ordineStato;


	public enum OrdineStato {
	    ORDINATO, CONSEGNATO, IN_CONSEGNA
	}
    
}
