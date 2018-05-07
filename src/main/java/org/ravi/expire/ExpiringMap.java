package org.ravi.expire;

/**
 * Time:
 * <p>
 * 12:00:00 - map.put(10, 25, 5000)
 * <p>
 * 12:00:04 - map.get(10) -> 25
 * <p>
 * 12:00:06 - map.get(10) -> null
 */
public interface ExpiringMap<K, V> {
    V put(K key, V value, long ttl);

    V get(K key);

    V remove(K key);

    void clear();

    int size();
}
