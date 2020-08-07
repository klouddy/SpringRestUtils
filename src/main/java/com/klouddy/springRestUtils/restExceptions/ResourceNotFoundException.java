package com.klouddy.springRestUtils.restExceptions;

/**
 * Use this to return a 404.
 */
public class ResourceNotFoundException extends Exception {
  public ResourceNotFoundException(String responseBody) {
  }
}
