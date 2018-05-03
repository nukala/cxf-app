package org.ravi.expire;

import org.junit.After;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class ExpiringMapTest {
    private ExpiringMap<Integer, String> map = new ExpiringHashMap<>();

    @After
    public void afterEveryTest() {
        map.clear();
    }

    @Test
    public void immediateTest() {
        String old = map.put(1, "one", 99);
        assertThat(old, is(nullValue()));

        assertThat(map.get(1), equalTo("one"));
    }

    @Test
    public void napSometimeAndVerify() {
        ExpiringMap<Integer, String> map = new ExpiringHashMap<>();

        String old = map.put(1, "napSometimeAndVerify", 99);
        assertThat(old, is(nullValue()));

        assertThat(map.get(1), equalTo("napSometimeAndVerify"));
        sleepUninterruptibly(150, TimeUnit.MILLISECONDS);
        assertThat(map.get(1), nullValue());
    }

    @Test
    public void twoNapsThenVerify() {
        ExpiringMap<Integer, String> map = new ExpiringHashMap<>();

        String old = map.put(1, "twoNapsThenVerify", 99);
        assertThat(old, is(nullValue()));

        assertThat(map.get(1), equalTo("twoNapsThenVerify"));
        sleepUninterruptibly(75, TimeUnit.MILLISECONDS);
        assertThat(map.get(1), equalTo("twoNapsThenVerify"));

        sleepUninterruptibly(75, TimeUnit.MILLISECONDS);
        assertThat(map.get(1), nullValue());
    }
}
