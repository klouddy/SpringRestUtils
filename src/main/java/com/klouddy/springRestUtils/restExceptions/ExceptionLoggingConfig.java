package com.klouddy.springRestUtils.restExceptions;

import lombok.Builder;
import lombok.Data;

/**
 * Configuration object for printing logs for exception.
 */
@Data
@Builder
public class ExceptionLoggingConfig {

  // If you have specific classes to highlight then set this to true
  private boolean printHighlightedClasses;
  // if you want the entire stack trace printed
  private boolean printStackTrace;
  //packages to add to the highlighted stack trace.  This is useful when
  // you need to see what happened relative to your code.
  private String[] highlightPackages;
  //if this is true then this config will be used. Otherwise it will be ignored.
  private boolean useCustomLogging;

  /**
   * For this to be true you must also have highlightPackages defined.
   */
  public boolean isPrintHighlightedClasses() {
    return printHighlightedClasses && highlightPackages != null
        && highlightPackages.length > 0;
  }

  public static ExceptionLoggingConfig DEFAULT() {
    return ExceptionLoggingConfig
        .builder()
        .useCustomLogging(true)
        .printHighlightedClasses(false)
        .printStackTrace(true)
        .build();
  }
}
