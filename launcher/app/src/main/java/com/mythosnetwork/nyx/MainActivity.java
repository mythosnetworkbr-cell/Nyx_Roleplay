package com.mythosnetwork.nyx;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity {
    static final int PICK = 1001;
    TextView status;
    int bg=Color.rgb(7,6,12), panel=Color.rgb(18,16,27), panel2=Color.rgb(25,22,37), purple=Color.rgb(139,92,246), purpleLight=Color.rgb(196,181,253), white=Color.rgb(248,247,255), muted=Color.rgb(165,158,181);
    GradientDrawable rounded(int c,float r){GradientDrawable g=new GradientDrawable();g.setColor(c);g.setCornerRadius(r);return g;}
    TextView label(String s,float z,int c){TextView v=new TextView(this);v.setText(s);v.setTextColor(c);v.setTextSize(z);v.setGravity(Gravity.CENTER_VERTICAL);return v;}
    TextView centered(String s,float z,int c){TextView v=label(s,z,c);v.setGravity(Gravity.CENTER);return v;}
    Button actionButton(String s,int c,int tc){Button b=new Button(this);b.setText(s);b.setTextColor(tc);b.setTextSize(14);b.setAllCaps(false);b.setTypeface(Typeface.DEFAULT,Typeface.BOLD);b.setGravity(Gravity.CENTER);b.setPadding(12,0,12,0);b.setBackground(rounded(c,28));return b;}
    @Override public void onCreate(Bundle b){super.onCreate(b);getWindow().setStatusBarColor(bg);getWindow().setNavigationBarColor(bg);
        ScrollView scroll=new ScrollView(this);scroll.setFillViewport(true);scroll.setBackgroundColor(bg);
        LinearLayout root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(22,26,22,26);scroll.addView(root);
        LinearLayout header=new LinearLayout(this);header.setGravity(Gravity.CENTER_VERTICAL);
        ImageView logo=new ImageView(this);logo.setImageResource(R.drawable.nyx_logo);logo.setPadding(5,5,5,5);header.addView(logo,new LinearLayout.LayoutParams(66,66));
        LinearLayout titles=new LinearLayout(this);titles.setOrientation(LinearLayout.VERTICAL);TextView name=label("NYX ROLEPLAY",22,white);name.setTypeface(Typeface.DEFAULT,Typeface.BOLD);titles.addView(name,new LinearLayout.LayoutParams(-1,38));titles.addView(label("MYTHØS NETWORK",11,purpleLight),new LinearLayout.LayoutParams(-1,25));header.addView(titles,new LinearLayout.LayoutParams(0,66,1));
        TextView online=centered("● ONLINE",10,Color.rgb(134,239,172));online.setBackground(rounded(Color.rgb(19,45,31),24));header.addView(online,new LinearLayout.LayoutParams(84,34));root.addView(header);
        root.addView(new Space(this),new LinearLayout.LayoutParams(1,22));
        LinearLayout hero=new LinearLayout(this);hero.setOrientation(LinearLayout.VERTICAL);hero.setPadding(22,22,22,22);hero.setBackground(rounded(panel,24));
        TextView headline=label("Entre no Nyx Roleplay.\nViva sua própria história.",28,white);headline.setTypeface(Typeface.DEFAULT,Typeface.BOLD);hero.addView(headline,new LinearLayout.LayoutParams(-1,82));hero.addView(label("Um roleplay mobile feito para você.",13,muted),new LinearLayout.LayoutParams(-1,40));root.addView(hero);
        root.addView(new Space(this),new LinearLayout.LayoutParams(1,14));
        LinearLayout statusCard=new LinearLayout(this);statusCard.setGravity(Gravity.CENTER_VERTICAL);statusCard.setPadding(18,12,18,12);statusCard.setBackground(rounded(panel2,18));TextView dot=centered("●",18,Color.rgb(134,239,172));statusCard.addView(dot,new LinearLayout.LayoutParams(34,40));status=label("Verificando GTA San Andreas...",13,white);statusCard.addView(status,new LinearLayout.LayoutParams(0,40,1));statusCard.addView(centered("MOBILE",10,muted),new LinearLayout.LayoutParams(62,40));root.addView(statusCard);
        root.addView(new Space(this),new LinearLayout.LayoutParams(1,14));
        Button play=actionButton("▶   JOGAR NYX ROLEPLAY",purple,Color.WHITE);play.setTextSize(17);root.addView(play,new LinearLayout.LayoutParams(-1,68));play.setOnClickListener(v->launchGta());
        root.addView(new Space(this),new LinearLayout.LayoutParams(1,12));
        Button folder=actionButton("📁   Selecionar pasta do GTA",panel2,white);root.addView(folder,new LinearLayout.LayoutParams(-1,58));folder.setOnClickListener(v->pick());
        root.addView(new Space(this),new LinearLayout.LayoutParams(1,20));TextView section=label("SERVIDOR",11,muted);section.setTypeface(Typeface.DEFAULT,Typeface.BOLD);root.addView(section,new LinearLayout.LayoutParams(-1,26));
        LinearLayout server=new LinearLayout(this);server.setPadding(18,14,18,14);server.setGravity(Gravity.CENTER_VERTICAL);server.setBackground(rounded(panel,18));LinearLayout st=new LinearLayout(this);st.setOrientation(LinearLayout.VERTICAL);TextView sn=label("Nyx Roleplay",16,white);sn.setTypeface(Typeface.DEFAULT,Typeface.BOLD);st.addView(sn,new LinearLayout.LayoutParams(-1,28));st.addView(label("Roleplay Mobile",12,muted),new LinearLayout.LayoutParams(-1,24));server.addView(st,new LinearLayout.LayoutParams(0,52,1));server.addView(centered("›",28,purpleLight),new LinearLayout.LayoutParams(35,52));root.addView(server);
        root.addView(new Space(this),new LinearLayout.LayoutParams(1,18));root.addView(centered("NYX ROLEPLAY  •  MYTHØS NETWORK\nVersão 1.0.0",10,Color.rgb(105,99,120)),new LinearLayout.LayoutParams(-1,45));setContentView(scroll);check();}
    boolean installed(){try{getPackageManager().getPackageInfo("com.rockstargames.gtasa",0);return true;}catch(Exception e){return false;}}
    void check(){status.setText(installed()?"GTA San Andreas encontrado — pronto para jogar":"GTA San Andreas não encontrado");}
    void pick(){Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);startActivityForResult(i,PICK);}
    void launchGta(){if(!installed()){new AlertDialog.Builder(this).setTitle("GTA San Andreas não encontrado").setMessage("Instale uma versão compatível do GTA San Andreas e selecione a pasta dos arquivos do jogo.").setPositiveButton("Selecionar pasta",(d,w)->pick()).setNegativeButton("Fechar",null).show();return;}Intent i=getPackageManager().getLaunchIntentForPackage("com.rockstargames.gtasa");if(i!=null){status.setText("Iniciando Nyx Roleplay...");startActivity(i);}else status.setText("Não foi possível iniciar o GTA.");}
    @Override protected void onActivityResult(int r,int c,Intent d){super.onActivityResult(r,c,d);if(r==PICK&&c==RESULT_OK&&d!=null){try{getContentResolver().takePersistableUriPermission(d.getData(),Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);}catch(Exception ignored){}getPreferences(0).edit().putString("gta_tree",d.getData().toString()).apply();status.setText("Pasta GTA vinculada — Nyx pronto para iniciar");}}
}