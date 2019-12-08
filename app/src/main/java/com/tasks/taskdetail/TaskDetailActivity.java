package com.tasks.taskdetail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tasks.R;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_detail_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TaskDetailFragment.newInstance())
                    .commitNow();
        }
    }
}
