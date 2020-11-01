package work.newproject.asus.as.swadeshiebazaar.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Calendar;


public class DatePickerFragment extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "DatePickerFragment";
    final Calendar c = Calendar.getInstance();
    int year, month, day;
    DatePickerDialog datePickerDialog;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // Return a new instance of DatePickerDialog
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);
        datePickerDialog = new DatePickerDialog(getActivity(), DatePickerFragment.this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();

        //    datePickerDialog.setCustomTitle(tv);
        return datePickerDialog;
    }

    // called when a date has been selected
    public void onDateSet(DatePicker view, int year, int month, int day) {


        String monthString = String.valueOf(month+1);

        String date= String.valueOf(day);


        if (date.length() == 1){
            date="0"+date;
        }

        if (monthString.length() == 1) {
            monthString = "0" + monthString;
        }

        String selectedDate = year + "-" + monthString + "-" + date;
        getTargetFragment().onActivityResult(
                getTargetRequestCode(),
                Activity.RESULT_OK,
                new Intent().putExtra("selectedDate", selectedDate)
        );
    }
}

