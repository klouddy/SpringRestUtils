package com.gentex.comp.web.springRestUtils.restExceptions;

/**
 * Use this when a request body is not valid.
 */
public class MalformedRequestException extends RuntimeException {
  public MalformedRequestException(String message) {
    super(message);
  }
}
