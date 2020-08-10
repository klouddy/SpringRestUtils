# Spring Rest Utils

A simple set of utilities to use for Spring Boot Apis.  

**Currently not supporting webflux**

## Install

Add to your maven pom.xml file:

```xml
<repository>
  <id>github</id>
  <name>klouddy github</name>
  <url>https://maven.pkg.github.com/klouddy/SpringRestUtils</url>
</repository>
```

To your dependencies:

```xml
<dependency>
  <groupId>com.kloudd.SpringRestUtils</groupId>
  <artifactId>springrestutils</artifactId>
  <version>0.0.1-7</version>
</dependency>
```

## Global Exception Handling

This includes a set of exceptions classes that the programmer can throw
at any point during a web request, and the Spring MVC will handle the exception
and return correct status code and the message in a standard format.

#### Example

Register the global exception handler by declaring a bean that returns
a instance of `GlobalRestControllerExceptionHandler`

```java
@Bean
protected GlobalRestControllerExceptionHandler globalRestControllerExceptionHandler(){
    return new GlobalRestControllerExceptionHandler();
}
```

All Responses will contain a body of `RestErrorResponse` the localized message 
in the `Exception` class will be added to the `RestErrorResponse.message` 
value.  If there is not a message in the `Exception` class then there will
be a default message.


## Api Key Authentication

This will allow you to add an interceptor to all your controllers. It will check to make sure the 
request has a valid api key.

You will need to provide the interceptor with a header key, list of valid api keys and any paths that should be fully open.

So for instance if you had a rest api where there was a /open and /open/getStuff endpoints that you wanted to be open, but all other items should be secured behind an apikey.

```java
    
    String header = "API_KEY"; // this is the key of the header that the inerceptor will look for.
    
    // this will be the acceptable api keys.
    List<String> validApiKeys = Arrays.asList(new String[] {"apikey1", "apikey2"});
    
    //this will be a list of antPatterns of paths that are open.
    // usually a good idea to allow /error/** to be open.
    List<String> openPaths = Arrays.asList(new String[] {"/open/**", "/error/**"});
    
    new ApiKeyInterceptor("API_HEADER_KEY", apiKeys, openPaths);
```

Now you should at this to your `WebMvcConfigurere` configuration class:

```java


@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(final InterceptorRegistry registry){
        String header = "API_KEY"; // this is the key of the header that the inerceptor will look for.
            
        // this will be the acceptable api keys.
        List<String> validApiKeys = Arrays.asList(new String[] {"apikey1", "apikey2"});
        
        //this will be a list of antPatterns of paths that are open.
        // usually a good idea to allow /error/** to be open.
        List<String> openPaths = Arrays.asList(new String[] {"/open/**", "/error/**"});
        
        registry.addInterceptor(new ApiKeyInterceptor("API_HEADER_KEY", apiKeys, openPaths););
    }
}
```
