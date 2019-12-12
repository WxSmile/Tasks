package com.tasks.data.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

import static com.google.common.truth.Truth.assertThat;
import static com.tasks.data.util.DateUtils.getCurrentTime;
import static com.tasks.data.util.DateUtils.getNextTodayZeroClockTime;
import static com.tasks.data.util.DateUtils.getTodayZeroClockTime;

/**
 * Author: murphy
 * Description:
 */

@RunWith(JUnit4.class)
public class DateUtilsTest {

    @Test
    public void testTodayTimeStamp() {
        long time = getTodayZeroClockTime().getTime();
        long time1 = getCurrentTime().getTime();

        assertThat(time).isEqualTo(time1);

    }

    @Test
    public void testHotDateLessThanHotStamp() {
        Date hotDate = TestUtils.getHotDate();
        Date nextTodayZeroClockTime = getNextTodayZeroClockTime();
        long time = hotDate.getTime();
        long time1 = nextTodayZeroClockTime.getTime();

        assertThat(time).isLessThan(time1);
        assertThat(time1 - time).isAtMost(86400000L);
        assertThat(time1 - time).isAtLeast(0L);
    }
}
