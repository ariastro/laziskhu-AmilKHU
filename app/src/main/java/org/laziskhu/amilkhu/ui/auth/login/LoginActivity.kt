package org.laziskhu.amilkhu.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import org.laziskhu.amilkhu.R
import org.laziskhu.amilkhu.base.BaseActivity
import org.laziskhu.amilkhu.data.source.local.Prefs
import org.laziskhu.amilkhu.data.source.remote.response.LoginResponse
import org.laziskhu.amilkhu.databinding.ActivityLoginBinding
import org.laziskhu.amilkhu.ui.main.MainActivity
import org.laziskhu.amilkhu.utils.*
import org.laziskhu.amilkhu.utils.Constants.ADMIN_CODE
import org.laziskhu.amilkhu.utils.Constants.SUPER_ADMIN_CODE
import org.laziskhu.amilkhu.utils.Constants.USER_CODE
import org.laziskhu.amilkhu.vo.Status

class LoginActivity : BaseActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        observeLoginFormState()
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.username.text.toString(), binding.password.text.toString())
                .observe(this) {
                    when (it?.status) {
                        Status.SUCCESS -> {
                            progress.dismiss()
                            it.data?.let { response ->
                                response.data?.let { data -> saveUserData(data) }
                            }
                            navigateToHome()
                        }
                        Status.LOADING -> {
                            progress.show()
                        }
                        Status.ERROR -> {
                            progress.dismiss()
                            showErrorToasty(it.message.toString())
                        }
                    }
                }
        }

        binding.username.afterTextChanged {
            viewModel.loginDataChanged(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }

        binding.password.apply {
            afterTextChanged {
                viewModel.loginDataChanged(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        viewModel.login(
                            binding.username.text.toString(),
                            binding.password.text.toString()
                        )
                }
                false
            }
        }

    }

    private fun observeLoginFormState() {
        viewModel.loginFormState.observe(this, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            binding.btnLogin.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                binding.usernameLayout.error = getString(loginState.usernameError, getString(R.string.username))
            } else {
                binding.usernameLayout.error = null
                binding.usernameLayout.isErrorEnabled = false
            }
            if (loginState.passwordError != null) {
                binding.passwordLayout.error = getString(loginState.passwordError, getString(R.string.password))
            } else {
                binding.passwordLayout.error = null
                binding.passwordLayout.isErrorEnabled = false
            }
        })
    }

    private fun saveUserData(data: LoginResponse.LoginData) {
//        Prefs.token = data.token
        when (data.role) {
            USER_CODE -> Prefs.role = Role.USER
            ADMIN_CODE -> Prefs.role = Role.ADMIN
            SUPER_ADMIN_CODE -> Prefs.role = Role.SUPER_ADMIN
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java)).also {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}