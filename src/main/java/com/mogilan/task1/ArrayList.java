package com.mogilan.task1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * Resizable-array implementation of the {@link List} interface.
 * <p>Implements all {@link List} methods and permits all elements, including
 * {@code null}. In addition ArrayList overrides toString() method that returns a string representation
 * of this ArrayList.</p>
 *
 * <p>The main field of ArrayList is field elements, which represent an array that store all the elements
 * of this ArrayList. </p>
 * <p>Constructor without parameters creates an ArrayList with empty array for elements field. In this case,
 * to save space, the field elements is assigned the value of the static field EMPTY_ELEMENTS.</p>
 * <p>Creating an ArrayList it's possible to specify in constructor initialCapacity, which creates for elements
 * field an array with length equal to this initialCapacity.</p>
 * <p>Capacity corresponds to the length of array of the field elements, but does not correspond to the number
 * of elements in this ArrayList. The number of elements in this ArrayList is stored in the field size.</p>
 * <p>If the length of the array elements is not enough to perform operations on elements of this ArrayList
 * (adding new element to the end of this ArrayList or moving elements of this ArrayList when adding an element to the
 * beginning or middle of this ArrayList), before performing of such operations a new array of a larger size is created
 * (by methods grow() or moveElementsRightFromIndex(int index)) and all elements are copied from the old array into
 * a new one. The old array is subsequently destroyed by the garbage collector.</p>
 *
 * @param <E> the type of elements in this list
 * @author Ilya Mogilan
 * @see List
 */
public class ArrayList<E> implements List<E> {

    /**
     * Initial capacity that is used to expand an empty array of the field elements when the first element is added.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * An empty shared array, which, in order to save space, is assigned to the elements field in
     * the following cases:
     * <ul>
     *     <li>creating an ArrayList using a constructor without parameters; </li>
     *     <li>clearing an ArrayList using the clear() method.</li>
     * </ul>
     */
    private static final Object[] EMPTY_ELEMENTS = {};

    /**
     * <p>The array into which the elements of the ArrayList are stored.</p>
     * <p>The capacity of the ArrayList is the length of this array.</p>
     */
    private Object[] elements;

    /**
     * The number of elements in this ArrayList
     */
    private int size;

    /**
     * Creates an ArrayList with empty array for elements field. In this case, to save space, the field elements
     * is assigned the value of the static field EMPTY_ELEMENTS
     */
    public ArrayList() {
        elements = EMPTY_ELEMENTS;
    }

    /**
     * Creates an ArrayList with capacity (the length of the array of field elements) equal
     * to the initialCapacity specified in the constructor.
     */
    public ArrayList(int initialCapacity) {
        elements = new Object[initialCapacity];
    }

    /**
     * Adds the element passed in the parameters to the end of this ArrayList.
     * <p>If the capacity is not enough to perform addition, before performing of such operation a new array of a larger
     * size is created by methods grow() and all elements are copied from the old array into a new one. The old array
     * is subsequently destroyed by the garbage collector</p>
     *
     * @param element element to be added to this list
     */
    @Override
    public void add(E element) {
        if (size == elements.length) {
            elements = grow();
        }
        elements[size++] = element;
    }

    /**
     * Adds the element passed in the parameters to this ArrayList at the specified index.
     * <p>All elements of this ArrayList, from the specified index and to the end of the entire ArrayList,
     * are moved to the right on one position.</p>
     * <p>If the capacity is not enough to perform addition, before performing of such operation a new array of a larger
     * size is created by methods moveElementsRightFromIndex(int index) and all elements are copied from the old array
     * into a new one. The old array is subsequently destroyed by the garbage collector</p>
     *
     * @param index   index at which the element should be inserted
     * @param element element to be added to this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */

    @Override
    public void add(int index, E element) {
        Objects.checkIndex(index, size);
        moveElementsRightFromIndex(index);
        elements[index] = element;
        size++;
    }

    /**
     * Removes all elements from this ArrayList and set the size as 0.
     * <p>In order to save space, the array of the field elements is assigned to EMPTY_ELEMENTS.</p>
     */
    @Override
    public void clear() {
        elements = EMPTY_ELEMENTS;
        size = 0;
    }

