package com.medical.medical.code.manager.exception.handler;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.medical.medical.code.manager.dto.response.Response;
import com.medical.medical.code.manager.exception.CSVFileParsingException;
import com.medical.medical.code.manager.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ValidationAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  @ResponseBody
  public Response<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
    return Response.<Object>builder()
        .messageKey(ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage())
        .status(HttpStatus.BAD_REQUEST)
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .timeStamp(now())
        .build();
  }

  @ExceptionHandler(value = {CSVFileParsingException.class})
  @ResponseStatus(BAD_REQUEST)
  @ResponseBody
  public Response<Object> handleServerException() {
    return Response.<Object>builder()
        .status(HttpStatus.BAD_REQUEST)
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .error("An error occurred during parsing of the csv file.")
        .timeStamp(now())
        .build();
  }

  @ExceptionHandler(value = {NotFoundException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Response<Object> handleNotFoundException() {
    return Response.<Object>builder()
        .error("Entity not found.")
        .status(NOT_FOUND)
        .timeStamp(now())
        .build();
  }
}
