package onesource.shiftdigitalsolutions.authmodule.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import onesource.shiftdigitalsolutions.authmodule.authentication.presentation.LoginActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}