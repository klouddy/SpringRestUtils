package com.klouddy.springRestUtils;

import java.time.LocalDateTime;

public interface RestErrorResponse {
  public static final String NOT_FOUND_ERROR_TYPE = "Resource Not Found";
  public static final String MALFORMED_REQUEST_ERROR_TYPE = "Malformed Request";
  public static final String UNAUTHORIZED_ERROR_TYPE = "Unauthorized";
  public static final String SQL_EXCEPTION = "Sql Exception";
  public static final String SERVICE_EXCEPTION = "Service Exception";
  public static final String UPSTREAM_API_EXCEPTION = "Upstream Api Exception";

  String getError();

  String getType();

  LocalDateTime getTimeStamp();

  String getMessage();

  String getPath();

}
