package pe.com.demo.book.domain.aggregate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pe.com.demo.book.domain.command.CreateBookCmd;
import pe.com.demo.book.domain.event.AddAuthorToBookEvt;
import pe.com.demo.book.domain.event.CreateBookEvt;
import pe.com.demo.book.domain.event.SnapshotBookEvt;

@Aggregate(snapshotTriggerDefinition = "clasicSnapShotter")
@NoArgsConstructor
@Getter
public class Book {

	@AggregateIdentifier
	private String idBook;
	private String title;
	private Date publish;
	private List<Author> authors;
	
	private String isbn;
	
	@EventHandler
	public void on(SnapshotBookEvt snapshot) {
		this.idBook = snapshot.getIdBook();
		this.publish = snapshot.getPublish();
		this.title = snapshot.getTitle();
		this.authors = snapshot.getAuthors();
	}
	
	@CommandHandler
	public Book(CreateBookCmd cmd) {
		AggregateLifecycle.apply(
				new CreateBookEvt(cmd.getIdBook(),
						cmd.getTitle(),
						cmd.getPublish(),
						cmd.getAuthors(),
						cmd.getIsbn()));
	}
	
	@EventSourcingHandler
	public void on(CreateBookEvt evt) {
		this.idBook = evt.getIdBook();
		this.title = evt.getTitle();
		this.publish = evt.getPublish();
		this.isbn = evt.getIsbn();
		this.authors = new ArrayList<>();
		if(evt.getAuthors() != null && !evt.getAuthors().isEmpty()) {
			evt.getAuthors()
				.stream()
				.forEach(c -> this.authors.add(new Author(UUID.randomUUID().toString(), c)));	
		}
	}
	
	@EventSourcingHandler
	public void on(AddAuthorToBookEvt evt) {
		this.authors.add(new Author(UUID.randomUUID().toString(), evt.getFullname()));
	}
	
	public void addAuthor(String fullname) {
		AggregateLifecycle.apply(new AddAuthorToBookEvt(fullname));
	}
}
