package com.frankxulei.mongodb;
 
public class Order {
	private int id;
	
	private String title;

	private String name;

	private String address;

	private double price;

	public Order()
	{
		
	}
	
	public Order(int i,String title, String name,String address, int price) {
		this.id = i;
		this.name = name;
		this.title = title;
		this.address = address;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", 姓名=" + name + ", 订单=" + title + ", 地址=" + address
				+ ", 价格=" + price + "]";
	}

}
