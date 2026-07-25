package com.example.desafio01_dsm

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegistroPerfilActivity : AppCompatActivity() {
    private lateinit var cameraStatusText: TextView
    private lateinit var fullNameLayout: TextInputLayout
    private lateinit var emailLayout: TextInputLayout
    private lateinit var phoneLayout: TextInputLayout
    private lateinit var birthDateLayout: TextInputLayout
    private lateinit var addressLayout: TextInputLayout
    private lateinit var fullNameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var phoneEditText: TextInputEditText
    private lateinit var birthDateEditText: TextInputEditText
    private lateinit var addressEditText: TextInputEditText

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
        bindFormViews()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registroRoot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btnTakePhoto).setOnClickListener {
            requestCameraPermission()
        }

        findViewById<Button>(R.id.btnSaveProfile).setOnClickListener {
            if (validateProfileForm()) {
                openSavedProfile()
            }
        }
    }

    private fun bindFormViews() {
        fullNameLayout = findViewById(R.id.tilFullName)
        emailLayout = findViewById(R.id.tilEmail)
        phoneLayout = findViewById(R.id.tilPhone)
        birthDateLayout = findViewById(R.id.tilBirthDate)
        addressLayout = findViewById(R.id.tilAddress)
        fullNameEditText = findViewById(R.id.etFullName)
        emailEditText = findViewById(R.id.etEmail)
        phoneEditText = findViewById(R.id.etPhone)
        birthDateEditText = findViewById(R.id.etBirthDate)
        addressEditText = findViewById(R.id.etAddress)
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

    private fun validateProfileForm(): Boolean {
        clearErrors()

        val fullName = fullNameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()
        val birthDate = birthDateEditText.text.toString().trim()
        val address = addressEditText.text.toString().trim()
        var isValid = true

        if (fullName.isEmpty()) {
            fullNameLayout.error = getString(R.string.required_field_error)
            isValid = false
        }

        if (email.isEmpty()) {
            emailLayout.error = getString(R.string.required_field_error)
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.error = getString(R.string.email_format_error)
            isValid = false
        }

        if (phone.isEmpty()) {
            phoneLayout.error = getString(R.string.required_field_error)
            isValid = false
        } else if (!phone.matches(Regex("\\d{8,15}"))) {
            phoneLayout.error = getString(R.string.phone_format_error)
            isValid = false
        }

        if (birthDate.isEmpty()) {
            birthDateLayout.error = getString(R.string.required_field_error)
            isValid = false
        } else if (!isValidBirthDate(birthDate)) {
            birthDateLayout.error = getString(R.string.date_format_error)
            isValid = false
        }

        if (address.isEmpty()) {
            addressLayout.error = getString(R.string.required_field_error)
            isValid = false
        }

        return isValid
    }

    private fun openSavedProfile() {
        val intent = Intent(this, PerfilGuardadoActivity::class.java).apply {
            putExtra(EXTRA_FULL_NAME, fullNameEditText.text.toString().trim())
            putExtra(EXTRA_EMAIL, emailEditText.text.toString().trim())
            putExtra(EXTRA_PHONE, phoneEditText.text.toString().trim())
            putExtra(EXTRA_BIRTH_DATE, birthDateEditText.text.toString().trim())
            putExtra(EXTRA_ADDRESS, addressEditText.text.toString().trim())
        }

        startActivity(intent)
    }

    private fun clearErrors() {
        fullNameLayout.error = null
        emailLayout.error = null
        phoneLayout.error = null
        birthDateLayout.error = null
        addressLayout.error = null
    }

    private fun isValidBirthDate(value: String): Boolean {
        val match = Regex("""^(\d{2})/(\d{2})/(\d{4})$""").matchEntire(value) ?: return false
        val day = match.groupValues[1].toInt()
        val month = match.groupValues[2].toInt()
        val year = match.groupValues[3].toInt()

        if (year !in 1900..2026 || month !in 1..12) {
            return false
        }

        val maxDay = when (month) {
            2 -> if (isLeapYear(year)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }

        return day in 1..maxDay
    }

    private fun isLeapYear(year: Int): Boolean {
        return year % 400 == 0 || year % 4 == 0 && year % 100 != 0
    }

    companion object {
        const val EXTRA_FULL_NAME = "com.example.desafio01_dsm.EXTRA_FULL_NAME"
        const val EXTRA_EMAIL = "com.example.desafio01_dsm.EXTRA_EMAIL"
        const val EXTRA_PHONE = "com.example.desafio01_dsm.EXTRA_PHONE"
        const val EXTRA_BIRTH_DATE = "com.example.desafio01_dsm.EXTRA_BIRTH_DATE"
        const val EXTRA_ADDRESS = "com.example.desafio01_dsm.EXTRA_ADDRESS"
    }
}
