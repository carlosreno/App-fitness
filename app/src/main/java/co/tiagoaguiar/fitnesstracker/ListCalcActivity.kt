package co.tiagoaguiar.fitnesstracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.fitnesstracker.model.Calc
import java.text.SimpleDateFormat

class ListCalcActivity : AppCompatActivity(),OnItemClickListener {
    lateinit var rv_list_calc: RecyclerView
    lateinit var listCalc : MutableList<Calc>
    lateinit var adapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)
        listCalc = mutableListOf<Calc>()
        rv_list_calc = findViewById(R.id.rv_list_calc)
        rv_list_calc.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(listCalc, layoutInflater = layoutInflater,this)
        rv_list_calc.adapter = adapter
        val type =
            intent?.extras?.getString("type") ?: throw IllegalStateException("type nao encontrado")

        Thread {
            val app = application as App
            val dao = app.db.calcDao()
            val response = dao.getCalcByType(type)

            runOnUiThread {
                listCalc.addAll(response)

                adapter.notifyDataSetChanged()
            }
        }.start()
    }

    public class MainAdapter(
        private val listCalc: List<Calc>,
        private val layoutInflater: LayoutInflater,
        private val listener: OnItemClickListener
    ) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
        inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: Calc) {
                val itemType: TextView = itemView.findViewById(R.id.type_calc_id)
                val itemResult: TextView = itemView.findViewById(R.id.result_calc_id)
                val itemData: TextView = itemView.findViewById(R.id.date_calc_id)
                val container: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)
                itemType.text = item.type
                itemResult.text = item.res.toString()
                val dataSimples = SimpleDateFormat().format(item.createDate)
                itemData.text = dataSimples.toString()
                container.setOnLongClickListener {
                    listener.onLongClick(adapterPosition,item)
                }
            }

        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainViewHolder {
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

    override fun onLongClick(position: Int, calc: Calc): Boolean {
        // FIXME: pergunta se realmente quer excluir
        AlertDialog.Builder(this@ListCalcActivity)
            .setMessage(getString(R.string.delete_message))
            .setNegativeButton(android.R.string.cancel) { dialog, which ->
            }
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                Thread {
                    val app = application as App
                    val dao = app.db.calcDao()

                    // FIXME: exclui o item que foi clicado com long-click
                    val response = dao.delete(calc)

                    if (response > 0) {
                        runOnUiThread {
                            // FIXME: remove da lista e do adapter o item
                            listCalc.removeAt(position)
                            adapter.notifyItemRemoved(position)
                        }
                    }
                }.start()

            }
            .create()
            .show()
        return true
    }


}

