package onesource.shiftdigitalsolutions.authmodule.presentation.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import onesource.shiftdigitalsolutions.authmodule.R;
import onesource.shiftdigitalsolutions.authmodule.data.local.SharedPreferenceHelper;
import onesource.shiftdigitalsolutions.authmodule.databinding.ActivityBaseBinding;
import onesource.shiftdigitalsolutions.authmodule.presentation.ui.fragment.HomeScreenFragment;
import onesource.shiftdigitalsolutions.authmodule.presentation.ui.fragment.SettingsFragment;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //+ Constant region
    private final static HomeScreenFragment homeScreenFragment = new HomeScreenFragment();
    private final static SettingsFragment settingsFragment = new SettingsFragment();
    //- End region

    //+ Variables region
    SharedPreferenceHelper sharedPreferenceHelper;
    ActivityBaseBinding binding;
    Integer currentFocus = null;
    //- End region

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_base);
        init();
    }

    private void init() {
        String SHARED_KEY = getString(R.string.shared_preference_key);
        sharedPreferenceHelper = new SharedPreferenceHelper(getSharedPreferences(SHARED_KEY, MODE_PRIVATE));
        binding.navigationView.setNavigationItemSelectedListener(this);
        action();
    }

    private void action() {
        startFragment(new HomeScreenFragment(), getString(R.string.home_screen));
        currentFocus = R.id.homeScreenNavItem;
        binding.navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void startFragment(Fragment fragment, @NonNull String toolbarTitle) {
        binding.toolbarTitleTV.setText(toolbarTitle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(binding.fragmentContainer.getId(), fragment, fragment.getClass().getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        binding.drawerLayout.close();
        setSelection(item);
        item.setChecked(true);
        if (item.getItemId() == currentFocus)
            return true;
        switch (item.getItemId()) {
            case R.id.homeScreenNavItem:
                currentFocus = R.id.homeScreenNavItem;
                startFragment(homeScreenFragment, getString(R.string.home_screen));
                return true;
            case R.id.settingsNavItem:
                startFragment(settingsFragment, getString(R.string.settings));
                currentFocus = R.id.settingsNavItem;
                return true;
            default:
                return true;
        }
    }

    private void setSelection(MenuItem selectedItem) {
        for (int i = 0; i < binding.navigationView.getMenu().size(); i++) {
            if (binding.navigationView.getMenu().getItem(i) == selectedItem) continue;
            binding.navigationView.getMenu().getItem(i).setChecked(false);
        }
    }

    public void logout(View view) {
        binding.drawerLayout.close();
        sharedPreferenceHelper.clear();
        startAuthenticationActivity();
    }

    private void startAuthenticationActivity() {
        startActivity(new Intent(BaseActivity.this, AuthenticationActivity.class));
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}