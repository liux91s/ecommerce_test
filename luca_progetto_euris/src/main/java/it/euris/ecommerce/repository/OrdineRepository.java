package it.euris.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.euris.ecommerce.model.Ordine;

public interface OrdineRepository extends JpaRepository<Ordine, Long>{

}
