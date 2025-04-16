package com.example.matchify.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.matchify.R
import com.example.matchify.databinding.FragmentLeaguesBinding
import com.example.matchify.models.LeagueData
import com.example.matchify.ui.activities.MainActivity
import com.example.matchify.ui.adpaters.LeagueAdapter
import com.example.matchify.utils.Constants
import com.example.matchify.viewmodels.LeaguesViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [LeaguesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LeaguesFragment : Fragment(), LeagueAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentLeaguesBinding
    private val viewModel: LeaguesViewModel by activityViewModels()
    private lateinit var leagueAdapter: LeagueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLeaguesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setting Up the Leagues Adapter
        leagueAdapter = LeagueAdapter(emptyList(), this)
        binding.leaguesRv.adapter = leagueAdapter

        observeViewModel()

        // Making the Leagues Request ONLY ONCE in the App Opening
        if (!viewModel.isLeaguesLoaded){
            viewModel.fetchLeagues(apiKey = Constants.API_KEY)
        }
    }

    private fun observeViewModel() {
        viewModel.leagues.observe(viewLifecycleOwner) {
            leagueAdapter.updateData(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onItemClicked(leagueData: LeagueData) {
        // Finding the Navigation Action from the Navigation Component
        val action = LeaguesFragmentDirections
            .leaguesFragmentNavigateToFixturesFragment(leagueData.id)

        // Navigating to Destination
        findNavController().navigate(action)

        // Getting Main Activity
        val mainActivity = (requireActivity() as MainActivity)

        // Save the selected League ID in MainActivity
        mainActivity.currentLeagueId = leagueData.id

        // Set the bottom nav to reflect the current selected tab
        mainActivity.setSelectedNavItem(R.id.fixturesFragment)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LeaguesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}