# Range List

## Project Structure

### Source

1. `Range` : It is a bean. It contains `start` and `end` info of a range. It is sortable and sort key is `start`. One `Range` bean can merge or remove another `Range` bean.
2. `RangeList` : It uses `TreeSet` to store `Range` beans. To `add` a range, firstly convert input int array to a `Range` bean, then get mergable `floor` beans and `ceiling` beans from dataset, at last add the bean. To `remove` a range, get removable `floor` beans and `ceiling` beans, remove the range from those beans, and add back remaining beans.

### Test

`RangeListTest` : It contains test cases.

## Build & Test

``` sh
./gradlew build
```
This command will build and test this project.
