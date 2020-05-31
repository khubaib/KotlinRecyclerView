package com.example.android.kotlinrecyclerview.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.android.kotlinrecyclerview.entity.*


@Dao
interface SectionsDao {
    @Query("select * from ViaplaySection")
    fun getSections(): LiveData<List<ViaplaySection>>

    @Query("select * from SectionDetails where sectionId = :sectionIdd ")
    fun getSectionDetails(sectionIdd: String): LiveData<SectionDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSections(sections: List<ViaplaySection>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSectionDetails( sectionDetails: SectionDetails)

}



@Database(entities = [/*Response::class, Links::class,*/ SectionDetails::class, ViaplaySection::class], version = 7, exportSchema = true)
@TypeConverters(DataConverter::class)
abstract class SectionsDatabase: RoomDatabase() {
    abstract val sectionsDao: SectionsDao
}


private lateinit var INSTANCE: SectionsDatabase

fun getDatabase(context: Context): SectionsDatabase {
    synchronized(SectionsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                SectionsDatabase::class.java,
                "sections").build()
        }
    }
    return INSTANCE
}
