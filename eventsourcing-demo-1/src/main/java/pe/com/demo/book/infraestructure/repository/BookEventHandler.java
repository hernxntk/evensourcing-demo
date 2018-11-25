package pe.com.demo.book.infraestructure.repository;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.com.demo.book.domain.event.UpdateAllSubscriptionQueryEvt;
import pe.com.demo.book.domain.query.FetchAllBooksQry;
import pe.com.demo.book.domain.query.FetchBookByIdQry;
import pe.com.demo.book.infraestructure.document.BookDocument;

@Component
public class BookEventHandler {

	private QueryBus queryBus;
	
	private BookDocumentRepository bookDocumentRepository;
	
	@Autowired
	public void setQueryBus(QueryBus queryBus) {
		this.queryBus = queryBus;
	}
	
	@Autowired
	public void setBookDocumentRepository(BookDocumentRepository bookDocumentRepository) {
		this.bookDocumentRepository = bookDocumentRepository;
	}
	
	@EventHandler
	public void on(UpdateAllSubscriptionQueryEvt evt) {
		BookDocument book = bookDocumentRepository
				.findById(evt.getIdBook())
				.orElse(null);
		
		// ACTUALIZAMOS TODAS LAS QUERYS SUSCRITAS A LA CONSULTA FetchAllBooks
		this.queryBus
			.queryUpdateEmitter()
			.emit(FetchAllBooksQry.class, f -> {return true;}, book);
				
		// ACTUALIZAMOS TODAS LAS QUERYS SUSCRITAS A LA CONSULTA FetchBookById
		this.queryBus
			.queryUpdateEmitter()
			.emit(FetchBookByIdQry.class, f -> {return f.getIdBook().equalsIgnoreCase(book.getIdBook());}, book);
	}
	
}
