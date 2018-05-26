package com.iuminov.lrucache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.LinkedList;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class LRUCacheTest {
    LRUCache cache;


    @Before
    public void init() {
        cache = new LRUCache(5);
    }

    @Test
    public void testGetHappyPath() {
        cache.put(1, 100);
        cache.put(2, 200);
        cache.put(4, 400);
        LinkedList l = new LinkedList();
        l.clear();
        int expectedResult = 200;
        int actualResult = cache.get(2);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetSadPathNoSuchElement() {
        cache.put(1, 100);
        cache.put(2, 200);
        cache.put(4, 400);

        int expectedResult = -1;
        int actualResult = cache.get(3);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetSadPathEmptyCache() {
        int expectedResult = -1;
        int actualResult = cache.get(10);

        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testPutOverrideHappyPath() {
        cache.put(1, 100);
        cache.put(2, 200);
        cache.put(1, 400);

        int expectedResult = 400;
        int actualResult = cache.get(1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetHappyPathFullCache() {
        cache.put(1, 100);
        cache.put(2, 200);
        cache.put(4, 400);
        cache.put(8, 400);
        cache.put(16, 400);

        cache.put(8, 800);
        cache.put(16, 1600);

        int expectedResult = 800;
        int actualResult = cache.get(8);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetSadPathLeastRecentUsedItemGotInvalidated() {
        cache.put(1, 100);
        cache.put(2, 200);
        cache.put(4, 400);
        cache.put(8, 400);
        cache.put(16, 400);

        cache.put(1, 1000);
        cache.put(2, 2000);
        cache.put(4, 4000);
        cache.put(8, 8000);

        cache.put(32, 32000);

        int expectedResult = -1;
        int actualResult = cache.get(16);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetSadPathPuttingElementsAboveCapacity() {
        cache.put(1, 100);
        cache.put(2, 200);
        cache.put(4, 400);
        cache.put(8, 400);
        cache.put(16, 400);

        cache.put(32, 400);

        int expectedResult = -1;
        int actualResult = cache.get(1);

        assertEquals(expectedResult, actualResult);
    }
}