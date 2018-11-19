package pe.com.demo.book.domain.aggregate;

import pe.com.demo.book.domain.command.SaveBookQueryCmd;

public interface BookRepository {

	public void saveBookQueryEvt(SaveBookQueryCmd cmd);
}
