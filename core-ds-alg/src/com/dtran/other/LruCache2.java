package com.dtran.other;

import java.util.HashMap;
import java.util.Map;

public class LruCache2 {

    public static class LruCache {

        public static class Node {
            String key;
            String val;
            Node prev = null;
            Node next = null;
            public Node(String key, String val) {
                this.key = key;
                this.val = val;
            }

            @Override
            public String toString() {
                return String.format("[%s, %s]", key, val);
            }
        }

        Map<String, Node> lruCache = new HashMap<>();
        int size;
        Node first = null;
        Node last = null;

        public LruCache(int size) {
            this.size = size;
        }

        public void put(String key, String val) {
            // if its in the map
            if (lruCache.containsKey(key)) {
                // update value
                Node node = lruCache.get(key);
                node.val = val;
                // remove from linked list <- linked list operations, separate out into helper methods
                remove(node);
                // add to front of linked list
                addToFront(node);
            } else {
                // if its not in the map
                // insert it into map and create a new node
                Node node = new Node(key, val);
                lruCache.put(key, node);
                // add to front of linked list
                addToFront(node);
            }

            if (lruCache.size() > size) {
                // if the size is bigger than size
                // evict last node
                evict();
            }
        }

        private void remove(Node node) {
            if (node.prev == null) {
                first = node.next;
            } else {
                node.prev.next = node.next;
            }
            if (node.next == null) {
                last = node.prev;
            } else {
                node.next.prev = node.prev;
            }
        }

        private void addToFront(Node node) {
            if (first == null) {
                first = node;
                last = node;
            } else {
                node.next = first;
                first.prev = node;
                first = node;
            }
        }

        private void evict() {
            if (last != null) {
                lruCache.remove(last.key);
                remove(last);
            }
        }

        public String get(String key) {
            // if its in the map
            if (lruCache.containsKey(key)) {
                Node node = lruCache.get(key);
                // remove from linked list
                remove(node);
                // add to front of linked list
                addToFront(node);
                // return the value
                return node.val;
            }
            // if its not in the map
            // return null
            return null;
        }

    }

    public static void main(String[] args) {

        LruCache cache = new LruCache(3);
        cache.put("one", "one");
        System.out.println(cache.lruCache + " " + cache.first + " " + cache.last);
        cache.put("two", "two");
        System.out.println(cache.lruCache + " " + cache.first + " " + cache.last);
        cache.put("three", "three");
        System.out.println(cache.lruCache + " " + cache.first + " " + cache.last);
        cache.put("four", "four");
        System.out.println(cache.lruCache + " " + cache.first + " " + cache.last);
        cache.get("three");
        System.out.println(cache.lruCache + " " + cache.first + " " + cache.last);
        cache.get("four");
        System.out.println(cache.lruCache + " " + cache.first + " " + cache.last);
    }
}
