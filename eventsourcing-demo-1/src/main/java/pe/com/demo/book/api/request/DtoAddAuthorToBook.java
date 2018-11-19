package pe.com.demo.book.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoAddAuthorToBook {

	private String idBook;
	private String fullname;
}
