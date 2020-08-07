package com.klouddy.springRestUtils.redis;

import java.util.List;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class CacheActuatorEndpoint {

  private StringRedisTemplate stringRedisTemplate;
  private CacheDetailsService cacheDetails;

  public CacheActuatorEndpoint(StringRedisTemplate stringRedisTemplate) {
    this.stringRedisTemplate = stringRedisTemplate;
    this.cacheDetails = new CacheDetailsService(this.stringRedisTemplate);
  }

  @GetMapping({"", "/"})
  public List<CachePrefix> cachePrefixes() {
    return this.cacheDetails.getPrefixes();
  }

  @GetMapping("/{prefix}")
  public CachePrefix cachePrefix(@PathVariable String prefix) {
    return this.cacheDetails.getPrefixWithKeys(prefix);
  }

}
