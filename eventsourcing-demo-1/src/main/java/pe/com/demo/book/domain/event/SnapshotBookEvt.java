package pe.com.demo.book.domain.event;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.demo.book.domain.aggregate.Author;

@Data
@NoArgsConstructor
public class SnapshotBookEvt {

	private String idBook;
	private String title;
	private Date publish;
	private List<Author> authors;
}
