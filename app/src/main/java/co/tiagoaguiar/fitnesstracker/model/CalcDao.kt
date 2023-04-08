package co.tiagoaguiar.fitnesstracker.model

import android.support.v4.app.INotificationSideChannel
import androidx.room.*

@Dao
interface CalcDao {
    @Insert
    fun insert(calc: Calc)
    @Query("Select * from Calc where type=:type")
    fun getCalcByType(type:String): List<Calc>

    @Delete
    fun delete(calc: Calc): Int

    @Update
    fun update(calc: Calc)

}