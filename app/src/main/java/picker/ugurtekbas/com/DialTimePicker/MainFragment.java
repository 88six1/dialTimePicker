package picker.ugurtekbas.com.DialTimePicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import picker.ugurtekbas.com.Picker.Picker;
import picker.ugurtekbas.com.Picker.TimeChangedListener;
import picker.ugurtekbas.com.Picker.TouchEventListener;

/**
 * For sample app, his controls two fragments with different time pickers.
 * @author Ugur Tekbas
 */
public class MainFragment extends Fragment{

    int layoutID;

    public static MainFragment newInstance(int layoutID){
        MainFragment fragment = new MainFragment();
        fragment.layoutID = layoutID;
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putInt("layoutID", layoutID);
    }

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        if(bundle != null && bundle.containsKey("layoutID")){
            layoutID = bundle.getInt("layoutID");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(layoutID, container, false);

        if (layoutID==R.layout.ampm_picker){
            final Picker  picker2 =   (Picker)v.findViewById(R.id.amPicker);
            picker2.setClockColor(getResources().getColor(R.color.clockColor));
            picker2.setDialColor(getResources().getColor(R.color.dialColor));
            picker2.setTime(3, 20, 2017, 12, 45, Picker.AM);
            picker2.setTrackSize(20);
            picker2.setDialRadiusDP(60);

            picker2.setTouchEventListener(new TouchEventListener() {
                @Override
                public void touchEventEnded() {
                    Log.d("TOUCH EVENT ENDED", "ENDED");
                }
            });
            picker2.setTimeChangedListener(new TimeChangedListener() {
                @Override
                public void timeChanged(Date date) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int minutes = calendar.get(Calendar.MINUTE);

                  /* int minPart = 15-(minutes%15);
                    if(minPart > 0) {
                        calendar.add(Calendar.MINUTE, minPart % 60);

                        picker2.setTime(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE));
                    }
                    */
                }
            });


            final CheckBox checkBox =   (CheckBox)v.findViewById(R.id.checkbox);
            picker2.setEnabled(checkBox.isChecked());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    picker2.setEnabled(isChecked);
                }
            });
        }else{
            final Picker  picker1 =   (Picker)v.findViewById(R.id.picker);
            final TextView et =  (TextView)v.findViewById(R.id.et);
            final Button btn  =   (Button)v.findViewById(R.id.btn);

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH,3);
            cal.set(Calendar.DAY_OF_MONTH,27);
            cal.set(Calendar.YEAR,2017);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 30);
            picker1.setTime(cal);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String minute = Integer.toString(picker1.getCurrentMin());
                    if (picker1.getCurrentMin() < 10) {
                        minute = "0" + minute;
                    }
                    et.setText("It's " + picker1.getCurrentHour() + ":" + minute);
                    et.setText(picker1.getTime().toString());
                }
            });

            picker1.setTimeChangedListener(new TimeChangedListener() {
                @Override
                public void timeChanged(Date date) {
                    Log.d("date", date.toString());
                    et.setText(picker1.getTime().toString());
                }
            });
        }

        return v;
    }
}
