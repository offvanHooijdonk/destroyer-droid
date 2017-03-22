package by.vanhooijdonk.destroyerdroid.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import by.vanhooijdonk.destroyerdroid.R;

/**
 * Created by Yahor_Fralou on 3/22/2017 12:17 PM.
 */

public class PrefHelper {

    public static String getBaseUrl(Context ctx) {
        return getSharedPreference(ctx).getString(ctx.getString(R.string.pref_server_host_key), ctx.getString(R.string.pref_server_host_default));
    }

    private static SharedPreferences getSharedPreference(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
}
