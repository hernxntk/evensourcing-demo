package pe.com.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.demo.book.domain.event.CreateBookEvt;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventsourcingDemo1ApplicationTests {
	

	@Test
	public void contextLoads() {
		String json = "{\"idBook\":\"1\",\"title\":\"Book 1\",\"publish\":\"2008-02-01T02:00:00.000+0000\",\"authors\":[\"Jhon Doe 1\",\"Jhon Doe 2\",\"Jhon Doe 3\"]}";
		ObjectMapper mapper = new ObjectMapper();
		try {
			CreateBookEvt evt = mapper.readValue(json, CreateBookEvt.class);
			System.out.println(evt.toString());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
