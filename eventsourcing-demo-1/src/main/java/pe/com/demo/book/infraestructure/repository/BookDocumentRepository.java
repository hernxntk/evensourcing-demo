package pe.com.demo.book.infraestructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pe.com.demo.book.infraestructure.document.BookDocument;

public interface BookDocumentRepository extends MongoRepository<BookDocument, String> {

}
