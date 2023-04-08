package co.tiagoaguiar.fitnesstracker

import co.tiagoaguiar.fitnesstracker.model.Calc

interface OnItemClickListener {
    fun onLongClick(position: Int, calc: Calc): Boolean
}