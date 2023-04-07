package co.tiagoaguiar.fitnesstracker

import android.app.Application
import co.tiagoaguiar.fitnesstracker.model.AppDateBase

class App : Application() {
    lateinit var db: AppDateBase
    override fun onCreate() {
        super.onCreate()
        db = AppDateBase.getDataBase(this)

    }
}