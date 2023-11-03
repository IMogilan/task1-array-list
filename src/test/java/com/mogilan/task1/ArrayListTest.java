package com.mogilan.task1;

import com.mogilan.task1.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArrayListTest {

    private static final Integer[] ARRAY_16_ELEMENTS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private static final Integer[] ARRAY_20_ELEMENTS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};

    private static final Comparator<Integer> INTEGER_COMPARATOR = Integer::compare;
    private ArrayList<Integer> integerArrayList;

    @BeforeEach
    void prepare() {
        integerArrayList = new ArrayList<>();
    }

    @Nested
    class AddTest {

        @ParameterizedTest
        @MethodSource("com.mogilan.task1.ArrayListTest#getArgumentsForAddSuccessTest")
        void addSuccessWhenDefaultCapacity(int numberOfElements, Integer[] expectingElements) {
            populateListWithElements(integerArrayList, numberOfElements);

            var actualElementsAfterAddition = getElementsArrayFrom(integerArrayList);

            assertThat(integerArrayList.size()).isEqualTo(numberOfElements);
            assertThat(actualElementsAfterAddition).isEqualTo(expectingElements);
        }

        @ParameterizedTest
        @MethodSource("com.mogilan.task1.ArrayListTest#getArgumentsForAddSuccessTest")
        void addSuccessWhenInitialCapacity(int numberOfElements, Integer[] expectingElements) {
            int initialCapacity = 5;
            var arrayList = new ArrayList<Integer>(initialCapacity);
            populateListWithElements(arrayList, numberOfElements);

            var actualElementsAfterAddition = getElementsArrayFrom(arrayList);

            assertThat(arrayList.size()).isEqualTo(numberOfElements);
            assertThat(actualElementsAfterAddition).isEqualTo(expectingElements);
        }

        @ParameterizedTest
        @MethodSource("com.mogilan.task1.ArrayListTest#getArgumentsForAddByIndexSuccessTest")
        void addByIndexSuccess(Integer[] elements, int index, Integer newElement, Integer[] expectingElements) {
            populateListWithElements(integerArrayList, elements);

            integerArrayList.add(index, newElement);
            var actualElementsAfterAddition = getElementsArrayFrom(integerArrayList);

            assertThat(integerArrayList.size()).isEqualTo(elements.length + 1);
            assertThat(integerArrayList.get(index)).isEqualTo(newElement);
            assertThat(actualElementsAfterAddition).isEqualTo(expectingElements);
        }


        @ParameterizedTest
        @MethodSource("com.mogilan.task1.ArrayListTest#getArgumentsForCheckingExceptionIfIndexIncorrect")
        void addByIndexShouldTrowExceptionIfIndexIncorrect(int numberOfElements, int index) {
            populateListWithElements(integerArrayList, numberOfElements);
            var any = 1000;

            assertThrows(IndexOutOfBoundsException.class, () -> integerArrayList.add(index, any));
        }
    }

    @Test
    void clearSuccess() {
        populateListWithElements(integerArrayList, ARRAY_20_ELEMENTS);
        integerArrayList.clear();

        assertThat(integerArrayList.size()).isEqualTo(0);
        assertThrows(IndexOutOfBoundsException.class, () -> integerArrayList.get(0));
    }

    @Nested
    class GetTest {

        @ParameterizedTest
        @MethodSource("com.mogilan.task1.ArrayListTest#getArgumentsForGetSuccessTest")
        void getSuccess(Integer[] elements, int checkingIndex, Integer expectingResult) {
            populateListWithElements(integerArrayList, elements);

            var actualResult = integerArrayList.get(checkingIndex);

            assertThat(actualResult).isEqualTo(expectingResult);
        }

        @ParameterizedTest
        @MethodSource("com.mogilan.task1.ArrayListTest#getArgumentsForCheckingExceptionIfIndexIncorrect")
        void getShouldTrowExceptionIfIndexIncorrect(int numberOfElements, int index) {
            populateListWithElements(integerArrayList, numberOfElements);

            assertThrows(IndexOutOfBoundsException.class, () -> integerArrayList.get(index));
        }
    }

    @Nested
    class RemoveTest {

        @ParameterizedTest
        @MethodSource("com.mogilan.task1.ArrayListTest#getArgumentsForRemoveByValueSuccessTest")
        void removeByValueSuccess(Integer[] elements, Integer removingElement, boolean expectingResult, int expectingSize, Integer[] expectingElements) {
            populateListWithElements(integerArrayList, elements);

            var actualResult = integerArrayList.remove(removingElement);
            var actualElementsAfterRemoving = getElementsArrayFrom(integerArrayList);

            assertThat(actualResult).isEqualTo(expectingResult);
            assertThat(integerArrayList.size()).isEqualTo(expectingSize);
            assertThat(actualElementsAfterRemoving).isEqualTo(expectingElements);
        }

        @ParameterizedTest
        @MethodSource("com.mogilan.task1.ArrayListTest#getArgumentsForRemoveByIndexSuccessTest")
        void removeByIndexSuccess(Integer[] elements, int index, Integer expectingResult, Integer[] expectingElements) {
            populateListWithElements(integerArrayList, elements);
            var expectingSize = elements.length - 1;

            var actualResult = integerArrayList.remove(index);
            var actualElementsAfterRemoving = getElementsArrayFrom(integerArrayList);

            assertThat(actualResult).isEqualTo(expectingResult);
            assertThat(integerArrayList.size()).isEqualTo(expectingSize);
            assertThat(actualElementsAfterRemoving).isEqualTo(expectingElements);
        }

        @ParameterizedTest
        @MethodSource("com.mogilan.task1.ArrayListTest#getArgumentsForCheckingExceptionIfIndexIncorrect")
        void removeByIndexShouldTrowExceptionIfIndexIncorrect(int numberOfElements, int index) {
            populateListWithElements(integerArrayList, numberOfElements);

            assertThrows(IndexOutOfBoundsException.class, () -> integerArrayList.remove(index));
        }
    }

    @Nested
    class SetTest {

        @ParameterizedTest
        @MethodSource("com.mogilan.task1.ArrayListTest#getArgumentsForSetSuccessTest")
        void setSuccess(int index, Integer newElement) {
            populateListWithElements(integerArrayList, ARRAY_20_ELEMENTS);
            var expectingResult = integerArrayList.get(index);

            var actualResult = integerArrayList.set(index, newElement);

            assertThat(integerArrayList.size()).isEqualTo(ARRAY_20_ELEMENTS.length);
            assertThat(actualResult).isEqualTo(expectingResult);
            assertThat(integerArrayList.get(index)).isEqualTo(newElement);
        }

        @ParameterizedTest
        @MethodSource("com.mogilan.task1.ArrayListTest#getArgumentsForCheckingExceptionIfIndexIncorrect")
        void setShouldTrowExceptionIfIndexIncorrect(int numberOfElements, int index) {
            populateListWithElements(integerArrayList, numberOfElements);
            var any = 1000;

            assertThrows(IndexOutOfBoundsException.class, () -> integerArrayList.set(index, any));
        }
    }

    @Nested
    class SizeTest {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 5, 10, 15, 100, 1000000})
        void sizeSuccessWithDefaultCapacity(int numberOfElements) {
            populateListWithElements(integerArrayList, numberOfElements);

            assertThat(integerArrayList.size()).isEqualTo(numberOfElements);
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 5, 10, 15, 100, 1000000})
        void sizeSuccessWithInitialCapacity(int numberOfElements) {
            int initialCapacity = 5;
            var arrayList = new ArrayList<Integer>(initialCapacity);
            populateListWithElements(arrayList, numberOfElements);

            assertThat(arrayList.size()).isEqualTo(numberOfElements);
        }
    }

    @Nested
    class SortTest {

        @ParameterizedTest
        @MethodSource("com.mogilan.task1.ArrayListTest#getArgumentsForSortSuccessTest")
        void sortSuccessWhenComparatorIsNotNull(Integer[] array, Object[] expectingElements) {
            populateListWithElements(integerArrayList, array);

            integerArrayList.sort(INTEGER_COMPARATOR);
            var actualElementsAfterSorting = getElementsArrayFrom(integerArrayList);

            assertThat(actualElementsAfterSorting).isEqualTo(expectingElements);
        }

        @ParameterizedTest
        @MethodSource("com.mogilan.task1.ArrayListTest#getArgumentsForSortSuccessTest")
        void sortSuccessWhenComparatorIsNull(Integer[] array, Object[] expectingElements) {
            populateListWithElements(integerArrayList, array);

            integerArrayList.sort(null);
            var actualElementsAfterSorting = getElementsArrayFrom(integerArrayList);

            assertThat(actualElementsAfterSorting).isEqualTo(expectingElements);
        }

        @Test
        void sortShouldTrowExceptionIfElementsAreNotComparable() {
            ArrayList<PersonNotComparable> arrayList = new ArrayList<>();
            var personsNotComparable = new PersonNotComparable[]{
                    new PersonNotComparable("Max"),
                    new PersonNotComparable("Michael"),
                    new PersonNotComparable("Julia")
            };
            Arrays.stream(personsNotComparable)
                    .forEach(arrayList::add);
            assertThrows(ClassCastException.class, () ->
                    arrayList.sort(null));
        }

        @Test
        void sortShouldTrowExceptionIfElementsAreNullAndThusNotComparable() {
            populateListWithElements(integerArrayList, 10);
            integerArrayList.set(5, null);

            assertThrows(NullPointerException.class, () ->
                    integerArrayList.sort(Integer::compare));
            assertThrows(NullPointerException.class, () ->
                    integerArrayList.sort(null));
        }

    }

    @ParameterizedTest
    @MethodSource("getArgumentsForToStringSuccessTest")
    void toStringSuccess(Object[] elements, String expectingResult) {
        var objectArrayList = new ArrayList<>();
        for (Object element : elements) {
            objectArrayList.add(element);
        }

        assertThat(objectArrayList.toString()).isEqualTo(expectingResult);
    }

    private Integer[] getElementsArrayFrom(ArrayList<Integer> integerArrayList) {
        Integer[] result = new Integer[integerArrayList.size()];
        for (int i = 0; i < integerArrayList.size(); i++) {
            result[i] = integerArrayList.get(i);
        }
        return result;
    }

    private void populateListWithElements(ArrayList<Integer> integerArrayList, int numberOfElements) {
        for (int i = 0; i < numberOfElements; i++) {
            integerArrayList.add(i);
        }
    }

    private void populateListWithElements(ArrayList<Integer> integerArrayList, Integer[] elements) {
        for (Integer element : elements) {
            integerArrayList.add(element);
        }
    }

    static Stream<Arguments> getArgumentsForCheckingExceptionIfIndexIncorrect() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(0, -1),
                Arguments.of(0, 1),
                Arguments.of(5, 10),
                Arguments.of(5, -1),
                Arguments.of(500, 1000),
                Arguments.of(500, -1)
        );
    }

    static Stream<Arguments> getArgumentsForAddSuccessTest() {
        return Stream.of(
                Arguments.of(1, new Integer[]{0}),
                Arguments.of(5, new Integer[]{0, 1, 2, 3, 4}),
                Arguments.of(10, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}),
                Arguments.of(15, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14}),
                Arguments.of(20, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19}),
                Arguments.of(30, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29})
        );
    }

    static Stream<Arguments> getArgumentsForAddByIndexSuccessTest() {
        return Stream.of(
                Arguments.of(
                        ARRAY_16_ELEMENTS,
                        1,
                        null,
                        new Integer[]{0, null, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}),
                Arguments.of(
                        ARRAY_16_ELEMENTS,
                        1,
                        1000,
                        new Integer[]{0, 1000, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}),
                Arguments.of(
                        ARRAY_16_ELEMENTS,
                        5,
                        1000,
                        new Integer[]{0, 1, 2, 3, 4, 1000, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}),
                Arguments.of(
                        ARRAY_16_ELEMENTS,
                        14,
                        1000,
                        new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 1000, 14, 15}),
                Arguments.of(
                        ARRAY_20_ELEMENTS,
                        1,
                        1000,
                        new Integer[]{0, 1000, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19}),
                Arguments.of(
                        ARRAY_20_ELEMENTS,
                        5,
                        1000,
                        new Integer[]{0, 1, 2, 3, 4, 1000, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19}),
                Arguments.of(
                        ARRAY_20_ELEMENTS,
                        14,
                        1000,
                        new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 1000, 14, 15, 16, 17, 18, 19})
        );
    }

    static Stream<Arguments> getArgumentsForGetSuccessTest() {
        return Stream.of(
                Arguments.of(new Integer[]{0, 1, 2, 3, 4, 5}, 0, 0),
                Arguments.of(new Integer[]{0, 1, 2, 3, 4, 5}, 1, 1),
                Arguments.of(new Integer[]{0, 1, 2, 3, 4, 5}, 2, 2),
                Arguments.of(new Integer[]{0, 1, 2, 3, 4, 5}, 3, 3),
                Arguments.of(new Integer[]{0, 1, 2, 3, 4, 5}, 4, 4),
                Arguments.of(new Integer[]{null, 1, 2, 3, 4, 5}, 0, null),
                Arguments.of(ARRAY_16_ELEMENTS, ARRAY_16_ELEMENTS[0], 0),
                Arguments.of(ARRAY_16_ELEMENTS, ARRAY_16_ELEMENTS[4], 4),
                Arguments.of(ARRAY_16_ELEMENTS, ARRAY_16_ELEMENTS[8], 8),
                Arguments.of(ARRAY_16_ELEMENTS, ARRAY_16_ELEMENTS[11], 11),
                Arguments.of(ARRAY_16_ELEMENTS, ARRAY_16_ELEMENTS[15], 15)
        );
    }

    static Stream<Arguments> getArgumentsForRemoveByValueSuccessTest() {
        return Stream.of(
                Arguments.of(
                        new Integer[]{}, 10, false, 0, new Integer[]{}
                ),
                Arguments.of(
                        ARRAY_16_ELEMENTS, 100, false, ARRAY_16_ELEMENTS.length, ARRAY_16_ELEMENTS
                ),
                Arguments.of(
                        ARRAY_16_ELEMENTS, null, false, ARRAY_16_ELEMENTS.length, ARRAY_16_ELEMENTS
                ),
                Arguments.of(
                        ARRAY_16_ELEMENTS, ARRAY_16_ELEMENTS[0], true, ARRAY_16_ELEMENTS.length - 1, new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}
                ),
                Arguments.of(
                        ARRAY_16_ELEMENTS, ARRAY_16_ELEMENTS[5], true, ARRAY_16_ELEMENTS.length - 1, new Integer[]{0, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}
                ),
                Arguments.of(
                        ARRAY_16_ELEMENTS, ARRAY_16_ELEMENTS[15], true, ARRAY_16_ELEMENTS.length - 1, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14}
                ),
                Arguments.of(
                        new Integer[]{1, 2, 3, 2, 1}, 1, true, 4, new Integer[]{2, 3, 2, 1}
                ),
                Arguments.of(
                        new Integer[]{1, 2, 3, 2, 1}, 2, true, 4, new Integer[]{1, 3, 2, 1}
                ),
                Arguments.of(
                        new Integer[]{1, null, 3, null, 1}, null, true, 4, new Integer[]{1, 3, null, 1}
                )
        );
    }

    static Stream<Arguments> getArgumentsForRemoveByIndexSuccessTest() {
        return Stream.of(
                Arguments.of(
                        ARRAY_16_ELEMENTS, 0, ARRAY_16_ELEMENTS[0], new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}
                ),
                Arguments.of(
                        ARRAY_16_ELEMENTS, 5, ARRAY_16_ELEMENTS[5], new Integer[]{0, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}
                ),
                Arguments.of(
                        ARRAY_16_ELEMENTS, 10, ARRAY_16_ELEMENTS[10], new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15}
                ),
                Arguments.of(
                        ARRAY_16_ELEMENTS, 15, ARRAY_16_ELEMENTS[15], new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14}
                )
        );
    }

    static Stream<Arguments> getArgumentsForSetSuccessTest() {
        return Stream.of(
                Arguments.of(0, 1000),
                Arguments.of(5, 9999),
                Arguments.of(10, 100000),
                Arguments.of(15, 8888),
                Arguments.of(7, null)
        );
    }

    static Stream<Arguments> getArgumentsForSortSuccessTest() {
        return Stream.of(
                Arguments.of(
                        new Integer[]{},
                        new Integer[]{}),
                Arguments.of(
                        new Integer[]{1},
                        new Integer[]{1}),
                Arguments.of(
                        new Integer[]{1, 3, 2, 4, 7, 6, 5, 8, 10, 9},
                        new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}),
                Arguments.of(
                        new Integer[]{2, 2, 2, 3, 3, 3, 1, 1, 1},
                        new Integer[]{1, 1, 1, 2, 2, 2, 3, 3, 3}),
                Arguments.of(
                        new Integer[]{10, 8, 9, 6, 2, 3, 5, 8, 1, 0, -1, -32, 55, -7, 99, 100, 77},
                        new Integer[]{-32, -7, -1, 0, 1, 2, 3, 5, 6, 8, 8, 9, 10, 55, 77, 99, 100})
        );
    }

    static Stream<Arguments> getArgumentsForToStringSuccessTest() {
        return Stream.of(
                Arguments.of(new Integer[]{}, "[]"),
                Arguments.of(new Integer[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 111, 112}, "[10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 111, 112]"),
                Arguments.of(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.1, 11.2}, "[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.1, 11.2]"),
                Arguments.of(new String[]{"10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "111", "112"}, "[10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 111, 112]")
        );
    }

    static class PersonNotComparable {
        String name;

        public PersonNotComparable(String name) {
            this.name = name;
        }
    }
}