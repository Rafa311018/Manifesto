package com.example.manifesto.signin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.manifesto.R
import com.example.manifesto.databinding.FragmentMainBinding
import com.example.manifesto.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by viewModels()

    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater,container,false)
        binding.viewModel = viewModel

        viewModel._nameError.observe(viewLifecycleOwner, Observer { error ->
            if (error){
                binding.fullNameTIL.helperText = "Must be 2-12 character&apos;s long and have no special chaaracters."
            }else {
                binding.fullNameTIL.helperText = null
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}