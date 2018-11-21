package pe.com.demo.book.infraestructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pe.com.demo.book.infraestructure.document.StockDocument;

public interface StockDocumentResository extends MongoRepository<StockDocument, String> {

}
