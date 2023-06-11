package com.medical.medical.code.manager.dto.response;


import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(NON_DEFAULT)
public class Response<T>{

	private LocalDateTime timeStamp;
	private T data;
	private int statusCode;
	private HttpStatus status;
	private String message;
	private String developerMessage;
	private T messageKey;
	private T error;
	private int pageSize;
	private int currentPage;
	private long totalItems;
	private int totalPages;

}
