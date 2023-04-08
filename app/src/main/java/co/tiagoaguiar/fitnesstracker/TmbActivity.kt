package co.tiagoaguiar.fitnesstracker

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.*
import co.tiagoaguiar.fitnesstracker.model.Calc

class TmbActivity : AppCompatActivity() {
    private lateinit var lifeStyle : AutoCompleteTextView
    private lateinit var editWeigth: EditText
    private lateinit var editHeigth: EditText
    private lateinit var editAge: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tmb)

        lifeStyle = findViewById(R.id.auto_life_style)
        editWeigth = findViewById(R.id.edit_tmb_weigth)
        editHeigth = findViewById(R.id.edit_tmb_heigth)
        editAge = findViewById(R.id.edit_tmb_idade)

        val items = resources.getStringArray(R.array.tmb_life_style)
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,items)
        lifeStyle.setAdapter(adapter)
        val btnSend: Button = findViewById(R.id.edit_tmb_calc)
        btnSend.setOnClickListener {
            if (!validate()){
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val weigth = editWeigth.text.toString().toInt()
            val heigth = editHeigth.text.toString().toInt()
            val age = editAge.text.toString().toInt()
            val result = calculate(weigth,heigth,age)
            val response = tmbResult(result)
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.tmb_response,response))
                .setPositiveButton("sair") { dialog, which ->

                }
                .setNegativeButton(R.string.save){dialog, which->
                    Thread{
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "tmb",res=response))
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

    private fun tmbResult(tmb: Double): Double {
        val items = resources.getStringArray(R.array.tmb_life_style)
        return when{
            lifeStyle.text.toString()==items[0] -> tmb*1.2
            lifeStyle.text.toString()==items[1] -> tmb*1.375
            lifeStyle.text.toString()==items[2] -> tmb*1.55
            lifeStyle.text.toString()==items[3] -> tmb*1.725
            lifeStyle.text.toString()==items[4] -> tmb*1.9

            else -> 0.0
        }
    }

    private fun calculate(weigth: Int, heigth: Int, age: Int): Double {
        return 66+((13.7 *weigth)+(5*heigth)-(6.8*age))
    }

    private fun validate(): Boolean {
        return (editHeigth.text.toString().isNotEmpty()
                && !editHeigth.text.toString().startsWith("0")
                && editWeigth.text.toString().isNotEmpty()
                && !editWeigth.text.toString().startsWith("0")
                && !editAge.text.toString().startsWith("0")
                && editAge.text.toString().isNotEmpty()
                && lifeStyle.text.toString().isNotEmpty())
    }



    private fun openListCalcActivity(){
        val i = Intent(this@TmbActivity,ListCalcActivity::class.java)
        i.putExtra("type","tmb")
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
}