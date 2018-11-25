package pe.com.demo.book.domain.saga;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.com.demo.book.domain.command.IncreaseStockCmd;
import pe.com.demo.book.domain.command.RollbackIncreaseStockCmd;
import pe.com.demo.book.domain.command.RollbackSaveBookQueryCmd;
import pe.com.demo.book.domain.command.SaveAuthorQueryCmd;
import pe.com.demo.book.domain.command.SaveBookQueryCmd;
import pe.com.demo.book.domain.command.UpdateAllSubscriptionQueryCmd;
import pe.com.demo.book.domain.event.ErrorIncreaseStockEvt;
import pe.com.demo.book.domain.event.ErrorSaveAuthorQueryEvt;
import pe.com.demo.book.domain.event.ErrorSaveBookQueryEvt;
import pe.com.demo.book.domain.event.OkIncreaseStockEvt;
import pe.com.demo.book.domain.event.OkSaveAuthorQueryEvt;
import pe.com.demo.book.domain.event.OkSaveBookQueryEvt;
import pe.com.demo.book.domain.event.TransferBookEvt;

@Saga
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
	
	private BookTransferData data;
	
	@StartSaga
	@SagaEventHandler(associationProperty = "idBook")
	public void on(TransferBookEvt evt) {
		System.out.println("Start " + SagaLifecycle.describeCurrentScope().scopeDescription());
		this.data = new BookTransferData(evt.getIdBook(), evt.getTitle(), evt.getPublish(), evt.getIdAuthors(), evt.getFullnameAuthors());
		SagaLifecycle.associateWith("idBook", this.data.getIdBook());
		increaseStock();
	}
	
	// ----------------------------- PASO 1 ---------------------------
	
	@SagaEventHandler(associationProperty = "idBook")
	public void on(OkIncreaseStockEvt evt) {
		saveBookQuery();
	}
	
	@EndSaga
	@SagaEventHandler(associationProperty = "idBook")
	public void on(ErrorIncreaseStockEvt evt) {
		// NO SE COMPENSA NADA DEBIDO A QUE ES EL PRIMER COMMIT
		System.out.println("End " + SagaLifecycle.describeCurrentScope().scopeDescription());
	}
	
	// ----------------------------- PASO 2 ---------------------------

	@SagaEventHandler(associationProperty = "idBook")
	public void on(OkSaveBookQueryEvt evt) {
		saveAuthorQuery();
	}
	
	@EndSaga
	@SagaEventHandler(associationProperty = "idBook")
	public void on(ErrorSaveBookQueryEvt evt) {
		rollbackStock();
		System.out.println("End " + SagaLifecycle.describeCurrentScope().scopeDescription());
	}
	
	// ----------------------------- PASO 3 ---------------------------
	
	@EndSaga
	@SagaEventHandler(associationProperty = "idBook")
	public void on(OkSaveAuthorQueryEvt evt) {
		// FINALIZANDO SAGA OK
		updateAllSubscriptionQuerys();
		System.out.println("End " + SagaLifecycle.describeCurrentScope().scopeDescription());
	}
	
	@EndSaga
	@SagaEventHandler(associationProperty = "idBook")
	public void on(ErrorSaveAuthorQueryEvt evt) {
		rollbackStock();
		rollbackBookQuery();
		System.out.println("End " + SagaLifecycle.describeCurrentScope().scopeDescription());
	}
	
	private void increaseStock() {
		this.commandBus.dispatch(asCommandMessage(new IncreaseStockCmd(this.data.getIdBook())));
	}
	
	private void saveBookQuery() {
		this.commandBus.dispatch(asCommandMessage(new SaveBookQueryCmd(this.data.getIdBook(), this.data.getTitle(), this.data.getPublish(), this.data.getIdAuthors(), this.data.getFullnameAuthors())));
	}
	
	private void saveAuthorQuery() {
		this.commandBus.dispatch(asCommandMessage(new SaveAuthorQueryCmd(this.data.getIdBook(), this.data.getIdAuthors(), this.data.getFullnameAuthors())));
	}
	
	private void rollbackStock() {
		this.commandBus.dispatch(asCommandMessage(new RollbackIncreaseStockCmd(this.data.getIdBook())));
	}
	
	private void rollbackBookQuery() {
		this.commandBus.dispatch(asCommandMessage(new RollbackSaveBookQueryCmd(data.getIdBook())));
	}
	
	private void updateAllSubscriptionQuerys() {
		this.commandBus.dispatch(asCommandMessage(new UpdateAllSubscriptionQueryCmd(data.getIdBook())));
	}

}
