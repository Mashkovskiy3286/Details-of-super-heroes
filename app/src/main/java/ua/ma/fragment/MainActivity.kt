package ua.ma.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listFragment = supportFragmentManager.findFragmentById(R.id.listContainer) as ListFragment

        listFragment.setItemClickListener {

            val detailsFragments = DetailsFragments()
            detailsFragments.setDescription(it)

            supportFragmentManager.beginTransaction()
                .add(R.id.listContainer, detailsFragments)
                .addToBackStack("details_fragment")
                .commit()

        }

    }
}

//data class HerosResponse(val heros:Array<Hero>)
data class Hero(val id:Int, val name:String, val images:Images, val biography:biography, val appearance:appearance)
data class Images(val md:String, val lg:String)
data class biography(val fullName:String)
data class appearance(val gender:String, val race:String)