package nl.flusso.rest.example;

import org.springframework.stereotype.Controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/product")
@Controller
public class ProductService {

	@GET
	@Produces("application/json")
	public List<Product> get() {
		Product product = new Product("Test desc", 123d);
		Product product2 = new Product("Another desc", 456d);
		List<Product> products = new ArrayList<>();
		products.add(product);
		products.add(product2);
		return products;
	}

	@GET
	@Consumes("application/my.app-v2+json")
	@Produces("application/my.app-v2+json")
	public Map<String, Product> newGet1() {
		return createMap();
	}

	@GET
	@Consumes("application/my.app+json;v=2")
	@Produces("application/my.app+json;v=2")
	public Map<String, Product> newGet2() {
		return createMap();
	}

	@GET
	@Path("/v2")
	@Produces("application/json")
	public Map<String, Product> newGet3() {
		return createMap();
	}

	private Map<String, Product> createMap() {
		Product product = new Product("Test desc", 123d);
		Product product2 = new Product("Another desc", 456d);

		Map<String, Product> products = new HashMap<>();
		products.put(product.getDescription(), product);
		products.put(product2.getDescription(), product2);
		return products;
	}


}
