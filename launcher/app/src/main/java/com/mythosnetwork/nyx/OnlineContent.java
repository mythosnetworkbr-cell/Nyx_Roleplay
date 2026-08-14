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

    public static void check(Context context, Callback callback) {
        new Thread(() -> {
            try {
                HttpURLConnection c = (HttpURLConnection) new URL(MANIFEST_URL).openConnection();
                c.setConnectTimeout(10000);
                c.setReadTimeout(15000);
                c.setRequestMethod("GET");
                int code = c.getResponseCode();
                if (code != 200) throw new Exception("HTTP " + code);
                InputStream in = c.getInputStream();
                File target = new File(context.getFilesDir(), "nyx_manifest.json");
                FileOutputStream out = new FileOutputStream(target);
                byte[] buffer = new byte[8192];
                int n;
                while ((n = in.read(buffer)) != -1) out.write(buffer, 0, n);
                out.close(); in.close(); c.disconnect();
                main(callback, true, "Conteúdo online verificado");
            } catch (Exception e) {
                main(callback, false, "Não foi possível atualizar agora: " + e.getMessage());
            }
        }).start();
    }

    private static void main(Callback callback, boolean ok, String message) {
        new Handler(Looper.getMainLooper()).post(() -> callback.done(ok, message));
    }
}
