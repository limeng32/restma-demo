package cn.limeng32.testSpring.web;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.*;

import com.thoughtworks.xstream.io.xml.StaxDriver;

import cn.limeng32.testSpring.converter.MyFormHttpMessageConverter;
import cn.limeng32.testSpring.pojo.Article2;

public class testTest {

	@Test
	public static void testhandle41() {
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		MyFormHttpMessageConverter mfhmConverter = new MyFormHttpMessageConverter();
		mfhmConverter.setCharset(Charset.forName("GBK"));
		list.add(mfhmConverter);
		restTemplate.setMessageConverters(list);
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("name", "limeng");
		form.add("address", "west");
		form.add("nickname", "¿Ó");
		form.add("hehe", "4");
		restTemplate.postForLocation(
				"http://localhost:8081/testSpring/test/handle41.html", form);
	}

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

	@Test
	public void testhandle51() throws IOException {
		RestTemplate restTemplate = buildRestTemplate();

		Article2 article = new Article2();
		article.setId(1);
		article.setTitle("∞¢Àπ∂Ÿ");

		HttpHeaders entityHeaders = new HttpHeaders();
		entityHeaders.setContentType(MediaType
				.valueOf("application/json;UTF-8"));
		entityHeaders.setAccept(Collections
				.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<Article2> requestEntity = new HttpEntity<Article2>(article,
				entityHeaders);
		ResponseEntity<Article2> responseEntity = restTemplate.exchange(
				"http://localhost:700/testSpring/test/handle55?",
				HttpMethod.POST, requestEntity, Article2.class);

		Article2 responseArticle = responseEntity.getBody();
		Assert.assertNotNull(responseArticle);
		Assert.assertEquals("∞¢Àπ∂Ÿ", responseArticle.getTitle());
	}

	@Test
	public void testhandle52() throws IOException {
		RestTemplate restTemplate = buildRestTemplate();

		Article2 article = new Article2();
		article.setId(1);
		article.setTitle("∞°");

		HttpHeaders entityHeaders = new HttpHeaders();
		entityHeaders
				.setContentType(MediaType.valueOf("application/xml;UTF-8"));
		entityHeaders.setAccept(Collections
				.singletonList(MediaType.APPLICATION_XML));

		HttpEntity<Article2> requestEntity = new HttpEntity<Article2>(article,
				entityHeaders);
		ResponseEntity<Article2> responseEntity = restTemplate.exchange(
				"http://localhost:700/testSpring/test/handle55?",
				HttpMethod.POST, requestEntity, Article2.class);

		Article2 responseArticle = responseEntity.getBody();
		Assert.assertNotNull(responseArticle);
		Assert.assertEquals("∞°", responseArticle.getTitle());
	}

	private RestTemplate buildRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();

		XStreamMarshaller xmlMarshaller = new XStreamMarshaller();
		xmlMarshaller.setStreamDriver(new StaxDriver());
		xmlMarshaller.setAnnotatedClasses(new Class[] { Article2.class });
		MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();

		xmlConverter.setMarshaller(xmlMarshaller);
		xmlConverter.setUnmarshaller(xmlMarshaller);
		restTemplate.getMessageConverters().add(xmlConverter);

		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		restTemplate.getMessageConverters().add(jsonConverter);

		return restTemplate;
	}

	public static void main(String[] args) {
		testTest test = new testTest();
		try {
			// test.testhandle51();
			test.testhandle52();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
