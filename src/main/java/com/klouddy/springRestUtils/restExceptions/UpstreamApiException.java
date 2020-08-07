package com.klouddy.springRestUtils.restExceptions;

/**
 * Use this when an error occurs using another api.
 * For instance if you service is calling a twitter api and you receive an
 * error.  Then throw this exception with the correct information.
 */
public class UpstreamApiException extends RuntimeException {
  public UpstreamApiException(String message) {
    super(message);
  }
}
