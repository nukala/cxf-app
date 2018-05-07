package org.ravi.expire;

import org.junit.After;
import org.junit.Test;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
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
        map.put(1, "napSometimeAndVerify", 99);

        assertThat(map.get(1), equalTo("napSometimeAndVerify"));
        sleepUninterruptibly(150, MILLISECONDS);
        assertThat(map.get(1), nullValue());
    }

    @Test
    public void twoNapsThenVerify() {
        map.put(1, "twoNapsThenVerify", 99);

        assertThat(map.get(1), equalTo("twoNapsThenVerify"));
        sleepUninterruptibly(55, MILLISECONDS);
        assertThat(map.get(1), equalTo("twoNapsThenVerify"));

        sleepUninterruptibly(55, MILLISECONDS);
        assertThat(map.get(1), nullValue());
    }

    @Test
    public void verifySizeAfterNap() {
        map.put(53, "verifySizeAfterNap", 59);

        assertThat(map.size(), equalTo(1));

        sleepUninterruptibly(75, MILLISECONDS);
        assertThat(map.size(), equalTo(0));
    }

    @Test
    public void sameSizeAfterShortNap() {
        map.put(63, "sameSizeWithNoNap", 25);
        map.put(1, "one", 99900L);

        assertThat(map.size(), equalTo(2));

        sleepUninterruptibly(18, MILLISECONDS);
        assertThat(map.size(), equalTo(2));
    }
}
