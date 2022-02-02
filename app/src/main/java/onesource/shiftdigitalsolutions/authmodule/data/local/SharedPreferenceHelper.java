package onesource.shiftdigitalsolutions.authmodule.data.local;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class SharedPreferenceHelper {
    private final SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public SharedPreferenceHelper(@NonNull SharedPreferences sharedPreferencesMode) {
        sharedPreferences = sharedPreferencesMode;
    }

    public synchronized void saveObject(@NonNull String key, @NonNull Object value) {

        editor = sharedPreferences.edit();
        if (value instanceof Integer)
            editor.putInt(key, (int) value);
        else if (value instanceof Float)
            editor.putFloat(key, (float) value);
        else if (value instanceof Boolean)
            editor.putBoolean(key, (boolean) value);
        else if (value instanceof String)
            editor.putString(key, (String) value);
        else if (value instanceof Long)
            editor.putLong(key, (Long) value);
        else if (value instanceof Double)
            editor.putLong(key, Double.doubleToLongBits((double) value));
        else
            throw new UnknownError("Unknown datatype");
        editor.apply();

    }

    public synchronized Object getValue(@NonNull Class<?> type, @NonNull String key) {

        if (type == Integer.class)
            return sharedPreferences.getInt(key, 0);
        else if (type == Long.class)
            return sharedPreferences.getLong(key, 0);
        else if (type == Float.class)
            return sharedPreferences.getFloat(key, 0);
        else if (type == Boolean.class)
            return sharedPreferences.getBoolean(key, false);
        else if (type == String.class)
            return sharedPreferences.getString(key, "");
        else if (type == Double.class)
            return Double.longBitsToDouble(sharedPreferences.getLong(key, 0));
        else
            throw new UnknownError("Unknown datatype");
    }

    public void clear() {
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void remove(@NonNull String key) {
        editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
