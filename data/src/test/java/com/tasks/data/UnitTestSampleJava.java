package com.tasks.data;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
@Config(sdk = 28)
public class UnitTestSampleJava {

    private static final String FAKE_STRING = "data";
    private Context context = ApplicationProvider.getApplicationContext();

    @Test
    public void readStringFromContext_LocalizedString() {
        // Given a Context object retrieved from Robolectric...
        ClassUnderTest myObjectUnderTest = new ClassUnderTest(context);

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.getHelloWorldString();

        // ...then the result should be the expected one.
        assertThat(result).isEqualTo(FAKE_STRING);
    }
}