package pe.com.demo.book.domain.aggregate;

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
import pe.com.demo.book.domain.event.AddAuthorToBookEvt;

//@AllArgsConstructor
@Component
public class BookCommandHandler {
	
	private Repository<Book> repository;
	private EventBus eventBus;
	
	@Autowired
	public void setRepository(Repository<Book> repository) {
		this.repository = repository;
	}
	
	@Autowired
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
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
}
