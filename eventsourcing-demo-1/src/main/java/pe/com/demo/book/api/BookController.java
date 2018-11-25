package pe.com.demo.book.api;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.demo.book.api.request.DtoAddAuthorToBook;
import pe.com.demo.book.api.request.DtoCreateBook;
import pe.com.demo.book.domain.command.AddAuthorToBookCmd;
import pe.com.demo.book.domain.command.CreateBookCmd;
import pe.com.demo.book.domain.command.TransferBookCmd;
import pe.com.demo.book.domain.query.FetchAllBooksQry;
import pe.com.demo.book.domain.query.FetchBookByIdQry;
import pe.com.demo.book.infraestructure.document.BookDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class BookController {

	private CommandGateway commandGateway;
	
	private QueryGateway queryGateway;
	
	@Autowired
	public void setCommandGateway(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}
	
	@Autowired
	public void setQueryGateway(QueryGateway queryGateway) {
		this.queryGateway = queryGateway;
	}
	
	@PostMapping(path = "/create-book")
	public ResponseEntity<?> createBook(@RequestBody DtoCreateBook dto){
		String idBook = UUID.randomUUID().toString();
		commandGateway.send(new CreateBookCmd(idBook, dto.getTitle(), dto.getPublish(), dto.getAuthors()));
		return ResponseEntity.ok(idBook);
	}
	
	@PostMapping(path = "/add-author")
	public ResponseEntity<?> addAuthor(@RequestBody DtoAddAuthorToBook dto){
		commandGateway.send(new AddAuthorToBookCmd(dto.getIdBook(), dto.getFullname()));
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping(path = "/transfer-book/{idBook}")
	public ResponseEntity<?> transferBook(@PathVariable String idBook){
		commandGateway.send(new TransferBookCmd(idBook));
		return ResponseEntity.ok(idBook);
	}
	
	@GetMapping(path = "/reactive-book", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ResponseEntity<BookDocument>> reactiveGetAllBooks(){
		FetchAllBooksQry q = new FetchAllBooksQry();
		SubscriptionQueryResult<BookDocument, BookDocument> fetchAll = queryGateway
				.subscriptionQuery(q,
						ResponseTypes.instanceOf(BookDocument.class),
						ResponseTypes.instanceOf(BookDocument.class));
		
		return fetchAll
				.updates()
				.map(doc -> ResponseEntity.ok(doc))
				.defaultIfEmpty(ResponseEntity.noContent().build());
	}
	
	@GetMapping(path = "/{idBook}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<ResponseEntity<BookDocument>> getBookById(@PathVariable String idBook){
		FetchBookByIdQry q = new FetchBookByIdQry(idBook);
		
		return Mono
				.fromFuture(queryGateway.query(q, ResponseTypes.instanceOf(BookDocument.class)))
				.map(book -> ResponseEntity.ok(book))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@GetMapping(path = "/reactive-book/{idBook}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ResponseEntity<BookDocument>> reactiveGetBookById(@PathVariable String idBook){
		FetchBookByIdQry q = new FetchBookByIdQry(idBook);
		SubscriptionQueryResult<BookDocument, BookDocument> fetchById = queryGateway
				.subscriptionQuery(q,
						ResponseTypes.instanceOf(BookDocument.class),
						ResponseTypes.instanceOf(BookDocument.class));
		
		return fetchById
				.updates()
				.map(doc -> ResponseEntity.ok(doc))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
}
