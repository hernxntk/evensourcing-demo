package pe.com.demo.book.infraestructure.document;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("document")
public class BookDocument {

	@Id
	@Field
	private String idBook;
	
	@Field
	private String title;
	
	@Field
	private Date publish;
	
	@Field
	private List<AuthorDocument> authors;
}
