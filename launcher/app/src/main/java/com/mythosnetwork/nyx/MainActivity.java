package com.mythosnetwork.nyx;

import android.app.*;import android.os.*;import android.content.*;import android.content.pm.PackageManager;import android.graphics.Color;import android.view.*;import android.widget.*;import android.net.Uri;

public class MainActivity extends Activity {
 static final int PICK=1001; TextView status;
 TextView t(String s,float z){TextView v=new TextView(this);v.setText(s);v.setTextColor(Color.rgb(248,247,255));v.setTextSize(z);v.setGravity(Gravity.CENTER);return v;}
 public void onCreate(Bundle b){super.onCreate(b); LinearLayout r=new LinearLayout(this);r.setOrientation(LinearLayout.VERTICAL);r.setGravity(Gravity.CENTER_HORIZONTAL);r.setPadding(32,55,32,30);r.setBackgroundColor(Color.rgb(8,7,13));
 ImageView l=new ImageView(this);l.setImageResource(R.drawable.nyx_logo);r.addView(l,new LinearLayout.LayoutParams(110,110));
 TextView title=t("NYX ROLEPLAY",29);title.setTypeface(null,1);r.addView(title,new LinearLayout.LayoutParams(-1,65));TextView sub=t("MYTHØS NETWORK",13);sub.setTextColor(Color.rgb(196,181,253));r.addView(sub,new LinearLayout.LayoutParams(-1,40));
 Button folder=new Button(this);folder.setText("SELECIONAR GTA");folder.setOnClickListener(v->pick());r.addView(folder,new LinearLayout.LayoutParams(-1,60));
 Button play=new Button(this);play.setText("JOGAR NYX");play.setTextSize(17);play.setOnClickListener(v->launchGta());LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,65);p.setMargins(0,18,0,12);r.addView(play,p);
 status=t("Verificando GTA San Andreas...",13);status.setTextColor(Color.rgb(169,162,184));r.addView(status,new LinearLayout.LayoutParams(-1,80));setContentView(r);check(); }
 boolean installed(){try{getPackageManager().getPackageInfo("com.rockstargames.gtasa",0);return true;}catch(Exception e){return false;}}
 void check(){status.setText(installed()?"GTA San Andreas encontrado.":"GTA San Andreas não encontrado.");}
 void pick(){Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);startActivityForResult(i,PICK);}
 void launchGta(){if(!installed()){new AlertDialog.Builder(this).setTitle("GTA não encontrado").setMessage("Instale o GTA San Andreas compatível antes de iniciar o Nyx Roleplay.").setPositiveButton("OK",null).show();return;}Intent i=getPackageManager().getLaunchIntentForPackage("com.rockstargames.gtasa");if(i!=null){status.setText("Iniciando GTA...");startActivity(i);}else status.setText("Não foi possível iniciar o GTA.");}
 protected void onActivityResult(int r,int c,Intent d){super.onActivityResult(r,c,d);if(r==PICK&&c==RESULT_OK&&d!=null){getPreferences(0).edit().putString("gta_tree",d.getData().toString()).apply();status.setText("Pasta GTA vinculada. Nyx pronto para iniciar.");}}
}
