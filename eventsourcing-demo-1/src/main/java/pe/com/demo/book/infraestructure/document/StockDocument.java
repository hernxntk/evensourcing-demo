package pe.com.demo.book.infraestructure.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("stock")
public class StockDocument {

	@Id
	@Field
	private String idStock;
	
	@Field
	private Integer total;
}
