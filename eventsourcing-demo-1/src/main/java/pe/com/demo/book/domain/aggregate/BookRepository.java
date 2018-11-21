package pe.com.demo.book.domain.aggregate;

import java.util.List;

import pe.com.demo.book.domain.command.SaveAuthorQueryCmd;
import pe.com.demo.book.domain.command.SaveBookQueryCmd;

public interface BookRepository {

	public void saveBookQuery(SaveBookQueryCmd cmd);
	
	public void deleteBookQuery(String idBook);
	
	public void saveAuthorQuery(SaveAuthorQueryCmd cmd);
	
	public void deleteAuthorQuery(List<String> idAuthors);
	
	public void increaseStock(String idStock);
	
	public void decreaseStock(String idStock);
}
