package com.example.msil_cam

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.msil_cam.databinding.ActivityLoginBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.log

class Login : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var employeeViewModel: EmployeeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        employeeViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application),
        )[EmployeeViewModel::class.java]

        employeeViewModel.insert(Employee(101.toString(),24032004.toString()
        ))
        loginBinding.btnLogin.setOnClickListener {
            employeeViewModel.login(
                loginBinding.etId.text.toString(),
                loginBinding.etDob.text.toString()
            )
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        employeeViewModel.loginResult.observe(this) { employee->
            if(employee != null){
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, "Invalid Staff ID or DOB", Toast.LENGTH_SHORT).show()
            }
        }
    }
}