    /**
     * Returns the element at the specified index in this ArrayList.
     *
     * @param index index of the element to be returned
     * @return the element at the specified index in this ArrayList
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        return (E) elements[index];
    }

    /**
     * Removes the specified element from this ArrayList.
     * <p>If an element occurs two or more times in this ArrayList, then only the first element (the element with the
     * lowest index) will be removed.</p>
     * <p>Elements that were to the right from the index of removed element are moved to
     * the left on one position.</p>
     *
     * @param element element to be removed from this ArrayList. {@code null} is also a valid option - in this case,
     *                the element with null value (and the lowest index if there are several elements
     *                with null value in this ArrayList) will be removed
     * @return true - if the specified element was found in this ArrayList and removed;
     * false - if the specified element wasn't found in this ArrayList
     */
    @Override
    public boolean remove(E element) {
        for (int index = 0; index < size; index++) {
            var currentElement = elements[index];
            if (element == null) {
                if (currentElement == null) {
                    simpleRemoveElement(index);
                    return true;
                }
            } else if (currentElement.equals(element)) {
                simpleRemoveElement(index);
                return true;
            }
        }
        return false;
    }

    /**
     * <p>Removes the element that is at the specified index in this ArrayList. </p>
     * <p>Elements that were to the right from the specified index are moved to the left on one position.</p>
     *
     * @param index index of the element to be removed
     * @return the element that was removed
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    @Override
    public E remove(int index) {
        Objects.checkIndex(index, size);
        var removingElement = (E) elements[index];
        simpleRemoveElement(index);
        return removingElement;
    }

    /**
     * Setts a new element at the specified position, replacing the previous element.
     * The ArrayList size does not change.
     *
     * @param index   index of the element to be replaced by new value
     * @param element element to be added to this ArrayList at the specified position
     * @return the element that was replaced by new value
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    @Override
    public E set(int index, E element) {
        Objects.checkIndex(index, size);
        var replacingElement = (E) elements[index];
        elements[index] = element;
        return replacingElement;
    }

    /**
     * Returns the number of elements in this ArrayList.
     *
     * @return the number of elements in this ArrayList
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Sorts this ArrayList using quicksort algorithm (using quicksort() from {@link SortUtil}) and according to the conditions settled by specified
     * {@link Comparator} or, if {@code null} is passed instead of the {@link Comparator}, according to the elements'
     * {@linkplain Comparable natural ordering} settled by implementation of {@link Comparable} interface.
     * <p>
     * Note: the sort realisation is not stable: method might reorder equal elements.
     * </p>
     *
     * @param comparator the {@code Comparator} used to compare ArrayList elements. {@code null} value is also valid
     *                   option indicating that the elements' {@linkplain Comparable natural ordering} should be used
     *                   and thus in this case all elements in this ArrayList must implement the {@link Comparable}
     *                   interface specifying {@linkplain Comparable natural ordering};
     * @throws ClassCastException   if {@code null} is passed instead of the {@link Comparator} and
     *                              class of elements of this ArrayList doesn't implement {@link Comparable}.
     * @throws NullPointerException if this ArrayList contains elements with {@code null} value and {@link Comparator} or
     *                              realisation of {@link Comparable} interface don't define how to compare elements
     *                              with null values.
     */
    @Override
    public void sort(Comparator<? super E> comparator) {
        if (size > 1) {
            SortUtil.quicksort(elements, comparator, size - 1);
        }
    }

    /**
     * Returns a string representation of this ArrayList, that consists of all ArrayList elements separated by the characters ", " (comma and space) and enclosed in square brackets ("[]").
     *
     * @return string representation of this ArrayList
     */
    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        int currentElementIndex = 0;
        for (Object element : elements) {
            stringBuilder.append(element);
            if (currentElementIndex != (size - 1)) {
                stringBuilder.append(", ");
            } else {
                stringBuilder.append("]");
                break;
            }
            currentElementIndex++;
        }
        return stringBuilder.toString();
    }

    private Object[] grow() {
        var currentCapacity = elements.length;
        if (currentCapacity == 0) {
            return new Object[DEFAULT_CAPACITY];
        }
        var newCapacity = (int) Math.ceil(currentCapacity * 1.5) + 1;
        return Arrays.copyOf(elements, newCapacity);
    }

    private void moveElementsRightFromIndex(int index) {
        if (size == elements.length) {
            var newArray = new Object[getNewCapacityForMovingElements()];
            System.arraycopy(elements, 0, newArray, 0, index);
            System.arraycopy(elements, index, newArray, index + 1, size - index);
            elements = newArray;
        } else {
            System.arraycopy(elements, index, elements, index + 1, size - index);
        }
    }

    private int getNewCapacityForMovingElements() {
        var currentCapacity = elements.length;
        return (int) Math.ceil(currentCapacity * 1.5) + 1;
    }

    private void simpleRemoveElement(int index) {
        if (index != size - 1) {
            System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        }
        elements[--size] = null;
    }
}
