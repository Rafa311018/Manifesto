package com.example.manifesto.signin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.manifesto.MainActivity
import com.example.manifesto.R
import com.example.manifesto.createViewModel
import com.example.manifesto.data.model.GuestDatabase
import com.example.manifesto.databinding.FragmentMainBinding
import com.example.manifesto.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

//    private val viewModel: SignInViewModel by viewModels()

    private lateinit var viewModel: SignInViewModel

    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = createViewModel{
            SignInViewModel(
                GuestDatabase.getInstanceGuestDB(MainActivity.appContext).guestDatabaseDao,
                MainActivity.App
            )
        }

        binding = FragmentSignInBinding.inflate(inflater,container,false)
        binding.viewModel = viewModel
        viewModel.navigateToMainScreen.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate){
                this.findNavController().navigate(
                    SignInFragmentDirections.
                    actionSignInFragmentToMainScreenFragment())
                viewModel.doneNavigating()
            }
        })

        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}