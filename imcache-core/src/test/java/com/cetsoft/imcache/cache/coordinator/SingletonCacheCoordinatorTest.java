/*
 * Copyright (C) 2015 KyleDing, http://www.kyleding.com
 *
 *
 * Author : Christian Bourque
 * Date   : August 1, 2015
 */
package com.KyleDing.imcache.cache.coordinator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.KyleDing.imcache.cache.CacheLoader;
import com.KyleDing.imcache.cache.EvictionListener;
import com.KyleDing.imcache.cache.ImcacheType;
import com.KyleDing.imcache.cache.search.IndexHandler;
import com.KyleDing.imcache.heap.HeapCache;

/**
 * The class SingletonCacheCoordinatorTest.
 */
public class SingletonCacheCoordinatorTest {

    /** The cache loader. */
    @Mock
    CacheLoader<Object, Object> cacheLoader;

    /** The eviction listener. */
    @Mock
    EvictionListener<Object, Object> evictionListener;

    /** The index handler. */
    @Mock
    IndexHandler<Object, Object> indexHandler;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Get instance.
     */
    @Test
    public void getInstance() {
        SingletonCacheCoordinator coordinator1 = SingletonCacheCoordinator.getInstance();
        SingletonCacheCoordinator coordinator2 = SingletonCacheCoordinator.getInstance();

        assertEquals(coordinator1, coordinator2);
    }

    /**
     * Clear all.
     */
    @Test
    public void clearAll() {
        HeapCache<Object, Object> c1 = new HeapCache<Object, Object>(cacheLoader, evictionListener, indexHandler, 100);
        c1.put(1, new Object());
        c1.put(2, new Object());
        c1.put(3, new Object());

        HeapCache<Object, Object> c2 = new HeapCache<Object, Object>(cacheLoader, evictionListener, indexHandler, 100);
        c2.put(1, new Object());
        c2.put(2, new Object());
        c2.put(3, new Object());

        SingletonCacheCoordinator coordinator = SingletonCacheCoordinator.getInstance();

        coordinator.addCache(new ImcacheType(), c1);
        coordinator.addCache(new ImcacheType(), c2);

        assertEquals(3, c1.size());
        assertEquals(3, c2.size());

        coordinator.clearAll();

        assertEquals(0, c1.size());
        assertEquals(0, c2.size());
    }

}
