package com.example.weatherapp.UI.Splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.weatherapp.R
import com.example.weatherapp.UI.Main.MainActivity

class SplashScreen : AppCompatActivity() {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE)
        if (!isVerified()) {
            fingerPrintAuth()
        } else {
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    fun verify() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("biometricSignIn", true)
        editor.apply()
    }

    fun isVerified(): Boolean {
        return sharedPreferences.getBoolean("biometricSignIn", false)
    }

    fun fingerPrintAuth() {
        checkBiometricCapabilities()
        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    displayMessage("Authentication error: $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    displayMessage("Authentication succeeded!")
                    val intent = Intent(this@SplashScreen, MainActivity::class.java)
                    verify()
                    startActivity(intent)
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    displayMessage("Authentication failed")
                }
            })
        promptInfo = BiometricPrompt.PromptInfo.Builder().setTitle("Biometric Login")
            .setDescription("Place your finger on the sensor to authenticate")
            .setNegativeButtonText("Cancel").build()

        biometricPrompt.authenticate(promptInfo)
    }


    fun displayMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun checkBiometricCapabilities() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> displayMessage("Biometric authentication is available")
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> displayMessage("This device doesn't support biometric authentication")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> displayMessage("Biometric authentication is currently unavailable")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> displayMessage("No biometric credentials are enrolled")
        }
    }


}