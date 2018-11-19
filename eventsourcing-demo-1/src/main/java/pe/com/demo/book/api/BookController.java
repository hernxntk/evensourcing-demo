package pe.com.demo.book.api;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.demo.book.api.request.DtoAddAuthorToBook;
import pe.com.demo.book.api.request.DtoCreateBook;
import pe.com.demo.book.domain.command.AddAuthorToBookCmd;
import pe.com.demo.book.domain.command.CreateBookCmd;

@RestController
@RequestMapping("/api")
public class BookController {

	private CommandGateway commandGateway;
	
	@Autowired
	public void setCommandGateway(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}
	
	@PostMapping(path = "/create-book")
	public ResponseEntity<?> createBook(@RequestBody DtoCreateBook dto){
		String idBook = "1";
		commandGateway.send(new CreateBookCmd(idBook, dto.getTitle(), dto.getPublish(), dto.getAuthors()));
		return ResponseEntity.ok(idBook);
	}
	
	@PostMapping(path = "/add-author")
	public ResponseEntity<?> addAuthor(@RequestBody DtoAddAuthorToBook dto){
		commandGateway.send(new AddAuthorToBookCmd(dto.getIdBook(), dto.getFullname()));
		return ResponseEntity.ok(dto);
	}
}
