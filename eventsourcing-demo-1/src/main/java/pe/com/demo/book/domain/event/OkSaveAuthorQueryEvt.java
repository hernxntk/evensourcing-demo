package pe.com.demo.book.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OkSaveAuthorQueryEvt {

	private String idBook;
}
