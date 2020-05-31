

package com.example.android.kotlinrecycelrview.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.kotlinrecyclerview.database.SectionsDatabase
import com.example.android.kotlinrecyclerview.entity.SectionDetails
import com.example.android.marsrealestate.network.MyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception

/**
 * Repository for fetching viaply sections from the network and storing them on disk
 */
class SectionsRepository(private val database: SectionsDatabase) {

    /* val sections: LiveData<List<Responses>> = Transformations.map(database.sectionsDao.getSections()) {
         it.asDomainModel()
     }*/

    val sections = database.sectionsDao.getSections();


    fun getSectionDetailsFromDB(id: String): LiveData<SectionDetails> {

        return database.sectionsDao.getSectionDetails(id)
    }


    suspend fun getSectionDetailsFromNetwork(href: String) {
        withContext(Dispatchers.IO) {
                Timber.d("getSectionDetails is called");
                val sectionDetails = MyApi.retrofitService.getSectionDetails(href).await()
                database.sectionsDao.insertAllSectionDetails(sectionDetails)
        }
    }

    /**
     * Refresh the sections stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     */
    suspend fun refreshSections() {
        withContext(Dispatchers.IO) {
            Timber.d("refresh sections is called");
            val sections = MyApi.retrofitService.getSections().await()
            database.sectionsDao.insertAllSections(sections.links.viaplaySections)
        }
    }

}
