/*
 * Trident - A Multithreaded Server Alternative
 * Copyright 2014 The TridentSDK Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.tridentsdk.config;

import com.google.gson.JsonArray;
import net.tridentsdk.docs.AccessNoDoc;
import net.tridentsdk.util.TridentLogger;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;

/**
 * A LinkedList [implementation] that also makes changes to the underlying JsonArray object
 *
 * @author The TridentSDK Team
 */
@NotThreadSafe
public class ConfigList<V> extends AbstractList<V> implements List<V>, Iterable<V> {
    private static final long serialVersionUID = -7535821700183585211L;
    private final Node<V> head = new Node<>(null, null, null);
    private final Node<V> footer = new Node<>(null, null, head);
    JsonArray jsonHandle;
    private int size = 0;

    /**
     * Creates a new linked list for the JSON config serializable
     *
     * @param handle the array handler for the list
     */
    protected ConfigList(JsonArray handle) {
        this.jsonHandle = handle;
        head.next = footer;
    }

    /**
     * Creates a new linked list by transferring elements from an existing collection
     *
     * @param handle the array handler for the new list
     * @param c      the initializing elements
     */
    protected ConfigList(JsonArray handle, Collection<V> c) {
        this(handle);
        addAll(c);
    }

    @Override
    public V get(int index) {
        checkElementIndex(index);
        return getNode(index + 1).value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(V element) {
        Node<V> prev = (size == 0) ? head : getNode(size - 1);

        prev.next = new Node<>(element, footer, prev);
        size += 1;
        modCount += 1;

        this.jsonHandle.add(GsonFactory.getGson().toJsonTree(element));
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends V> coll) {
        for (V element : coll) {
            this.add(element);
        }

        return true;
    }

    /* (non-Javadoc)
     * @see java.util.List#set(int, java.lang.Object)
     */
    @Override
    public V set(int index, V element) {
        checkElementIndex(index);
        this.jsonHandle.set(index, GsonFactory.getGson().toJsonTree(element));

        Node<V> node = getNode(index);
        final V oldValue = node.value;

        node.value = element;

        return oldValue;
    }

    @Override
    public V remove(int index) {
        checkElementIndex(index);
        this.jsonHandle.remove(index);

        Node<V> previous = getNode(index - 1);
        final V value = previous.next.value;
        previous.next = previous.next.next;

        size -= 1;
        modCount -= 1;

        return value;
    }

    @Override
    public boolean remove(Object element) {
        remove(indexOf(element));
        return true;
    }

    /* (non-Javadoc)
     * @see java.util.List#removeAll(java.util.Collection)
     */
    @Override
    public boolean removeAll(Collection<?> coll) {
        for (Object o : coll) {
            this.remove(o);
        }

        return true;
    }

    /* (non-Javadoc)
     * @see java.util.List#removeRange(int, int)
     */
    @Override
    protected void removeRange(int start, int end) {
        for (int i = start; i < end; i++) {
            remove(i);
        }
    }

    /* (non-Javadoc)
     * @see java.util.List#clear()
     */
    @Override
    public void clear() {
        removeAll(new ConfigList<>(jsonHandle, this));
        this.jsonHandle = new JsonArray();
    }

    /* (non-Javadoc)
     * @see java.util.List#add(int, java.lang.Object)
     */
    @Override
    public void add(int index, V element) {
        TridentLogger.error(new UnsupportedOperationException("Cannot invoke on Lists from Config"));
    }

    /* (non-Javadoc)
     * @see java.util.List#addAll(int, java.util.Collection)
     */
    @Override
    public boolean addAll(int arg0, Collection<? extends V> arg1) {
        TridentLogger.error(new UnsupportedOperationException("Cannot invoke on Lists from Config"));
        return false;
    }

    /* (non-Javadoc)
     * @see java.util.List#retainAll(java.util.Collection)
     */
    @Override
    public boolean retainAll(Collection<?> arg0) {
        TridentLogger.error(new UnsupportedOperationException("Cannot invoke on Lists from Config"));
        return false;
    }

    /* (non-Javadoc)
     * @see java.util.List#subList(int, int)
     */
    @Override
    public List<V> subList(int arg0, int arg1) {
        TridentLogger.error(new UnsupportedOperationException("Cannot invoke on Lists from Config"));
        return null;
    }

    /* (non-Javadoc)
     * @see java.util.List#toArray()
     */
    @Override
    public V[] toArray() {
        TridentLogger.error(new UnsupportedOperationException("Cannot invoke on Lists from Config"));
        return null;
    }

    /* (non-Javadoc)
     * @see java.util.List#toArray(java.lang.Object[])
     */
    @Override
    public <T> T[] toArray(T[] arg0) {
        TridentLogger.error(new UnsupportedOperationException("Cannot invoke on Lists from Config"));
        return null;
    }

    private void checkElementIndex(int index) {
        if (index < 0 && index > size)
            TridentLogger.error(new IndexOutOfBoundsException("Index: " + index + ", Size: " + size));
    }

    private Node<V> getNode(int index) {
        Node<V> node = head;

        if (index > (size >> 2)) {
            for (int i = 0; i < index && node.next != footer; i += 1) {
                node = node.next;
            }
        } else {
            for (int i = (size - 1); i < index && node.prev != head; i -= 1) {
                node = node.prev;
            }
        }

        return node;
    }

    @AccessNoDoc private static class Node<V> {
        V value;
        Node<V> next;
        Node<V> prev;

        private Node(V value, Node<V> next, Node<V> prev) {
            this.value = value;
            this.next = next;
        }
    }
}
