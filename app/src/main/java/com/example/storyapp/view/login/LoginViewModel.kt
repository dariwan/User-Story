package com.example.storyapp.view.login

import android.util.Log
import androidx.lifecycle.*
import com.example.storyapp.apihelper.ApiConfig
import com.example.storyapp.data.LoginResponse
import com.example.storyapp.data.User
import com.example.storyapp.utils.Constant.KEY_EMAIL
import com.example.storyapp.utils.Constant.KEY_IS_LOGIN
import com.example.storyapp.utils.Constant.KEY_NAME
import com.example.storyapp.utils.Constant.KEY_TOKEN
import com.example.storyapp.utils.Constant.KEY_USER_ID
import com.example.storyapp.utils.Event
import com.example.storyapp.utils.SesionManager
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {
    private var sharedPref: SesionManager? = null

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message

    private val _loginResult = MutableLiveData<User?>()
    val loginResut: LiveData<User?> = _loginResult


    fun loginUser(email: String, password: String) {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService().login(email, password)
        apiService.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()!!.loginResult
                    sharedPref?.apply {
                        setBooleanPref(KEY_IS_LOGIN, true)
                        setStringPref(KEY_TOKEN, responseBody.token)
                        setStringPref(KEY_USER_ID, responseBody.userId)
                        setStringPref(KEY_NAME, responseBody.name)
                        setStringPref(KEY_EMAIL, email)
                    }
                    
                    _loginResult.value = responseBody
                    _message.value = Event("${response.body()?.message}")

                } else {
                    _loginResult.value = null
                    val loginFailed = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        LoginResponse::class.java
                    )
                    _message.value = Event(loginFailed.message)

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