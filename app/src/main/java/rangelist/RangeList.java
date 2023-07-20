/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package rangelist;

import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Range List
 *
 * @author yangling
 */
public class RangeList {
    private static final int PAIR = 2;

    private final TreeSet<Range> data;

    public RangeList() {
        this.data = new TreeSet<>();
    }

    private boolean invalidArgs(int[] range) {
        if (Objects.isNull(range) || range.length != PAIR) {
            return true;
        }
        int start = range[0];
        int end = range[1];
        return start >= end;
    }

    public void add(int[] range) {
        if (invalidArgs(range)) {
            return;
        }
        int start = range[0];
        int end = range[1];
        Range r1 = Range.builder().start(start).end(end).build();
        Range floor = data.floor(r1);
        while (floor != null && r1.isMergable(floor)) {
            r1 = r1.merge(floor);
            data.remove(floor);
            floor = data.floor(r1);
        }
        Range ceiling = data.ceiling(r1);
        while (ceiling != null && r1.isMergable(ceiling)) {
            r1 = r1.merge(ceiling);
            data.remove(ceiling);
            ceiling = data.ceiling(r1);
        }
        data.add(r1);
    }

    public void remove(int[] range) {
        if (invalidArgs(range)) {
            return;
        }
        int start = range[0];
        int end = range[1];
        Range r1 = Range.builder().start(start).end(end).build();
        Range floor = data.floor(r1);
        while (floor != null && floor.isRemovable(r1)) {
            List<Range> afterRemove = floor.remove(r1);
            data.remove(floor);
            data.addAll(afterRemove);
            floor = data.floor(r1);
        }
        Range ceiling = data.ceiling(r1);
        while (ceiling != null && ceiling.isRemovable(r1)) {
            List<Range> afterRemove = ceiling.remove(r1);
            data.remove(ceiling);
            data.addAll(afterRemove);
            ceiling = data.ceiling(r1);
        }
    }

    @Override
    public String toString() {
        return data.stream().map(Range::toString).collect(Collectors.joining(" "));
    }

}
