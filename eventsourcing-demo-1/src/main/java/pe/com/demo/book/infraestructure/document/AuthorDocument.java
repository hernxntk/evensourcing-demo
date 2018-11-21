package pe.com.demo.book.infraestructure.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("author")
public class AuthorDocument {

	@Id
	@Field
	private String idAuthor;
	
	@Field
	private String fullname; 
}
