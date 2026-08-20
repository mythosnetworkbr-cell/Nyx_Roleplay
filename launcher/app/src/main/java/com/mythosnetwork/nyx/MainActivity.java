package com.mythosnetwork.nyx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final int BG = Color.rgb(6, 6, 9);
    private static final int SURFACE = Color.rgb(16, 15, 20);
    private static final int SURFACE_2 = Color.rgb(22, 20, 28);
    private static final int PURPLE = Color.rgb(157, 92, 255);
    private static final int PURPLE_DARK = Color.rgb(89, 45, 156);
    private static final int PURPLE_LIGHT = Color.rgb(213, 178, 255);
    private static final int WHITE = Color.WHITE;
    private static final int MUTED = Color.rgb(166, 162, 174);
    private static final int GREEN = Color.rgb(73, 224, 129);
    private static final int ORANGE = Color.rgb(255, 171, 88);
    private static final String SERVER_HOST = "ip.oscrias.com.br:7777";
    private static final String SERVER_URI = "samp://ip.oscrias.com.br:7777";

    private TextView status;
    private TextView clientStatus;
    private ProgressBar progress;
    private Button play;
    private Button refresh;
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
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    private void showLauncher() {
        FrameLayout frame = new FrameLayout(this);
        frame.setBackground(background());

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(28, 18, 28, 18);

        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout brand = new LinearLayout(this);
        brand.setOrientation(LinearLayout.VERTICAL);
        TextView title = text("MYTHØS", 28, WHITE, true);
        TextView subtitle = text("NETWORK  •  ROLEPLAY", 10, PURPLE_LIGHT, true);
        subtitle.setLetterSpacing(.18f);
        brand.addView(title, lp(-1, 34));
        brand.addView(subtitle, lp(-1, 22));
        header.addView(brand, lp(0, 60, 1));

        TextView version = text("2.2  STABLE", 10, MUTED, true);
        version.setGravity(Gravity.CENTER);
        version.setBackground(round(Color.rgb(25, 21, 34), 18, Color.rgb(66, 47, 90)));
        header.addView(version, lp(112, 38));
        Button settings = smallButton("⚙", 44);
        settings.setOnClickListener(v -> showSettings());
        header.addView(settings, lp(48, 44));
        root.addView(header, lp(-1, 68));

        LinearLayout hero = new LinearLayout(this);
        hero.setOrientation(LinearLayout.VERTICAL);
        hero.setPadding(30, 24, 30, 24);
        hero.setBackground(heroBackground());

        TextView eyebrow = text("LAUNCHER OFICIAL", 10, PURPLE_LIGHT, true);
        eyebrow.setLetterSpacing(.22f);
        hero.addView(eyebrow, lp(-1, 22));

        TextView headline = text("ENTRE NO SEU\nMUNDO.", 34, WHITE, true);
        headline.setLineSpacing(0, .92f);
        hero.addView(headline, lp(-1, 72));

        hero.addView(text("Uma experiência de entrada rápida, limpa e preparada para o Mythøs RP.", 13, MUTED, false), lp(-1, 42));

        LinearLayout server = card();
        LinearLayout serverInfo = new LinearLayout(this);
        serverInfo.setOrientation(LinearLayout.VERTICAL);
        serverInfo.addView(text("SERVIDOR OFICIAL", 9, MUTED, true), lp(-1, 19));
        serverInfo.addView(text(SERVER_HOST, 16, WHITE, true), lp(-1, 27));
        server.addView(serverInfo, lp(0, 52, 1));

        TextView fixed = text("FIXO", 9, GREEN, true);
        fixed.setGravity(Gravity.CENTER);
        fixed.setBackground(round(Color.rgb(12, 48, 29), 14, Color.rgb(30, 102, 59)));
        server.addView(fixed, lp(55, 32));
        hero.addView(server, lp(-1, 64));
        root.addView(hero, lp(-1, 260));

        LinearLayout statusCard = card();
        statusCard.setPadding(18, 10, 18, 10);
        LinearLayout statusText = new LinearLayout(this);
        statusText.setOrientation(LinearLayout.VERTICAL);
        status = text("VERIFICANDO CONTEÚDO...", 11, MUTED, true);
        clientStatus = text("Detectando cliente SAMP...", 10, MUTED, false);
        statusText.addView(status, lp(-1, 22));
        statusText.addView(clientStatus, lp(-1, 20));
        statusCard.addView(statusText, lp(0, 48, 1));
        progress = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progress.setMax(100);
        progress.setIndeterminate(true);
        statusCard.addView(progress, lp(135, 8));
        root.addView(statusCard, lp(-1, 62));

        LinearLayout actions = new LinearLayout(this);
        actions.setGravity(Gravity.CENTER_VERTICAL);
        actions.setPadding(0, 4, 0, 0);

        play = button("JOGAR AGORA", false);
        play.setEnabled(false);
        play.setAlpha(.55f);
        play.setOnClickListener(v -> launchServer());
        actions.addView(play, lp(0, 58, 1));

        refresh = button("ATUALIZAR", true);
        refresh.setOnClickListener(v -> refreshOnlineStatus());
        LinearLayout.LayoutParams refreshLp = lp(122, 58);
        refreshLp.leftMargin = 10;
        actions.addView(refresh, refreshLp);
        root.addView(actions, lp(-1, 66));

        LinearLayout quick = new LinearLayout(this);
        quick.setGravity(Gravity.CENTER);
        quick.setPadding(0, 2, 0, 0);
        quick.addView(pill("SEM IP MANUAL"), lp(0, 30, 1));
        quick.addView(pill("SAMP:// DIRETO"), lp(0, 30, 1));
        quick.addView(pill("CONTEÚDO ONLINE"), lp(0, 30, 1));
        root.addView(quick, lp(-1, 36));

        TextView footer = text("MYTHØS NETWORK  •  MOBILE RP  •  LAUNCHER OFICIAL", 8, MUTED, true);
        footer.setGravity(Gravity.CENTER);
        root.addView(footer, lp(-1, 28));

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setClipToPadding(false);
        scroll.addView(root);
        frame.addView(scroll, new FrameLayout.LayoutParams(-1, -1));
        setContentView(frame);

        clientStatus.setText(hasSampHandler() ? "Cliente SAMP detectado • pronto para jogar" : "Cliente SAMP não detectado • será necessário instalar o Mobile");
        clientStatus.setTextColor(hasSampHandler() ? GREEN : MUTED);
    }

    private void refreshOnlineStatus() {
        if (status == null) return;
        status.setText("VERIFICANDO CONTEÚDO ONLINE...");
        status.setTextColor(MUTED);
        progress.setIndeterminate(true);
        play.setEnabled(false);
        play.setAlpha(.55f);
        if (refresh != null) refresh.setEnabled(false);

        OnlineContent.check(this, (ok, message) -> {
            online = ok;
            if (ok) {
                status.setText("ONLINE  •  CONTEÚDO VERIFICADO");
                status.setTextColor(GREEN);
                progress.setIndeterminate(false);
                progress.setProgress(100);
            } else {
                status.setText("SEM CONEXÃO  •  MODO DE ENTRADA DISPONÍVEL");
                status.setTextColor(ORANGE);
                progress.setIndeterminate(false);
                progress.setProgress(0);
            }
            play.setEnabled(true);
            play.setAlpha(1f);
            if (refresh != null) refresh.setEnabled(true);
            clientStatus.setText(hasSampHandler() ? "Cliente SAMP detectado • pronto para jogar" : "Cliente SAMP não detectado • instale o Mobile para jogar");
            clientStatus.setTextColor(hasSampHandler() ? GREEN : MUTED);
        });
    }

    private boolean hasSampHandler() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(SERVER_URI));
            return getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null;
        } catch (Exception ignored) {
            return false;
        }
    }

    private void launchServer() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(SERVER_URI));
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Nenhum cliente compatível com samp:// foi encontrado.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Não foi possível abrir o cliente.", Toast.LENGTH_LONG).show();
        }
    }

    private void showSettings() {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(28, 18, 28, 8);
        box.addView(text("MYTHØS LAUNCHER", 20, WHITE, true), lp(-1, 34));
        box.addView(text("Servidor", 10, MUTED, true), lp(-1, 24));
        box.addView(text(SERVER_HOST, 15, WHITE, true), lp(-1, 28));
        box.addView(text("O servidor é fixo e não pode ser alterado pelo usuário.", 11, MUTED, false), lp(-1, 38));
        box.addView(text("Versão 2.2.0 • canal stable", 11, PURPLE_LIGHT, true), lp(-1, 28));
        box.addView(text("Base visual inspirada em padrões de UX do cliente Realidade RP: status persistente, atualização explícita, cards, rail de ações e tela de carregamento. Binários proprietários do cliente não são incorporados ao Launcher.", 10, MUTED, false), lp(-1, 70));

        new AlertDialog.Builder(this)
            .setView(box)
            .setPositiveButton("Verificar agora", (dialog, which) -> refreshOnlineStatus())
            .setNegativeButton("Fechar", null)
            .show();
    }

    private LinearLayout card() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(16, 8, 16, 8);
        card.setBackground(round(SURFACE_2, 16, Color.rgb(45, 39, 54)));
        return card;
    }

    private TextView pill(String value) {
        TextView pill = text(value, 8, MUTED, true);
        pill.setGravity(Gravity.CENTER);
        pill.setBackground(round(Color.rgb(15, 14, 18), 12, Color.rgb(39, 35, 47)));
        return pill;
    }

    private Button smallButton(String value, int size) {
        Button b = new Button(this);
        b.setText(value);
        b.setTextSize(17);
        b.setTextColor(WHITE);
        b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        b.setAllCaps(false);
        b.setMinHeight(0);
        b.setMinWidth(0);
        b.setPadding(0, 0, 0, 0);
        b.setBackground(round(Color.rgb(18, 17, 22), 16, Color.rgb(52, 46, 61)));
        return b;
    }

    private Button button(String value, boolean light) {
        Button b = new Button(this);
        b.setText(value);
        b.setTextSize(14);
        b.setTextColor(light ? Color.rgb(27, 23, 32) : WHITE);
        b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        b.setAllCaps(false);
        b.setMinHeight(0);
        b.setPadding(8, 0, 8, 0);
        b.setBackground(light ? round(WHITE, 18, Color.rgb(230, 230, 230)) : playBackground());
        return b;
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

    private GradientDrawable background() {
        return new GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            new int[]{Color.rgb(6, 6, 9), Color.rgb(15, 9, 23), Color.rgb(6, 6, 9)});
    }

    private GradientDrawable heroBackground() {
        GradientDrawable d = new GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            new int[]{Color.rgb(46, 23, 71), Color.rgb(19, 14, 27), Color.rgb(11, 10, 14)});
        d.setCornerRadius(26);
        d.setStroke(1, Color.rgb(84, 55, 111));
        return d;
    }

    private GradientDrawable playBackground() {
        GradientDrawable d = new GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            new int[]{PURPLE_DARK, PURPLE});
        d.setCornerRadius(18);
        d.setStroke(1, Color.rgb(189, 143, 255));
        return d;
    }

    private GradientDrawable round(int color, int radius, int stroke) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radius);
        d.setStroke(1, stroke);
        return d;
    }

    private LinearLayout.LayoutParams lp(int width, int height) {
        return new LinearLayout.LayoutParams(width, height);
    }

    private LinearLayout.LayoutParams lp(int width, int height, float weight) {
        return new LinearLayout.LayoutParams(width, height, weight);
    }
}
