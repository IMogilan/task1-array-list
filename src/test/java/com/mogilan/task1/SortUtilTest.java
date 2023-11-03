package com.mogilan.task1;

import com.mogilan.task1.SortUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Comparator;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SortUtilTest {

    @ParameterizedTest
    @MethodSource("getArgumentsForQuicksortSuccessWhenComparatorIsNotNullTest")
    void quicksortSuccessWhenComparatorIsNotNull(Object[] array, Comparator<? extends Object> comparator, Object[] expectingArray) {
        SortUtil.quicksort(array, comparator, array.length - 1);

        assertThat(array).isEqualTo(expectingArray);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForQuicksortSuccessWhenComparatorIsNullTest")
    void quicksortSuccessWhenComparatorIsNull(Object[] array, Object[] expectingArray) {
        SortUtil.quicksort(array, null, array.length - 1);

        assertThat(array).isEqualTo(expectingArray);
    }

    @ParameterizedTest
    @NullSource
    void quicksortShouldTrowExceptionIfArrayIsNull(Object[] array) {
        var any = 10;
        assertThrows(NullPointerException.class, () ->
                SortUtil.quicksort(array, Integer::compare, any));
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForQuicksortShouldTrowExceptionIfIndexIncorrectTest")
    void quicksortShouldTrowExceptionIfIndexIncorrect(Object[] array, int highIndex) {
        assertThrows(IndexOutOfBoundsException.class, () ->
                SortUtil.quicksort(array, Integer::compare, highIndex));
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForQuicksortShouldTrowExceptionIfElementsAreNotComparableTest")
    void quicksortShouldTrowExceptionIfElementsAreNotComparableOrInstanceOfDifferentTypes(Object[] array, Comparator<Integer> comparator) {
        assertThrows(ClassCastException.class, () ->
                SortUtil.quicksort(array, comparator, array.length - 1));
    }

    @Test
    void quicksortShouldTrowExceptionIfElementsAreNullAndThusNotComparable() {
        var integers = new Integer[]{0, 1, null, 3, 4};

        assertThrows(NullPointerException.class, () ->
                SortUtil.quicksort(integers, Integer::compare, integers.length - 1));
        assertThrows(NullPointerException.class, () ->
                SortUtil.quicksort(integers, null, integers.length - 1));
    }

    static Stream<Arguments> getArgumentsForQuicksortShouldTrowExceptionIfIndexIncorrectTest() {
        return Stream.of(
                Arguments.of(new Integer[]{}, 100),
                Arguments.of(new Integer[]{0, 1, 2, 3, 4}, 100),
                Arguments.of(new Integer[]{0, 1, 2, 3, 4}, -100)
        );
    }

    static Stream<Arguments> getArgumentsForQuicksortSuccessWhenComparatorIsNotNullTest() {
        Comparator<Integer> integerComparator = Integer::compare;
        Comparator<Double> doubleComparator = Double::compare;
        Comparator<PersonComparable> personComparator = Comparator.comparingInt(p -> p.age);
        var person11YearsOld = new PersonComparable(11);
        var person33YearsOld = new PersonComparable(33);
        var person44YearsOld = new PersonComparable(44);
        var person55YearsOld = new PersonComparable(55);
        var person77YearsOld = new PersonComparable(77);
        return Stream.of(
                Arguments.of(
                        new Object[]{10, 8, 9, 6, 2, 3, 5, 8, 1, 0, -1, -32, 55, -7, 99, 100, 77},
                        integerComparator,
                        new Object[]{-32, -7, -1, 0, 1, 2, 3, 5, 6, 8, 8, 9, 10, 55, 77, 99, 100}),
                Arguments.of(
                        new Object[]{2, 2, 2, 3, 3, 3, 1, 1, 1},
                        integerComparator,
                        new Object[]{1, 1, 1, 2, 2, 2, 3, 3, 3}),
                Arguments.of(
                        new Object[]{10.0, 8.0, 9.0, 6.0, 2.0, 3.0, 5.0, 8.0, 1.0, 0.0, -1.0, -32.0, 55.0, -7.0, 99.0, 100.0, 77.0},
                        doubleComparator,
                        new Object[]{-32.0, -7.0, -1.0, 0.0, 1.0, 2.0, 3.0, 5.0, 6.0, 8.0, 8.0, 9.0, 10.0, 55.0, 77.0, 99.0, 100.0}),
                Arguments.of(
                        new Object[]{
                                person55YearsOld,
                                person44YearsOld,
                                person33YearsOld,
                                person77YearsOld,
                                person11YearsOld
                        },
                        personComparator,
                        new Object[]{
                                person11YearsOld,
                                person33YearsOld,
                                person44YearsOld,
                                person55YearsOld,
                                person77YearsOld
                        })
        );
    }

    static Stream<Arguments> getArgumentsForQuicksortSuccessWhenComparatorIsNullTest() {
        var person11YearsOld = new PersonComparable(11);
        var person33YearsOld = new PersonComparable(33);
        var person44YearsOld = new PersonComparable(44);
        var person55YearsOld = new PersonComparable(55);
        var person77YearsOld = new PersonComparable(77);
        return Stream.of(
                Arguments.of(
                        new Object[]{10, 8, 9, 6, 2, 3, 5, 8, 1, 0, -1, -32, 55, -7, 99, 100, 77},
                        new Object[]{-32, -7, -1, 0, 1, 2, 3, 5, 6, 8, 8, 9, 10, 55, 77, 99, 100}),
                Arguments.of(
                        new Object[]{2, 2, 2, 3, 3, 3, 1, 1, 1},
                        new Object[]{1, 1, 1, 2, 2, 2, 3, 3, 3}),
                Arguments.of(
                        new Object[]{10.0, 8.0, 9.0, 6.0, 2.0, 3.0, 5.0, 8.0, 1.0, 0.0, -1.0, -32.0, 55.0, -7.0, 99.0, 100.0, 77.0},
                        new Object[]{-32.0, -7.0, -1.0, 0.0, 1.0, 2.0, 3.0, 5.0, 6.0, 8.0, 8.0, 9.0, 10.0, 55.0, 77.0, 99.0, 100.0}),
                Arguments.of(
                        new Object[]{
                                person55YearsOld,
                                person44YearsOld,
                                person33YearsOld,
                                person77YearsOld,
                                person11YearsOld
                        },
                        new Object[]{
                                person11YearsOld,
                                person33YearsOld,
                                person44YearsOld,
                                person55YearsOld,
                                person77YearsOld
                        })
        );
    }

    static Stream<Arguments> getArgumentsForQuicksortShouldTrowExceptionIfElementsAreNotComparableTest() {
        Comparator<Integer> comparator = Integer::compare;
        return Stream.of(
                Arguments.of(new Object[]{"1", 1, 1.0}, comparator),
                Arguments.of(new Object[]{"1", 1, 1.0}, null),
                Arguments.of(new Object[]{
                        new PersonNotComparable("Max"),
                        new PersonNotComparable("Michael"),
                        new PersonNotComparable("Julia")
                }, null)
        );
    }

    static class PersonComparable implements Comparable<PersonComparable> {
        Integer age;

        public PersonComparable(Integer age) {
            this.age = age;
        }

        @Override
        public int compareTo(PersonComparable otherPerson) {
            return Integer.compare(this.age, otherPerson.age);
        }
    }

    static class PersonNotComparable {
        String name;

        public PersonNotComparable(String name) {
            this.name = name;
        }
    }

}