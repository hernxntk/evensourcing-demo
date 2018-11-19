package pe.com.demo.book.domain.event;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Value <--- contiene error
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookEvt {

	private String idBook;
	private String title;
	private Date publish;
	private List<String> authors;
}
