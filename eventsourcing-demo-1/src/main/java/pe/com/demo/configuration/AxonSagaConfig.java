package pe.com.demo.configuration;

import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.modelling.saga.repository.SagaStore;
import org.axonframework.modelling.saga.repository.jpa.JpaSagaStore;
import org.axonframework.serialization.Serializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class AxonSagaConfig {

	
//	@Bean("bookTransferSagaStore")
//	public SagaStore<Object> bookTransferSagaStore(EntityManagerProvider entityManagerProvider, Serializer serializer){
//		return JpaSagaStore
//				.builder()
//				.entityManagerProvider(entityManagerProvider)
//				.serializer(serializer)
//				.build();
//	}
}
