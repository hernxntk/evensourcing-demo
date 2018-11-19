package pe.com.demo.book.domain.event;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferBookEvt {
	
	private String idBook;
	private String title;
	private Date publish;
	private List<String> idAuthors;
	private List<String> fullnameAuthors;
}
