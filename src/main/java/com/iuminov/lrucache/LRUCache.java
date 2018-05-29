package com.iuminov.lrucache;

public class LRUCache {
    private final int capacity;
    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (head == null) {
            return -1;
        }

        Node node = findNodeByKey(key);

        if (node != null) {
            putToHead(node);
            return node.value;
        }
        return -1;
    }

    public void put(int key, int value) {
        if (head == null) {
            head = new LRUCache.Node(key, value);
            tail = head;
            return;
        }

        LRUCache.Node node = findNodeByKey(key);
        if (node != null) {
            node.value = value;
            putToHead(node);
            return;
        }

        if (tail.index == capacity - 1) {
            tail = tail.previous;
            tail.next = null;
        }

        LRUCache.Node newHead = new LRUCache.Node(key, value);
        newHead.next = head;
        head.previous = newHead;
        head = newHead;
        alignIndices();
    }

    private void putToHead(Node node) {
        if (node == head) {
            return;
        }

        node.previous.next = (node.next == null) ? null : node.next;
        tail = (node.next == null) ? node.previous : tail;
        if (node.next != null) {
            node.next.previous = node.previous;
        }

        node.previous = null;
        node.index = 0;
        node.next = head;
        head.previous = node;
        head = node;
        alignIndices();
    }

    private Node findNodeByKey(int key) {
        Node tempNode = head;

        while (tempNode != null) {
            if (tempNode.key == key) {
                return tempNode;
            }
            tempNode = tempNode.next;
        }

        return null;
    }

    private void alignIndices() {
        Node tempNode = head.next;

        while (tempNode != null){
            tempNode.index = tempNode.previous.index + 1;
            tempNode = tempNode.next;
        }
    }

    private static class Node {
        private int key;
        private int value;
        private int index;
        private Node next;
        private Node previous;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}
