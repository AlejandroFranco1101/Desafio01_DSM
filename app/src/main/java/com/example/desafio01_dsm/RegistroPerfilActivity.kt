package com.example.desafio01_dsm

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistroPerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_perfil)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registroRoot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btnTakePhoto).setOnClickListener {
            Toast.makeText(this, R.string.camera_permission_pending, Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnSaveProfile).setOnClickListener {
            Toast.makeText(this, R.string.profile_validation_pending, Toast.LENGTH_SHORT).show()
        }
    }
}
