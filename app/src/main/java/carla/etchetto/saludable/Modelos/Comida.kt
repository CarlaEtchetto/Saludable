package carla.etchetto.saludable.Modelos
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Date

@Parcelize
class Comida (val paciente: String, val nombre: String, val comidaPpal: String,val comidaSria: String, val bebida: String, val postre: String,
               val comidaExtra: String,val fechaRegistro: String, val satisfecho: String ) : Parcelable {
}
