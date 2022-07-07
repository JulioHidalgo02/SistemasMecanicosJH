package com.sistemasmcanicosjh.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sistemasmcanicosjh.model.Inventario

@Database(entities = [Inventario::class], version = 1, exportSchema = false)
abstract class InventarioDatabase: RoomDatabase(){
    abstract fun inventarioDao() : InventarioDao

    companion object{
        @Volatile
        private var INSTANCE: InventarioDatabase? = null

        fun getDatabase(context: android.content.Context) : InventarioDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InventarioDatabase::class.java,
                    "inventario_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}