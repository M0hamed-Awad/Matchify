package com.example.matchify.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.matchify.models.StandingModel
import kotlinx.coroutines.launch

class StandingsViewModel : BaseViewModel() {
    private val _standings = MutableLiveData<List<StandingModel>>()
    val standings: LiveData<List<StandingModel>> = _standings

    private val isStandingsLoaded get() = _standings.value?.isNotEmpty() == true

    fun fetchStandings(leagueId: Int = 39, season: Int = 2023, apiKey: String) {
        // If the League ID wasn't changed, then don't make the API Request
        if (lastLeagueId == leagueId && isStandingsLoaded) return

        // Changing the Current League ID for the Next API Request
        lastLeagueId = leagueId

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val data = repository.getStandings(leagueId = leagueId, season = season, apiKey = apiKey)
                _standings.value = data
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}