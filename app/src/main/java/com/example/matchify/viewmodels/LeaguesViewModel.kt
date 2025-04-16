package com.example.matchify.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.matchify.models.LeagueData
import com.example.matchify.utils.HelperFunctions
import kotlinx.coroutines.launch

class LeaguesViewModel : BaseViewModel() {
    private val _leagues = MutableLiveData<List<LeagueData>>()
    val leagues: LiveData<List<LeagueData>> = _leagues

    val isLeaguesLoaded get() = _leagues.value?.isNotEmpty() == true

    fun fetchLeagues(apiKey: String) {
        // If the Leagues is Loaded already, then DO NOT make the API Request anymore
        if (isLeaguesLoaded) return

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val data = repository.getLeagues(apiKey = apiKey)
                // Getting only the Top Five Leagues
                val top5Leagues = HelperFunctions.getTop5Leagues(data)
                _leagues.value = top5Leagues
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}