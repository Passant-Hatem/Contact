package com.example.contactapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Contact::class] ,version = 1,exportSchema = false)
@TypeConverters(ImageConverter::class)
abstract class ContactDataBase:RoomDatabase(){
    abstract fun contactDao():ContactDao
    companion object{
        @Volatile
        private var INSTANCE: ContactDataBase? = null

        fun getDatabase(context: Context):ContactDataBase{
            val tempInstance = INSTANCE
            if(tempInstance != null) return tempInstance
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDataBase::class.java,
                    "contact_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}