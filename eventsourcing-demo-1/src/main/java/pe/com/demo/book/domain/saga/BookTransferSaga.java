package pe.com.demo.book.domain.saga;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.com.demo.book.domain.command.SaveBookQueryCmd;
import pe.com.demo.book.domain.event.ErrorSaveBookQueryEvt;
import pe.com.demo.book.domain.event.OkSaveBookQueryEvt;
import pe.com.demo.book.domain.event.TransferBookEvt;

@Saga(sagaStore = "sagaStore")
@Getter
@Setter
@NoArgsConstructor
public class BookTransferSaga {

	@JsonIgnore
	private transient CommandBus commandBus;
	
	@Autowired
	public void setCommandBus(CommandBus commandBus) {
		this.commandBus = commandBus;
	}
	
	private String idBook;
	
	@StartSaga
	@SagaEventHandler(associationProperty = "idBook")
	public void on(TransferBookEvt evt) {
		this.idBook = evt.getIdBook();
		this.commandBus.dispatch(GenericCommandMessage.asCommandMessage(new SaveBookQueryCmd(evt.getIdBook(), evt.getTitle(), evt.getPublish(), evt.getIdAuthors(), evt.getFullnameAuthors())));
	}
	

	@EndSaga
	@SagaEventHandler(associationProperty = "idBook")
	public void on(OkSaveBookQueryEvt evt) {
		System.out.println("OK SAGA: " + evt.getIdBook());
	}
	
	@EndSaga
	@SagaEventHandler(associationProperty = "idBook")
	public void on(ErrorSaveBookQueryEvt evt) {
		System.out.println("ERROR SAGA: " + evt.getIdBook());
	}
}
