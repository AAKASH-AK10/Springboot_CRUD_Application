package com.OrderManagementCrud.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OrderManagementCrud.dto.OrderDTO;
import com.OrderManagementCrud.entity.OrderEntity;
import com.OrderManagementCrud.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/v1")  // Used to map web requests to specific controller methods.(class and method level)
@Slf4j	// Used to write logging
//@Controller // Handles HTTP requests and returns a view (e.g., JSP, HTML). Typically used for traditional web applications.
@RestController //Handles HTTP requests and returns data (e.g., JSON, XML). Combines @Controller and @ResponseBody annotations.
@RequiredArgsConstructor
public class OrderRestController {
	
	private final OrderService orderService;
	
	@GetMapping("/springboot")
	public String basics() 
	{
		return "Welcome to Springboot basics";
	}
	
	//To save the Order
	@PostMapping("/saveOrder")
	public void saveOrder(@RequestBody OrderDTO orderDTO) 
	{
		try 
		{
			if(orderDTO != null)
			{
				orderService.saveOrder(orderDTO);
				log.error("Order Saved Successfully :) :: {}" , HttpStatus.OK);
			}
		} 
		catch (Exception e) 
		{
			log.error("Error in saving the order :: {}", e);
			log.error("Error in saving the order :: {}", HttpStatus.NOT_FOUND);
		}
	}
	
	// To show all the Orders
	@SneakyThrows
	@PostMapping("/showOrders")
	public ResponseEntity<List<OrderEntity>> showOrders()
	{
		try 
		{
			List<OrderEntity> showOrders = orderService.showOrders();
			return new ResponseEntity<>(showOrders, HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			log.error("Error in fetching the orders :: {}", e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//To find the Order with Order Id
	@SneakyThrows
	@PostMapping("/showOrder")
	public ResponseEntity<OrderDTO> showOrder(@RequestParam String orderId)
	{
		ResponseEntity<OrderDTO> responseEntity = null;
		try 
		{
			OrderDTO showOrderDTO = orderService.showOrder(orderId);
			if (showOrderDTO !=  null) 
			{
				responseEntity = new ResponseEntity<>(showOrderDTO, HttpStatus.OK);
			}
			else 
			{
				responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} 
		catch (Exception e) 
		{
			log.error("Error in fetching the Order :: {}", e);
			responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
	
	// To truncate the entire table
	@DeleteMapping("/deleteAllOrders")
	public ResponseEntity<String> deleteOrders()
	{
		ResponseEntity<String> responseEntity= null;
		try 
		{
			orderService.truncateOrderTable();
			responseEntity = new ResponseEntity<>("All Orders deleted Successfully", HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			log.error("Error in deleting the all Orders :: {}", e);
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	// To delete the particular order
	@DeleteMapping("/deletebyorderid/{orderId}")
	public ResponseEntity<String>  deleteOrder(@PathVariable String orderId)
	{
		ResponseEntity<String> responseEntity = null;
		try 
		{
			orderService.deleteOrderById(orderId);
			responseEntity = new ResponseEntity<>("Order deleted Successfully", HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			log.error("Error in fetching the particular Order :: {}", e);
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	// To update the order
	// @PostMapping in update - POST requests are must used for creating new records. But we can update the columns based on the any column
	@SneakyThrows
	@PutMapping("/updateorder")
	public ResponseEntity<OrderEntity> updateOrder(@RequestBody OrderDTO orderDTO)
	{
		ResponseEntity<OrderEntity> responseEntity = null;
		try 
		{
			OrderEntity updateOrder = orderService.updateOrder(orderDTO);
			responseEntity = new ResponseEntity<>(updateOrder, HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			log.error("Error in updating the Order :: {}", e);
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	// To update the order partially means updating the specific column
	// Special method which i have used @PathVariable, @RequestBody, @RequestParam in a single method
	@PatchMapping("/partialUpdateOrder/{orderId}")
	public ResponseEntity<OrderEntity> partialUpdateOrder(@PathVariable("orderId") String OrderID, @RequestBody OrderDTO orderDTO, @RequestParam String status)
	{
		ResponseEntity<OrderEntity> responseEntity = null;
		try 
		{
			OrderEntity partialUpdateOrder = orderService.partialUpdateOrder(OrderID, orderDTO, status);
			responseEntity = new ResponseEntity<>(partialUpdateOrder, HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			log.error("Error in updating the partial Order :: {}", e);
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	// To update the specific column in the table(faster) but main drawback is we have to maintain the proper naming inside @RequestBody
	@PatchMapping("/partialUpdate/{id}")
	public ResponseEntity<OrderEntity> partialUpdateOrder(@PathVariable("id") String OrderID, @RequestBody Map<String, String> updateOrder)
	{
		ResponseEntity<OrderEntity> responseEntity = null;
		try 
		{
			OrderEntity partialUpdateOrder = orderService.partialUpdateOrder(OrderID, updateOrder);
			responseEntity = new ResponseEntity<>(partialUpdateOrder, HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			log.error("Error in updating the partial Order :: {}", e);
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
}
