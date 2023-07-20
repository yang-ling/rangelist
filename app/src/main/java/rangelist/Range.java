package rangelist;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Range bean
 *
 * @author yangling
 */
public class Range implements Comparable<Range> {
    private final int start;
    private final int end;

    @Override
    public String toString() {
        return "[" + start + ", " + end + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Range range = (Range)o;
        return getStart() == range.getStart() && getEnd() == range.getEnd();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStart(), getEnd());
    }

    public boolean isMergable(Range other) {
        if (this.end < other.getStart()) {
            return false;
        }
        return this.start <= other.getEnd();
    }

    public boolean isRemovable(Range other) {
        if (this.end <= other.getStart()) {
            return false;
        }
        return this.start < other.getEnd();
    }

    public Range merge(Range visitor) {
        if (!isMergable(visitor)) {
            throw new IllegalArgumentException("It is not mergable!");
        }
        return Range.builder()
            .start(Math.min(this.start, visitor.getStart()))
            .end(Math.max(this.end, visitor.getEnd()))
            .build();
    }

    public List<Range> remove(Range other) {
        if (!isRemovable(other)) {
            throw new IllegalArgumentException("It is not mergable!");
        }
        List<Range> res = new ArrayList<>();
        if (this.start < other.getStart()) {
            res.add(Range.builder().start(this.start).end(other.getStart()).build());
        }
        if (this.end > other.getEnd()) {
            res.add(Range.builder().start(other.getEnd()).end(this.end).build());
        }
        return res;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public static RangeBuilder builder() {
        return new RangeBuilder();
    }

    @Override
    public int compareTo(Range range) {
        return this.start - range.getStart();
    }

    public static class RangeBuilder {
        private int start;
        private int end;

        RangeBuilder() {
        }

        public RangeBuilder start(int start) {
            this.start = start;
            return this;
        }

        public RangeBuilder end(int end) {
            this.end = end;
            return this;
        }

        private void validate() {
            if (this.end < this.start) {
                throw new IllegalArgumentException("Start should be less or equal to End!");
            }
        }

        public Range build() {
            validate();
            return new Range(this.start, this.end);
        }
    }
}
