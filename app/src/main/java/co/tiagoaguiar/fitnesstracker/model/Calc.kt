package co.tiagoaguiar.fitnesstracker.model

import androidx.room.Entity
import java.util.Date

@Entity
data class Calc(
    val id: Int,
    val type: String,
    val res: Double,
    val createDate: Date
)
