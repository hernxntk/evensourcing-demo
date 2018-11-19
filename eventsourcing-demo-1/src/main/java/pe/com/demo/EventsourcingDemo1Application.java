package pe.com.demo;

import javax.persistence.EntityManager;

import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.jpa.SimpleEntityManagerProvider;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.modelling.command.GenericJpaRepository;
import org.axonframework.modelling.command.Repository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pe.com.demo.book.domain.aggregate.Book;

@SpringBootApplication
public class EventsourcingDemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(EventsourcingDemo1Application.class, args);
	}
	
//	@Bean
//	public EntityManagerProvider entityManagerProvider(EntityManager entityManager) {
//		return new SimpleEntityManagerProvider(entityManager);
//	}
//	
//	@Bean
//	public Repository<Book> bookAggregateRepository(EntityManagerProvider entityManagerProvider, EventBus eventBus){
//		return GenericJpaRepository
//				.builder(Book.class)
//				.entityManagerProvider(entityManagerProvider)
//				.eventBus(eventBus)
//				.build();
//	}
}
