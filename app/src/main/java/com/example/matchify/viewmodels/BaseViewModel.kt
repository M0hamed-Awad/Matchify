package com.example.matchify.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.matchify.repository.Repository

open class BaseViewModel : ViewModel() {
    protected val repository = Repository()

    // Loading variable for the Circular Progress Bar ( VISIBLE - NOT VISIBLE )
    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // For storing the League ID of the last API Call, to control  When and When NOT to make the API Request
    var lastLeagueId: Int? = null
}