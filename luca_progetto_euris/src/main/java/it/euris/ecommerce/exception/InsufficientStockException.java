package it.euris.ecommerce.exception;

public class InsufficientStockException extends RuntimeException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientStockException(String message) {
        super(message);
    }
    
    public InsufficientStockException(String nomeProdotto, Integer requested, Integer disponibile) {
        super(String.format("Stock insufficiente per '%s': richiesti %d, disponibili %d", 
        		nomeProdotto, requested, disponibile));
    }
    
}
