package onesource.shiftdigitalsolutions.authmodule.presentation.ui.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import onesource.shiftdigitalsolutions.authmodule.R;
import onesource.shiftdigitalsolutions.authmodule.data.local.SharedPreferenceHelper;
import onesource.shiftdigitalsolutions.authmodule.databinding.FragmentSettingsBinding;
import onesource.shiftdigitalsolutions.authmodule.presentation.ui.activity.BaseActivity;

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    //+ Constants region
    String DEFAULT_LANG_KEY;
    //- End region

    //+ Variables region
    SharedPreferenceHelper sharedPreferenceHelper;
    FragmentSettingsBinding binding;
    //- End region

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }


    private void init() {
        String SHARED_KEY = getString(R.string.shared_preference_key);
        DEFAULT_LANG_KEY = getString(R.string.default_language_key);
        sharedPreferenceHelper = new SharedPreferenceHelper(requireContext().getSharedPreferences(SHARED_KEY, MODE_PRIVATE));
        String[] languages = requireActivity().getResources().getStringArray(R.array.languages);
        binding.spinner.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, languages));
        binding.spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 1)
            changeDefaultLanguage("en");
        else if (position == 2)
            changeDefaultLanguage("ar");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    private void changeDefaultLanguage(String language) {
        sharedPreferenceHelper.saveObject(DEFAULT_LANG_KEY, language);
        Configuration configuration = new Configuration(getResources().getConfiguration());
        Locale locale = new Locale(language);
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        requireActivity().finishAffinity();
        startActivity(new Intent(requireContext(), BaseActivity.class));
        binding.spinner.setSelection(0);
    }

}