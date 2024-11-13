package org.fastcampus.common.domain;

public class PositiveIntegerCounter {

    private int count;

    public PositiveIntegerCounter(int count) {
        this.count = count;
    }

    public PositiveIntegerCounter() {
        this(0);
    }

    public void increase() {
        this.count++;
    }

    public void decrease() {
        if (count <= 0) {
            return;
        }

        this.count--;
    }

    public int getCount() {
        return count;
    }
}
