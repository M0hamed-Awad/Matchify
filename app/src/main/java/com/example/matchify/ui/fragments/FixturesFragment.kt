package com.example.matchify.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.matchify.databinding.FragmentFixturesBinding
import com.example.matchify.ui.adpaters.FixtureAdapter
import com.example.matchify.utils.Constants
import com.example.matchify.viewmodels.FixtureViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [FixturesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FixturesFragment : Fragment(){
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentFixturesBinding
    private val viewModel: FixtureViewModel by activityViewModels()
    private lateinit var fixtureAdapter: FixtureAdapter

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
        binding = FragmentFixturesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setting Up the Fixtures Adapter
        fixtureAdapter = FixtureAdapter(emptyList())
        binding.fixturesRv.adapter = fixtureAdapter

        observeViewModel()

        // Getting the League ID Selected from the Leagues Fragment
        val leagueId = arguments?.getInt("leagueId") ?: 39

        // Making the Request ONLY IF the League ID Changed
        if(viewModel.lastLeagueId != leagueId){
            viewModel.fetchFixtures(leagueId = leagueId, apiKey = Constants.API_KEY)
        }
    }

    private fun observeViewModel() {
        viewModel.fixtures.observe(viewLifecycleOwner) { fixtures ->
            fixtureAdapter.updateData(fixtures)
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
         * @return A new instance of fragment MatchesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FixturesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}