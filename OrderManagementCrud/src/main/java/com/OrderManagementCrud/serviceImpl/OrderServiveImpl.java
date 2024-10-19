package com.OrderManagementCrud.serviceImpl;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.OrderManagementCrud.dto.OrderDTO;
import com.OrderManagementCrud.entity.OrderEntity;
import com.OrderManagementCrud.exceptionhandling.ResourceNotFoundException;
import com.OrderManagementCrud.exceptionhandling.UserDefinedException;
import com.OrderManagementCrud.repo.OrderRepository;
import com.OrderManagementCrud.service.OrderService;

import io.micrometer.common.lang.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiveImpl implements OrderService{
	
	public final OrderRepository orderRepository;
	
	// Method is for saving the orders
	@Override
	public void saveOrder(OrderDTO orderDTO) {
		
		try 
		{
			OrderEntity orderEntity = OrderEntity.builder()
					.productName(orderDTO.getProductName())
					.orderStatus(orderDTO.getOrderStatus())
					.quantity(Integer.parseInt(orderDTO.getQuantity()))
					.subtotal(Double.parseDouble(orderDTO.getSubtotal()))
					.gst(Integer.parseInt(orderDTO.getGst()))
					.amountPayable(Double.parseDouble(orderDTO.getAmountPayable()))
					.build();
			
			OrderEntity orderRow = orderRepository.save(orderEntity);
			String orderId = "order_".concat(orderRow.getId().toString());
			orderRow.setOrderId(orderId);
			orderRepository.save(orderRow);
		} 
		catch (Exception e) 
		{
			log.error("Exception while creating the order :: {}" , e);
		}
	}

	// Method is for show the orders
	@SneakyThrows
	@Override
	public List<OrderEntity> showOrders()  
	{
		try 
		{
			List<OrderEntity> allOrders = null;
			allOrders = orderRepository.findAll();
			return allOrders;
		} 
		catch (Exception e) 
		{
			log.error("Exception while fetching the orders :: {}" , e);
			throw new UserDefinedException("Error while fetching the order..!", HttpStatus.NO_CONTENT);
		}
	}
	
	// Method for to show the particular order
	@SneakyThrows
	@Override
	public OrderDTO showOrder(@NonNull String orderId) 
	{
		try 
		{
			OrderEntity orderRow = orderRepository.findByOrderId(orderId);
			OrderDTO orderDTO = null;
			if (orderRow != null) 
			{
				orderDTO = OrderDTO.builder()
						.id(orderRow.getId())
						.orderId(String.valueOf(orderRow.getOrderId()))
						.orderStatus(String.valueOf(orderRow.getOrderStatus()))
		                .productName(orderRow.getProductName())
		                .quantity(orderRow.getQuantity().toString())
		                .subtotal(orderRow.getSubtotal().toString())
		                .gst(orderRow.getGst().toString())
		                .amountPayable(orderRow.getAmountPayable().toString())
		                .build();
			}
			else 
			{
				throw new UserDefinedException("Show order row is empty..!", HttpStatus.NO_CONTENT);
			}
			return orderDTO;
		}
		catch (Exception e) 
		{
			log.error("Exception while fetching the orders :: {}" , e);
			throw new UserDefinedException("Error while fetching the order..!", HttpStatus.NO_CONTENT);
		}
	}

	// To delete all the Orders
	@SneakyThrows
	@Override
	public void truncateOrderTable() 
	{
		try 
		{
			orderRepository.truncateOrderTable();
		} 
		catch (Exception e) 
		{
			log.error("Error while deleting the orders :: {}" , e);
			throw new UserDefinedException("Error while fetching the order..!", HttpStatus.NO_CONTENT);
		}
	}
	
	// To delete order by Id
	@Override
	public void deleteOrderById(@NonNull String orderId) 
	{
		try 
		{
			orderRepository.deleteByOrderId(orderId);
		} 
		catch (Exception e) 
		{
			log.error("Error while deleting the orders :: {}" , e);
			throw new IllegalArgumentException("Error while fetching the order..!");
		}
	}
	
	// To update the record using PostMapping
	@SneakyThrows
	@Override
	public OrderEntity updateOrder(OrderDTO orderDTO) 
	{
		try 
		{
		    OrderEntity updatedOrder = null;
		    Optional<OrderEntity> orderRow = orderRepository.findById(orderDTO.getId());

		    if (orderRow.isPresent()) {
		        OrderEntity orderEntity = orderRow.get();

		        // Update fields directly on the fetched entity
		        orderEntity.setOrderId(orderDTO.getOrderId() == null ? orderEntity.getOrderId() : orderDTO.getOrderId());
		        orderEntity.setProductName(orderDTO.getProductName() == null ? orderEntity.getProductName() : orderDTO.getProductName());
		        orderEntity.setQuantity(orderDTO.getQuantity() == null ? orderEntity.getQuantity() : Integer.parseInt(orderDTO.getQuantity()));
		        orderEntity.setOrderStatus(orderDTO.getOrderStatus() == null ? orderEntity.getOrderStatus() : orderDTO.getOrderStatus());
		        orderEntity.setSubtotal(orderDTO.getSubtotal() == null ? orderEntity.getSubtotal() : Double.parseDouble(orderDTO.getSubtotal()));
		        orderEntity.setGst(orderDTO.getGst() == null ? orderEntity.getGst() : Integer.parseInt(orderDTO.getGst()));
		        orderEntity.setAmountPayable(orderDTO.getAmountPayable() == null ? orderEntity.getAmountPayable() : Double.parseDouble(orderDTO.getAmountPayable()));

		        // Save the updated entity (with its original ID)
		        updatedOrder = orderRepository.save(orderEntity);
		    }
		    else 
		    {
		    	throw new UserDefinedException("Update order row is empty..!", HttpStatus.NO_CONTENT);
			}

		    return updatedOrder;
		}
		catch (Exception e) 
		{
			log.error("Exception while updating the orders :: {}" , e);
			throw new UserDefinedException("Error while updating the order..!", HttpStatus.NO_CONTENT);
		}
	}
	
	// To update the order partially but it will take same time of UpdateOrder method.
	// Check the below another partial update method for faster execution
	@SneakyThrows
	@Override
	public OrderEntity partialUpdateOrder(@NonNull String orderID,@NonNull OrderDTO orderDTO, @NonNull String status) 
	{
		try 
		{
			OrderEntity updatedPartialOrder = null;
			String[] orderIdArr = orderID.split("_");
			Long orderId = Long.valueOf(orderIdArr[1]);
			OrderEntity orderEntity = orderRepository.findById(orderId)
			        .orElseThrow(() -> new ResourceNotFoundException("Order id is not present while update partial order..!"));

		    orderEntity.setOrderId(orderDTO.getOrderId() == null ? orderEntity.getOrderId() : orderDTO.getOrderId());
		    orderEntity.setProductName(orderDTO.getProductName() == null ? orderEntity.getProductName() : orderDTO.getProductName());
		    orderEntity.setQuantity(orderDTO.getQuantity() == null ? orderEntity.getQuantity() : Integer.parseInt(orderDTO.getQuantity()));
		    orderEntity.setOrderStatus(status);
		    orderEntity.setSubtotal(orderDTO.getSubtotal() == null ? orderEntity.getSubtotal() : Double.parseDouble(orderDTO.getSubtotal()));
		    orderEntity.setGst(orderDTO.getGst() == null ? orderEntity.getGst() : Integer.parseInt(orderDTO.getGst()));
		    orderEntity.setAmountPayable(orderDTO.getAmountPayable() == null ? orderEntity.getAmountPayable() : Double.parseDouble(orderDTO.getAmountPayable()));

			updatedPartialOrder = orderRepository.save(orderEntity);
			
			return updatedPartialOrder;
		} 
		catch (Exception e) 
		{
			log.error("Exception while updating the partial orders :: {}" , e);
			throw new UserDefinedException("Error while updating the partial order..!", HttpStatus.NO_CONTENT);
		}
	}
	
	// Not Recommended
	@SneakyThrows
	@Override
	public OrderEntity partialUpdateOrder(@NonNull String orderID,@NonNull Map<String, String> updateOrder) 
	{
		try 
		{
			if (updateOrder.isEmpty()) 
			{
				throw new NoSuchElementException("Update Order is Empty..!");
			}
			
			OrderEntity orderEntity = orderRepository.findById(Long.valueOf(orderID))
			        .orElseThrow(() -> new ResourceNotFoundException("Order id is not present while update partial order..!"));
			
			OrderEntity updatedOrder = updateARow(orderEntity, updateOrder);
			return updatedOrder;
		} 
		catch (Exception e) 
		{
			log.error("Exception while updating the partial order using Map :: {}" , e);
			throw new UserDefinedException("Error while updating the partial order using Map..!", HttpStatus.NO_CONTENT);
		}
	}

	private OrderEntity updateARow(@NonNull OrderEntity orderEntity, Map<String, String> updateOrder) 
	{
			updateOrder.forEach((key, value) -> {
					switch (key) 
					{
						case "orderId": 
						{
							orderEntity.setOrderId(value);
							break;
						}
						case "productName": 
						{
							orderEntity.setProductName(value);
							break;
						}
						case "orderStatus":
						{
							orderEntity.setOrderStatus(value);
							break;
						}
						case "subtotal": 
						{
							orderEntity.setSubtotal(Double.valueOf(value));
							break;
						}
						case "gst": 
						{
							orderEntity.setGst(Integer.valueOf(value));
							break;
						}
						default:
							throw new IllegalArgumentException("Unexpected value: " + key);
					}
				});
			OrderEntity updatedOrder = orderRepository.save(orderEntity);
			return updatedOrder;
		}
		
	}
