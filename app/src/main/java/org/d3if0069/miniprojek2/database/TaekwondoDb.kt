package org.d3if0069.miniprojek2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if0069.miniprojek2.model.Taekwondo

@Database(entities = [Taekwondo::class], version = 1, exportSchema = false)
abstract class TaekwondoDb: RoomDatabase(){

    abstract val dao: TaekwondoDao

    companion object {

        @Volatile
        private var INSTANCE: TaekwondoDb? = null

        fun getInstance(context: Context): TaekwondoDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaekwondoDb::class.java,
                        "taekwondo.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}