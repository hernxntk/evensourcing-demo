package pe.com.demo.util;

import org.axonframework.serialization.SimpleSerializedType;
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import pe.com.demo.book.domain.event.CreateBookEvt;

@Component
public class CreateBookEvtUpcaster extends SingleEventUpcaster {
	
	private static SimpleSerializedType targetType = 
			new SimpleSerializedType(CreateBookEvt.class.getTypeName(), "1.0");

	@Override
	protected boolean canUpcast(IntermediateEventRepresentation intermediateRepresentation) {
		return intermediateRepresentation.getType().equals(targetType);
	}

	@Override
	protected IntermediateEventRepresentation doUpcast(IntermediateEventRepresentation intermediateRepresentation) {
		return intermediateRepresentation
				.upcastPayload(new SimpleSerializedType(targetType.getName(), "2.0"),
						JsonNode.class,
						json ->{
							((ObjectNode)json).put("isbn", "no-isbn-registration");
							return json;
						});
	}

}
