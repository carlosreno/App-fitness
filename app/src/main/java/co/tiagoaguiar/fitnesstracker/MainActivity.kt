package co.tiagoaguiar.fitnesstracker

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var bntImg: LinearLayout
    private lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainItems = mutableListOf<MainItem>()
        mainItems.addAll(
            listOf(
                MainItem(1, R.drawable.baseline_wb_sunny_24, R.string.imc, Color.GREEN),
                MainItem(2, R.drawable.baseline_wc_24, R.string.calculo_DPP, Color.CYAN),
                MainItem(3, R.drawable.baseline_wb_sunny_24, R.string.calculo_IG, Color.GREEN),
                MainItem(4, R.drawable.baseline_wc_24, R.string.imc, Color.CYAN)
            )
        )
        val adapter = MainAdapter(mainItems){id->
            when(id){
                1 -> {
                    val i = Intent(this,ImcActivity::class.java)
                    startActivity(i)
                }
                2 -> {
                    Log.i("tela 2","tela 2")
                }
                3 -> {
                    Log.i("tela 3","tela 3")
                }
                4 -> {
                    Log.i("tela 4","tela 4")
                }
            }

        }
        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = GridLayoutManager(this, 2)

    }

    private inner class MainAdapter(private val mainItems: MutableList<MainItem>,
                                    private val onItemClickListener: (Int) -> Unit)
        : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

            val itemCurrent = mainItems[position]
            holder.bind(itemCurrent)
        }

        override fun getItemCount(): Int {

            return 4
        }
        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: MainItem) {
                val iconImc:ImageView = itemView.findViewById(R.id.item_img_icon)
                val textView: TextView = itemView.findViewById(R.id.item_txt_name)
                val container: LinearLayout = itemView.findViewById(R.id.container_imc)
                iconImc.setImageResource(item.drawableId)
                textView.setText(item.texStringId)
                container.setBackgroundColor(item.color)

                container.setOnClickListener {
                    onItemClickListener.invoke(item.id)

                }
            }
        }
    }



}
//bntImg = findViewById(R.id.btnImg)
//        bntImg.setOnClickListener {
//            val i =Intent(this,ImcActivity::class.java)
//            startActivity(i)
//        }
//override fun onClick(id: Int) {
//        Log.i("deu bom","deu bom $id")
//        when(id){
//            1 -> {
//                val i = Intent(this,ImcActivity::class.java)
//                startActivity(i)
//            }
//            2 -> {
//                Log.i("tela 2","tela 2")
//            }
//            3 -> {
//                Log.i("tela 3","tela 3")
//            }
//            4 -> {
//                Log.i("tela 4","tela 4")
//            }
//        }
//    }


//---------------refazendo a aula adpter
//setContentView(R.layout.activity_main)
//val mainItems = mutableListOf<MainItem>()
//mainItems.addAll(
//listOf(
//MainItem(1,R.drawable.baseline_wb_sunny_24,R.string.imc, Color.GREEN)
//,MainItem(2,R.drawable.baseline_wc_24,R.string.calculo_DPP, Color.CYAN)
//,MainItem(3,R.drawable.baseline_wb_sunny_24,R.string.calculo_IG, Color.GREEN)
//,MainItem(4,R.drawable.baseline_wc_24,R.string.imc, Color.CYAN)
//)
//
//)
//
//val adapter = MainAdapter(mainItems) { id->
//    when(id){
//        1 -> {
//            val i = Intent(this,ImcActivity::class.java)
//            startActivity(i)
//        }
//        2 -> {
//            Log.i("tela 2","tela 2")
//        }
//        3 -> {
//            Log.i("tela 3","tela 3")
//        }
//        4 -> {
//            Log.i("tela 4","tela 4")
//        }
//    }
//}
//rvMain = findViewById(R.id.rv_main)
//rvMain.adapter=adapter
//rvMain.layoutManager = GridLayoutManager(this,2)
////
//}
////
//
//private inner class MainAdapter(private val mainItems: MutableList<MainItem>,
//                                private val onItemClickListener: (Int) -> Unit)
//    : RecyclerView.Adapter<MainAdapter.MainViewHolder>(){
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
//        val view = layoutInflater.inflate(R.layout.main_item, parent, false)
//        return MainViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
//        val itemCurrent = mainItems[position]
//        holder.bind(itemCurrent)
//
//    }
//
//    override fun getItemCount(): Int {
//        return mainItems.size
//    }
//    private inner class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        fun bind(itemCurrent: MainItem) {
//            val img: ImageView = itemView.findViewById(R.id.item_img_icon)
//            val name: TextView = itemView.findViewById(R.id.item_txt_name)
//            val containner:LinearLayout = itemView.findViewById(R.id.container_imc)
//            img.setImageResource(itemCurrent.drawableId)
//            name.setText(itemCurrent.texStringId)
//            containner.setBackgroundColor(itemCurrent.color)
//            containner.setOnClickListener {
//                onItemClickListener.invoke(itemCurrent.id)
//            }
//        }
//
//    }
