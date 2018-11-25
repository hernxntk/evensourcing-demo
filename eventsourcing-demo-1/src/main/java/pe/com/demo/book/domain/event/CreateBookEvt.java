package pe.com.demo.book.domain.event;

import java.util.Date;
import java.util.List;

import org.axonframework.serialization.Revision;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Value <--- contiene error
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Revision("1.0")
@Revision("2.0")
public class CreateBookEvt {

	private String idBook;
	private String title;
	private Date publish;
	private List<String> authors;
	
	private String isbn; // nuevo campo
}
