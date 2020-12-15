package com.learn.bookreader;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        final SharedPreferences sharedpreferences = getActivity().getSharedPreferences("preferences", MODE_PRIVATE);

        ListPreference lang = findPreference("language");
        lang.setValue(sharedpreferences.getString("language", "en"));

        SwitchPreferenceCompat delete = findPreference("delete");
        delete.setChecked(sharedpreferences.getBoolean("delete", false));

        lang.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("language", (String) newValue);
                editor.apply();
                return true;
            }
        });
        delete.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean("delete", (Boolean) newValue);
                editor.apply();
                return true;
            }
        });
    }
}
