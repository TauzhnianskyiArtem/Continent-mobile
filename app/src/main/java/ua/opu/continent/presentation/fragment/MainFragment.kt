package ua.opu.continent.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ua.opu.continent.App
import ua.opu.continent.R
import ua.opu.continent.databinding.FragmentMainBinding
import ua.opu.continent.presentation.MainViewModel
import ua.opu.continent.presentation.MainViewModelFactory
import ua.opu.continent.presentation.adapter.UsersAdapter
import ua.opu.continent.useсase.impl.PresenceUseCaseFirebase
import javax.inject.Inject

class MainFragment() : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(requireContext(), 2)
        val usersAdapter = UsersAdapter(requireContext()) {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToChatFragment(
                    it.name,
                    it.profileImage,
                    it.uid
                )
            )
        }
        binding.mRec.layoutManager = layoutManager
        binding.mRec.adapter = usersAdapter
        usersAdapter.submitList(null)
        viewModel.bindToGetAllUsers(usersAdapter)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity().applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.setUserPresence(PresenceUseCaseFirebase.PRESENCE_ONLINE)
    }

    override fun onPause() {
        super.onPause()
        viewModel.setUserPresence(PresenceUseCaseFirebase.PRESENCE_OFFLINE)
    }


}