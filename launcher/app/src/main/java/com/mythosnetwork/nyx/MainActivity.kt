package com.mythosnetwork.nyx

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : Activity() {
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildLauncher()
    }

    private fun buildLauncher() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(48, 48, 48, 36)
            setBackgroundColor(Color.rgb(8, 7, 13))
        }

        val logo = TextView(this).apply {
            text = "NYX"
            textSize = 52f
            setTextColor(Color.rgb(201, 167, 255))
            gravity = Gravity.CENTER
        }
        root.addView(logo, LinearLayout.LayoutParams(-1, 90))

        val subtitle = TextView(this).apply {
            text = "ROLEPLAY LAUNCHER"
            textSize = 16f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }
        root.addView(subtitle, LinearLayout.LayoutParams(-1, 50))

        val server = TextView(this).apply {
            text = "SERVIDOR NYX\nStatus: configuração do servidor em andamento"
            textSize = 15f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 18, 0, 18)
        }
        root.addView(server, LinearLayout.LayoutParams(-1, 100))

        val play = Button(this).apply {
            text = "JOGAR"
            textSize = 18f
            setOnClickListener {
                status.text = "Cliente selecionado. A conexão será habilitada quando o endpoint do Game_base estiver configurado."
            }
        }
        root.addView(play, LinearLayout.LayoutParams(-1, 64))

        val update = Button(this).apply {
            text = "VERIFICAR ATUALIZAÇÃO"
            setOnClickListener {
                status.text = "Launcher atualizado. Nenhum pacote de atualização foi configurado ainda."
            }
        }
        root.addView(update, LinearLayout.LayoutParams(-1, 58))

        status = TextView(this).apply {
            text = "Pronto."
            textSize = 14f
            setTextColor(Color.rgb(201, 167, 255))
            gravity = Gravity.CENTER
            setPadding(0, 24, 0, 0)
        }
        root.addView(status, LinearLayout.LayoutParams(-1, 100))

        val scroll = ScrollView(this)
        val info = TextView(this).apply {
            text = "NYX Roleplay\n\nLauncher Android\nCliente: Nyx Roleplay\nGame: Game_base\n\nEste launcher será responsável por atualização, seleção do servidor e inicialização do cliente."
            textSize = 13f
            setTextColor(Color.GRAY)
            setPadding(0, 12, 0, 12)
        }
        scroll.addView(info)
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))

        setContentView(root)
    }
}
