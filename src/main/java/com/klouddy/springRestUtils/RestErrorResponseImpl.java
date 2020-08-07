package com.klouddy.springRestUtils;

import com.klouddy.springRestUtils.restExceptions.MalformedRequestException;
import com.klouddy.springRestUtils.restExceptions.ResourceNotFoundException;
import com.klouddy.springRestUtils.restExceptions.UnAuthorizedException;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Data
public class RestErrorResponseImpl implements RestErrorResponse {
  private String error;
  private String type;
  private LocalDateTime timeStamp;
  private String message;
  private String path;

  public RestErrorResponseImpl(String error, String type) {
    this.error = error;
    this.type = type;
    this.timeStamp = LocalDateTime.now();
    this.message = "";
    this.path = "";
  }

  public static RestErrorResponse fromException(
      MethodArgumentNotValidException exception) {
    if (exception != null && StringUtils.hasLength(
        exception.getBindingResult().toString())) {
      StringBuilder message = new StringBuilder("Validation Failed: ");
      int i = 0;
      for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
        if (i > 0) {
          message.append("; ");
        }
        message.append("Field ").append(fieldError.getField()).append(
            " ").append(fieldError.getDefaultMessage());
        i++;
      }
      return new RestErrorResponseImpl(message.toString(),
          MALFORMED_REQUEST_ERROR_TYPE);
    } else {
      return new RestErrorResponseImpl(MALFORMED_REQUEST_ERROR_TYPE,
          MALFORMED_REQUEST_ERROR_TYPE);
    }
  }

  /**
   * Generate respnse body for resource not found error.
   */
  public static RestErrorResponse fromException(
      ResourceNotFoundException exception) {
    if (exception != null && StringUtils.hasLength(
        exception.getLocalizedMessage())) {
      return new RestErrorResponseImpl(exception.getLocalizedMessage(),
          NOT_FOUND_ERROR_TYPE);

    } else {
      return new RestErrorResponseImpl(NOT_FOUND_ERROR_TYPE,
          NOT_FOUND_ERROR_TYPE);
    }
  }

  /**
   * Generate Response body for mailformed request.
   */
  public static RestErrorResponse fromException(
      MalformedRequestException exception) {
    if (exception != null && StringUtils.hasLength(
        exception.getLocalizedMessage())) {
      return new RestErrorResponseImpl(exception.getLocalizedMessage(),
          MALFORMED_REQUEST_ERROR_TYPE);

    } else {
      return new RestErrorResponseImpl(MALFORMED_REQUEST_ERROR_TYPE,
          MALFORMED_REQUEST_ERROR_TYPE);
    }
  }

  /**
   * Generate Response body for unauthorized exception
   */
  public static RestErrorResponse fromException(
      UnAuthorizedException exception) {
    if (exception != null && StringUtils.hasLength(
        exception.getLocalizedMessage())) {
      return new RestErrorResponseImpl(exception.getLocalizedMessage(),
          UNAUTHORIZED_ERROR_TYPE);
    } else {
      return new RestErrorResponseImpl(UNAUTHORIZED_ERROR_TYPE,
          UNAUTHORIZED_ERROR_TYPE);
    }
  }

  public static RestErrorResponse fromException(BindException exception) {
    if (exception != null && StringUtils.hasLength(
        exception.getBindingResult().toString())) {
      StringBuilder message = new StringBuilder("Validation Failed: ");
      int i = 0;
      for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
        if (i > 0) {
          message.append("; ");
        }
        message.append("Field ").append(fieldError.getField()).append(
            " ").append(fieldError.getDefaultMessage());
        i++;
      }
      return new RestErrorResponseImpl(message.toString(),
          MALFORMED_REQUEST_ERROR_TYPE);
    } else {
      return new RestErrorResponseImpl(MALFORMED_REQUEST_ERROR_TYPE,
          MALFORMED_REQUEST_ERROR_TYPE);
    }
  }
}
