package com.warusmart.iam.interfaces.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.warusmart.R
import com.warusmart.iam.domain.model.SessionManager
import com.warusmart.iam.infrastructure.RetrofitClient
import com.warusmart.iam.domain.model.SignInRequest
import com.warusmart.iam.application.services.AuthServiceImpl
import com.warusmart.iam.application.services.ProfileServiceImpl

/**
 * Activity for user sign in (login).
 * Handles user authentication and navigation to profile or plans.
 */
class SignInActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signin)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rlLogIn)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnSignUp: Button = findViewById(R.id.btnSignUp)

        val authServiceImpl = AuthServiceImpl(RetrofitClient.authService)
        val profileServiceImpl = ProfileServiceImpl(RetrofitClient.profileService)

        btnLogin.setOnClickListener {
            val username = findViewById<EditText>(R.id.edtUser).text.toString()
            val password = findViewById<EditText>(R.id.edtPassword).text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                val signInRequest = SignInRequest(username, password)

                authServiceImpl.signIn(signInRequest) { signInResponse ->
                    if (signInResponse != null) {
                        Log.d("SignInActivity", "Received sign-in response: $signInResponse")
                        SessionManager.signInResponse = signInResponse
                        Toast.makeText(this, "Welcome ${signInResponse.username}", Toast.LENGTH_LONG).show()
                        profileServiceImpl.getAllProfiles(SessionManager.token!!) { profiles ->
                            Log.d("SignInActivity", "Profiles: $profiles")
                            val profile = profiles?.find { it.email == username }
                            if (profile != null) {
                                Log.d("SignInActivity", "Profile found: $profile")
                                SessionManager.profileId = profile.userId
                                startActivity(GoProfile(this))
                            } else {
                                Log.d("SignInActivity", "Profile not found, going to plans")
                                SessionManager.profileId = signInResponse.id
                                startActivity(GoPlans(this))
                            }
                        }
                    } else {
                        Log.e("SignInActivity", "Login failed: response is null")
                        Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Log.w("SignInActivity", "Username or password is empty")
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_LONG).show()
            }
        }

        btnSignUp.setOnClickListener {
            startActivity(GoSignup(this))
        }
    }

    /**
     * Navigation helpers for different activities after sign in.
     */
    companion object {
        fun GoSignup(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
        fun GoPlans(context: Context): Intent {
            return Intent(context, PlansActivity::class.java)
        }
        fun GoProfile(context: Context): Intent {
            return Intent(context, ProfileActivity::class.java)
        }
    }
}
