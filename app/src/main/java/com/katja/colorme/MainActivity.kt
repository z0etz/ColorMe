// Jag har gått igenom appen och funderat på var jag borde lägga till kommentarer, men inte hittat
// några ställen där jag tycker att det behövs. Tar gärna feedback på om det finns saker som bör
// kommenteras, men avsaknaden av kommentarer är i alla fall ett medvetet val.


package com.katja.colorme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.katja.colorme.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.commit {add(R.id.frameTop, PointerFragment())}

        supportFragmentManager.commit {add(R.id.frameBottom, DisplayFragment())}
    }
}