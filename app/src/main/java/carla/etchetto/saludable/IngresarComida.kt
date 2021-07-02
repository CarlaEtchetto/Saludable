package carla.etchetto.saludable

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import carla.etchetto.saludable.Modelos.Comida
import carla.etchetto.saludable.dao.dbHelper
import kotlinx.android.synthetic.main.activity_ingresar_comida.*
import java.text.SimpleDateFormat
import java.util.*

class IngresarComida : AppCompatActivity() {

    lateinit var comidaPpal: EditText
    lateinit var comidaSria: EditText
    lateinit var bebida: EditText
    lateinit var postre: Spinner
    lateinit var adicional: Spinner
    lateinit var satisfaccion: Spinner
    lateinit var detallePostre: EditText
    lateinit var detalleComidaAdic: EditText
    lateinit var guardar: Button
    lateinit var volver: Button
    var comidaElegida: String = ""
    var paciente: String=""
    var satisfecho:String=""
    var validador: Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_comida)

        IniciarElementos()
        paciente = intent.getSerializableExtra("usuario") as String
        val db: dbHelper = dbHelper(this,null)
        detalleComidaAdic.visibility=View.INVISIBLE
        detallePostre.visibility=View.INVISIBLE

        val respuestas = arrayOf("Indique su respuesta","Si", "No")
        val tipoComida = arrayOf("Seleccione Comida","Desayuno", "Almuerzo", "Merienda","Cena")
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipoComida)
        val adapter2 = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, respuestas)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        val spin: Spinner = findViewById(R.id.tipo_comida)
        val spin2:Spinner=findViewById(R.id.sp_satisfaccion)
        val spin3:Spinner=findViewById(R.id.sp_adicional)
        val spin4:Spinner=findViewById(R.id.sp_postre)
        spin.adapter = adapter
        spin2.adapter=adapter2
        spin3.adapter=adapter2
        spin4.adapter=adapter2

        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(tipo_comida.selectedItem.toString()=="Almuerzo" || tipo_comida.selectedItem.toString()=="Cena")
                {
                    postre_layout.visibility=View.VISIBLE
                    comidaElegida= parent.getItemAtPosition(position).toString()
                }
                else{
                    postre_layout.visibility=View.INVISIBLE
                    comidaElegida= parent.getItemAtPosition(position).toString()
                }
            }
        }
        spin2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                satisfecho = parent.getItemAtPosition(position).toString()
            }
        }
        spin3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(sp_adicional.selectedItem.toString()=="Si") {
                    detalle_comida_adicional.visibility = View.VISIBLE

                }
                else{
                    detalle_comida_adicional.visibility=View.INVISIBLE
                }
            }
        }
        spin4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                if(sp_postre.selectedItem.toString()=="Si")
                {
                    detalle_postre.visibility=View.VISIBLE
                }
                else{
                    detalle_postre.visibility=View.INVISIBLE
                }
            }
        }
        Log.i("USUARIO COMIDA", paciente)
        guardar.setOnClickListener(View.OnClickListener {

            val fecha = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = fecha.format(Date())
            val dia: String = currentDate.toString()

            validador=ValidarIngreso(comidaElegida,comidaPpal.text.toString(),comidaSria.text.toString(),bebida.text.toString(),satisfecho)

            if (validador){
                db.guardarComida(Comida(paciente, comidaElegida, comidaPpal.text.toString(), comidaSria.text.toString(), bebida.text.toString(), detallePostre.text.toString(), detalleComidaAdic.text.toString(), dia, satisfecho))
                val intent = Intent(this, UsuarioActivo::class.java)
                intent.putExtra("usuario", paciente)
                startActivity(intent)
            }
        })

        volver.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, UsuarioActivo::class.java)
            intent.putExtra("usuario",paciente)
            startActivity(intent)
        })

    }


    private fun IniciarElementos(){
        comidaPpal=findViewById(R.id.comida_ppal)
        comidaSria=findViewById(R.id.comida_sria)
        bebida=findViewById(R.id.bebida)
        detalleComidaAdic=findViewById(R.id.detalle_comida_adicional)
        detallePostre=findViewById(R.id.detalle_postre)
        guardar=findViewById(R.id.guardar)
        postre=findViewById(R.id.sp_postre)
        adicional=findViewById(R.id.sp_adicional)
        satisfaccion=findViewById(R.id.sp_satisfaccion)
        volver=findViewById(R.id.gc_volver)
    }

    private fun ValidarIngreso(datoIn: String, datoIn2: String, datoIn3: String,datoIn4:String, datoIn5:String): Boolean
    {
        if (datoIn=="" || datoIn2 ==""|| datoIn3 ==""|| datoIn4 ==""||datoIn5 =="" )
        {
            Toast.makeText(this,"Hay campos obligatorios sin completar", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}