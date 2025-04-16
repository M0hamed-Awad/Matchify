package com.example.matchify.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.matchify.models.FixtureModel
import kotlinx.coroutines.launch

class FixtureViewModel : BaseViewModel() {
    private val _fixtures = MutableLiveData<List<FixtureModel>>()
    val fixtures: LiveData<List<FixtureModel>> = _fixtures

    private val isFixturesLoaded get() = _fixtures.value?.isNotEmpty() == true

    fun fetchFixtures(leagueId: Int, apiKey: String) {
        // If the League ID wasn't changed, then don't make the API Request
        if (leagueId == lastLeagueId && isFixturesLoaded) return

        // Changing the Current League ID for the Next API Request
        lastLeagueId = leagueId

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val data = repository.getFixtures(leagueId = leagueId, apiKey = apiKey)
                _fixtures.value = data
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}