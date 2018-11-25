package pe.com.demo.book.infraestructure.repository;

import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.com.demo.book.domain.query.FetchAllBooks;
import pe.com.demo.book.domain.query.FetchBookById;
import pe.com.demo.book.infraestructure.document.BookDocument;

@Component
public class BookQueryHandler {

	private BookDocumentRepository bookDocumentRepository;
	
	@Autowired
	public void setBookDocumentRepository(BookDocumentRepository bookDocumentRepository) {
		this.bookDocumentRepository = bookDocumentRepository;
	}
	
	@QueryHandler
	public List<BookDocument> fecthAll(FetchAllBooks query) {
		return bookDocumentRepository.findAll();
	}
	
	@QueryHandler
	public BookDocument fetchById(FetchBookById query) {
		return bookDocumentRepository.findById(query.getIdBook())
				.orElse(null)
				;
	}
}
