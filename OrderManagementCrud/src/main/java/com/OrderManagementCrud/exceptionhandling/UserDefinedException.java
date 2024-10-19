package com.OrderManagementCrud.exceptionhandling;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class UserDefinedException extends Throwable
{
	private HttpStatus status;
	public UserDefinedException(String message, HttpStatus status) 
	{
		super(message);
		this.status = status;
	}
}
