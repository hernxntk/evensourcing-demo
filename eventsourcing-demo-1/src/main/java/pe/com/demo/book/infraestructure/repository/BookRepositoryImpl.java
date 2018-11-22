package pe.com.demo.book.infraestructure.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.demo.book.domain.aggregate.BookRepository;
import pe.com.demo.book.domain.command.SaveAuthorQueryCmd;
import pe.com.demo.book.domain.command.SaveBookQueryCmd;
import pe.com.demo.book.infraestructure.document.AuthorDocument;
import pe.com.demo.book.infraestructure.document.BookDocument;
import pe.com.demo.book.infraestructure.document.StockDocument;

@Repository
public class BookRepositoryImpl implements BookRepository {

	private BookDocumentRepository bookDocumentRepository;
	
	private AuthorDocumentRepository authorDocumentRepository;
	
	private StockDocumentResository stockBookDocumentResository;
	
	@Autowired
	public void setBookDocumentRepository(BookDocumentRepository bookDocumentRepository) {
		this.bookDocumentRepository = bookDocumentRepository;
	}
	
	@Autowired
	public void setAuthorDocumentRepository(AuthorDocumentRepository authorDocumentRepository) {
		this.authorDocumentRepository = authorDocumentRepository;
	}
	
	@Autowired
	public void setStockBookDocumentResository(StockDocumentResository stockBookDocumentResository) {
		this.stockBookDocumentResository = stockBookDocumentResository;
	}
	
	@Override
	public void saveBookQuery(SaveBookQueryCmd cmd) {
//		double revert = Math.random();
//		if(revert > 0.5) {
//			throw new RuntimeException("Error no se debe actualizar.");
//		}
		
		List<AuthorDocument> authors = new ArrayList<AuthorDocument>();
		if(cmd.getFullnameAuthors() != null && !cmd.getFullnameAuthors().isEmpty()
				&& cmd.getIdAuthors() != null && !cmd.getIdAuthors().isEmpty()
				&& cmd.getIdAuthors().size() == cmd.getFullnameAuthors().size()) {
			for(int i = 0; i < cmd.getIdAuthors().size() ; i++) {
				AuthorDocument author = new AuthorDocument(cmd.getIdAuthors().get(i), cmd.getFullnameAuthors().get(i));
				authors.add(author);
			}
		}
		
		BookDocument book = new BookDocument(cmd.getIdBook(), cmd.getTitle(), cmd.getPublish(), authors);
		bookDocumentRepository.save(book);
	}
	
	@Override
	public void deleteBookQuery(String idBook) {
		bookDocumentRepository.deleteById(idBook);
	}

	@Override
	public void saveAuthorQuery(SaveAuthorQueryCmd cmd) {
//		double revert = Math.random();
//		if(revert > 0.5) {
//			throw new RuntimeException("Error no se debe actualizar.");
//		}
		if(cmd.getFullnameAuthors() != null && !cmd.getFullnameAuthors().isEmpty()
				&& cmd.getIdAuthors() != null && !cmd.getIdAuthors().isEmpty()
				&& cmd.getIdAuthors().size() == cmd.getFullnameAuthors().size()) {
			for(int i = 0; i < cmd.getIdAuthors().size() ; i++) {
				AuthorDocument author = authorDocumentRepository
					.findById(cmd.getIdAuthors().get(i))
					.orElse(new AuthorDocument(cmd.getIdAuthors().get(i), cmd.getFullnameAuthors().get(i)));
				author.setFullname(cmd.getFullnameAuthors().get(i));
				authorDocumentRepository.save(author);
			}
		}
	}
	
	@Override
	public void deleteAuthorQuery(List<String> idAuthors) {
		if(idAuthors != null && !idAuthors.isEmpty()) {
			for(String idAuthor : idAuthors) {
				authorDocumentRepository.deleteById(idAuthor);
			}
		}
	}

	@Override
	public void increaseStock(String idStock) {
//		double revert = Math.random();
//		if(revert > 0.5) {
//			throw new RuntimeException("Error no se debe actualizar.");
//		}
		StockDocument stock = stockBookDocumentResository.findById(idStock)
			.orElse(new StockDocument(idStock, new Integer(0)));
		stock.setTotal(new Integer(stock.getTotal().intValue() + 1));
		stockBookDocumentResository.save(stock);
	}

	@Override
	public void decreaseStock(String idStock) {
		StockDocument stock = stockBookDocumentResository.findById(idStock)
			.orElse(new StockDocument(idStock, new Integer(1)));
		stock.setTotal(new Integer(stock.getTotal().intValue() - 1));
		stockBookDocumentResository.save(stock);
	}
}
