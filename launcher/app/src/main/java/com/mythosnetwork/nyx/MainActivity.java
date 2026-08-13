package com.mythosnetwork.nyx;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
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
        getWindow().setStatusBarColor(Color.rgb(5,5,9));
        getWindow().setNavigationBarColor(Color.rgb(5,5,9));

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setBackgroundColor(Color.rgb(5,5,9));
        root.setPadding(24,34,24,28);

        TextView brand = text("NYX", 42, Color.WHITE, true);
        root.addView(brand, lp(-1,58));
        TextView roleplay = text("ROLEPLAY", 13, Color.rgb(180,170,200), true);
        roleplay.setLetterSpacing(.28f);
        root.addView(roleplay, lp(-1,30));

        TextView tagline = text("DUAS CIDADES  •  DUAS HISTÓRIAS", 12, Color.rgb(130,125,145), false);
        tagline.setGravity(Gravity.CENTER);
        root.addView(tagline, lp(-1,40));

        addSpace(root, 12);
        root.addView(cityCard("CIDADE 01", "ip.oscrias.com.br:7777", "Seu primeiro personagem", CITY1_HOST, CITY1_PORT), lp(-1,210));
        addSpace(root, 16);
        root.addView(cityCard("CIDADE 02", "51.254.21.27:7777", "Seu segundo personagem", CITY2_HOST, CITY2_PORT), lp(-1,210));

        addSpace(root, 18);
        TextView footer = text("MYTHØS NETWORK", 10, Color.rgb(90,86,100), true);
        footer.setGravity(Gravity.CENTER);
        root.addView(footer, lp(-1,28));
        setContentView(root);
    }

    private View cityCard(String name, String address, String character, String host, int port) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(22,18,22,16);
        GradientDrawable bg = new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[]{Color.rgb(29,25,40), Color.rgb(13,12,18)});
        bg.setCornerRadius(26);
        bg.setStroke(1, Color.rgb(62,54,76));
        card.setBackground(bg);

        TextView city = text(name, 22, Color.WHITE, true);
        card.addView(city, lp(-1,42));
        TextView addr = text(address, 11, Color.rgb(160,150,175), false);
        card.addView(addr, lp(-1,26));
        TextView charText = text("PERSONAGEM  •  " + character, 11, Color.rgb(120,190,160), true);
        card.addView(charText, lp(-1,28));

        Button play = new Button(this);
        play.setText("JOGAR  ▶");
        play.setTextColor(Color.WHITE);
        play.setTextSize(15);
        play.setAllCaps(false);
        play.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        play.setGravity(Gravity.CENTER);
        GradientDrawable pb = new GradientDrawable();
        pb.setColor(Color.rgb(104,52,170));
        pb.setCornerRadius(18);
        play.setBackground(pb);
        play.setOnClickListener(v -> launchServer(host, port));
        card.addView(play, lp(-1,58));
        return card;
    }

    private TextView text(String value, float size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER_VERTICAL);
        t.setTypeface(Typeface.DEFAULT, bold ? Typeface.BOLD : Typeface.NORMAL);
        return t;
    }

    private LinearLayout.LayoutParams lp(int w, int h) {
        return new LinearLayout.LayoutParams(w, h);
    }

    private void addSpace(LinearLayout root, int h) {
        root.addView(new View(this), lp(1, h));
    }

    private void launchServer(String host, int port) {
        Intent launch = getPackageManager().getLaunchIntentForPackage("com.rockstargames.gtasa");
        if (launch == null) {
            showMessage("Cliente do jogo não encontrado. Instale o cliente Nyx/SA-MP antes de jogar.");
            return;
        }
        launch.putExtra("server_ip", host);
        launch.putExtra("server_port", port);
        launch.putExtra("ip", host);
        launch.putExtra("port", port);
        launch.setData(Uri.parse("samp://" + host + ":" + port));
        startActivity(launch);
    }

    private void showMessage(String message) {
        new android.app.AlertDialog.Builder(this)
                .setTitle("NYX ROLEPLAY")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
