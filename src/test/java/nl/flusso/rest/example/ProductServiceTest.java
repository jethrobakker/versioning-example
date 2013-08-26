package nl.flusso.rest.example;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONObject;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest {

	Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();

	MockHttpResponse response;

	@Before
	public void before(){
		dispatcher.getRegistry().addSingletonResource(new ProductService());
		response = new MockHttpResponse();
	}

	@Test
	public void testGet() throws Exception {
		MockHttpRequest request = MockHttpRequest.get("product/");

		dispatcher.invoke(request, response);

		String json = response.getContentAsString();

		assertJson(json, "$.[*].description", "Test desc", "Another desc");
		assertJson(json, "$.[*].price", 123.0d, 456d);
	}

	@Test
	public void testNewGet1() throws Exception {
		MockHttpRequest request = MockHttpRequest.get("product/");
		request.contentType("application/my.app-v2+json");

		dispatcher.invoke(request, response);
		String json = response.getContentAsString();

		assertsV2(json);
	}

	@Test
	public void testNewGet2() throws Exception {
		MockHttpRequest request = MockHttpRequest.get("product/");
		request.contentType("application/my.app+json;v=2");

		dispatcher.invoke(request, response);
		String json = response.getContentAsString();

		assertsV2(json);
	}

	@Test
	public void testNewGet3() throws Exception {
		MockHttpRequest request = MockHttpRequest.get("product/v2/");

		dispatcher.invoke(request, response);
		String json = response.getContentAsString();

		assertsV2(json);
	}

	private void assertsV2(String json) {
		JSONObject one = JsonPath.read(json, "$.['Test desc']");
		JSONObject two = JsonPath.read(json, "$.['Another desc']");

		assertEquals("{\"price\":123.0,\"description\":\"Test desc\"}", one.toJSONString());
		assertEquals("{\"price\":456.0,\"description\":\"Another desc\"}", two.toJSONString());
	}

	private static void assertJson(String json, String path, Object... expected) {
		Object actual = JsonPath.read(json, path);
		if (actual instanceof List)
			assertEquals(json, Arrays.asList(expected), actual);
		else
			assertEquals(json, expected[0], actual);
	}
}
