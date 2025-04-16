package it.euris.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.euris.ecommerce.dto.ProdottoRequestDto;
import it.euris.ecommerce.dto.ProdottoResponseDto;
import it.euris.ecommerce.service.ClienteService;
import it.euris.ecommerce.service.ProdottoService;
import lombok.Data;

@RestController
@RequestMapping("/prodotti")
@Data
public class ProdottoController {
    
    @Autowired
    private ProdottoService prodottoService;
  //task 3
    @GetMapping
    public Page<ProdottoResponseDto> getAll(Pageable pageable) {
        return prodottoService.getAllProdotti(pageable);
    }
  //task 3
    @PostMapping
    public ResponseEntity<ProdottoResponseDto> create(@RequestBody ProdottoRequestDto dto) {
        return ResponseEntity.ok(prodottoService.creaProdotto(dto));
    }
}