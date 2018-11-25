package pe.com.demo.book.domain.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAllSubscriptionQueryCmd {

	private String idBook;
}
