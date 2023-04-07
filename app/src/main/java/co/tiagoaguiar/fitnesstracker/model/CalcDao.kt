package co.tiagoaguiar.fitnesstracker.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase

@Dao
interface CalcDao {
    @Insert
    fun insert(calc: Calc)
    @Query("Select * from Calc where type=:type")
    fun getCalcByType(type:String): List<Calc>
}