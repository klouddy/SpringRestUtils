package com.kloudd.springRestUtils.pagination;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.klouddy.springRestUtils.pagination.SimplePageRequest;
import com.klouddy.springRestUtils.pagination.SimplePageResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class SimplePageResponseTest {

  @Test
  public void fromList_whenLastPage() {
    SimplePageRequest simplePageRequest = new SimplePageRequest();
    simplePageRequest.setSize(2);
    simplePageRequest.setPage(3);

    List<String> items = Arrays.asList("1", "2", "3", "4", "5", "6", "7");
    SimplePageResponse<String> response = SimplePageResponse.fromList(
        simplePageRequest, items);

    assertThat(response.getHasMore(), is(equalTo(false)));
    assertThat(response.getItems().size(), is(equalTo(1)));
    assertThat(response.getItems().get(0), is(equalTo("7")));
    assertThat(response.getPageNumber(), is(equalTo(3)));
    assertThat(response.getTotalPages(), is(equalTo(4)));
    assertThat(response.getTotalSize(),
        is(equalTo(Long.valueOf(items.size()))));
  }

  @Test
  public void fromList_whenPageIsTooLarge() {
    SimplePageRequest simplePageRequest = new SimplePageRequest();
    simplePageRequest.setSize(1);
    simplePageRequest.setPage(3);

    List<String> items = Arrays.asList("1", "2", "3");
    SimplePageResponse<String> response = SimplePageResponse.fromList(
        simplePageRequest, items);

    assertThat(response.getHasMore(), is(equalTo(false)));
    assertThat(response.getItems().isEmpty(), is(equalTo(true)));
    assertThat(response.getPageNumber(), is(equalTo(3)));
    assertThat(response.getTotalPages(), is(equalTo(3)));
    assertThat(response.getTotalSize(),
        is(equalTo(Long.valueOf(items.size()))));
  }

  @Test
  public void fromList_whenNullForValues() {
    SimplePageRequest simplePageRequest = new SimplePageRequest();
    simplePageRequest.setSize(20);
    simplePageRequest.setPage(1);
    SimplePageResponse<String> response = SimplePageResponse.fromList(
        simplePageRequest,
        null);
    assertThat(response.getHasMore(), is(equalTo(false)));
    assertThat(response.getItems().isEmpty(), is(equalTo(true)));
    assertThat(response.getPageNumber(),
        is(equalTo(simplePageRequest.getPage())));
    assertThat(response.getPageSize(),
        is(equalTo(simplePageRequest.getSize())));
    assertThat(response.getTotalPages(), is(equalTo(0)));
    assertThat(response.getTotalSize(), is(equalTo(0L)));
  }

  @Test
  public void fromList_whenNoValues() {
    SimplePageRequest simplePageRequest = new SimplePageRequest();
    simplePageRequest.setSize(20);
    simplePageRequest.setPage(1);
    SimplePageResponse<String> response = SimplePageResponse.fromList(
        simplePageRequest,
        new ArrayList<>());
    assertThat(response.getHasMore(), is(equalTo(false)));
    assertThat(response.getItems().isEmpty(), is(equalTo(true)));
    assertThat(response.getPageNumber(),
        is(equalTo(simplePageRequest.getPage())));
    assertThat(response.getPageSize(),
        is(equalTo(simplePageRequest.getSize())));
    assertThat(response.getTotalPages(), is(equalTo(0)));
    assertThat(response.getTotalSize(), is(equalTo(0L)));

  }

  @Test
  public void fromList_whenNullPageRequest() {
    List<String> items = Arrays.asList("bob", "bob2", "bob3");
    SimplePageResponse<String> response = SimplePageResponse.fromList(null,
        items);

    assertThat(response.getTotalSize(), is(equalTo((long) items.size())));
    assertThat(response.getTotalPages(), is(equalTo(1)));
    assertThat(response.getPageSize(), is(equalTo(20)));

  }


}
