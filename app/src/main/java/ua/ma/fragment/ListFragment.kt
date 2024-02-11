package ua.ma.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import ua.ma.superheroes.RecyclerViewAdapter
import java.io.IOException

class ListFragment:Fragment() {

    private var onItemClick: (Int) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        val client = OkHttpClient()
        val request = okhttp3.Request.Builder()
            .url("https://akabab.github.io/superhero-api/api/all.json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val handler = Handler(Looper.getMainLooper())
                    handler.post{
                        val a = response.body?.string()
                        val heros = Gson().fromJson(a, Array<Hero>::class.java)

                        if (heros.isNotEmpty()){
                            val myAdapter = RecyclerViewAdapter(heros)
                            {

                                onItemClick(it)

                            }
                            recyclerView.adapter = myAdapter
                        }
                        recyclerView.layoutManager = LinearLayoutManager(view.context)
                    }
                }
            }
        })

    }

    fun setItemClickListener(lambda: (Int) -> Unit){
        onItemClick = lambda
    }

}