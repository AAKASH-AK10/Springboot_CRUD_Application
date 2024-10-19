package com.OrderManagementCrud.service;

import java.util.List;
import java.util.Map;

import com.OrderManagementCrud.dto.OrderDTO;
import com.OrderManagementCrud.entity.OrderEntity;

public interface OrderService {

	void saveOrder(OrderDTO orderDTO);

	List<OrderEntity> showOrders();

	OrderDTO showOrder(String orderId);

	void truncateOrderTable();

	void deleteOrderById(String orderId);

	OrderEntity updateOrder(OrderDTO orderDTO);
	
	OrderEntity partialUpdateOrder(String orderID, OrderDTO orderDTO, String status);

	OrderEntity partialUpdateOrder(String orderID, Map<String, String> updateOrder);

}
