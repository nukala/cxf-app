package org.ravi.expire;


import java.util.HashMap;
import java.util.Map;


public class ExpiringHashMap<K, V> implements ExpiringMap<K, V> {
    private final HashMap<K, ExpiringHashMap.ExpiringValue<V>> map;

    public ExpiringHashMap() {
        super();

        this.map = new HashMap<>();
    }

    // expensive, is this needed?
    private void cleanExpired() {
        // stream barfs!
        for (Map.Entry<K, ExpiringValue<V>> entry : map.entrySet()) {
            if (System.currentTimeMillis() > entry.getValue().expiresAt) {
                map.remove(entry.getKey());
            }
        }
    }

    @Override
    public V put(K key, V value, long ttl) {
        ExpiringValue<V> old = map.put(key, new ExpiringValue(value, ttl));
        return old == null ? null : old.value;
    }

    @Override
    public V get(K key) {
        ExpiringValue<V> expiringValue = map.get(key);

        if (expiringValue == null) {
            return null;
        }

        if (System.currentTimeMillis() < expiringValue.expiresAt) {
            return expiringValue.value;
        }

        remove(key);
        return null;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public V remove(K key) {
        ExpiringValue<V> removed = map.remove(key);

        return removed == null ? null : removed.value;
    }

    @Override
    public int size() {
        cleanExpired();
        return map.size();
    }

    private static class ExpiringValue<V> {
        private final long expiresAt;
        private final V value;

        ExpiringValue(V value, long ttl) {
            super();

            this.value = value;
            this.expiresAt = System.currentTimeMillis() + ttl;
        }
    }
}
