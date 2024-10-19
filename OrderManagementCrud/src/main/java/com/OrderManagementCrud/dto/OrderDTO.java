package com.OrderManagementCrud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDTO {
	private Long id;
	private String orderId;
	private String orderStatus;
	private String productName;
	private String quantity;
	private String subtotal;
	private String gst;
	private String amountPayable;
}
