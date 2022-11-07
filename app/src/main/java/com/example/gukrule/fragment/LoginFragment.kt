package com.example.gukrule.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gukrule.LoginActivity
import com.example.gukrule.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var loginActivity: LoginActivity
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = activity as LoginActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.signUp.setOnClickListener{
            loginActivity.moveToSignUp()
        }

        binding.findIdPassword.setOnClickListener{
            loginActivity.clickFindPwBtn()
        }

        return root
    }

}