package carla.etchetto.saludable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import carla.etchetto.saludable.Vistas.IngresarFragment
import kotlinx.android.synthetic.main.activity_ingresar_comida.*


class MainActivity : AppCompatActivity() {
    lateinit var login: Button
    lateinit var  registrarse: Button
    lateinit var main: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val manager = supportFragmentManager
        login = findViewById(R.id.ingresar)
        registrarse = findViewById(R.id.registrarse)
        main=findViewById(R.id.main)

        var LoginFg : IngresarFragment = IngresarFragment()

        login.setOnClickListener(
            View.OnClickListener {
                main.visibility=View.INVISIBLE
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.login,LoginFg)
                transaction.commit()
            }
        )
        registrarse.setOnClickListener(
            View.OnClickListener {
                val i: Intent = Intent(this,registrarUsuario::class.java)
                startActivity(i)
            }
        )
    }


}