package pe.com.demo.book.domain.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RollbackSaveBookQueryCmd {

	private String idBook;
}
