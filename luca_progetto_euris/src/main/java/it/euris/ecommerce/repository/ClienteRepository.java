package it.euris.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.euris.ecommerce.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
