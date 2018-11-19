package pe.com.demo.book.infraestructure.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.com.demo.book.domain.aggregate.BookRepository;
import pe.com.demo.book.domain.command.SaveBookQueryCmd;
import pe.com.demo.book.infraestructure.document.AuthorDocument;
import pe.com.demo.book.infraestructure.document.BookDocument;

@Repository
public class BookRepositoryImpl implements BookRepository {

	private BookDocumentRepository repository;
	
	@Autowired
	public void setRepository(BookDocumentRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public void saveBookQueryEvt(SaveBookQueryCmd cmd) {
		double revert = Math.random();
		if(revert > 0) {
			throw new RuntimeException("Error no se debe actualizar.");
		}
		
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
		repository.save(book);
	}

}
