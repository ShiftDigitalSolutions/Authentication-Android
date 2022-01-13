package onesource.shiftdigitalsolutions.layers.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import onesource.shiftdigitalsolutions.authmodule.R;
import onesource.shiftdigitalsolutions.authmodule.databinding.ActivityMainBinding;
import onesource.shiftdigitalsolutions.layers.data.local.SharedPreferenceHelper;

public class MainActivity extends AppCompatActivity {

    //+ Constants region
    String SHARED_KEY;
    String SQL_KEY;
    private static final int SUCCESS_LOGIN = 200;
    //- End region

    //+ Variables region
    SharedPreferenceHelper sharedPreferenceHelper;
    ActivityMainBinding binding;
    //- End region

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
    }

    private void init() {
        SHARED_KEY = getString(R.string.shared_preference_key);
        SQL_KEY = getString(R.string.sql_authentication_success_key);
        sharedPreferenceHelper = new SharedPreferenceHelper(getSharedPreferences(SHARED_KEY, MODE_PRIVATE));

//        sharedPreferenceHelper.clear();
        checkLogin();
    }

    private void checkLogin() {
        if ((int) sharedPreferenceHelper.getValue(Integer.class, SQL_KEY) != SUCCESS_LOGIN) {
            startAuthenticationActivity();
        } else {
            binding.testBinding.setText("Success Login");
        }
    }

    private void startAuthenticationActivity() {
        startActivity(new Intent(MainActivity.this, AuthenticationActivity.class));
    }
}