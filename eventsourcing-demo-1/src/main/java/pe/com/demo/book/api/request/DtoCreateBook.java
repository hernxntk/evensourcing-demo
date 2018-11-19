package pe.com.demo.book.api.request;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCreateBook {

	private String title;
	private Date publish;
	private List<String> authors;
}
