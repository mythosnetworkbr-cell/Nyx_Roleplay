package com.mythosnetwork.nyx;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final int BG = Color.rgb(5, 5, 14);
    private static final int SURFACE = Color.rgb(15, 15, 27);
    private static final int SURFACE2 = Color.rgb(22, 20, 38);
    private static final int PURPLE = Color.rgb(139, 92, 246);
    private static final int PURPLE2 = Color.rgb(168, 85, 247);
    private static final int MUTED = Color.rgb(174, 169, 190);
    private String selectedCity = "";
    private String selectedGender = "";

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);
        hideSystemBars();
        showLogin();
    }

    private void hideSystemBars() {
        Window w = getWindow();
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            WindowInsetsController c = w.getInsetsController();
            if (c != null) c.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
        } else {
            w.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    private void showLogin() {
        LinearLayout root = base();
        addBrand(root, "NYX", "ROLEPLAY");
        root.addView(text("VIVA SUA HISTÓRIA. ESCREVA SUA LENDA.", 16, Color.WHITE, true), lp(-1, 40));
        root.addView(text("Curitiba  •  Florianópolis  •  Mythøs Network", 12, MUTED, false), lp(-1, 30));
        addSpace(root, 12);

        Button google = button("G   ENTRAR COM GOOGLE", true);
        google.setOnClickListener(v -> showCities());
        root.addView(google, lp(-1, 62));
        addSpace(root, 12);
        root.addView(text("OU", 11, MUTED, true), lp(-1, 24));
        addSpace(root, 8);

        EditText email = input("E-mail ou nome de usuário");
        root.addView(email, lp(-1, 56));
        addSpace(root, 10);
        EditText pass = input("Senha");
        pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        root.addView(pass, lp(-1, 56));
        addSpace(root, 14);

        Button login = button("ENTRAR", false);
        login.setOnClickListener(v -> showCities());
        root.addView(login, lp(-1, 56));
        addSpace(root, 10);
        Button create = outlineButton("CRIAR CONTA");
        create.setOnClickListener(v -> showCities());
        root.addView(create, lp(-1, 56));
        root.addView(text("MUNDO ABERTO   •   DUAS CIDADES   •   PROFISSÕES   •   ROLEPLAY REAL", 10, MUTED, true), lp(-1, 44));
        setContentView(wrap(root));
    }

    private void showCities() {
        LinearLayout root = base();
        addBrand(root, "ESCOLHA SUA CIDADE", "");
        root.addView(text("Cada cidade possui personagem e progresso próprios.", 14, MUTED, false), lp(-1, 38));
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER);
        row.addView(cityCard("CURITIBA", "Cidade urbana • empregos • polícia • universidade", "CIDADE 01"), lp(0, 330, 1));
        row.addView(cityCard("FLORIANÓPOLIS", "Ilha • praias • turismo • porto • comunidades", "CIDADE 02"), lp(0, 330, 1));
        root.addView(row, lp(-1, 350));
        root.addView(text("ESCOLHA UMA CIDADE PARA CRIAR OU CONTINUAR SEU PERSONAGEM", 10, MUTED, true), lp(-1, 42));
        setContentView(wrap(root));
    }

    private View cityCard(String city, String desc, String id) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(22, 20, 22, 18);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setBackground(panel());
        card.addView(text(id, 11, PURPLE2, true), lp(-1, 26));
        card.addView(text(city, 28, Color.WHITE, true), lp(-1, 48));
        card.addView(text(desc, 12, MUTED, false), lp(-1, 62));
        Button b = button("ESCOLHER  ›", false);
        b.setOnClickListener(v -> { selectedCity = city; showCharacter(); });
        card.addView(b, lp(-1, 54));
        return card;
    }

    private void showCharacter() {
        LinearLayout root = base();
        addBrand(root, "SEU PERSONAGEM", "");
        root.addView(text(selectedCity + "  •  NOVO PERSONAGEM", 13, PURPLE2, true), lp(-1, 32));
        EditText name = input("Nome do personagem");
        root.addView(name, lp(-1, 58));
        addSpace(root, 16);
        root.addView(text("GÊNERO", 12, MUTED, true), lp(-1, 26));
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        Button male = outlineButton("MASCULINO");
        Button female = outlineButton("FEMININO");
        male.setOnClickListener(v -> { selectedGender = "Masculino"; selected(male); selected(female); });
        female.setOnClickListener(v -> { selectedGender = "Feminino"; selected(female); selected(male); });
        row.addView(male, lp(0, 56, 1));
        row.addView(female, lp(0, 56, 1));
        root.addView(row, lp(-1, 58));
        addSpace(root, 12);
        root.addView(text("A escolha de gênero não limita relacionamentos ou casamento.", 11, MUTED, false), lp(-1, 34));
        Button enter = button("CRIAR E ENTRAR NA CIDADE", false);
        enter.setOnClickListener(v -> {
            if (name.getText().toString().trim().isEmpty()) {
                name.setError("Digite o nome do personagem");
                return;
            }
            if (selectedGender.isEmpty()) {
                Toast.makeText(this, "Escolha o gênero do personagem", Toast.LENGTH_SHORT).show();
                return;
            }
            showWorld(name.getText().toString().trim());
        });
        root.addView(enter, lp(-1, 60));
        setContentView(wrap(root));
    }

    private void showWorld(String character) {
        LinearLayout root = base();
        addBrand(root, "NYX", "ROLEPLAY");
        root.addView(text(selectedCity + "  •  " + character, 14, Color.WHITE, true), lp(-1, 34));
        LinearLayout map = new LinearLayout(this);
        map.setOrientation(LinearLayout.VERTICAL);
        map.setGravity(Gravity.CENTER);
        map.setPadding(24, 18, 24, 18);
        map.setBackground(panel());
        map.addView(text("MAPA DO MUNDO", 20, PURPLE2, true), lp(-1, 34));
        map.addView(text("CURITIBA       🌉       FLORIANÓPOLIS", 16, Color.WHITE, true), lp(-1, 44));
        map.addView(text("Ponte da Integração  •  Mirante  •  NYXStore", 12, MUTED, false), lp(-1, 38));
        map.addView(text("Hospital  •  Polícia  •  BOPE  •  Exército  •  Universidade  •  Jornal", 11, MUTED, false), lp(-1, 38));
        root.addView(map, lp(-1, 200));
        addSpace(root, 14);
        Button play = button("ENTRAR NO MUNDO", false);
        play.setOnClickListener(v -> Toast.makeText(this, "Cliente Nyx pronto para a próxima camada do mundo 3D.", Toast.LENGTH_LONG).show());
        root.addView(play, lp(-1, 58));
        root.addView(text("NYXSTORE  •  ROUPAS  •  VEÍCULOS  •  EMPREGOS  •  CASAMENTO", 10, MUTED, true), lp(-1, 42));
        setContentView(wrap(root));
    }

    private void addBrand(LinearLayout root, String main, String sub) {
        TextView a = title(main, 32);
        a.setGravity(Gravity.CENTER_VERTICAL);
        root.addView(a, lp(-1, 48));
        if (!sub.isEmpty()) {
            TextView b = title(sub, 13);
            b.setLetterSpacing(.28f);
            root.addView(b, lp(-1, 30));
        }
    }

    private LinearLayout base() {
        LinearLayout r = new LinearLayout(this);
        r.setOrientation(LinearLayout.VERTICAL);
        r.setGravity(Gravity.CENTER_HORIZONTAL);
        r.setPadding(24, 18, 24, 18);
        r.setBackgroundColor(BG);
        return r;
    }

    private ScrollView wrap(View v) {
        ScrollView s = new ScrollView(this);
        s.setFillViewport(true);
        s.setClipToPadding(false);
        s.addView(v);
        return s;
    }

    private TextView title(String v, float z) { return text(v, z, Color.WHITE, true); }
    private TextView text(String v, float z, int c, boolean b) {
        TextView t = new TextView(this);
        t.setText(v);
        t.setTextSize(z);
        t.setTextColor(c);
        t.setGravity(Gravity.CENTER_VERTICAL);
        t.setTypeface(Typeface.DEFAULT, b ? Typeface.BOLD : Typeface.NORMAL);
        return t;
    }

    private EditText input(String hint) {
        EditText e = new EditText(this);
        e.setHint(hint);
        e.setHintTextColor(Color.rgb(105, 102, 120));
        e.setTextColor(Color.WHITE);
        e.setTextSize(16);
        e.setSingleLine(true);
        e.setPadding(18, 0, 18, 0);
        e.setBackground(round(SURFACE, 16));
        return e;
    }

    private Button button(String v, boolean google) {
        Button b = new Button(this);
        b.setText(v);
        b.setTextColor(google ? Color.rgb(25,25,30) : Color.WHITE);
        b.setTextSize(14);
        b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        b.setAllCaps(false);
        b.setGravity(Gravity.CENTER);
        b.setMinHeight(0);
        b.setPadding(8, 0, 8, 0);
        b.setBackground(round(google ? Color.WHITE : PURPLE, 18));
        return b;
    }

    private Button outlineButton(String v) {
        Button b = button(v, false);
        b.setBackground(stroke());
        return b;
    }

    private void selected(Button b) {
        b.setTextColor(Color.WHITE);
        b.setBackground(round(PURPLE, 18));
    }

    private GradientDrawable panel() { return round(SURFACE2, 20); }
    private GradientDrawable round(int c, int r) {
        GradientDrawable g = new GradientDrawable();
        g.setColor(c);
        g.setCornerRadius(r);
        g.setStroke(1, Color.rgb(52, 45, 72));
        return g;
    }
    private GradientDrawable stroke() {
        GradientDrawable g = round(Color.TRANSPARENT, 18);
        g.setStroke(2, Color.rgb(110, 78, 180));
        return g;
    }
    private LinearLayout.LayoutParams lp(int w, int h) { return new LinearLayout.LayoutParams(w, h); }
    private LinearLayout.LayoutParams lp(int w, int h, float weight) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, h, weight);
        p.setMargins(6, 0, 6, 0);
        return p;
    }
    private void addSpace(LinearLayout r, int h) { r.addView(new View(this), lp(1, h)); }
}
