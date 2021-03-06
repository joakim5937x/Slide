package me.ccrama.redditslide.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import me.ccrama.redditslide.PostMatch;
import me.ccrama.redditslide.Reddit;
import me.ccrama.redditslide.SettingValues;

/**
 * Created by ccrama on 9/28/2015.
 */
public class MakeExternal extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String url = intent.getStringExtra("url");
        if(url != null){
            try {
                URL u = new URL(url);
                SettingValues.alwaysExternal = SettingValues.alwaysExternal + ", " + u.getHost();

                ArrayList<String> domains = new ArrayList<>();
                for (String s : SettingValues.alwaysExternal.replaceAll("^[,\\s]+", "").split("[,\\s]+")) {
                    if (!s.isEmpty()) {
                        s = s.trim();
                        final String finalS = s;
                        domains.add(finalS);
                    }
                }

                SharedPreferences.Editor e = SettingValues.prefs.edit();
                e.putString(SettingValues.PREF_ALWAYS_EXTERNAL, Reddit.arrayToString(domains));
                e.apply();
                PostMatch.externalDomain = null;
                SettingValues.alwaysExternal = SettingValues.prefs.getString(SettingValues.PREF_ALWAYS_EXTERNAL, "");


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
    }
}
