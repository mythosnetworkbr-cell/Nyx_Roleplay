package com.mythosnetwork.nyx;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.Color;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity {
    static final int PICK = 1001;
    TextView status;

    TextView text(String s, float size) {
        TextView v = new TextView(this);
        v.setText(s);
        v.setTextColor(Color.rgb(248,247,255));
        v.setTextSize(size);
        v.setGravity(Gravity.CENTER);
        return v;
    }

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setPadding(32,55,32,30);
        root.setBackgroundColor(Color.rgb(8,7,13));

        ImageView logo = new ImageView(this);
        logo.setImageResource(R.drawable.nyx_logo);
        root.addView(logo, new LinearLayout.LayoutParams(110,110));

        TextView title = text("NYX ROLEPLAY", 30);
        title.setTypeface(null, 1);
        root.addView(title, new LinearLayout.LayoutParams(-1,65));

        TextView sub = text("MYTHØS NETWORK", 13);
        sub.setTextColor(Color.rgb(196,181,253));
        root.addView(sub, new LinearLayout.LayoutParams(-1,40));

        TextView mode = text("ROLEPLAY MOBILE", 12);
        mode.setTextColor(Color.rgb(169,162,184));
        root.addView(mode, new LinearLayout.LayoutParams(-1,45));

        Button folder = new Button(this);
        folder.setText("SELECIONAR PASTA DO GTA");
        folder.setOnClickListener(v -> pick());
        root.addView(folder, new LinearLayout.LayoutParams(-1,60));

        Button play = new Button(this);
        play.setText("JOGAR NYX ROLEPLAY");
        play.setTextSize(17);
        play.setOnClickListener(v -> launchGta());
        LinearLayout.LayoutParams pp = new LinearLayout.LayoutParams(-1,65);
        pp.setMargins(0,18,0,12);
        root.addView(play, pp);

        status = text("Verificando GTA San Andreas...", 13);
        status.setTextColor(Color.rgb(169,162,184));
        root.addView(status, new LinearLayout.LayoutParams(-1,80));

        setContentView(root);
        check();
    }

    boolean installed() {
        try {
            getPackageManager().getPackageInfo("com.rockstargames.gtasa", 0);
            return true;
        } catch (Exception e) { return false; }
    }

    void check() {
        status.setText(installed() ? "GTA San Andreas encontrado. Nyx pronto." : "GTA San Andreas não encontrado.");
    }

    void pick() {
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(i, PICK);
    }

    void launchGta() {
        if (!installed()) {
            new AlertDialog.Builder(this)
                .setTitle("GTA não encontrado")
                .setMessage("Instale uma versão compatível do GTA San Andreas antes de iniciar o Nyx Roleplay.")
                .setPositiveButton("OK", null).show();
            return;
        }
        Intent i = getPackageManager().getLaunchIntentForPackage("com.rockstargames.gtasa");
        if (i != null) {
            status.setText("Iniciando Nyx Roleplay...");
            startActivity(i);
        } else status.setText("Não foi possível iniciar o GTA.");
    }

    @Override protected void onActivityResult(int r, int c, Intent d) {
        super.onActivityResult(r,c,d);
        if (r == PICK && c == RESULT_OK && d != null) {
            getPreferences(0).edit().putString("gta_tree", d.getData().toString()).apply();
            status.setText("Pasta GTA vinculada. Nyx pronto para iniciar.");
        }
    }
}
