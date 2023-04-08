package co.tiagoaguiar.fitnesstracker

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import co.tiagoaguiar.fitnesstracker.model.Calc
import kotlin.math.pow

class ImcActivity : AppCompatActivity() {
    private lateinit var editWeigth: EditText
    private lateinit var editHeigth: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        editWeigth = findViewById(R.id.edit_imc_weigth)
        editHeigth = findViewById(R.id.edit_imc_heigth)
        val btnSend: Button = findViewById(R.id.edit_imc_calc)
        btnSend.setOnClickListener {
            if (!validate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val weigth = editWeigth.text.toString().toInt()
            val heigth = editHeigth.text.toString().toInt()
            val result = calculate(weigth,heigth)

            val title = getString(R.string.imc_response,result)
            AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(imcResponse(result))
                .setPositiveButton("sair") { dialog, which ->

                }
                .setNegativeButton(R.string.save){dialog, which->
                    Thread{
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "imc",res=result))
                        runOnUiThread {
                            openListCalcActivity()
                        }
                    }.start()
                }
                .create().show()
            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken,0)
        }
    }
    private fun openListCalcActivity(){
        val i = Intent(this@ImcActivity,ListCalcActivity::class.java)
        i.putExtra("type","imc")
        startActivity(i)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId ==R.id.menu_search){
            finish()
            openListCalcActivity()
        }
        return super.onOptionsItemSelected(item)
    }
    @StringRes
    private fun imcResponse(imc:Double): Int {
        return when{
            imc < 15.0 -> R.string.imc_severely_low_weight
            imc < 16.0 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_low_weight
            imc < 25.0 -> R.string.normal
            imc < 30.0 -> R.string.imc_high_weight
            imc < 35.0 -> R.string.imc_so_high_weight
            imc < 40.0 -> R.string.imc_severely_high_weight
            else -> R.string.imc_extreme_weight

        }
    }
    private fun calculate(weigth: Int, heigth:Int): Double{
        return weigth / (heigth/100.0).pow(2)

    }
    private fun validate(): Boolean {
        return (editHeigth.text.toString().isNotEmpty()
                && !editHeigth.text.toString().startsWith("0")
                && editWeigth.text.toString().isNotEmpty()
                && !editWeigth.text.toString().startsWith("0"))
    }
}