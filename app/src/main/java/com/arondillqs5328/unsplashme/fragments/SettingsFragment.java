package com.arondillqs5328.unsplashme.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arondillqs5328.unsplashme.MVP.contracts.SettingsContract;
import com.arondillqs5328.unsplashme.MVP.models.SettingsRepository;
import com.arondillqs5328.unsplashme.MVP.presenters.SettingsPresenter;
import com.arondillqs5328.unsplashme.R;
import com.arondillqs5328.unsplashme.UnsplashMe;
import com.arondillqs5328.unsplashme.activities.MainActivity;
import com.arondillqs5328.unsplashme.activities.SettingsActivity;

import androidx.core.app.TaskStackBuilder;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat implements SettingsContract.View {

    private ListPreference mLanguagePreference;
    private ListPreference mThemePreference;
    private ListPreference mLoadQualityPreference;
    private ListPreference mDownloadQualityPreference;

    private SettingsContract.Presenter mPresenter;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);

        mPresenter = new SettingsPresenter(this, new SettingsRepository(UnsplashMe.getInstance().getSharedPreferences(UnsplashMe.APP_PREFERENCES, Context.MODE_PRIVATE)));

        mLanguagePreference = findPreference(getString(R.string.language_key));
        mLanguagePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                mPresenter.onChangedPreference(mLanguagePreference.getKey(), newValue);
                return true;
            }
        });

        mThemePreference = findPreference(getString(R.string.theme_key));
        mThemePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                mPresenter.onChangedPreference(mThemePreference.getKey(), newValue);
                return true;
            }
        });

        mLoadQualityPreference = findPreference(getString(R.string.load_quality_key));
        mLoadQualityPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                mPresenter.onChangedPreference(mLoadQualityPreference.getKey(), newValue);
                return true;
            }
        });

        mDownloadQualityPreference = findPreference(getString(R.string.download_quality_key));
        mDownloadQualityPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                mPresenter.onChangedPreference(mDownloadQualityPreference.getKey(), newValue);
                return true;
            }
        });
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void restart() {
        TaskStackBuilder.create(getActivity())
                .addNextIntent(new Intent(getActivity(), MainActivity.class))
                .addNextIntent(new Intent(getActivity(), SettingsActivity.class))
                .startActivities();
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        getActivity().finish();
    }
}
