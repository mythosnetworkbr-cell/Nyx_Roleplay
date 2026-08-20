package com.mythosnetwork.nyx;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public final class OnlineContent {
    public interface Callback { void done(boolean ok, String message); }
    private static final String MANIFEST_URL = "https://raw.githubusercontent.com/mythosnetworkbr-cell/Nyx_Roleplay/main/launcher/update/manifest.json";

    private OnlineContent() {}

    public static void check(Context context, Callback callback) {
        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) new URL(MANIFEST_URL).openConnection();
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setUseCaches(false);
                int code = connection.getResponseCode();
                if (code != HttpURLConnection.HTTP_OK) throw new Exception("HTTP " + code);

                File target = new File(context.getFilesDir(), "launcher-manifest.json");
                try (InputStream input = connection.getInputStream(); FileOutputStream output = new FileOutputStream(target)) {
                    byte[] buffer = new byte[8192];
                    int count;
                    while ((count = input.read(buffer)) != -1) output.write(buffer, 0, count);
                }
                finish(callback, true, "Conteúdo online verificado");
            } catch (Exception e) {
                finish(callback, false, "Conteúdo online indisponível");
            } finally {
                if (connection != null) connection.disconnect();
            }
        }).start();
    }

    private static void finish(Callback callback, boolean ok, String message) {
        new Handler(Looper.getMainLooper()).post(() -> callback.done(ok, message));
    }
}
