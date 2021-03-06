package com.aAronQInk.Walls.MVP.models;

import android.content.SharedPreferences;

import com.aAronQInk.Walls.MVP.callbacks.SettingsCallback;
import com.aAronQInk.Walls.MVP.contracts.SettingsContract;

public class SettingsRepository implements SettingsContract.Repository {

    private SharedPreferences mPreferences;
    private SettingsCallback mCallback;

    public SettingsRepository(SharedPreferences preferences) {
        mPreferences = preferences;
    }

    public void setCallback(SettingsCallback callback) {
        mCallback = callback;
    }

    @Override
    public void changePreference(String key, Object value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, String.valueOf(value));
        editor.apply();

        switch (key) {
            case "Language":
                mCallback.onSuccessChanged("Language");
                break;
            case "Theme":
                mCallback.onSuccessChanged("Theme");
                break;
        }
    }
}