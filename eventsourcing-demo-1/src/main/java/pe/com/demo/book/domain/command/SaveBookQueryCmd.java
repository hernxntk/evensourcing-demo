package pe.com.demo.book.domain.command;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveBookQueryCmd {

	private String idBook;
	private String title;
	private Date publish;
	private List<String> idAuthors;
	private List<String> fullnameAuthors;
}
