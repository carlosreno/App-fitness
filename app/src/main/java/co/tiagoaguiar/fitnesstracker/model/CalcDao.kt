package co.tiagoaguiar.fitnesstracker.model

import androidx.room.Insert
import androidx.room.RoomDatabase

interface CalcDao {
    @Insert
    fun insert(calc: Calc)
}