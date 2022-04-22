package com.sos.comboapp.Utilites;

import android.content.Context;
import android.content.SharedPreferences;
import com.sos.comboapp.Calss.Constant;

public class PreferenceManager
{
    private  final SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public PreferenceManager(Context context)
    {
        preferences=context.getSharedPreferences(Constant.Key_PREFERENCE_NAME,Context.MODE_PRIVATE);
    }

    public void putBoolean(String key,Boolean value)
    {
        editor=preferences.edit();
        editor.putBoolean(key,value);
        editor.apply();

    }
    public boolean getBoolean(String key)
    {
        return preferences.getBoolean(key,false);
    }
    public void putString(String key,String value)
    {
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public String getString(String key)
    {
        return preferences.getString(key,null);

    }
    public  void  clear()
    {
        editor=preferences.edit();
        editor.clear();
        editor.apply();
    }
}
