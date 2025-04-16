package com.example.matchify.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.matchify.databinding.FragmentStandingsBinding
import com.example.matchify.ui.adpaters.StandingsAdapter
import com.example.matchify.utils.Constants
import com.example.matchify.viewmodels.StandingsViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [StandingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StandingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentStandingsBinding
    private val viewModel: StandingsViewModel by activityViewModels()
    private lateinit var standingsAdapter: StandingsAdapter

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
        // Inflate the layout for this fragment
        binding = FragmentStandingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setting Up the Standings Adapter
        standingsAdapter = StandingsAdapter(emptyList())
        binding.standingsRv.adapter = standingsAdapter

        observeViewModel()

        // Getting the League ID Selected from the Leagues Fragment
        val leagueId = arguments?.getInt("leagueId") ?: 39

        // Making the Request ONLY IF the League ID Changed
        if (viewModel.lastLeagueId != leagueId){
            viewModel.fetchStandings(leagueId = leagueId, apiKey = Constants.API_KEY)
        }
    }


    private fun observeViewModel() {
        viewModel.standings.observe(viewLifecycleOwner) { standings ->
            standingsAdapter.updateData(standings)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StandingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StandingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}