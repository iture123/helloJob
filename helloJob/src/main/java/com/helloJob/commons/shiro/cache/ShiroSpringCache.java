/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Dreamlu (596392912@qq.com).
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.helloJob.commons.shiro.cache;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.CacheException;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;

/**
 * 使用spring-cache作为shiro缓存
 * @author L.cm
 *
 */
@SuppressWarnings("unchecked")
public class ShiroSpringCache<K, V> implements org.apache.shiro.cache.Cache<K, V> {
	private static final Logger logger = LogManager.getLogger(ShiroSpringCache.class);
	
	private final org.springframework.cache.Cache cache;
	private final boolean hasEhcache;
	
	public ShiroSpringCache(Cache cache, boolean hasEhcache) {
		if (cache == null) {
			throw new IllegalArgumentException("Cache argument cannot be null.");
		}
		this.cache = cache;
		this.hasEhcache = hasEhcache;
	}

	@Override
	public V get(K key) throws CacheException {
		if (logger.isTraceEnabled()) {
			logger.trace("Getting object from cache [" + this.cache.getName() + "] for key [" + key + "]key type:" + key.getClass());
		}
		ValueWrapper valueWrapper = cache.get(key);
		if (valueWrapper == null) {
			if (logger.isTraceEnabled()) {
				logger.trace("Element for [" + key + "] is null.");
			}
			return null;
		}
		return (V) valueWrapper.get();
	}

	@Override
	public V put(K key, V value) throws CacheException {
		if (logger.isTraceEnabled()) {
			logger.trace("Putting object in cache [" + this.cache.getName() + "] for key [" + key + "]key type:" + key.getClass());
		}
		V previous = get(key);
		cache.put(key, value);
		return previous;
	}

	@Override
	public V remove(K key) throws CacheException {
		if (logger.isTraceEnabled()) {
			logger.trace("Removing object from cache [" + this.cache.getName() + "] for key [" + key + "]key type:" + key.getClass());
		}
		V previous = get(key);
		cache.evict(key);
		return previous;
	}

	@Override
	public void clear() throws CacheException {
		if (logger.isTraceEnabled()) {
			logger.trace("Clearing all objects from cache [" + this.cache.getName() + "]");
		}
		cache.clear();
	}

	@Override
	public int size() {
		if (hasEhcache) {
			Object nativeCache = cache.getNativeCache();
			if (nativeCache instanceof net.sf.ehcache.Ehcache) {
				net.sf.ehcache.Ehcache ehcache = (net.sf.ehcache.Ehcache) nativeCache;
				return ehcache.getSize();
			}
		}
		return 0;
	}

	@Override
	public Set<K> keys() {
		if (hasEhcache) {
			Object nativeCache = cache.getNativeCache();
			if (nativeCache instanceof net.sf.ehcache.Ehcache) {
				net.sf.ehcache.Ehcache ehcache = (net.sf.ehcache.Ehcache) nativeCache;
				return new HashSet<>(ehcache.getKeys());
			}
		}
		return Collections.emptySet();
	}

	@Override
	public Collection<V> values() {
		if (hasEhcache) {
			Object nativeCache = cache.getNativeCache();
			if (nativeCache instanceof net.sf.ehcache.Ehcache) {
				net.sf.ehcache.Ehcache ehcache = (net.sf.ehcache.Ehcache) nativeCache;
				List keys = ehcache.getKeys();
				Map<Object, net.sf.ehcache.Element> elementMap = ehcache.getAll(keys);
				List<Object> values = new ArrayList<>();
				for (net.sf.ehcache.Element element : elementMap.values()) {
					values.add(element.getObjectValue());
				}
				return (Collection<V>) values;
			}
		}
		return Collections.emptySet();
	}

	@Override
	public String toString() {
		return "ShiroSpringCache [" + this.cache.getName() + "]";
	}
}
