package com.klouddy.springRestUtils.redis;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@Builder
public class CacheKey {

  private String keyName;
  private Long ttl;
  private Object value;

}
