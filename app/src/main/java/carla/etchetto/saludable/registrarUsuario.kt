package carla.etchetto.saludable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import carla.etchetto.saludable.Modelos.Usuario
import carla.etchetto.saludable.dao.dbHelper
import kotlinx.android.synthetic.main.activity_registrar_usuario.*
import java.util.*

class registrarUsuario : AppCompatActivity() {

    lateinit var nombre: EditText
    lateinit var apellido: EditText
    lateinit var dni: EditText
    lateinit var fechaNacimiento: EditText
    lateinit var localidad: EditText
    lateinit var usuario: EditText
    lateinit var pass: EditText
    lateinit var rePass: EditText
    lateinit var aceptar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_usuario)
        val tratamientos = arrayOf("Seleccione Tratamiento","Anorexia","Bulimia", "Obesidad")
        val sexos = arrayOf("Seleccione Sexo","Femenino", "Masculino")
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,tratamientos)
        var adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item,sexos)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        val spin: Spinner = findViewById(R.id.tratamiento)
        val spin2: Spinner = findViewById(R.id.sexo)
        spin.adapter = adapter
        spin2.adapter=adapter2
        var tratamiento: String = ""
        var sexo: String=""

        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                tratamiento = parent.getItemAtPosition(position).toString()
            }
        }
        spin2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                sexo = parent.getItemAtPosition(position).toString()
            }
        }

        val db: dbHelper = dbHelper(this,null)
        IniciarElementos()
        aceptar.setOnClickListener(
            View.OnClickListener {
                val passOK =(ValidarContrasenia(pass.text.toString(),rePass.text.toString()))
                val userOK = db.NombreUsuarioUtilizado(usuario.text.toString())
                var validador = ValidarSexo(sexo.toString())
                var validador2=ValidarTratamiento(tratamiento.toString())
                if(userOK)
                {
                    Toast.makeText(this,"Usuario ya utilizado, ingrese uno nuevo", Toast.LENGTH_LONG).show()
                }
                else
                {
                    if(passOK && validador && validador2)
                    {

                        db.guardarUsuario(Usuario(nombre.text.toString(),apellido.text.toString(),dni.text.toString().toInt(),fechaNacimiento.text.toString(),localidad.text.toString(), usuario.text.toString(),pass.text.toString(),tratamiento.toString(),sexo.toString()))
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("usuario", usuario.toString());
                        startActivity(intent)
                    }
                }
            })
    }



    private fun IniciarElementos(){
        nombre = findViewById(R.id.nombre)
        apellido = findViewById(R.id.apellido)
        dni=findViewById(R.id.dni)
        fechaNacimiento=findViewById(R.id.fecha_nacimiento)
        localidad= findViewById(R.id.localidad)
        usuario=findViewById(R.id.usuario)
        pass=findViewById(R.id.contrasenia)
        rePass=findViewById(R.id.reing_contrasenia)
        aceptar=findViewById(R.id.r_aceptar)

    }

    private fun ValidarContrasenia(pass: String, rePass: String): Boolean{
        if(pass != rePass)
        {
            Toast.makeText(this,"Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun ValidarTratamiento(tratamiento:String):Boolean
    {
        if (tratamiento.equals("Seleccione Tratamiento"))
        {
            Toast.makeText(this,"Debe seleccionar un Tratamiento", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun ValidarSexo(sexo: String) : Boolean {
        if(sexo.equals("Seleccion Sexo"))
        {
            Toast.makeText(this,"Debe indicar su Sexo", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}