package com.mythosnetwork.nyx;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    private static final String SERVER1_HOST = "ip.oscrias.com.br";
    private static final String SERVER1_IP = "ip.oscrias.com.br";
    private static final int SERVER1_PORT = 7777;
    private static final String SERVER2_HOST = "51.254.21.27";
    private static final int SERVER2_PORT = 7777;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(7,6,12));
        getWindow().setNavigationBarColor(Color.rgb(7,6,12));

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setBackgroundColor(Color.rgb(7,6,12));
        root.setPadding(28,28,28,28);

        Button server1 = button("▶  NYX ROLEPLAY 01");
        Button server2 = button("▶  NYX ROLEPLAY 02");
        root.addView(server1, new LinearLayout.LayoutParams(-1,72));
        LinearLayout.LayoutParams gap = new LinearLayout.LayoutParams(1,18);
        root.addView(new android.view.View(this),gap);
        root.addView(server2, new LinearLayout.LayoutParams(-1,72));
        server1.setOnClickListener(v -> launchServer(SERVER1_HOST, SERVER1_PORT));
        server2.setOnClickListener(v -> launchServer(SERVER2_HOST, SERVER2_PORT));
        setContentView(root);
    }

    private Button button(String text) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextSize(18);
        b.setTextColor(Color.WHITE);
        b.setAllCaps(false);
        b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return b;
    }

    private void launchServer(String host, int port) {
        Intent launch = getPackageManager().getLaunchIntentForPackage("com.rockstargames.gtasa");
        if (launch == null) return;
        launch.putExtra("server_ip", host);
        launch.putExtra("server_port", port);
        launch.putExtra("ip", host);
        launch.putExtra("port", port);
        launch.setData(Uri.parse("samp://" + host + ":" + port));
        startActivity(launch);
    }
}
