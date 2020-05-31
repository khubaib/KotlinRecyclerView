package com.example.android.kotlinrecyclerview.fragments.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.kotlinrecycelrview.repository.SectionsRepository
import com.example.android.kotlinrecyclerview.database.getDatabase
import com.example.android.kotlinrecyclerview.entity.ViaplaySection
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.IOException
import java.util.*
import kotlin.concurrent.schedule

class MainFragmentViewModel(val application: Application): ViewModel() {

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job();


    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError



    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    /**
     * Flag to display the error message. Views should use this to get access
     * to the data.
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


    /**
     * The data source this ViewModel will fetch results from.
     */
    private val sectionsRepository = SectionsRepository(getDatabase(application))


    private val _navigateToSectionDetails = MutableLiveData<ViaplaySection>()
    val navigateToSectionDetails
        get() = _navigateToSectionDetails

    fun onSectionClicked(sections: ViaplaySection){
        _navigateToSectionDetails.value = sections
    }

    fun onSectionDetailNavigated() {
        _navigateToSectionDetails.value = null
    }

    val sections = sectionsRepository.sections



    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    init {
        fetchFromRepo()
    }



    private fun fetchFromRepo() {
        uiScope.launch {
            try {
                sectionsRepository.refreshSections()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException) {
                Timber.e(networkError)
                delay(5000)
                if(sections.value.isNullOrEmpty()) {
                    _eventNetworkError.value = true
                }

                // Show a Toast error message and hide the progress bar.
            }
        }
    }


    /**
     * Resets the network error flag.
     */
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}