package com.example.manifesto.mainscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.manifesto.R
import com.example.manifesto.databinding.FragmentMainBinding

class MainScreenFragment : Fragment() {

    private val viewModel: MainScreenViewModel by viewModels()

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater,container, false)
        binding.viewModel = viewModel
        binding.viewModel

        viewModel.navigateToSignIn.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                this.findNavController().navigate(
                    MainScreenFragmentDirections.
                    actionMainScreenFragmentToSignInFragment())
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }
}