package pe.com.demo.book.domain.aggregate;

import java.util.stream.Collectors;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;
import static org.axonframework.eventhandling.GenericEventMessage.asEventMessage;
import org.axonframework.modelling.command.Aggregate;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.axonframework.modelling.command.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.com.demo.book.domain.command.AddAuthorToBookCmd;
import pe.com.demo.book.domain.command.IncreaseStockCmd;
import pe.com.demo.book.domain.command.RollbackIncreaseStockCmd;
import pe.com.demo.book.domain.command.RollbackSaveAuthorQueryCmd;
import pe.com.demo.book.domain.command.RollbackSaveBookQueryCmd;
import pe.com.demo.book.domain.command.SaveAuthorQueryCmd;
import pe.com.demo.book.domain.command.SaveBookQueryCmd;
import pe.com.demo.book.domain.command.TransferBookCmd;
import pe.com.demo.book.domain.event.AddAuthorToBookEvt;
import pe.com.demo.book.domain.event.ErrorIncreaseStockEvt;
import pe.com.demo.book.domain.event.ErrorSaveAuthorQueryEvt;
import pe.com.demo.book.domain.event.ErrorSaveBookQueryEvt;
import pe.com.demo.book.domain.event.OkIncreaseStockEvt;
import pe.com.demo.book.domain.event.OkSaveAuthorQueryEvt;
import pe.com.demo.book.domain.event.OkSaveBookQueryEvt;
import pe.com.demo.book.domain.event.TransferBookEvt;

//@AllArgsConstructor
@Component
public class BookCommandHandler {
	
	private Repository<Book> repository;
	private EventBus eventBus; 
	private BookRepository bookRepository;
	
	@Autowired
	public void setRepository(Repository<Book> repository) {
		this.repository = repository;
	}
	
	@Autowired
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}
	
	@Autowired
	public void setBookRepository(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@CommandHandler
	public void on(AddAuthorToBookCmd cmd) {
		try {
			Aggregate<Book> aggregate = repository.load(cmd.getIdBook());
			aggregate.execute(b -> b.addAuthor(cmd.getFullname()));
		}catch(AggregateNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	@CommandHandler
	public void on(TransferBookCmd cmd) {
		try {
			Aggregate<Book> aggregate = repository.load(cmd.getIdBook());
			
			aggregate.execute(b -> 	{
					TransferBookEvt evt = new TransferBookEvt(
					b.getIdBook(),
					b.getTitle(),
					b.getPublish(),
					b.getAuthors().stream().map(Author::getIdAuthor).collect(Collectors.toList()),
					b.getAuthors().stream().map(Author::getFullname).collect(Collectors.toList()));
					eventBus.publish(asEventMessage(evt));
			});
		}catch(AggregateNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	@CommandHandler
	public void on(IncreaseStockCmd cmd) {
		try {
			bookRepository.increaseStock(cmd.getIdBook());
			eventBus.publish(asEventMessage(new OkIncreaseStockEvt(cmd.getIdBook())));
		}catch(Exception ex) {
			eventBus.publish(asEventMessage(new ErrorIncreaseStockEvt(cmd.getIdBook())));
		}
	}
	
	// compensacion del IncreaseStockCmd
	@CommandHandler
	public void on(RollbackIncreaseStockCmd cmd) {
		bookRepository.decreaseStock(cmd.getIdBook());
	}
	
	@CommandHandler
	public void on(SaveBookQueryCmd cmd) {
		try {
			bookRepository.saveBookQuery(cmd);
			eventBus.publish(asEventMessage(new OkSaveBookQueryEvt(cmd.getIdBook())));
		}catch(Exception ex) {
			eventBus.publish(asEventMessage(new ErrorSaveBookQueryEvt(cmd.getIdBook())));
		}
	}
	
	// compensacion del SaveBookQueryCmd
	@CommandHandler
	public void on(RollbackSaveBookQueryCmd cmd) {
		bookRepository.deleteBookQuery(cmd.getIdBook());
	}
	
	@CommandHandler
	public void on(SaveAuthorQueryCmd cmd) {
		try {
			bookRepository.saveAuthorQuery(cmd);
			eventBus.publish(asEventMessage(new OkSaveAuthorQueryEvt(cmd.getIdBook())));
		}catch(Exception ex) {
			eventBus.publish(asEventMessage(new ErrorSaveAuthorQueryEvt(cmd.getIdBook())));
		}
	}
	
	// compensacion del SaveAuthorQueryCmd
	@CommandHandler
	public void on(RollbackSaveAuthorQueryCmd cmd) {
		bookRepository.deleteAuthorQuery(cmd.getIdAuthors());
	}
}
