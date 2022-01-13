package onesource.shiftdigitalsolutions.layers.presentation.ui.utility;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

public class Utility {
    public void displaySnakeBar(@NonNull View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}
