package pe.com.demo.book.domain.aggregate;

import java.util.stream.Collectors;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.modelling.command.Aggregate;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.axonframework.modelling.command.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import pe.com.demo.book.domain.command.AddAuthorToBookCmd;
import pe.com.demo.book.domain.command.SaveBookQueryCmd;
import pe.com.demo.book.domain.command.TransferBookCmd;
import pe.com.demo.book.domain.event.AddAuthorToBookEvt;
import pe.com.demo.book.domain.event.ErrorSaveBookQueryEvt;
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
//		eventBus.publish(GenericEventMessage.asEventMessage(new AddAuthorToBookEvt(cmd.getIdBook(), cmd.getFullname())));
//		eventBus.publish(GenericEventMessage.asEventMessage(new AddAuthorToBookEvt(cmd.getFullname())));
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
					eventBus.publish(GenericEventMessage.asEventMessage(evt));
			});
		}catch(AggregateNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	@CommandHandler
	public void on(SaveBookQueryCmd cmd) {
		try {
			bookRepository.saveBookQueryEvt(cmd);
			eventBus.publish(GenericEventMessage.asEventMessage(new OkSaveBookQueryEvt(cmd.getIdBook())));
		}catch(Exception ex) {
			eventBus.publish(GenericEventMessage.asEventMessage(new ErrorSaveBookQueryEvt(cmd.getIdBook())));
		}
	}
}
