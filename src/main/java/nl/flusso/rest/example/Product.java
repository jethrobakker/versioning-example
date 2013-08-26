package nl.flusso.rest.example;

public class Product {

	private String description;

	private Double price;

	public Product(String description, Double price) {
		this.description = description;
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public Double getPrice() {
		return price;
	}
}
