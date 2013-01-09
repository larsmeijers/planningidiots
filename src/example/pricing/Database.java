package example.pricing;

import example.pricing.model.Order;

import java.util.Collection;

public interface Database {
	public Collection getCatalogItems();
	public Order getOrder(int orderNumber);
}