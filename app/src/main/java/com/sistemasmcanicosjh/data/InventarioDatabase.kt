package com.sistemasmcanicosjh.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sistemasmcanicosjh.model.Inventario

@Database(entities = [Inventario::class], version = 2, exportSchema = false)
abstract class InventarioDatabase: RoomDatabase(){
    abstract fun inventarioDao() : InventarioDao

    companion object{
        @Volatile
        private var INSTANCE: InventarioDatabase? = null

        var MIGRATION_1_2: Migration = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE inventario ADD COLUMN ruta TEXT")
            }
        }

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
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}