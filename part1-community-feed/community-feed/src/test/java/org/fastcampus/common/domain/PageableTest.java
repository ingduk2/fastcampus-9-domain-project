package org.fastcampus.common.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PageableTest {

    @DisplayName("pageIndex 1, pageSize 10 -> offset = 0, pageLimit = 10")
    @Test
    void test1() {
        // given
        Pageable pageable = new Pageable();

        // when
        int offset = pageable.getOffset();
        int limit = pageable.getLimit();


        // then
        assertThat(offset).isEqualTo(0);
        assertThat(limit).isEqualTo(10);
    }

    @DisplayName("pageIndex 2 pageSize 10 -> offset = 10, pageLimit = 10")
    @Test
    void test2() {
        // given
        Pageable pageable = new Pageable(2, 10);

        // when
        int offset = pageable.getOffset();
        int limit = pageable.getLimit();


        // then
        assertThat(offset).isEqualTo(10);
        assertThat(limit).isEqualTo(10);
    }
}
