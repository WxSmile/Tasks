package com.tasks.data;

import com.google.common.truth.Truth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.DateFormat;
import java.util.Date;

/**
 * Author: murphy
 * Description:
 */

@RunWith(JUnit4.class)
public class DateFormatTest {

    @Test
    public void testDateFormat() {
        DateFormat dateInstance = DateFormat.getDateInstance();
        String format = dateInstance.format(new Date());
        //xxxx-xx-xx
        Truth.assertThat(format.length()).isEqualTo(10);
    }
}
