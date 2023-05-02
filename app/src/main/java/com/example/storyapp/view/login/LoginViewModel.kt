package com.example.storyapp.view.login

import android.util.Log
import androidx.lifecycle.*
import com.example.storyapp.apihelper.ApiConfig
import com.example.storyapp.data.LoginResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult


    fun loginUser(email: String, password: String) {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService().login(email, password)
        apiService.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _loginResult.value = response.body()

                    _message.value = "${response.body()?.message}"

                } else {
                    val loginFailed = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        LoginResponse::class.java
                    )
                    _loginResult.value = loginFailed
                    _message.value = loginFailed.message

                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: " + t.message)

            }
        })
    }



    companion object {
        private const val TAG = "LoginViewModel"
    }

}