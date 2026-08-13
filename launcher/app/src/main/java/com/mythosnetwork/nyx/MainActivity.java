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
    private int bg = Color.rgb(6,6,10), card = Color.rgb(18,17,25), accent = Color.rgb(145,92,255), white = Color.rgb(248,247,252), muted = Color.rgb(160,154,173);

    private GradientDrawable shape(int color, float radius) {
        GradientDrawable g = new GradientDrawable(); g.setColor(color); g.setCornerRadius(radius); return g;
    }

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(bg); getWindow().setNavigationBarColor(bg);
        LinearLayout root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setBackgroundColor(bg); root.setPadding(24,24,24,24);

        TextView brand = text("NYX", 34, white); brand.setTypeface(Typeface.DEFAULT, Typeface.BOLD); brand.setGravity(Gravity.CENTER); root.addView(brand, new LinearLayout.LayoutParams(-1,54));
        TextView role = text("ROLEPLAY", 13, accent); role.setTypeface(Typeface.DEFAULT, Typeface.BOLD); role.setGravity(Gravity.CENTER); root.addView(role, new LinearLayout.LayoutParams(-1,28));
        TextView hint = text("DUAS CIDADES • DUAS HISTÓRIAS", 11, muted); hint.setGravity(Gravity.CENTER); root.addView(hint, new LinearLayout.LayoutParams(-1,42));

        root.addView(cityCard("CIDADE 01", "Seu primeiro personagem", "ip.oscrias.com.br:7777", CITY1_HOST, CITY1_PORT));
        root.addView(space(14));
        root.addView(cityCard("CIDADE 02", "Seu segundo personagem", "51.254.21.27:7777", CITY2_HOST, CITY2_PORT));
        root.addView(space(18));
        TextView footer = text("MYTHØS NETWORK\nEscolha uma cidade para entrar", 10, Color.rgb(105,100,116)); footer.setGravity(Gravity.CENTER); root.addView(footer, new LinearLayout.LayoutParams(-1,45));
        setContentView(root);
    }

    private LinearLayout cityCard(String city, String character, String endpoint, String host, int port) {
        LinearLayout box = new LinearLayout(this); box.setOrientation(LinearLayout.VERTICAL); box.setPadding(18,16,18,16); box.setBackground(shape(card,22));
        LinearLayout top = new LinearLayout(this); top.setGravity(Gravity.CENTER_VERTICAL);
        TextView name = text(city, 19, white); name.setTypeface(Typeface.DEFAULT, Typeface.BOLD); top.addView(name, new LinearLayout.LayoutParams(0,42,1));
        TextView online = text("● ONLINE", 10, Color.rgb(130,235,160)); online.setGravity(Gravity.CENTER); online.setBackground(shape(Color.rgb(18,48,31),22)); top.addView(online, new LinearLayout.LayoutParams(78,30));
        box.addView(top);
        TextView c = text(character, 13, muted); box.addView(c, new LinearLayout.LayoutParams(-1,27));
        TextView ip = text(endpoint, 10, Color.rgb(120,114,133)); box.addView(ip, new LinearLayout.LayoutParams(-1,24));
        Button play = new Button(this); play.setText("JOGAR"); play.setTextColor(Color.WHITE); play.setTextSize(15); play.setAllCaps(false); play.setTypeface(Typeface.DEFAULT,Typeface.BOLD); play.setGravity(Gravity.CENTER); play.setBackground(shape(accent,28));
        LinearLayout.LayoutParams pp = new LinearLayout.LayoutParams(-1,58); pp.topMargin=8; box.addView(play,pp); play.setOnClickListener(v -> launchServer(host,port));
        return box;
    }

    private TextView text(String s,float size,int color){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);return t;}
    private View space(int h){View v=new View(this);v.setLayoutParams(new LinearLayout.LayoutParams(1,h));return v;}

    private void launchServer(String host,int port){
        Intent launch=getPackageManager().getLaunchIntentForPackage("com.rockstargames.gtasa");
        if(launch==null){
            new android.app.AlertDialog.Builder(this).setTitle("Cliente Nyx não encontrado").setMessage("O launcher está pronto, mas o cliente Android do jogo precisa estar instalado para iniciar a cidade.").setPositiveButton("OK",null).show();
            return;
        }
        launch.putExtra("server_ip",host); launch.putExtra("server_port",port); launch.putExtra("ip",host); launch.putExtra("port",port); launch.setData(Uri.parse("samp://"+host+":"+port)); startActivity(launch);
    }
}
