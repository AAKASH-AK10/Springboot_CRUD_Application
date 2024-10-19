package com.OrderManagementCrud.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.OrderManagementCrud.entity.OrderEntity;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	
	OrderEntity findByOrderId(String orderId);
	
	@Modifying
    @Query(value = "TRUNCATE TABLE order_table", nativeQuery = true)
    void truncateOrderTable();

	void deleteByOrderId(String orderId);
}
