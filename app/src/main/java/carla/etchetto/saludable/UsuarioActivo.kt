package carla.etchetto.saludable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class UsuarioActivo : AppCompatActivity() {

    lateinit var titulo: TextView
    lateinit var guardarComida: Button
    lateinit var salir: Button
    var usuario: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_activo)
        usuario = intent.getSerializableExtra("usuario") as String

        guardarComida=findViewById(R.id.cargar_comida)
        salir=findViewById(R.id.salir)
        titulo=findViewById(R.id.titulo_usuario)


        titulo.setText("Bienvenido " + usuario.toString().toUpperCase())

        guardarComida.setOnClickListener(View.OnClickListener {
            val i: Intent = Intent(this,IngresarComida::class.java)
            i.putExtra("usuario",usuario)
            startActivity(i)
        })

        salir.setOnClickListener(View.OnClickListener {
            val i: Intent = Intent(this,MainActivity::class.java)
            startActivity(i)
        })


    }
}