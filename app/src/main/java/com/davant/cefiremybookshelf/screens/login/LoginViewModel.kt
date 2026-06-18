package com.davant.cefiremybookshelf.screens.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(
    private val auth: FirebaseAuth,
    private val navigateToHome: (String) -> Unit
) : ViewModel() {
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _password = MutableLiveData<String>()
    val password:LiveData<String> = _password

    private val _isLoginEnable = MutableLiveData<Boolean>()
    val isLoginEnable: LiveData<Boolean> = _isLoginEnable

    private val _isLoginError = MutableLiveData<Boolean>()
    val isLoginError: LiveData<Boolean> = _isLoginError


    fun onLoginChange(userName:String, password:String) {
        _userName.value = userName
        _password.value = password
        _isLoginEnable.value = enableLogin(userName, password)
        _isLoginError.value = false
    }

    private fun enableLogin(userName: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(userName).matches()
            && password.length > 7

    fun registerUser() {
        auth.createUserWithEmailAndPassword(
            _userName.value.toString(),
            _password.value.toString()
        ).addOnCompleteListener { result ->
            Log.i(
                "Register button",
                if (result.isSuccessful)
                    "User registered with ID: ${auth.currentUser?.uid}"
                else
                    "Registry failed ${result.exception.toString()}"
            )
        }
    }

    fun loginUser() {
        auth.signInWithEmailAndPassword(
            _userName.value.toString(),
            _password.value.toString()
        ).addOnCompleteListener {
            if(it.isSuccessful) {
                Log.i("Login button", "User logged: ${auth.currentUser?.email}")
                navigateToHome(_userName.value.toString())
            } else {
                Log.i("Login button", "User login failed: ${it.exception.toString()}")
                _isLoginError.value = true
            }
        }
    }
}