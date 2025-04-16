package it.euris.ecommerce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.euris.ecommerce.dto.OrdineRequestDto;
import it.euris.ecommerce.dto.OrdineResponseDto;
import it.euris.ecommerce.model.Ordine.OrdineStato;

public interface OrdineService {
    Page<OrdineResponseDto> getAllOrdini(Pageable pageable);
    OrdineResponseDto creaOrdine(OrdineRequestDto ordineDto);
    OrdineResponseDto updateStato(Long id, OrdineStato nuovoStato);
    void deleteOrdine(Long id) throws IllegalStateException;
}
