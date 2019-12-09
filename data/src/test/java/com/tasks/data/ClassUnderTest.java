package com.tasks.data;

import android.content.Context;

/**
 * Author: murphy
 * Description:
 */
class ClassUnderTest {
    private Context context;

    public ClassUnderTest(Context context) {

        this.context = context;
    }

    public String getHelloWorldString() {
        return context.getString(R.string.app_name);
    }
}
