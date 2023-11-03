package com.mogilan.task1;

import java.util.Comparator;

/**
 * An ordered collection of elements. A simplified version of the {@link java.util.List}.
 * Does not implement the {@link java.util.Collection} and {@link Iterable} interfaces.
 *
 * <p>Supports basic methods for working with a collection of elements:</p>
 * <ul>
 *     <li>methods for working with separate elements: adding an element, adding an element
 *     by index, getting an element by index, removing an element (by index or by value),
 *     setting an element by index;</li>
 *     <li>methods for working with the entire collection: getting the size of the collection,
 *     clearing the entire collection, sorting all elements in the collection.</li>
 * </ul>
 *  @param <E> the type of elements in this list
 *
 * @author Ilya Mogilan
 * @see ArrayList
 */
public interface List<E> {

    /**
     * Adds the element passed in the parameters to the end of this list.
     *
     * @param element element to be added to this list
     */
    void add(E element);

    /**
     * Adds the element passed in the parameters to this list at the specified index.
     * <p>All elements of this list, from the specified index and to the end of the entire list,
     * are moved to the right on one position.</p>
     *
     * @param index   index at which the element should be inserted
     * @param element element to be added to this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    void add(int index, E element);

    /**
     * Removes all elements from this list.
     */
    void clear();

    /**
     * Returns the element at the specified index in this list.
     *
     * @param index index of the element to be returned
     * @return the element at the specified index in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    E get(int index);

    /**
     * Removes the specified element from this list.
     * <p>If an element occurs two or more times in this list, then only the first element (the element with the
     * lowest index) will be removed.</p>
     * <p>Elements that were to the right from the index of removed element are moved to the left
     * on one position.</p>
     *
     * @param element element to be removed from this list. {@code null} is also a valid option - in this case,
     *                the element with null value (and the lowest index if there are several elements
     *                with null value in this list) will be removed
     * @return true - if the specified element was found in this list and removed;
     * false - if the specified element wasn't found in this list
     */
    boolean remove(E element);

    /**
     * Removes the element that is at the specified index in this list.
     * <p>Elements that were to the right from the specified index are moved to the left on one position.</p>
     *
     * @param index index of the element to be removed
     * @return the element that was removed
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    E remove(int index);

    /**
     * Setts a new element at the specified position, replacing the previous element.
     * The list size does not change.
     *
     * @param index   index of the element to be replaced by new value
     * @param element element to be added to this list at the specified position
     * @return the element that was replaced by new value
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    E set(int index, E element);

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    int size();

    /**
     * Sorts this list according to the conditions settled by specified {@link Comparator} or,
     * if {@code null} is passed instead of the {@link Comparator}, according to the elements'
     * {@linkplain Comparable natural ordering} settled by implementation of {@link Comparable} interface.
     * <p>
     * Note! The sort realisation might be not stable: method might reorder equal elements.
     * </p>
     *
     * @param comparator the {@code Comparator} used to compare list elements. {@code null} value is also valid
     *                   option indicating that the elements' {@linkplain Comparable natural ordering} should be used
     *                   and thus in this case all elements in this list must implement the {@link Comparable}
     *                   interface specifying {@linkplain Comparable natural ordering};
     * @throws ClassCastException   if {@code null} is passed instead of the {@link Comparator} and
     *                              class of elements of this list doesn't implement {@link Comparable}.
     * @throws NullPointerException if this list contains elements with {@code null} value and {@link Comparator} or
     *                              realisation of {@link Comparable} interface don't define how to compare elements
     *                              with null values.
     */
    void sort(Comparator<? super E> comparator);
}
