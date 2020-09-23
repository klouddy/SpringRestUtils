package com.gentex.comp.web.springRestUtils.restExceptions;

import com.gentex.comp.web.springRestUtils.RestErrorResponse;
import com.gentex.comp.web.springRestUtils.RestErrorResponseImpl;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalRestControllerExceptionHandler extends ResponseEntityExceptionHandler {

  ExceptionLoggingConfig loggingConfig;

  public GlobalRestControllerExceptionHandler(
      ExceptionLoggingConfig loggingConfig) {
    this.loggingConfig = loggingConfig;
  }

  public GlobalRestControllerExceptionHandler() {
    this.loggingConfig = ExceptionLoggingConfig.DEFAULT();
  }

  /**
   * Handles Resource Not Found Exception.
   */
  @ExceptionHandler(value = {ResourceNotFoundException.class})
  protected ResponseEntity<RestErrorResponse> handleNotFound(
      RuntimeException ex, WebRequest req) {

    ExceptionLogObject exceptionLogObject = new ExceptionLogObject(ex,
        this.loggingConfig);
    exceptionLogObject.setPath(reqPath(req));
    log.warn("Resource not found {}", exceptionLogObject);

    RestErrorResponseImpl restErrorResponse = new RestErrorResponseImpl(
        ex.getLocalizedMessage(),
        RestErrorResponse.NOT_FOUND_ERROR_TYPE
    );
    restErrorResponse.setPath(reqPath(req));

    return new ResponseEntity<>(restErrorResponse,
        HttpStatus.NOT_FOUND);
  }

  /**
   * Handles Malformed Request exception.
   */
  @ExceptionHandler(value = {MalformedRequestException.class,
      IllegalArgumentException.class})
  protected ResponseEntity<RestErrorResponse> handleMalformedRequest(
      RuntimeException ex,
      WebRequest req) {

    ExceptionLogObject exceptionLogObject = new ExceptionLogObject(ex,
        this.loggingConfig);
    exceptionLogObject.setPath(reqPath(req));
    log.info("Malformed Request: {}", exceptionLogObject);

    RestErrorResponseImpl restErrorResponse = new RestErrorResponseImpl(
        ex.getLocalizedMessage(),
        RestErrorResponse.MALFORMED_REQUEST_ERROR_TYPE
    );
    restErrorResponse.setPath(reqPath(req));

    return new ResponseEntity<>(restErrorResponse, HttpStatus.BAD_REQUEST);
  }


  /**
   * Handles Unauthorized Exception.
   */
  @ExceptionHandler(value = {UnAuthorizedException.class})
  protected ResponseEntity<RestErrorResponse> handleUnauthorized(
      RuntimeException ex,
      WebRequest req) {

    log.warn("Unauthorized access for {}", reqPath(req));
    RestErrorResponseImpl restErrorResponse = new RestErrorResponseImpl(
        RestErrorResponse.UNAUTHORIZED_ERROR_TYPE,
        RestErrorResponse.UNAUTHORIZED_ERROR_TYPE
    );
    restErrorResponse.setPath(reqPath(req));
    return new ResponseEntity<>(restErrorResponse,
        HttpStatus.FORBIDDEN);
  }

  /**
   * Override of handling MethodArgumentNotValidException.  This exception is used when using @valid
   * for a body object or a query parameters.
   *
   * @param ex -- exception
   * @param headers -- current headers
   * @param status -- status of request
   * @param request -- request
   * @return New Response entity.
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    ExceptionLogObject exceptionLogObject = new ExceptionLogObject(ex,
        loggingConfig);
    exceptionLogObject.setPath(reqPath(request));

    log.warn("Invalid Arguments: {}", exceptionLogObject);

    return new ResponseEntity<>(
        RestErrorResponseImpl.fromException(ex),
        HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleBindException(BindException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    ExceptionLogObject exceptionLogObject = new ExceptionLogObject(ex,
        this.loggingConfig);
    exceptionLogObject.setPath(reqPath(request));
    log.warn("Invalid Arguments: {}", exceptionLogObject);
    return new ResponseEntity<>(
        RestErrorResponseImpl.fromException(ex),
        HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles any SQLException Thrown.
   *
   * @param ex -- the sql exception.
   * @param req -- request that causes the exception
   * @return New Response entity with our custom Error Object
   */
  @ExceptionHandler(value = {SQLException.class})
  protected ResponseEntity<RestErrorResponse> handleSqlException(
      SQLException ex,
      WebRequest req) {

    ExceptionLogObject exceptionLogObject = new ExceptionLogObject(ex,
        this.loggingConfig);
    exceptionLogObject.setPath(reqPath(req));
    log.error("Exception: {}", exceptionLogObject);

    RestErrorResponse restErrorResponse = new RestErrorResponseImpl(
        ex.getLocalizedMessage(),
        RestErrorResponse.SQL_EXCEPTION);
    return new ResponseEntity<>(restErrorResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handle upstream api exception.
   *
   * @param ex -- Upstream expcetion that is thrown.
   * @param req -- request that was made.
   * @return Built response.
   */
  @ExceptionHandler(value = {UpstreamApiException.class})
  protected ResponseEntity<RestErrorResponse> handleUpstreamServiceError(
      UpstreamApiException ex,
      WebRequest req) {
    ExceptionLogObject exceptionLogObject = new ExceptionLogObject(ex,
        this.loggingConfig);
    exceptionLogObject.setPath(reqPath(req));
    log.error("UpstreamException: {}", exceptionLogObject);

    RestErrorResponse restErrorResponse = new RestErrorResponseImpl(
        ex.getLocalizedMessage(),
        RestErrorResponse.UPSTREAM_API_EXCEPTION);
    return new ResponseEntity<>(restErrorResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Hand Service Exception
   *
   * @param ex -- Service Exception that is thrown
   * @param req -- request that triggered the exception
   * @return Built error response.
   */
  @ExceptionHandler(value = {ServiceException.class})
  protected ResponseEntity<RestErrorResponse> handleServiceException(
      ServiceException ex,
      WebRequest req) {
    ExceptionLogObject exceptionLogObject = new ExceptionLogObject(ex,
        this.loggingConfig);
    exceptionLogObject.setPath(reqPath(req));
    log.error("Service Exception: {}", exceptionLogObject);

    RestErrorResponse restErrorResponse = new RestErrorResponseImpl(
        ex.getLocalizedMessage(),
        RestErrorResponse.SERVICE_EXCEPTION);
    return new ResponseEntity<>(restErrorResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private String reqPath(WebRequest webRequest) {
    return ((ServletWebRequest) webRequest).getRequest().getRequestURI();
  }
}
