package it.euris.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.euris.ecommerce.dto.OrdineRequestDto;
import it.euris.ecommerce.dto.OrdineResponseDto;
import it.euris.ecommerce.exception.InsufficientStockException;
import it.euris.ecommerce.model.Ordine.OrdineStato;
import it.euris.ecommerce.service.ClienteService;
import it.euris.ecommerce.service.OrdineService;
import jakarta.validation.Valid;
import lombok.Data;

@RestController
@RequestMapping("/ordini")
@Data
public class OrdineController {
    
    @Autowired
    private OrdineService ordineService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody OrdineRequestDto dto) {
        try {
            return ResponseEntity.ok(ordineService.creaOrdine(dto));
        } catch (InsufficientStockException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
  //task 3
    @PatchMapping("/{id}/stato")
    public ResponseEntity<OrdineResponseDto> updateStatus(
            @PathVariable Long id, 
            @RequestParam OrdineStato nuovoStato) {
        return ResponseEntity.ok(ordineService.updateStato(id, nuovoStato));
    }
    //task 3
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            ordineService.deleteOrdine(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}