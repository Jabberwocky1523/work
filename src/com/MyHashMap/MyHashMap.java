package com.MyHashMap;

public class MyHashMap<K, V> implements MyMap<K, V> {
    class Node<K, V> implements Entry<K, V> {
        private K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    Node<K, V>[] table = null;
    int size;
    float DEFAULT_LOAD_FACTOR = 0.75f;
    int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    @Override
    public V put(K key, V value) {
        if (table == null) {
            table = new Node[DEFAULT_INITIAL_CAPACITY];
        }
        if (size > (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY)) {
            resize();
        }
        int index = getIndex(key, DEFAULT_INITIAL_CAPACITY);
        Node<K, V> node = table[index];

        if (node == null) {
            node = new Node<K, V>(key, value, null);
            size++;
        } else {
            Node<K, V> newNode = node;
            while (newNode != null) {
                if (node.getKey().equals(key) || node.getKey() == key) {
                    return node.setValue(value);
                } else {
                    node = new Node<K, V>(key, value, node);
                    size++;
                }
                newNode = node.next;
            }

        }
        table[index] = node;
        return node.value;
    }

    public int getIndex(K k, int length) {
        int hashCode = k.hashCode();
        int index = hashCode % length;
        return index;
    }

    @Override
    public V get(K k) {
        Node<K, V> node = getNode(table[getIndex(k, DEFAULT_INITIAL_CAPACITY)], k);
        return node == null ? null : node.value;
    }

    public Node<K, V> getNode(Node<K, V> node, K k) {
        while (node != null) {
            if (node.getKey().equals(k)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public int remove(K k) {
        Node CUR = table[getIndex(k, DEFAULT_INITIAL_CAPACITY)];
        Node<K, V> node = getNode(CUR, k);
        if (node == null) {
            return 0;
        } else {
            Node<K, V> curnode = CUR;
            if (curnode.key.equals(k)) {
                table[getIndex(k, DEFAULT_INITIAL_CAPACITY)] = curnode.next;
                return 1;
            }
            while (!curnode.next.key.equals(k)) {
                curnode = curnode.next;
            }
            curnode.next = curnode.next.next;
            return 1;
        }
    }

    @Override
    public int size() {
        return size;
    }

    private void resize() {
        Node<K, V>[] newTable = new Node[DEFAULT_INITIAL_CAPACITY << 1];
        for (int i = 0; i < table.length; i++) {
            Node<K, V> oldNode = table[i];
            while (oldNode != null) {
                table[i] = null;
                K oldK = oldNode.key;
                int index = getIndex(oldK, newTable.length);
                Node<K, V> oldNext = oldNode.next;
                oldNode.next = newTable[index];
                newTable[index] = oldNode;
                oldNode = oldNext;
            }
        }
        table = newTable;
        DEFAULT_INITIAL_CAPACITY = newTable.length;
        newTable = null;
    }

}
