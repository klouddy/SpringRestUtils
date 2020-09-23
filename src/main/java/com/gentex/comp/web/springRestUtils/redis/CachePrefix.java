package com.gentex.comp.web.springRestUtils.redis;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@Builder
public class CachePrefix {

  private String prefix;
  private Integer keysCount;
  private List<CacheKey> keys;

}
