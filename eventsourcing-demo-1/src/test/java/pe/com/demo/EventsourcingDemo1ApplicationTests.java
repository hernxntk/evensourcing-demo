package pe.com.demo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.GenericQueryMessage;
import org.axonframework.queryhandling.GenericQueryResponseMessage;
import org.axonframework.queryhandling.QueryBus;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryResponseMessage;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.demo.book.domain.aggregate.BookRepository;
import pe.com.demo.book.domain.command.CreateBookCmd;
import pe.com.demo.book.domain.command.SaveBookQueryCmd;
import pe.com.demo.book.domain.event.CreateBookEvt;
import pe.com.demo.book.domain.query.FetchAllBooksQry;
import pe.com.demo.book.infraestructure.document.BookDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventsourcingDemo1ApplicationTests {
	
	@Autowired
	private BookRepository bookRepository;
	
	private CommandGateway commandGateway;
	
	private CommandBus commandBus;
	
	private QueryGateway queryGateway;
	
	private QueryBus queryBus;

	@Test
	@Ignore
	public void test1() {
		String json = "{\"idBook\":\"1\",\"title\":\"Book 1\",\"publish\":\"2008-02-01T02:00:00.000+0000\",\"authors\":[\"Jhon Doe 1\",\"Jhon Doe 2\",\"Jhon Doe 3\"]}";
		ObjectMapper mapper = new ObjectMapper();
		try {
			CreateBookEvt evt = mapper.readValue(json, CreateBookEvt.class);
			System.out.println(evt.toString());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Test
	@Ignore
	public void test2() {
		SaveBookQueryCmd cmd = new SaveBookQueryCmd(UUID.randomUUID().toString(), "Event sourcing", new Date(), Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString()), Arrays.asList("Jhon Doe 1", "Jhon Doe 2", "Jhon Doe 3"));
		bookRepository.saveBookQuery(cmd);
	}

	@Test
	@Ignore
	public void test3() throws JsonProcessingException {
		Date dt = new Date();
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(dt));
	}

	@Test
	@Ignore
	public void test4() {
		CreateBookCmd cmd = new CreateBookCmd();
		CompletableFuture<Object> future = this.commandGateway
				.send(cmd);
		this.commandBus
			.dispatch(GenericCommandMessage.asCommandMessage(cmd));
	}
	
	@Test
	@Ignore
	public void test5() {
		FetchAllBooksQry query = new FetchAllBooksQry();
		CompletableFuture<List<BookDocument>> future1 = 
			this.queryGateway.query(query,
			ResponseTypes.multipleInstancesOf(BookDocument.class));
		
		CompletableFuture<QueryResponseMessage<List<BookDocument>>> future2 = 
			this.queryBus.query(new GenericQueryMessage<>(query, 
			ResponseTypes.multipleInstancesOf(BookDocument.class)));
		
		Mono<List<BookDocument>> initialResult =
			this.queryGateway
			.subscriptionQuery(query,
					ResponseTypes.multipleInstancesOf(BookDocument.class),
					ResponseTypes.multipleInstancesOf(BookDocument.class))
			.initialResult();
		
		Flux<List<BookDocument>> updates =
				this.queryGateway
				.subscriptionQuery(query,
						ResponseTypes.multipleInstancesOf(BookDocument.class),
						ResponseTypes.multipleInstancesOf(BookDocument.class))
				.updates();
	}
}
