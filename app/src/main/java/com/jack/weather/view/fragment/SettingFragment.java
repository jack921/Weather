package com.jack.weather.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import com.jack.weather.R;
import com.jack.weather.biz.TimeLintener;
import com.jack.weather.service.NotificationService;
import com.jack.weather.util.LocalData;
import com.jack.weather.view.widget.DialogShow;

/**
 * Created by Jack on 2016/8/18.
 */

public class SettingFragment extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private CheckBoxPreference notification_remind=null;
    private Preference notification_time=null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        notification_remind=(CheckBoxPreference)findPreference("notification_remind");
        notification_time=findPreference("notification_update");
        notification_remind.setOnPreferenceChangeListener(this);
        notification_time.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if(preference==notification_remind){
            if(notification_remind.isChecked()){
                LocalData.clearSharedPreferencesData(getActivity(),"notification");
                Intent intent=new Intent(getActivity(),NotificationService.class);
                getActivity().stopService(intent);
            }else{
                LocalData.sharedPreferencesSave(getActivity(),"notification","status","true");
                Intent intent=new Intent(getActivity(),NotificationService.class);
                getActivity().startService(intent);
            }
        }
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if(preference==notification_time){
            DialogShow.setTimeDialog(getActivity(),new TimeLintener() {
                @Override
                public void getTime(int time) {
                    LocalData.sharedPreferencesSave(getActivity(),"notification","time",time+"");
                }
            });
        }
        return false;
    }
}
