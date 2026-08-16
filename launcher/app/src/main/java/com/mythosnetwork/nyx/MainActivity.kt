package com.mythosnetwork.nyx

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import java.text.DecimalFormat

class MainActivity : Activity() {
    private lateinit var status: TextView
    private lateinit var progress: ProgressBar

    companion object {
        private const val CLIENT_DOWNLOAD_URL = "https://github.com/mythosnetworkbr-cell/Nyx_Roleplay/releases/latest/download/nyx-client.apk"
        private const val VERSION = "1.2.0"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(8, 7, 13)
        window.navigationBarColor = Color.rgb(8, 7, 13)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        buildLauncher()
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()

    private fun text(value: String, size: Float, color: Int, bold: Boolean = false): TextView = TextView(this).apply {
        this.text = value
        textSize = size
        setTextColor(color)
        typeface = if (bold) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
    }

    private fun card(): LinearLayout = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(20), dp(18), dp(20), dp(18))
        setBackgroundColor(Color.rgb(18, 15, 27))
    }

    private fun button(label: String, primary: Boolean): Button = Button(this).apply {
        text = label
        textSize = 15f
        isAllCaps = false
        setTextColor(Color.WHITE)
        setBackgroundColor(if (primary) Color.rgb(109, 43, 255) else Color.rgb(35, 30, 48))
        minHeight = dp(54)
    }

    private fun buildLauncher() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(22), dp(18), dp(22), dp(18))
            setBackgroundColor(Color.rgb(8, 7, 13))
        }

        val header = LinearLayout(this).apply {
            gravity = Gravity.CENTER_VERTICAL
        }
        val logo = ImageView(this).apply {
            setImageResource(com.mythosnetwork.nyx.R.drawable.nyx_roleplay_icon)
            scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
        header.addView(logo, LinearLayout.LayoutParams(dp(76), dp(76)))
        val titleBox = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        titleBox.addView(text("NYX ROLEPLAY", 25f, Color.WHITE, true))
        titleBox.addView(text("MOBILE LAUNCHER", 12f, Color.rgb(201, 167, 255), true))
        header.addView(titleBox, LinearLayout.LayoutParams(0, dp(76), 1f))
        val version = text("v$VERSION", 12f, Color.LTGRAY)
        version.gravity = Gravity.CENTER
        header.addView(version, LinearLayout.LayoutParams(dp(62), dp(40)))
        root.addView(header)

        val scroll = ScrollView(this).apply { isFillViewport = true }
        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, dp(14), 0, dp(14))
        }

        val hero = card()
        hero.addView(text("CIDADE VIVA.\nSEU PERSONAGEM.\nSUA HISTÓRIA.", 26f, Color.WHITE, true))
        hero.addView(text("Entre no universo NYX e continue sua jornada diretamente pelo launcher.", 14f, Color.LTGRAY).apply {
            setPadding(0, dp(10), 0, dp(14))
        })
        val play = button("JOGAR AGORA", true)
        play.setOnClickListener { startClientDownload() }
        hero.addView(play)
        content.addView(hero, LinearLayout.LayoutParams(-1, dp(230)).apply { bottomMargin = dp(12) })

        val serverCard = card()
        serverCard.addView(text("STATUS DO SERVIDOR", 13f, Color.rgb(201, 167, 255), true))
        serverCard.addView(text("NYX ROLEPLAY", 20f, Color.WHITE, true).apply { setPadding(0, dp(8), 0, 0) })
        serverCard.addView(text("Online / aguardando conexão do Game_base", 13f, Color.LTGRAY).apply { setPadding(0, dp(4), 0, dp(10)) })
        content.addView(serverCard, LinearLayout.LayoutParams(-1, dp(132)).apply { bottomMargin = dp(12) })

        val updateCard = card()
        updateCard.addView(text("CLIENTE", 13f, Color.rgb(201, 167, 255), true))
        updateCard.addView(text("Arquivos do jogo", 18f, Color.WHITE, true).apply { setPadding(0, dp(8), 0, dp(2)) })
        updateCard.addView(text("O launcher verifica e baixa a versão publicada do cliente.", 13f, Color.LTGRAY))
        progress = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal)
        progress.max = 100
        progress.visibility = View.GONE
        updateCard.addView(progress, LinearLayout.LayoutParams(-1, dp(8)).apply { topMargin = dp(12) })
        val update = button("VERIFICAR / BAIXAR CLIENTE", false)
        update.setOnClickListener { startClientDownload() }
        updateCard.addView(update, LinearLayout.LayoutParams(-1, dp(54)).apply { topMargin = dp(12) })
        content.addView(updateCard, LinearLayout.LayoutParams(-1, dp(190)).apply { bottomMargin = dp(12) })

        val news = card()
        news.addView(text("NOVIDADES NYX", 13f, Color.rgb(201, 167, 255), true))
        news.addView(text("• Launcher redesenhado\n• Preparação para atualização automática\n• Integração com Game_base\n• Cliente e launcher com versão controlada", 13f, Color.LTGRAY).apply { setPadding(0, dp(10), 0, 0) })
        content.addView(news, LinearLayout.LayoutParams(-1, dp(170)).apply { bottomMargin = dp(12) })

        status = text("Pronto para iniciar.", 13f, Color.rgb(201, 167, 255))
        status.gravity = Gravity.CENTER
        content.addView(status, LinearLayout.LayoutParams(-1, dp(50)))

        scroll.addView(content)
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        setContentView(root)
    }

    private fun startClientDownload() {
        try {
            progress.visibility = View.VISIBLE
            progress.progress = 0
            status.text = "Iniciando download do cliente..."
            val request = DownloadManager.Request(Uri.parse(CLIENT_DOWNLOAD_URL))
                .setTitle("NYX Roleplay — Cliente")
                .setDescription("Baixando o cliente NYX Roleplay")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "nyx-client.apk")
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(false)
            val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            manager.enqueue(request)
            status.text = "Download iniciado. Acompanhe a notificação do Android."
            progress.progress = 10
        } catch (error: Exception) {
            progress.visibility = View.GONE
            status.text = "Falha ao iniciar o download: ${error.javaClass.simpleName}"
        }
    }
}
