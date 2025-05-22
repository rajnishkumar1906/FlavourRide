package com.example.falvourride

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var storedVerificationId: String? = null
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val phoneInput = findViewById<EditText>(R.id.phoneInput)
        val otpInput = findViewById<EditText>(R.id.otpInput)
        val sendOtpBtn = findViewById<Button>(R.id.sendOtpBtn)
        val verifyOtpBtn = findViewById<Button>(R.id.verifyOtpBtn)

        sendOtpBtn.setOnClickListener {
            val phone = phoneInput.text.toString().trim()
            if (phone.isNotEmpty()) {
                startPhoneAuth(phone)
            } else {
                Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show()
            }
        }

        verifyOtpBtn.setOnClickListener {
            val code = otpInput.text.toString().trim()
            if (code.isNotEmpty() && storedVerificationId != null) {
                val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, code)
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(this, "Enter valid OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startPhoneAuth(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // Auto verification (OTP auto detected)
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(this@LoginActivity, "Verification failed: ${e.message}", Toast.LENGTH_LONG).show()
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            storedVerificationId = verificationId
            resendToken = token
            Toast.makeText(this@LoginActivity, "OTP Sent", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    Toast.makeText(this, "Welcome: ${user?.phoneNumber}", Toast.LENGTH_SHORT).show()
                    // TODO: Redirect to Home screen
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
