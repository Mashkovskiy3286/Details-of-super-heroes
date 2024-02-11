package ua.ma.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import ua.ma.superheroes.RecyclerViewAdapter
import java.io.IOException

class DetailsFragments : Fragment() {

    private var idHero:Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val client = OkHttpClient()
        val request = okhttp3.Request.Builder()
            .url("https://akabab.github.io/superhero-api/api/id/$idHero.json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val handler = Handler(Looper.getMainLooper())
                    handler.post{
                        val a = response.body?.string()
                        val hero = Gson().fromJson(a, Hero::class.java)

                        val imageHero:ImageView = view.findViewById(R.id.imageHero)
                        val name:TextView = view.findViewById(R.id.name)
                        val fullName:TextView = view.findViewById(R.id.fullName)
                        val gender:TextView = view.findViewById(R.id.gender)
                        val race:TextView = view.findViewById(R.id.race)

                        Glide.with(view.context)
                            .load(hero.images.md)
                            .into(imageHero)

                        name.text = "Ім'я: "+hero.name
                        fullName.text = "ПІБ: "+hero.biography.fullName
                        gender.text = "Стать: "+hero.appearance.gender
                        race.text = "Раса: "+hero.appearance.race
                    }
                }
            }
        })

//        val titleTextView:TextView = view.findViewById(R.id.name)
//        titleTextView.text = idHero.toString();

    }

    fun setDescription(title:Int){
        idHero = title
    }

}
