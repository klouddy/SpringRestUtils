package com.klouddy.springRestUtils.restExceptions;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class ExceptionLogObject {

  public static final String EXCEPTION_LOG_OBJECT = "Exception Log Object: { ";
  public static final String EXCEPTION_CLASS_NAME = " Exception Class Name: ";
  public static final String EXCEPTION_MESSAGE = ", Exception Message: ";
  public static final String EXCEPTION_OBJECT_PATH = ", Path: ";
  public static final String HIGHLIGHT_CLASSES_IN_STACK_TRACE = ", Highlighting Stack Trace: ";
  public static final String FULL_STACK_TRACE = ", Full Stack Trace: ";
  public static final String EXCEPTION_LOG_OBJECT_END = " }";

  private String exceptionClassName;
  private String message;
  private List<StackTraceElement> highlightStackTrace;
  private StackTraceElement[] fullStackTrace;
  private String path;
  private String[] packagesToHighlight;
  private ExceptionLoggingConfig config;

  public ExceptionLogObject(Exception ex, ExceptionLoggingConfig config) {
    //set default if null
    if (config == null) {
      this.config = ExceptionLoggingConfig.DEFAULT();
    } else {
      this.config = config;
    }
    this.setupHighlightClasses(ex);
    this.exceptionClassName = ex.getClass().getCanonicalName();
    this.message = ex.getLocalizedMessage();
    this.fullStackTrace = ex.getStackTrace();
    this.path = "";
  }

  private void setupHighlightClasses(Exception ex) {
    if (this.config != null
        && ex != null
        && ex.getStackTrace() != null
        && this.config.isUseCustomLogging()
        && this.config.isPrintHighlightedClasses()) {

      this.highlightStackTrace =
          Arrays.stream(ex.getStackTrace())
              .filter(
                  st -> Arrays.stream(
                      this.config.getHighlightPackages()
                  ).anyMatch(p -> p.contains(st.getClassName()))
              ).collect(Collectors.toList());
    }
  }

  @Override
  public String toString() {
    String output = "";
    if (this.config.isUseCustomLogging()) {
      output += EXCEPTION_LOG_OBJECT;
      output += EXCEPTION_CLASS_NAME + this.exceptionClassName;
      output += EXCEPTION_MESSAGE + this.message;
      if (this.config.isPrintHighlightedClasses()) {
        output += HIGHLIGHT_CLASSES_IN_STACK_TRACE + toStringForStackList(
            this.highlightStackTrace
        );
      }
      if (this.config.isPrintStackTrace()) {
        output += FULL_STACK_TRACE + toStringForStackList(
            this.fullStackTrace
        );
      }
      output += EXCEPTION_LOG_OBJECT_END;
    }
    return output;
  }

  private String toStringForStackList(
      List<StackTraceElement> stackTraceElements) {
    StackTraceElement[] elements;
    elements = (StackTraceElement[]) stackTraceElements.toArray();
    return toStringForStackList(
        elements
    );
  }

  private String toStringForStackList(StackTraceElement[] stackTraceElements) {
    StringBuilder stackToString;
    if (stackTraceElements == null || stackTraceElements.length == 0) {
      stackToString = new StringBuilder("[]");
    } else {
      stackToString = new StringBuilder("[");
      for (StackTraceElement currClass : stackTraceElements) {
        stackToString.append(", ").append(currClass.toString());
      }
      stackToString.append("]");
    }
    return stackToString.toString();
  }
}
