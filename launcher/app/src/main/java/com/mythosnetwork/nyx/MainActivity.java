package com.mythosnetwork.nyx;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final int BG = Color.rgb(7, 6, 12);
    private static final int SURFACE = Color.rgb(17, 15, 25);
    private static final int SURFACE_2 = Color.rgb(25, 21, 36);
    private static final int PURPLE = Color.rgb(139, 92, 246);
    private static final int PURPLE_LIGHT = Color.rgb(196, 155, 255);
    private static final int WHITE = Color.WHITE;
    private static final int MUTED = Color.rgb(171, 165, 184);
    private static final String SERVER_URI = "samp://51.68.107.75:10961";

    private TextView status;
    private ProgressBar progress;
    private Button play;
    private boolean online;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);
        hideSystemBars();
        showLauncher();
        refreshOnlineStatus();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) hideSystemBars();
    }

    private void hideSystemBars() {
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            WindowInsetsController controller = getWindow().getInsetsController();
            if (controller != null) controller.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    private void showLauncher() {
        LinearLayout root = base();
        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout brand = new LinearLayout(this);
        brand.setOrientation(LinearLayout.VERTICAL);
        TextView mythos = text("MYTHØS", 29, WHITE, true);
        TextView network = text("NETWORK", 11, PURPLE_LIGHT, true);
        network.setLetterSpacing(.30f);
        brand.addView(mythos, lp(-1, 40));
        brand.addView(network, lp(-1, 24));
        header.addView(brand, lp(0, 70, 1));

        TextView version = text("LAUNCHER 2.2", 11, MUTED, true);
        version.setGravity(Gravity.CENTER);
        version.setBackground(round(Color.rgb(32, 27, 45), 16));
        header.addView(version, lp(125, 42));
        root.addView(header, lp(-1, 78));

        LinearLayout hero = new LinearLayout(this);
        hero.setOrientation(LinearLayout.VERTICAL);
        hero.setGravity(Gravity.CENTER_VERTICAL);
        hero.setPadding(34, 28, 34, 28);
        hero.setBackground(heroBackground());

        TextView eyebrow = text("MYTHØS ROLEPLAY", 12, PURPLE_LIGHT, true);
        eyebrow.setLetterSpacing(.20f);
        hero.addView(eyebrow, lp(-1, 26));
        hero.addView(text("SEU MUNDO.\nSUA HISTÓRIA.", 35, WHITE, true), lp(-1, 92));
        hero.addView(text("Entre diretamente no servidor Mythøs Network.\nO Launcher verifica o conteúdo online antes de abrir o cliente.", 14, MUTED, false), lp(-1, 58));

        LinearLayout server = new LinearLayout(this);
        server.setOrientation(LinearLayout.VERTICAL);
        server.setPadding(18, 12, 18, 12);
        server.setBackground(round(SURFACE, 14));
        server.addView(text("SERVIDOR OFICIAL", 10, MUTED, true), lp(-1, 20));
        server.addView(text("51.68.107.75:10961", 16, WHITE, true), lp(-1, 28));
        hero.addView(server, lp(-1, 70));
        root.addView(hero, lp(-1, 270));

        LinearLayout statusPanel = new LinearLayout(this);
        statusPanel.setOrientation(LinearLayout.VERTICAL);
        statusPanel.setPadding(20, 12, 20, 12);
        statusPanel.setBackground(round(SURFACE_2, 16));
        status = text("VERIFICANDO CONTEÚDO...", 12, MUTED, true);
        statusPanel.addView(status, lp(-1, 24));
        progress = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progress.setMax(100);
        progress.setIndeterminate(true);
        statusPanel.addView(progress, lp(-1, 8));
        root.addView(statusPanel, lp(-1, 62));

        play = button("JOGAR", false);
        play.setEnabled(false);
        play.setAlpha(.55f);
        play.setOnClickListener(v -> launchServer());
        root.addView(play, lp(-1, 62));

        TextView info = text("O IP é fixo. Não é necessário adicionar servidor manualmente.\nO Android decide automaticamente qual cliente SAMP deve receber o protocolo samp://.", 11, MUTED, false);
        info.setGravity(Gravity.CENTER);
        root.addView(info, lp(-1, 54));

        TextView footer = text("MYTHØS NETWORK  •  RP MOBILE  •  LAUNCHER OFICIAL", 9, MUTED, true);
        footer.setGravity(Gravity.CENTER);
        root.addView(footer, lp(-1, 32));
        setContentView(wrap(root));
    }

    private void refreshOnlineStatus() {
        OnlineContent.check(this, (ok, message) -> {
            online = ok;
            if (ok) {
                status.setText("ONLINE  •  CONTEÚDO VERIFICADO");
                status.setTextColor(Color.rgb(120, 220, 150));
                progress.setIndeterminate(false);
                progress.setProgress(100);
                play.setEnabled(true);
                play.setAlpha(1f);
            } else {
                status.setText("OFFLINE  •  TENTAR NOVAMENTE");
                status.setTextColor(Color.rgb(255, 170, 100));
                progress.setIndeterminate(false);
                progress.setProgress(0);
                play.setEnabled(true);
                play.setAlpha(1f);
            }
        });
    }

    private void launchServer() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(SERVER_URI));
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Nenhum cliente SAMP compatível com samp:// foi encontrado. Instale o cliente Mobile e tente novamente.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Não foi possível abrir o cliente: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private LinearLayout base() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(28, 20, 28, 16);
        root.setBackgroundColor(BG);
        return root;
    }

    private ScrollView wrap(View view) {
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setClipToPadding(false);
        scroll.addView(view);
        return scroll;
    }

    private TextView text(String value, float size, int color, boolean bold) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextSize(size);
        view.setTextColor(color);
        view.setTypeface(Typeface.DEFAULT, bold ? Typeface.BOLD : Typeface.NORMAL);
        view.setGravity(Gravity.CENTER_VERTICAL);
        return view;
    }

    private Button button(String value, boolean light) {
        Button button = new Button(this);
        button.setText(value);
        button.setTextSize(16);
        button.setTextColor(light ? Color.rgb(20, 18, 24) : WHITE);
        button.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        button.setAllCaps(false);
        button.setMinHeight(0);
        button.setPadding(8, 0, 8, 0);
        button.setBackground(round(light ? WHITE : PURPLE, 18));
        return button;
    }

    private GradientDrawable heroBackground() {
        GradientDrawable drawable = new GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            new int[]{Color.rgb(31, 19, 49), Color.rgb(14, 12, 22)});
        drawable.setCornerRadius(24);
        drawable.setStroke(1, Color.rgb(70, 50, 96));
        return drawable;
    }

    private GradientDrawable round(int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(radius);
        drawable.setStroke(1, Color.rgb(53, 43, 69));
        return drawable;
    }

    private LinearLayout.LayoutParams lp(int width, int height) {
        return new LinearLayout.LayoutParams(width, height);
    }

    private LinearLayout.LayoutParams lp(int width, int height, float weight) {
        return new LinearLayout.LayoutParams(width, height, weight);
    }
}
