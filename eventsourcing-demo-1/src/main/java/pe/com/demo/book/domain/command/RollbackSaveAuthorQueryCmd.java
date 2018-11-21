package pe.com.demo.book.domain.command;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RollbackSaveAuthorQueryCmd {

	private String idBook;
	private List<String> idAuthors;
}