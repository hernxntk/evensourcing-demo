package pe.com.demo.book.infraestructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pe.com.demo.book.infraestructure.document.AuthorDocument;

public interface AuthorDocumentRepository extends MongoRepository<AuthorDocument, String>{

}
