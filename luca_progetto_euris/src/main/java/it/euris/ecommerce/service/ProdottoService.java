package it.euris.ecommerce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.euris.ecommerce.dto.ProdottoRequestDto;
import it.euris.ecommerce.dto.ProdottoResponseDto;

//ProdottoService.java
public interface ProdottoService {
 Page<ProdottoResponseDto> getAllProdotti(Pageable pageable);
 ProdottoResponseDto creaProdotto(ProdottoRequestDto prodottoDto);
 ProdottoResponseDto updateStock(Long id, Integer quantity);
}
