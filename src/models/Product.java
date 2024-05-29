package models;

public class Product {
	
	private String product_name;
	private int product_price;
	private int quantity;
	private int total_price;
	
	public Product(String product_name, int product_price, int quantity, int total_price) {
		this.product_name = product_name;
		this.product_price = product_price;
		this.quantity = quantity;
		this.total_price = total_price;
	}
	
	public Product(String product_name, int product_price) {
		this.product_name = product_name;
		this.product_price = product_price;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getProduct_price() {
		return product_price;
	}

	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTotal_price() {
		return total_price;
	}

	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}
	
	
	
	

}
