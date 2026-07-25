package com.example.desafio01_dsm

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistroPerfilActivity : AppCompatActivity() {
    private lateinit var cameraStatusText: TextView

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        updateCameraStatus(isGranted)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_perfil)
        cameraStatusText = findViewById(R.id.tvCameraStatus)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registroRoot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btnTakePhoto).setOnClickListener {
            requestCameraPermission()
        }

        findViewById<Button>(R.id.btnSaveProfile).setOnClickListener {
            Toast.makeText(this, R.string.profile_validation_pending, Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestCameraPermission() {
        val permission = Manifest.permission.CAMERA
        val isAlreadyGranted = ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED

        if (isAlreadyGranted) {
            updateCameraStatus(true)
        } else {
            cameraPermissionLauncher.launch(permission)
        }
    }

    private fun updateCameraStatus(isGranted: Boolean) {
        val messageRes = if (isGranted) {
            R.string.camera_permission_granted
        } else {
            R.string.camera_permission_denied
        }

        cameraStatusText.setText(messageRes)
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }
}
