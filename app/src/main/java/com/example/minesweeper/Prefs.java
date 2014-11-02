package com.example.minesweeper;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by amiShi on 11/1/14.
 */
    public class Prefs extends PreferenceActivity  {
    private static final String OPT_MUSIC = "music";
    private static final boolean OPT_MUSIC_DEF = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.music_settings);
    }
    public static boolean getMusic(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(OPT_MUSIC, OPT_MUSIC_DEF);
    }




}
