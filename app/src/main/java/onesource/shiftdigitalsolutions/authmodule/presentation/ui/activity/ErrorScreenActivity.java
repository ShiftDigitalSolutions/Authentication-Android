package onesource.shiftdigitalsolutions.authmodule.presentation.ui.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import onesource.shiftdigitalsolutions.authmodule.R;
import onesource.shiftdigitalsolutions.authmodule.databinding.ActivityErrorScreenBinding;

public class ErrorScreenActivity extends AppCompatActivity {

    //+ Variables region
    ActivityErrorScreenBinding binding;

    //- End region
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_error_screen);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        boolean isInMaintenance = intent.getBooleanExtra(getString(R.string.server_in_maintenance), false);
        actions();
        if (isInMaintenance) maintenanceView();
        else regularErrorView();


    }

    private void actions() {
        binding.errorScreenFinish.setOnClickListener(v -> this.finishAffinity());
        binding.errorScreenRetry.setOnClickListener(v -> startMainActivity());
    }

    private void regularErrorView() {
        binding.errorScreenMessage.setText(getString(R.string.something_went_wrong));
    }

    private void maintenanceView() {
        binding.errorScreenRetry.setVisibility(View.GONE);
        binding.errorScreenMessage.setText(getString(R.string.server_in_maintenance_text));
    }

    private void startMainActivity() {
        this.finishAffinity();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}