package pe.com.demo;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.demo.book.domain.aggregate.BookRepository;
import pe.com.demo.book.domain.command.SaveBookQueryCmd;
import pe.com.demo.book.domain.event.CreateBookEvt;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventsourcingDemo1ApplicationTests {
	
	@Autowired
	private BookRepository bookRepository;

	@Test
	@Ignore
	public void test1() {
		String json = "{\"idBook\":\"1\",\"title\":\"Book 1\",\"publish\":\"2008-02-01T02:00:00.000+0000\",\"authors\":[\"Jhon Doe 1\",\"Jhon Doe 2\",\"Jhon Doe 3\"]}";
		ObjectMapper mapper = new ObjectMapper();
		try {
			CreateBookEvt evt = mapper.readValue(json, CreateBookEvt.class);
			System.out.println(evt.toString());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Test
	@Ignore
	public void test2() {
		SaveBookQueryCmd cmd = new SaveBookQueryCmd(UUID.randomUUID().toString(), "Event sourcing", new Date(), Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString()), Arrays.asList("Jhon Doe 1", "Jhon Doe 2", "Jhon Doe 3"));
		bookRepository.saveBookQuery(cmd);
	}

	
	@Test
	@Ignore
	public void test3() throws JsonProcessingException {
		Date dt = new Date();
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(dt));
	}
}
