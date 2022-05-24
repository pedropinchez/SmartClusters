package com.supreme.smartclusters.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private Context ctx;
    private SharedPreferences default_prefence;

    public SharedPref(Context context) {
        this.ctx = context;
        default_prefence = context.getSharedPreferences("smartclusters", Context.MODE_PRIVATE);
    }
        public void setclusters(int clusters) {
            default_prefence.edit().putInt("clusters", clusters).apply();
        }

        public int getclusters() {
            return default_prefence.getInt("clusters",0);
        }



}
