package co.tiagoaguiar.fitnesstracker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.fitnesstracker.model.Calc
import java.text.DateFormat
import java.text.SimpleDateFormat

class ListCalcActivity : AppCompatActivity() {
    lateinit var rv_list_calc: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)

        rv_list_calc = findViewById(R.id.rv_list_calc)
        rv_list_calc.layoutManager = LinearLayoutManager(this)
        val type =
            intent?.extras?.getString("type") ?: throw IllegalStateException("type nao encontrado")

        Thread {
            val app = application as App
            val dao = app.db.calcDao()
            val result = dao.getCalcByType(type)
            runOnUiThread {
                rv_list_calc.adapter = MainAdapter(result, layoutInflater = layoutInflater)
                
            }

        }.start()
    }


}

private class MainAdapter(
    private val listCalc: List<Calc>,
    private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = layoutInflater.inflate(R.layout.item_calc, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listCalc.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val itemCurrent = listCalc[position]
        holder.bind(itemCurrent)
    }

}

private class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Calc) {
        val itemType: TextView = itemView.findViewById(R.id.type_calc_id)
        val itemResult: TextView = itemView.findViewById(R.id.result_calc_id)
        val itemData: TextView = itemView.findViewById(R.id.date_calc_id)
        itemType.text = item.type
        itemResult.text = item.res.toString()
        val dataSimples = SimpleDateFormat().format(item.createDate)
        itemData.text = dataSimples.toString()
    }

}
