package org.fastcampus.common.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PositiveIntegerCounterTest {

    @Nested
    class Increase {

        @Test
        void test1000() {
            // Given
            PositiveIntegerCounter counter = new PositiveIntegerCounter();

            // When
            counter.increase();

            // Then
            assertThat(counter.getCount()).isEqualTo(1);
        }
    }

    @Nested
    class Decrease {
        @Test
        void test1000() {
            // Given
            PositiveIntegerCounter counter = new PositiveIntegerCounter();

            // When
            counter.decrease();

            // Then
            assertThat(counter.getCount()).isEqualTo(0);
        }

        @Test
        void test1001() {
            // Given
            PositiveIntegerCounter counter = new PositiveIntegerCounter();
            counter.increase();

            // When
            counter.decrease();

            // Then
            assertThat(counter.getCount()).isEqualTo(0);
        }
    }
}