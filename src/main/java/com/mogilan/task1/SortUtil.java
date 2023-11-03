package com.mogilan.task1;

import java.util.Comparator;
import java.util.Objects;
import java.util.Random;

/**
 * Utility class that provides sort functionality for arrays of Objects. Contains only one public method - quicksort.
 *
 * <p>Most methods are parameterized by E (type of element) as they accept a {@link Comparator} object.</p>
 *
 * @author Ilya Mogilan
 * @see ArrayList
 */
public class SortUtil {

    private SortUtil() {
    }

    /**
     * Sorts specified array of Objects using quicksort algorithm and according to the conditions
     * settled by specified {@link Comparator} or, if {@code null} is passed instead of the {@link Comparator},
     * according to the array elements' {@linkplain Comparable natural ordering} settled by implementation
     * of {@link Comparable} interface.
     * <p>
     * Note! The sort realisation is not stable: method might reorder equal elements.
     * </p>
     * Based on explanation of quicksort algorithm in tutorial
     * <a href="https://www.youtube.com/watch?v=h8eyY7dIiN4">Quicksort Sort Algorithm in Java</a>
     *
     * @param array      array of Objects to be sorted. All objects should be the same type and if {@code null} is passed
     *                   instead of the {@link Comparator} all elements in this array must implement the {@link Comparable}
     *                   interface specifying natural ordering;
     * @param comparator the {@code Comparator} used to compare array elements. {@code null} value is also valid
     *                   option indicating that the elements' {@linkplain Comparable natural ordering} should be used
     * @param highIndex  the index of the last element in array to be sorted.
     * @throws NullPointerException      (1) if value of the specified array is {@code null} or (2) if specified array contains elements with {@code null} value and {@link Comparator} or
     *                                   realisation of {@link Comparable} interface don't define how to compare elements
     *                                   with null values.
     * @throws IndexOutOfBoundsException if the highIndex is out of range ({@code highIndex < 0 || highIndex >= array.length})
     * @throws ClassCastException        (1) if the array contains objects of different types that can't be cast to
     *                                   each other or (2) if {@code null} is passed instead of the {@link Comparator}
     *                                   and class of elements of the specified array doesn't implement {@link Comparable}.
     */
    public static <T> void quicksort(Object[] array, Comparator<? super T> comparator, int highIndex) {
        Objects.requireNonNull(array);
        Objects.checkIndex(highIndex, array.length);

        quicksort(array, comparator, 0, highIndex);
    }

    private static <T> void quicksort(Object[] array, Comparator<? super T> comparator, int lowIndex, int highIndex) {
        if (lowIndex >= highIndex) {
            return;
        }

        T pivot = getPivot(array, lowIndex, highIndex);

        int leftPointer = partition(array, comparator, lowIndex, highIndex, pivot);
        quicksort(array, comparator, lowIndex, leftPointer - 1);
        quicksort(array, comparator, leftPointer + 1, highIndex);
    }

    private static <T> T getPivot(Object[] array, int lowIndex, int highIndex) {
        int pivotIndex = new Random().nextInt(highIndex - lowIndex) + lowIndex;
        T pivot = (T) array[pivotIndex];
        swap(array, pivotIndex, highIndex);
        return pivot;
    }

    private static <T> int partition(Object[] array, Comparator<? super T> comparator, int lowIndex, int highIndex, T pivot) {
        int leftPointer = lowIndex;
        int rightPointer = highIndex;
        while (leftPointer < rightPointer) {
            while (compare(comparator, array[leftPointer], pivot) <= 0 && leftPointer < rightPointer) {
                leftPointer++;
            }
            while (compare(comparator, array[rightPointer], pivot) >= 0 && leftPointer < rightPointer) {
                rightPointer--;
            }
            swap(array, leftPointer, rightPointer);
        }
        swap(array, leftPointer, highIndex);
        return leftPointer;
    }

    private static <T> int compare(Comparator<? super T> comparator, Object element, T pivot) {
        if (comparator != null) {
            return comparator.compare((T) element, pivot);
        }
        return ((Comparable<T>) element).compareTo(pivot);
    }

    private static void swap(Object[] array, int firstIndex, int secondIndex) {
        Object temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }
}
