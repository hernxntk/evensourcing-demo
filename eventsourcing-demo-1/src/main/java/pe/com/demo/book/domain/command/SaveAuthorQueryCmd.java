package pe.com.demo.book.domain.command;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveAuthorQueryCmd {

	private String idBook;
	private List<String> idAuthors;
	private List<String> fullnameAuthors;
}
