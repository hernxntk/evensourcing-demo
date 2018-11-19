package pe.com.demo.book.domain.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferBookCmd {

	@TargetAggregateIdentifier
	private String idBook;
}
