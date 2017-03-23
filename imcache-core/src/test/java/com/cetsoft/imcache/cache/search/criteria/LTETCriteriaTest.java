/*
 * Copyright (C) 2015 KyleDing, http://www.kyleding.com
 *
 *
 * Author : Kyle Ding
 * Date   : Jun 2, 2014
 */
package com.KyleDing.imcache.cache.search.criteria;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.KyleDing.imcache.cache.search.index.CacheIndex;

/**
 * The Class LTETCriteriaTest.
 */
public class LTETCriteriaTest {

    /** The expected value. */
    @Mock
    Object expectedValue;

    /** The result. */
    @Mock
    List<Object> result;

    /** The cache index. */
    @Mock
    CacheIndex cacheIndex;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Meets.
     */
    @Test
    public void meets() {
        LTETCriteria criteria = new LTETCriteria("a", expectedValue);
        doReturn(result).when(cacheIndex).lessThanOrEqualsTo(expectedValue);
        List<Object> actualResult = criteria.meets(cacheIndex);
        verify(cacheIndex).lessThanOrEqualsTo(expectedValue);
        assertEquals(result, actualResult);
    }

}
