package com.example.dpa_v6;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void mostrarAvisoSinConexion(Context context, Dialog avisoSinInternet) {
        avisoSinInternet.show();
    }

    public static void ocultarAvisoSinConexion(Dialog avisoSinInternet) {
        avisoSinInternet.dismiss();
    }

    public static void registerNetworkReceiver(Context context, BroadcastReceiver networkReceiver) {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(networkReceiver, filter);
    }

    public static void unregisterNetworkReceiver(Context context, BroadcastReceiver networkReceiver) {
        context.unregisterReceiver(networkReceiver);
    }
}

