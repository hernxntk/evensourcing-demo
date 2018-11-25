package pe.com.demo.book.api.request;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoCreateBook {

	private String title;
	private Date publish;
	private List<String> authors;
	private String isbn;
}
