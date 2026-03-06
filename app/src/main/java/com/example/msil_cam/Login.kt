package com.example.msil_cam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.msil_cam.databinding.ActivityLoginBinding
import com.example.msil_cam.models.LoginUiState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var employeeViewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        lifecycleScope.launch {
            if(isUserLoggedIn(this@Login)){
                startActivity(Intent(this@Login, MainActivity::class.java))
                finishAffinity()
            }
        }

        employeeViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application),
        )[AuthViewModel::class.java]

//        employeeViewModel.insert(Employee(101.toString(),24032004.toString()))
        loginBinding.btnLogin.setOnClickListener {
            loginBinding.apply {
                loginLoading.visibility = View.VISIBLE
                btnLogin.text = ""
                btnLogin.isEnabled = false
            }
            employeeViewModel.login(
                loginBinding.etId.text.toString(),
                loginBinding.etDob.text.toString()
            )
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                employeeViewModel.loginState.collect { state ->
                    when(state) {
                        is LoginUiState.Idle -> {
                            // do nothing
                        }
                        is LoginUiState.Loading -> {
                            // progress bar
                        }
                        is LoginUiState.Success -> {
                            Toast.makeText(
                                this@Login,
                                "Welcome ${state.data.user.name}",
                                Toast.LENGTH_SHORT
                            ).show()
                            employeeViewModel.loadProfile()
                            loginBinding.loginLoading.visibility = View.GONE
                            loginBinding.btnLogin.text = "Login"
                            loginBinding.btnLogin.isEnabled = true
                            startActivity(Intent(this@Login, MainActivity::class.java))
                            finishAffinity()
                        }
                        is LoginUiState.Error -> {
                            Toast.makeText(this@Login, state.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    suspend fun isUserLoggedIn(context: Context) : Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[AuthKeys.IS_LOGGED_IN] == true
    }
}