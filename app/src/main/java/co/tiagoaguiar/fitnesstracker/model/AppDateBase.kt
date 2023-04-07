package co.tiagoaguiar.fitnesstracker.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Calc::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDateBase: RoomDatabase() {
    abstract fun calcDao() : CalcDao
    companion object{
        private var INSTANCE: AppDateBase? = null
        fun getDataBase(context: Context): AppDateBase{
            if (INSTANCE ==null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDateBase::class.java,
                        "fitness_tracker"
                    ).build()
                    return INSTANCE as AppDateBase
                }
            }else{
                return INSTANCE as AppDateBase
            }
        }
    }
}