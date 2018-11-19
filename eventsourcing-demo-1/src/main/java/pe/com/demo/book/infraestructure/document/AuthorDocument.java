package pe.com.demo.book.infraestructure.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDocument {

	private String idAuthor;
	
	private String fullname; 
}
