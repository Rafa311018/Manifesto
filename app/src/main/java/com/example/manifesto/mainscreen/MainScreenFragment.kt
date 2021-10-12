package com.example.manifesto.mainscreen

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.manifesto.MainActivity
import com.example.manifesto.createViewModel
import com.example.manifesto.data.model.GuestDatabase
import com.example.manifesto.databinding.FragmentMainBinding
import com.example.manifesto.signin.SignInFragmentDirections
import com.example.manifesto.signin.SignInViewModel

class MainScreenFragment : Fragment() {

//    private val viewModel: MainScreenViewModel by viewModels()

    private lateinit var viewModel: MainScreenViewModel

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = createViewModel {
            MainScreenViewModel(
                GuestDatabase.getInstanceGuestDB(MainActivity.appContext).guestDatabaseDao,
                MainActivity.App
            )
        }

        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.viewModel

        val manager = LinearLayoutManager(activity)
        binding.guestList.layoutManager = manager

        val adapter = MainScreenAdapter(GuestListener { guestId ->
            viewModel.getGuestName(guestId)
        },
            EditGuestListener { guestIdE ->
                viewModel.onEditGuestClicked(guestIdE)
            })
        binding.guestList.adapter = adapter

        viewModel.guests.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
                Log.d("Yoshi", "${it.size}")
            }
            if (it.size != 0) {
                binding.guestList.visibility = View.VISIBLE
                binding.noRecords.visibility = View.GONE
            }else{binding.guestList.visibility = View.GONE
                binding.noRecords.visibility = View.VISIBLE
            }
        })

        viewModel.navigateToEditGuest.observe(viewLifecycleOwner, Observer { guest ->
            if (guest) {
                this.findNavController().navigate(
                    MainScreenFragmentDirections.actionMainScreenFragmentToSignInFragment(
                        viewModel.guestIdG,
                        true
                    )
                )
                viewModel.onEditGuestNavigated()
            }
        })

        viewModel.navigateToSignIn.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                this.findNavController().navigate(
                    MainScreenFragmentDirections.actionMainScreenFragmentToSignInFragment(0, false)
                )
                viewModel.doneNavigating()
            }
        })
        viewModel.deleteGuest.observe(viewLifecycleOwner, Observer { name ->
            showUserDialog(name)
        })
        binding.setLifecycleOwner(this)

        return binding.root
    }

    fun showUserDialog(guestName: String?) {
        val alerDialog: AlertDialog = activity.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Continue to delete $guestName")
            builder.apply {
                setPositiveButton("Ok",
                    DialogInterface.OnClickListener { dialog, id ->
                        viewModel.deleteUser()
                        dialog.dismiss()
                    })
                setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    })
            }
            builder.create()
        }
        alerDialog.show()
    }
}

