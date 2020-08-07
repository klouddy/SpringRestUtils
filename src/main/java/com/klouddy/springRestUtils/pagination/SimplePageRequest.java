package com.klouddy.springRestUtils.pagination;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

@Data
public class SimplePageRequest {
  private Integer page;
  private Integer size;
  private String sortBy;
  private SortOrder sortDirection;

  public Integer getPage() {
    if (page == null) {
      return 0;
    } else {
      return page;
    }
  }

  public Integer getSize() {
    if (size == null) {
      return 20;
    } else {
      return size;
    }
  }

  public PageRequest getPageRequest() {

    if (StringUtils.hasLength(sortBy)) {
      if (SortOrder.DESCENDING.equals(sortDirection)) {
        return PageRequest.of(this.getPage(), this.getSize(),
            Sort.by(sortBy).descending());
      } else {
        return PageRequest.of(this.getPage(), this.getSize(),
            Sort.by(sortBy).ascending());
      }
    } else {
      return PageRequest.of(this.getPage(), this.getSize());
    }
  }
}
