/*
 * Copyright (C) 2014 Yusuf Aytas, http://www.yusufaytas.com
 *
 *
 * Author : Kyle Ding
 * Date   : Jan 6, 2014
 */
package com.KyleDing.imcache.spring;

import com.KyleDing.imcache.cache.Cache;
import com.KyleDing.imcache.cache.CacheCategoryEnum;
import com.KyleDing.imcache.cache.CacheLoader;
import com.KyleDing.imcache.cache.EvictionListener;
import com.KyleDing.imcache.cache.builder.CacheBuilder;
import com.KyleDing.imcache.cache.builder.SearchableCacheBuilder;
import com.KyleDing.imcache.cache.search.IndexHandler;
import com.KyleDing.imcache.heap.tx.TransactionCommitter;
import com.KyleDing.imcache.offheap.OffHeapCache;
import com.KyleDing.imcache.offheap.bytebuffer.OffHeapByteBufferStore;
import com.KyleDing.imcache.serialization.Serializer;

/**
 * The Class SpringCacheBuilder.
 */
public class SpringCacheBuilder extends SearchableCacheBuilder {

    /** The type. */
    protected CacheCategoryEnum type;

    /** The concurrency level. */
    private int concurrencyLevel = OffHeapCache.DEFAULT_CONCURRENCY_LEVEL;

    /** The eviction period. */
    private long evictionPeriod = OffHeapCache.DEFAULT_EVICTION_PERIOD;

    /** The buffer cleaner period. */
    private long bufferCleanerPeriod = OffHeapCache.DEFAULT_BUFFER_CLEANER_PERIOD;

    /** The buffer cleaner threshold. */
    private float bufferCleanerThreshold = OffHeapCache.DEFAULT_BUFFER_CLEANER_THRESHOLD;

    /** The serializer. */
    private Serializer<Object> serializer = SERIALIZER;

    /** The buffer store. */
    private OffHeapByteBufferStore bufferStore;

    private TransactionCommitter<Object, Object> transactionCommitter;

    private String redisHost = "127.0.0.1";

    private int redisPort = 6379;

    private int heapCapacity = 10000;

    /**
     * Instantiates a new spring cache builder.
     */
    public SpringCacheBuilder() {
    	this.type = CacheCategoryEnum.CONCURRENTHEAP;
    }

    /**
     * Instantiates a new spring cache builder.
     */
    public SpringCacheBuilder(String type) {
        super();
        this.type = CacheCategoryEnum.valueOf(type.toUpperCase());
    }

    /*
     * (non-Javadoc)
     *
     * @see com.KyleDing.imcache.cache.builder.CacheBuilder#build()
     */
	public <K, V> Cache<K, V> build() {

		switch (this.type) {

		case HEAP:
			return CacheBuilder.heapCache().cacheLoader(cacheLoader).evictionListener(evictionListener)
					.indexHandler(indexHandler).capacity(heapCapacity).build();

		case TRANSACTIONALHEAP:
			return CacheBuilder.transactionalHeapCache().cacheLoader(cacheLoader).evictionListener(evictionListener)
					.indexHandler(indexHandler).transactionCommitter(transactionCommitter).build();

		case OFFHEAP:
			return CacheBuilder.offHeapCache().cacheLoader(cacheLoader).evictionListener(evictionListener)
					.indexHandler(indexHandler).concurrencyLevel(concurrencyLevel)
					.bufferCleanerPeriod(bufferCleanerPeriod).bufferCleanerThreshold(bufferCleanerThreshold)
					.evictionPeriod(evictionPeriod).serializer(serializer).storage(bufferStore).build();

		case VERSIONEDOFFHEAP:
			return CacheBuilder.versionedOffHeapCache().cacheLoader(cacheLoader).evictionListener(evictionListener)
					.indexHandler(indexHandler).concurrencyLevel(concurrencyLevel)
					.bufferCleanerPeriod(bufferCleanerPeriod).bufferCleanerThreshold(bufferCleanerThreshold)
					.evictionPeriod(evictionPeriod).serializer(serializer).storage(bufferStore).build();

		case REDIS:
			return CacheBuilder.redisCache().cacheLoader(cacheLoader).evictionListener(evictionListener)
					.serializer(serializer).concurrencyLevel(concurrencyLevel).hostName(redisHost).port(redisPort)
					.build();

		default:
			return CacheBuilder.concurrentHeapCache().cacheLoader(cacheLoader).evictionListener(evictionListener)
					.indexHandler(indexHandler).build();
		}
	}

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(String type) {
    	this.type = CacheCategoryEnum.valueOf(type.toUpperCase());
    }

    /**
     * Sets the concurrency level.
     *
     * @param concurrencyLevel the new concurrency level
     */
    public void setConcurrencyLevel(int concurrencyLevel) {
        this.concurrencyLevel = concurrencyLevel;
    }

    /**
     * Sets the eviction period.
     *
     * @param evictionPeriod the new eviction period
     */
    public void setEvictionPeriod(long evictionPeriod) {
        this.evictionPeriod = evictionPeriod;
    }

    /**
     * Sets the buffer cleaner period.
     *
     * @param bufferCleanerPeriod the new buffer cleaner period
     */
    public void setBufferCleanerPeriod(long bufferCleanerPeriod) {
        this.bufferCleanerPeriod = bufferCleanerPeriod;
    }

    /**
     * Sets the buffer cleaner threshold.
     *
     * @param bufferCleanerThreshold the new buffer cleaner threshold
     */
    public void setBufferCleanerThreshold(float bufferCleanerThreshold) {
        this.bufferCleanerThreshold = bufferCleanerThreshold;
    }

    /**
     * Sets the serializer.
     *
     * @param serializer the new serializer
     */
    public void setSerializer(Serializer<Object> serializer) {
        this.serializer = serializer;
    }

    /**
     * Sets the cache loader.
     *
     * @param cacheLoader the cache loader
     */
    public void setCacheLoader(CacheLoader<Object, Object> cacheLoader) {
        this.cacheLoader = cacheLoader;
    }

    /**
     * Sets the eviction listener.
     *
     * @param evictionListener the eviction listener
     */
    public void setEvictionListener(EvictionListener<Object, Object> evictionListener) {
        this.evictionListener = evictionListener;
    }

    /**
     * Sets the index handler.
     *
     * @param indexHandler the index handler
     */
    public void setIndexHandler(IndexHandler<Object, Object> indexHandler) {
        this.indexHandler = indexHandler;
    }

    /**
     * Sets the buffer store.
     *
     * @param bufferStore the new buffer store
     */
    public void setBufferStore(OffHeapByteBufferStore bufferStore) {
        this.bufferStore = bufferStore;
    }

    /**
     * Sets the transaction committer.
     *
     * @param transactionCommitter the transaction committer
     */
    public void setTransactionCommitter(TransactionCommitter<Object, Object> transactionCommitter) {
        this.transactionCommitter = transactionCommitter;
    }

    /**
     * Sets the REDIS server host.
     *
     * @param redisHost
     */
	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}

	/**
	 * Sets the REDIS server PORT.
	 *
	 * @param redisPort
	 */
	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}

	/**
	 * Sets the HEAP capacity. If not set then it defaults to 10000.
	 *
	 * @param heapCapacity
	 */
	public void setHeapCapacity(int heapCapacity) {
		this.heapCapacity = heapCapacity;
	}

}
