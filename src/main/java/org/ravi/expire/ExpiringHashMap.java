package org.ravi.expire;


import java.util.HashMap;


public class ExpiringHashMap<K, V> implements ExpiringMap<K, V> {
    private final HashMap<K, ExpiringValue<V>> map;

    public ExpiringHashMap() {
        super();

        this.map = new HashMap<>();
    }

    public V put(K key, V value, long ttl) {
        ExpiringValue<V> old = map.put(key, new ExpiringValue(value, ttl));
        return old == null ? null : old.value;
    }

    public V get(K key) {
        ExpiringValue<V> expiringValue = map.get(key);

        if (expiringValue == null) {
            return null;
        }

        if (System.currentTimeMillis() < expiringValue.expiresAt) {
            return expiringValue.value;
        }
        return null;
    }

    public void clear() {
        map.clear();
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
