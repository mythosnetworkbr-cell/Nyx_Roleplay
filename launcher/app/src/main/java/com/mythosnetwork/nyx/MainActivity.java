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
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String SERVER_IP = "51.254.21.27";
    private static final int SERVER_PORT = 7777;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(7,6,12));
        getWindow().setNavigationBarColor(Color.rgb(7,6,12));

        LinearLayout root = new LinearLayout(this);
        root.setGravity(Gravity.CENTER);
        root.setBackgroundColor(Color.rgb(7,6,12));
        root.setPadding(28,28,28,28);

        Button play = new Button(this);
        play.setText("▶  JOGAR NYX ROLEPLAY");
        play.setTextSize(18);
        play.setTextColor(Color.WHITE);
        play.setAllCaps(false);
        play.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        play.setMinHeight(72);
        play.setOnClickListener(v -> launchNyx());

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1,72);
        root.addView(play,p);
        setContentView(root);
    }

    private void launchNyx() {
        Intent launch = getPackageManager().getLaunchIntentForPackage("com.rockstargames.gtasa");
        if (launch == null) {
            Toast.makeText(this, "Cliente Nyx não encontrado neste aparelho.", Toast.LENGTH_LONG).show();
            return;
        }

        // Test server embedded in the launcher. The Nyx client can consume these
        // extras if its launcher/SA-MP integration supports explicit server data.
        launch.putExtra("server_ip", SERVER_IP);
        launch.putExtra("server_port", SERVER_PORT);
        launch.putExtra("ip", SERVER_IP);
        launch.putExtra("port", SERVER_PORT);
        launch.setData(Uri.parse("samp://" + SERVER_IP + ":" + SERVER_PORT));
        startActivity(launch);
    }
}
