package it.euris.ecommerce.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.euris.ecommerce.dto.ProdottoRequestDto;
import it.euris.ecommerce.dto.ProdottoResponseDto;
import it.euris.ecommerce.exception.EntityNotFoundException;
import it.euris.ecommerce.mapper.ProdottoMapper;
import it.euris.ecommerce.model.Prodotto;
import it.euris.ecommerce.repository.ProdottoRepository;
import it.euris.ecommerce.service.ProdottoService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class ProdottoServiceImpl implements ProdottoService {

	private final ProdottoRepository prodottoRepository;
	private final ProdottoMapper prodottoMapper;

	
	//Recupero della lista prodotti task 1
	@Override
	@Transactional(readOnly = true)
	public Page<ProdottoResponseDto> getAllProdotti(Pageable pageable) {
		return prodottoRepository.findAll(pageable).map(prodottoMapper::toDto);
	}

	
	//Aggiunta di un prodo􀆩o task 1
	@Override
	@Transactional
	public ProdottoResponseDto creaProdotto(ProdottoRequestDto prodottoDto) {
		Prodotto prodotto = prodottoMapper.toEntity(prodottoDto);
		Prodotto savedProdotto = prodottoRepository.save(prodotto);
		return prodottoMapper.toDto(savedProdotto);
	}

	@Override
    @Transactional
    public ProdottoResponseDto updateStock(Long id, Integer quantity) {
        Prodotto prodotto = prodottoRepository.findById(id)
        		.orElseThrow(() -> new EntityNotFoundException("Prodotto non trovato con ID: " + id));
        
        if(prodotto.getStock() + quantity < 0) {
            throw new IllegalArgumentException("Quantità non valida. Stock non può essere negativo");
        }
        
        prodotto.setStock(prodotto.getStock() + quantity);
        Prodotto updatedProdotto = prodottoRepository.save(prodotto);
        return prodottoMapper.toDto(updatedProdotto);
    }
}