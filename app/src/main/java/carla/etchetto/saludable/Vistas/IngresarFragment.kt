package carla.etchetto.saludable.Vistas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import carla.etchetto.saludable.IngresarComida
import carla.etchetto.saludable.R
import carla.etchetto.saludable.UsuarioActivo
import carla.etchetto.saludable.dao.dbHelper

class IngresarFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_login,container,false)
        val usuarioIn: EditText = view.findViewById(R.id.l_usuario)
        val pass: EditText = view.findViewById(R.id.l_pass)
        val login: Button = view.findViewById(R.id.l_aceptar)

        val db: dbHelper = dbHelper(view.context,null)

        login.setOnClickListener(
            View.OnClickListener {
                if(db.validarUsuarioLogin(usuarioIn.text.toString(),pass.text.toString())){
                    val intent: Intent = Intent(view.context, UsuarioActivo::class.java)
                    var usuario = usuarioIn.text.toString()
                    intent.putExtra("usuario",usuario)
                    startActivity(intent)
                }else{
                    Toast.makeText(view.context,"Usuario no encontrado, REGISTRESE", Toast.LENGTH_LONG).show()
                }

            }
        )


        return view
    }
}