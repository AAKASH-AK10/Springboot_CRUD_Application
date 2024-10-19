package com.OrderManagementCrud.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass //@MappedSuperclass classes are not entities and Entities inherit from @MappedSuperclass classes.
@SuperBuilder	// @SuperBuilder is an annotation that generates a builder pattern for a class, including inheritance.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {
	
	@Column(name = "creation_date")
	private LocalDateTime creationDate;
	
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;
	
	@PrePersist
    public void onPrePersist() {
        this.setCreationDate (LocalDateTime.now());
        this.setUpdatedDate(LocalDateTime.now());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdatedDate(LocalDateTime.now());
    }
}
