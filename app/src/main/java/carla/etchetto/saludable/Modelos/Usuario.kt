package carla.etchetto.saludable.Modelos
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Date

@Parcelize
class Usuario ( val nombre: String, val apellido: String,val dni: Int, val fechaNacimiento: String, val localidad: String,
val usuario: String, val pass: String, val tratamiento: String, val sexo: String) : Parcelable {
}
