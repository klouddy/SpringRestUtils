package com.klouddy.springRestUtils;

import com.klouddy.springRestUtils.restExceptions.ResourceNotFoundException;
import com.klouddy.springRestUtils.restExceptions.UnAuthorizedException;
import com.klouddy.springRestUtils.restExceptions.UpstreamApiException;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import org.apache.commons.io.IOUtils;

public class FeignErrorDecoder implements ErrorDecoder {
  @Override
  public Exception decode(String s, Response response) {
    String responseBody = null;
    try (final Reader reader = new InputStreamReader(
        response.body().asInputStream())) {
      responseBody = IOUtils.toString(reader);
    } catch (IOException e) {
      throw new UpstreamApiException(
          "Read body of response from upstream server.");
    }
    if (response.status() == 404) {
      return new ResourceNotFoundException(responseBody);
    } else if (response.status() == 403) {
      return new UnAuthorizedException(responseBody);
    } else {
      return new UpstreamApiException(responseBody);
    }
  }
}
