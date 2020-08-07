package com.klouddy.springRestUtils.redis;

import com.klouddy.springRestUtils.restExceptions.ResourceNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

public class CacheDetailsService {

  private StringRedisTemplate stringRedisTemplate;

  public CacheDetailsService(StringRedisTemplate stringRedisTemplate) {
    this.stringRedisTemplate = stringRedisTemplate;
  }


  public List<CachePrefix> getPrefixes() {

    return Objects.requireNonNull(stringRedisTemplate.keys("*::*"))
        .stream()
        .map(k -> k.substring(0, k.indexOf("::")))
        .distinct()
        .map(k -> CachePrefix.builder()
            .prefix(k)
            .keys(Collections.emptyList())
            .keysCount(
                Objects.requireNonNull(
                    stringRedisTemplate.keys(k + "*")).size())
            .build()).collect(Collectors.toList());
  }


  public CachePrefix getPrefixWithKeys(String prefix) {
    checkPrefix(prefix);
    Set<String> keys = stringRedisTemplate.keys(prefix + "*");
    prefixExists(keys);
    List<CacheKey> keyList = keys
        .stream()
        .map(k -> CacheKey.builder()
            .keyName(k)
            .ttl(stringRedisTemplate.getExpire(k))
            .build())
        .collect(Collectors.toList());

    return CachePrefix.builder()
        .prefix(prefix)
        .keysCount(keyList.size())
        .keys(keyList)
        .build();
  }

  private void prefixExists(Set<String> keyList) {
    if (keyList == null || keyList.isEmpty()) {
      throw new ResourceNotFoundException("Prefix not found.");
    }
  }

  private void checkPrefix(String prefix) {
    if (!StringUtils.hasLength(prefix)) {
      throw new IllegalArgumentException("Prefix cannot be null");
    }
  }
}
