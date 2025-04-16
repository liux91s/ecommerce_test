package it.euris.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.euris.ecommerce.model.Prodotto;

public interface ProdottoRepository  extends JpaRepository<Prodotto, Long>{

}
