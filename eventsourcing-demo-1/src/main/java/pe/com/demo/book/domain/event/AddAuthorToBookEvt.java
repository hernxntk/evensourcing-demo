package pe.com.demo.book.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddAuthorToBookEvt {
	
//	private String idBook;
	private String fullname;
}
