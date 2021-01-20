package app.workersdata.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
@JsonClass(generateAdapter = true)
data class Specialty(
    @Json(name = "specialty_id") val specialtyId: Int,
    @Json(name = "name") val name: String
) : Parcelable