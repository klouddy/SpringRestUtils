package com.gentex.comp.web.springRestUtils.restExceptions;

/**
 * Generic Exception for use when there is an error in any service.
 * If you have specific services it might be good to extend this exception.
 */
public class ServiceException extends RuntimeException {
  public ServiceException(String message) {
    super(message);
  }
}
