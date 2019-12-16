package com.tasks.taskadd;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.tasks.R;
import com.tasks.databinding.FragmentTaskAddBinding;
import com.tasks.utils.SpaceItemDecoration;
import com.tasks.utils.TaskDateRangeLimiter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import io.reactivex.disposables.Disposable;

import static com.tasks.utils.UnitUtil.dp2px;

/**
 * Author: murphy
 * Description:
 */
public class AddTaskBottomDialogFragment extends BottomSheetDialogFragment {

    private FragmentTaskAddBinding dataBinding;
    private AddTaskViewModel addTaskViewModel;
    private Disposable subscribe;

    public static AddTaskBottomDialogFragment getInstance() {
        return new AddTaskBottomDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_add, container, false);
        dataBinding.btnAddTask.setOnClickListener(this::addTask);
        dataBinding.tvDate.setOnClickListener(this::setDate);
        dataBinding.tvTime.setOnClickListener(this::setTime);
        return dataBinding.getRoot();
    }

    private void setTime(View view) {
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this::onTimeSelected, true);
        timePickerDialog.setAccentColor(ActivityCompat.getColor(requireContext(), R.color.colorPrimary));
        timePickerDialog.show(requireFragmentManager(), "time_picker_dialog");
    }

    private void setDate(View view) {
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this::onDateSelected);
        datePickerDialog.setDateRangeLimiter(new TaskDateRangeLimiter());
        datePickerDialog.setAccentColor(ActivityCompat.getColor(requireContext(), R.color.colorPrimary));
        datePickerDialog.show(requireFragmentManager(), "date_picker_dialog");
    }

    private void onTimeSelected(TimePickerDialog pickerDialog, int hourOfDay, int minute, int second) {
        addTaskViewModel.setTime(hourOfDay, minute, second);
    }

    private void onDateSelected(DatePickerDialog dialog, int year, int month, int dayOfMonth) {
        addTaskViewModel.setDate(year, month, dayOfMonth);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addTaskViewModel = ViewModelProviders.of(requireActivity()).get(AddTaskViewModel.class);
        addTaskViewModel.resetDate();
        dataBinding.setAddTaskModel(addTaskViewModel);
        dataBinding.setLifecycleOwner(this);

        dataBinding.rcvCategory.setAdapter(new CategoryAdapter());
        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(((int) dp2px(10)));
        dataBinding.rcvCategory.addItemDecoration(spaceItemDecoration);
        addTaskViewModel.fetchCategories();

    }

    private void addTask(View view) {
        String taskName = dataBinding.etTaskName.getText().toString();
        if (addTaskViewModel.checkAndAddTask(taskName)) {
            subscribe = addTaskViewModel.getAddTaskEvent().subscribe(this::onAddTaskSuccess);
        }
    }

    private void onAddTaskSuccess() {
        dismiss();

        if (requireActivity() instanceof OnAddTaskListener) {
            ((OnAddTaskListener) requireActivity()).onTaskAdded("Work");
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (subscribe != null) subscribe.dispose();
    }

    public interface OnAddTaskListener {
        void onTaskAdded(String taskCategory);
    }
}
