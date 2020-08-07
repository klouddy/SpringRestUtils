package com.klouddy.springRestUtils.apiKey;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Slf4j
public class ApiKeyInterceptor extends HandlerInterceptorAdapter {

  private List<String> apiKeys;
  private String header;
  private List<String> openPaths;


  public ApiKeyInterceptor(String header, List<String> apiKeys,
      List<String> openPaths) {
    this.apiKeys = apiKeys;
    this.header = header;
    this.openPaths = openPaths;
  }

  /**
   * Executed before actual handler is executed
   **/
  @Override
  public boolean preHandle(final HttpServletRequest request,
      final HttpServletResponse response,
      final Object handler) throws Exception {

    if (isOpenPath(request.getRequestURI())) {
      return true;
    } else if (hasValidApiKey(request)) {
      return true;
    } else {
      response.sendError(403);
      return false;
    }
  }

  public boolean hasValidApiKey(HttpServletRequest request) {
    log.info("hasValidApiKey.");
    String headerStr = request.getHeader(this.header);
    return (headerStr != null && this.apiKeys.contains(headerStr));
  }

  public boolean isOpenPath(String pathToTest) {
    log.info("testing is openPath for path: {}", pathToTest);
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    for (String openPath : this.openPaths) {
      if (antPathMatcher.match(openPath, pathToTest)) {
        return true;
      }
    }

    return false;
  }

}
