package com.example.android.kotlinrecyclerview.fragments.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.kotlinrecycelrview.repository.SectionsRepository
import com.example.android.kotlinrecyclerview.database.getDatabase
import com.example.android.kotlinrecyclerview.entity.SectionDetails
import com.example.android.kotlinrecyclerview.entity.ViaplaySection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class DetailFragmentViewModel(var sectionCLicked: ViaplaySection, val application: Application): ViewModel() {


    private val sectionDetailsRepository = SectionsRepository(getDatabase(application))
    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job();

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

    // The internal MutableLiveData String that stores the most recent response
    private  var _sectionDetailFromDB  =  sectionDetailsRepository.getSectionDetailsFromDB(sectionCLicked.id)


    // The external immutable LiveData for the response String
    val sectionDetailFromDB: LiveData<SectionDetails>
        get() = _sectionDetailFromDB




    /**
     * Variable that tells the fragment whether it should navigate to [SleepTrackerFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToMainSections = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [SleepTrackerFragment]
     */
    val navigateToMainSections: LiveData<Boolean?>
        get() = _navigateToMainSections

    /**
     * Cancels all coroutines when the ViewModel is cleared, to cleanup any pending work.
     *
     * onCleared() gets called when the ViewModel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    /**
     * Call this immediately after navigating to [SleepTrackerFragment]
     */
    fun doneNavigating() {
        _navigateToMainSections.value = false
    }



    fun onClose() {
        _navigateToMainSections.value = true
    }



    
    init {
        retrieveSectionDetailsFromNetwork(sectionCLicked.href.substring(0,sectionCLicked.href.indexOf('{')))
    }

    private fun retrieveSectionDetailsFromNetwork(href: String) {
        uiScope.launch {
            try {
                sectionDetailsRepository.getSectionDetailsFromNetwork(href)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }



}