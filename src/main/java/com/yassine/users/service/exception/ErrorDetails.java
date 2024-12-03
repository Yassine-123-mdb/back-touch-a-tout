package com.yassine.users.service.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor 
public class ErrorDetails {
	private LocalDateTime timestamp;
	 private String message;
	 private String path;
	 private String errorCode;


}