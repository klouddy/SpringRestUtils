package com.gentex.comp.web.springRestUtils.restExceptions;

/**
 * Use this to return a 404.
 */
public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String responseBody) {
  }
}
