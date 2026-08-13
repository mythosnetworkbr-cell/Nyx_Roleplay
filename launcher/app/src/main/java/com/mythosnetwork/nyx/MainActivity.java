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
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String CITY1_HOST = "ip.oscrias.com.br";
    private static final int CITY1_PORT = 7777;
    private static final String CITY2_HOST = "51.254.21.27";
    private static final int CITY2_PORT = 7777;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(7,6,12));
        getWindow().setNavigationBarColor(Color.rgb(7,6,12));

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setBackgroundColor(Color.rgb(7,6,12));
        root.setPadding(28,28,28,28);

        TextView title = new TextView(this);
        title.setText("NYX ROLEPLAY");
        title.setTextColor(Color.WHITE);
        title.setTextSize(28);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        root.addView(title, new LinearLayout.LayoutParams(-1,60));

        TextView subtitle = new TextView(this);
        subtitle.setText("ESCOLHA SUA CIDADE");
        subtitle.setTextColor(Color.rgb(170,160,190));
        subtitle.setTextSize(12);
        subtitle.setGravity(Gravity.CENTER);
        root.addView(subtitle, new LinearLayout.LayoutParams(-1,38));

        Button city1 = button("▶  CIDADE 01");
        Button city2 = button("▶  CIDADE 02");
        root.addView(city1, new LinearLayout.LayoutParams(-1,72));
        root.addView(new android.view.View(this), new LinearLayout.LayoutParams(1,18));
        root.addView(city2, new LinearLayout.LayoutParams(-1,72));

        city1.setOnClickListener(v -> launchServer(CITY1_HOST, CITY1_PORT));
        city2.setOnClickListener(v -> launchServer(CITY2_HOST, CITY2_PORT));
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
