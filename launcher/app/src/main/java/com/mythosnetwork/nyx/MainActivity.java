package com.mythosnetwork.nyx;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final int BG = Color.rgb(7,7,17);
    private static final int SURFACE = Color.rgb(17,17,29);
    private static final int PURPLE = Color.rgb(139,92,246);
    private static final int MUTED = Color.rgb(156,163,175);
    private String selectedCity = "";
    private String selectedGender = "";
    private EditText nameInput;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);
        showLogin();
    }

    private void showLogin() {
        LinearLayout root = base();
        root.addView(title("NYX", 46), lp(-1,62));
        TextView rp = title("ROLEPLAY", 13);
        rp.setLetterSpacing(.30f);
        root.addView(rp, lp(-1,32));
        root.addView(text("Entre no seu mundo. Construa sua história.", 15, MUTED, false), lp(-1,48));

        nameInput = new EditText(this);
        nameInput.setHint("E-mail ou nome de usuário");
        nameInput.setHintTextColor(Color.rgb(110,110,125));
        nameInput.setTextColor(Color.WHITE);
        nameInput.setSingleLine(true);
        styleInput(nameInput);
        root.addView(nameInput, lp(-1,58));
        addSpace(root,12);

        EditText pass = new EditText(this);
        pass.setHint("Senha");
        pass.setHintTextColor(Color.rgb(110,110,125));
        pass.setTextColor(Color.WHITE);
        pass.setSingleLine(true);
        pass.setInputType(0x81);
        styleInput(pass);
        root.addView(pass, lp(-1,58));
        addSpace(root,22);

        Button login = button("ENTRAR");
        login.setOnClickListener(v -> showCities());
        root.addView(login, lp(-1,58));
        addSpace(root,12);
        Button create = outlineButton("CRIAR CONTA");
        create.setOnClickListener(v -> showCities());
        root.addView(create, lp(-1,58));
        root.addView(text("MYTHØS NETWORK  •  NYX ROLEPLAY", 10, Color.rgb(95,90,110), true), lp(-1,55));
        setContentView(wrap(root));
    }

    private void showCities() {
        LinearLayout root = base();
        root.addView(title("ESCOLHA SUA CIDADE", 28), lp(-1,55));
        root.addView(text("Cada cidade possui um personagem e progresso próprios.", 13, MUTED, false), lp(-1,55));
        root.addView(cityCard("CURITIBA", "Cidade urbana • empregos • polícia • universidade", "CIDADE 01"), lp(-1,180));
        addSpace(root,16);
        root.addView(cityCard("FLORIANÓPOLIS", "Ilha • praias • turismo • porto • comunidades", "CIDADE 02"), lp(-1,180));
        setContentView(wrap(root));
    }

    private View cityCard(String city, String desc, String id) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(22,18,22,16);
        card.setBackground(panel());
        card.addView(text(id,11,PURPLE,true),lp(-1,24));
        card.addView(text(city,25,Color.WHITE,true),lp(-1,42));
        card.addView(text(desc,12,MUTED,false),lp(-1,38));
        Button b = button("ESCOLHER  ›");
        b.setOnClickListener(v -> { selectedCity=city; showCharacter(); });
        card.addView(b,lp(-1,52));
        return card;
    }

    private void showCharacter() {
        LinearLayout root = base();
        root.addView(title("SEU PERSONAGEM",30),lp(-1,55));
        root.addView(text(selectedCity+"  •  NOVO PERSONAGEM",12,PURPLE,true),lp(-1,35));
        EditText name = new EditText(this);
        name.setHint("Nome do personagem");
        name.setHintTextColor(Color.rgb(110,110,125));
        name.setTextColor(Color.WHITE);
        name.setSingleLine(true);
        styleInput(name);
        root.addView(name,lp(-1,58));
        addSpace(root,18);
        root.addView(text("GÊNERO",12,MUTED,true),lp(-1,30));
        LinearLayout row=new LinearLayout(this); row.setOrientation(LinearLayout.HORIZONTAL);
        Button male=outlineButton("MASCULINO"); Button female=outlineButton("FEMININO");
        male.setOnClickListener(v->{selectedGender="Masculino";Toast.makeText(this,"Masculino selecionado",Toast.LENGTH_SHORT).show();});
        female.setOnClickListener(v->{selectedGender="Feminino";Toast.makeText(this,"Feminino selecionado",Toast.LENGTH_SHORT).show();});
        row.addView(male,lp(0,56,1)); row.addView(female,lp(0,56,1));
        root.addView(row,lp(-1,56));
        addSpace(root,18);
        root.addView(text("A escolha de gênero não limita relacionamentos ou casamento.",11,MUTED,false),lp(-1,45));
        Button enter=button("CRIAR E ENTRAR NA CIDADE");
        enter.setOnClickListener(v->showWorld(name.getText().toString().trim()));
        root.addView(enter,lp(-1,60));
        setContentView(wrap(root));
    }

    private void showWorld(String character) {
        if(character.isEmpty()) character="Cidadão Nyx";
        LinearLayout root=base();
        root.addView(title("NYX",38),lp(-1,50));
        root.addView(text(selectedCity+"  •  "+character,13,Color.WHITE,true),lp(-1,36));
        LinearLayout map=new LinearLayout(this); map.setOrientation(LinearLayout.VERTICAL); map.setGravity(Gravity.CENTER); map.setPadding(20,20,20,20); map.setBackground(panel());
        map.addView(text("MAPA DO MUNDO",16,PURPLE,true),lp(-1,35));
        map.addView(text("🌆  CURITIBA        🌉        FLORIANÓPOLIS  🌴",14,Color.WHITE,true),lp(-1,55));
        map.addView(text("Ponte da Integração • Mirante do Vale • NYXStore",12,MUTED,false),lp(-1,50));
        map.addView(text("Hospital • Polícia • BOPE • Exército • Universidade • Jornal",11,MUTED,false),lp(-1,45));
        root.addView(map,lp(-1,220));
        addSpace(root,16);
        Button play=button("ENTRAR NO MUNDO");
        play.setOnClickListener(v->Toast.makeText(this,"Cliente nativo Nyx iniciado. Mapa 3D e multiplayer entram na próxima camada.",Toast.LENGTH_LONG).show());
        root.addView(play,lp(-1,60));
        root.addView(text("NYXSTORE  •  ROUPAS  •  VEÍCULOS  •  EMPREGOS  •  CASAMENTO",10,MUTED,true),lp(-1,60));
        setContentView(wrap(root));
    }

    private LinearLayout base(){ LinearLayout r=new LinearLayout(this); r.setOrientation(LinearLayout.VERTICAL); r.setGravity(Gravity.CENTER_HORIZONTAL); r.setPadding(24,34,24,28); r.setBackgroundColor(BG); return r; }
    private ScrollView wrap(View v){ ScrollView s=new ScrollView(this); s.setFillViewport(true); s.addView(v); return s; }
    private TextView title(String v,float z){ return text(v,z,Color.WHITE,true); }
    private TextView text(String v,float z,int c,boolean b){ TextView t=new TextView(this); t.setText(v); t.setTextSize(z); t.setTextColor(c); t.setGravity(Gravity.CENTER_VERTICAL); t.setTypeface(Typeface.DEFAULT,b?Typeface.BOLD:Typeface.NORMAL); return t; }
    private Button button(String v){ Button b=new Button(this); b.setText(v); b.setTextColor(Color.WHITE); b.setTextSize(14); b.setTypeface(Typeface.DEFAULT,Typeface.BOLD); b.setAllCaps(false); b.setBackground(round(PURPLE,18)); return b; }
    private Button outlineButton(String v){ Button b=button(v); b.setBackground(stroke()); return b; }
    private GradientDrawable panel(){ return round(SURFACE,20); }
    private GradientDrawable round(int c,int r){ GradientDrawable g=new GradientDrawable(); g.setColor(c); g.setCornerRadius(r); g.setStroke(1,Color.rgb(48,44,64)); return g; }
    private GradientDrawable stroke(){ GradientDrawable g=round(Color.TRANSPARENT,18); g.setStroke(1,Color.rgb(110,85,170)); return g; }
    private void styleInput(EditText e){ e.setPadding(18,0,18,0); e.setBackground(round(SURFACE,16)); }
    private LinearLayout.LayoutParams lp(int w,int h){ return new LinearLayout.LayoutParams(w,h); }
    private LinearLayout.LayoutParams lp(int w,int h,float weight){ LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(w,h,weight); p.setMargins(6,0,6,0); return p; }
    private void addSpace(LinearLayout r,int h){ r.addView(new View(this),lp(1,h)); }
}
