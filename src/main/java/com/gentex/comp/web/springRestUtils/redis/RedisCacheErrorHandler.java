package com.gentex.comp.web.springRestUtils.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

@Slf4j
public class RedisCacheErrorHandler implements CacheErrorHandler {

  @Override
  public void handleCacheGetError(RuntimeException e, Cache cache, Object o) {
    log.error("Redis Cache Get Error {}, {}, {}", e, cache, o);
  }

  @Override
  public void handleCachePutError(RuntimeException e, Cache cache, Object o, Object o1) {
    log.error("Redis Cache Put Error {}, {}, {}", e, cache, o);
  }

  @Override
  public void handleCacheEvictError(RuntimeException e, Cache cache, Object o) {
    log.error("Redis Cache Evict Error {}, {}, {}", e, cache, o);
  }

  @Override
  public void handleCacheClearError(RuntimeException e, Cache cache) {
    log.error("Redis Cache Clear Error {}, {}", e, cache);
  }
}
