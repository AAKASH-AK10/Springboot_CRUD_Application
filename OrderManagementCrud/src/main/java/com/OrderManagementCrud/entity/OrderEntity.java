package com.OrderManagementCrud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "order_table")
@NoArgsConstructor
@AllArgsConstructor
@Data // Equivalent to @Getter, @Setter, @toString, @EqualsAndHashCode
@SuperBuilder // This is mandatory to create builders inside this class variables
public class OrderEntity extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name= "order_id")
	private String orderId;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "order_status")
	private String orderStatus;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "subtotal")
	private Double subtotal;
	
	@Column(name = "gst")
	private Integer gst;
	
	@Column(name = "amount_payable")
	private Double amountPayable;
}
