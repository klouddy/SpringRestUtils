package com.gentex.comp.web.springRestUtils.restExceptions;

/**
 * Use this to return 401
 */
public class UnAuthorizedException extends RuntimeException {
  public UnAuthorizedException(String message) {
    super(message);
  }
}
