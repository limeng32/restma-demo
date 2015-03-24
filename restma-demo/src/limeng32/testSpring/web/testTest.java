package limeng32.testSpring.web;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;

public class testTest {

	@Test
	public static void testhandle42() {
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = restTemplate
				.getMessageConverters();
		list.remove(0);
		list.add(new ByteArrayHttpMessageConverter());
		byte[] response = restTemplate.postForObject(
				"http://localhost:8081/testSpring/test/handle44/{itemId}.html",
				null, byte[].class, "12356");
		Resource outFile = new FileSystemResource("d:/asdasd.jpg");
		try {
			FileCopyUtils.copy(response, outFile.getFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		testhandle42();
	}
}
