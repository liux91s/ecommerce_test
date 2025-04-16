package it.euris.ecommerce.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.euris.ecommerce.dto.OrdineRequestDto;
import it.euris.ecommerce.dto.OrdineResponseDto;
import it.euris.ecommerce.exception.InsufficientStockException;
import it.euris.ecommerce.mapper.OrdineMapper;
import it.euris.ecommerce.model.Cliente;
import it.euris.ecommerce.model.Ordine;
import it.euris.ecommerce.model.Ordine.OrdineStato;
import it.euris.ecommerce.model.OrdineItem;
import it.euris.ecommerce.model.Prodotto;
import it.euris.ecommerce.repository.ClienteRepository;
import it.euris.ecommerce.repository.OrdineRepository;
import it.euris.ecommerce.repository.ProdottoRepository;
import it.euris.ecommerce.service.OrdineService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@Data
@RequiredArgsConstructor
public class OrdineServiceImpl implements OrdineService {

	private final OrdineRepository ordineRepository;
	private final ProdottoRepository prodottoRepository;
	private final ClienteRepository clienteRepository;
	private final OrdineMapper ordineMapper;
	
	
	//Creazione di un ordine per un cliente task1 con spiegazione su cosa sto facendo ad ogni passo
	@Override
	public OrdineResponseDto creaOrdine(OrdineRequestDto ordineDto) {
		
		//task 2
		// 1. Verifica cliente esistente
		Cliente cliente = clienteRepository.findById(ordineDto.getClienteId())
				.orElseThrow(() -> new EntityNotFoundException("Cliente non trovato"));

		// 2. Crea ordine
		Ordine ordine = new Ordine();
		ordine.setCliente(cliente);
		ordine.setOrdineStato(OrdineStato.ORDINATO);

		// 3. Processa ogni item
		ordineDto.getItems().forEach(item -> {
			Prodotto prodotto = prodottoRepository.findById(item.getIdProdotto())
					.orElseThrow(() -> new EntityNotFoundException("Prodotto non trovato"));

			// 4. Verifica stock task 2
			if (prodotto.getStock() < item.getQuantita()) {
				throw new InsufficientStockException("Stock insufficiente per: " + prodotto.getNome());
			}

			// 5. Aggiorna stock task 2
			prodotto.setStock(prodotto.getStock() - item.getQuantita());
			prodottoRepository.save(prodotto);

			// 6. Aggiungi item all'ordine
			OrdineItem ordineItem = new OrdineItem();
			ordineItem.setProdotto(prodotto);
			ordineItem.setQuantita(item.getQuantita());
			ordineItem.setOrdine(ordine);
			ordine.getListaOrdini().add(ordineItem);
		});

		// 7. Salva ordine
		return ordineMapper.toDto(ordineRepository.save(ordine));
	}

	
	//Recupero della lista degli ordini task1
	@Override
	public Page<OrdineResponseDto> getAllOrdini(Pageable pageable) {
		return ordineRepository.findAll(pageable).map(ordineMapper::toDto);
	}

	@Override
	public OrdineResponseDto updateStato(Long id, OrdineStato nuovoStato) {
		Ordine ordine = ordineRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Ordine non trovato"));

		if (ordine.getOrdineStato() == OrdineStato.CONSEGNATO) {
			throw new IllegalStateException("Impossibile modificare un ordine consegnato");
		}

		ordine.setOrdineStato(nuovoStato);
		return ordineMapper.toDto(ordineRepository.save(ordine));
	}

	@Override
	public void deleteOrdine(Long id) {
		Ordine ordine = ordineRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Ordine non trovato"));

		if (ordine.getOrdineStato() == OrdineStato.CONSEGNATO) {
			throw new IllegalStateException("Impossibile cancellare un ordine consegnato");
		}

		ordineRepository.delete(ordine);
	}

}
