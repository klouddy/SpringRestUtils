package com.gentex.comp.web.springRestUtils.pagination;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class SimplePageResponse<T> {
  private Integer pageNumber;
  private Integer pageSize;
  private Integer totalPages;
  private Long totalSize;
  private Boolean hasMore;
  private List<T> items;

  public void setPageable(Page<T> pageWithItems) {
    if (pageWithItems != null) {
      setStats(pageWithItems);
      this.items = pageWithItems.getContent();
    }
  }

  private void setStats(Page<?> pageWithItems) {
    this.pageNumber = pageWithItems.getNumber();
    this.totalSize = pageWithItems.getTotalElements();
    this.pageSize = pageWithItems.getSize();
    this.totalPages = pageWithItems.getTotalPages();
    this.hasMore = (pageWithItems.getNumber()
        < pageWithItems.getTotalPages() - 1);
  }

  public void setStatsFromPageable(Page<?> pageableResponse) {
    if (pageableResponse != null) {
      this.setStats(pageableResponse);
    }
  }

  private void setStatsFromPageRequest(SimplePageRequest pageRequest) {
    if (pageRequest != null) {
      this.setPageNumber(pageRequest.getPage());
      this.setPageSize(pageRequest.getSize());
    } else {
      this.setPageNumber(1);
      this.setPageSize(20);
    }
  }

  public static <T> SimplePageResponse<T> empty(Page<T> page) {
    SimplePageResponse<T> simplePageResponse = new SimplePageResponse<>();
    simplePageResponse.setItems(new ArrayList<>());
    simplePageResponse.setStatsFromPageable(page);
    return simplePageResponse;
  }


  public static <T> SimplePageResponse<T> fromList(
      SimplePageRequest pageRequest, List<T> fullList) {
    if (pageRequest == null) {
      pageRequest = new SimplePageRequest();
    }
    SimplePageResponse<T> simplePageResponse = new SimplePageResponse<>();
    simplePageResponse.setStatsFromPageRequest(pageRequest);
    if (fullList == null || fullList.isEmpty()) {
      simplePageResponse.setItems(new ArrayList<>());
      simplePageResponse.setTotalSize(0L);
      simplePageResponse.setTotalPages(0);
      simplePageResponse.setHasMore(false);
      return simplePageResponse;
    } else {
      simplePageResponse.setTotalSize((long) fullList.size());

      Double totalSizeDbl = (double) fullList.size();
      Double pageSizeDbl = Double.valueOf(pageRequest.getSize());
      double totalPages = Math.ceil(totalSizeDbl / pageSizeDbl);
      simplePageResponse.setTotalPages((int) totalPages);
      int start =
          pageRequest.getSize() * (pageRequest.getPage() + 1)
              - pageRequest.getSize();

      if (start + pageRequest.getSize() < fullList.size()) {
        List<T> items = fullList.subList(start, start + pageRequest.getSize());
        simplePageResponse.setHasMore(true);
        simplePageResponse.setItems(items);
      } else if (start >= fullList.size()) {
        simplePageResponse.setHasMore(false);
        simplePageResponse.setItems(new ArrayList<>());
      } else if (start == fullList.size() - 1) {
        List<T> items = new ArrayList<>();
        items.add(fullList.get(fullList.size() - 1));
        simplePageResponse.setHasMore(false);
        simplePageResponse.setItems(items);
      } else {
        List<T> items = fullList.subList(start, fullList.size() - 1);
        simplePageResponse.setHasMore(false);
        simplePageResponse.setItems(items);
      }
    }

    return simplePageResponse;
  }
}
