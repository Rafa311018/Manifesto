package com.example.manifesto.mainscreen

import android.os.Bundle
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
import com.example.manifesto.signin.SignInViewModel

class MainScreenFragment : Fragment() {

//    private val viewModel: MainScreenViewModel by viewModels()

    private lateinit var viewModel: MainScreenViewModel

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = createViewModel{
            MainScreenViewModel(
                GuestDatabase.getInstanceGuestDB(MainActivity.appContext).guestDatabaseDao,
                MainActivity.App
            )
        }

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
        binding.setLifecycleOwner(this)

        return binding.root
    }
}