package com.example.desafio01_dsm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PerfilGuardadoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil_guardado)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.perfilGuardadoRoot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        showProfileData()
        setupButtons()
    }

    private fun showProfileData() {
        findViewById<TextView>(R.id.tvSummaryFullName).text =
            intent.getStringExtra(RegistroPerfilActivity.EXTRA_FULL_NAME).orEmpty()
        findViewById<TextView>(R.id.tvSummaryEmail).text =
            intent.getStringExtra(RegistroPerfilActivity.EXTRA_EMAIL).orEmpty()
        findViewById<TextView>(R.id.tvSummaryPhone).text =
            intent.getStringExtra(RegistroPerfilActivity.EXTRA_PHONE).orEmpty()
        findViewById<TextView>(R.id.tvSummaryBirthDate).text =
            intent.getStringExtra(RegistroPerfilActivity.EXTRA_BIRTH_DATE).orEmpty()
        findViewById<TextView>(R.id.tvSummaryAddress).text =
            intent.getStringExtra(RegistroPerfilActivity.EXTRA_ADDRESS).orEmpty()
    }

    private fun setupButtons() {
        findViewById<Button>(R.id.btnBackHome).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.btnNewProfile).setOnClickListener {
            val intent = Intent(this, RegistroPerfilActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
