package app.workersdata.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

@Keep
@JsonClass(generateAdapter = true)
@Parcelize
data class Employee(
    @Json(name = "f_name") val firstName: String,
    @Json(name = "l_name") val lastName: String,
    @Json(name = "birthday") val birthday: String?,
    @Json(name = "avatr_url") val avatarUrl: String,
    @Json(name = "specialty") val specialties: List<Specialty> = listOf(),
) : Parcelable {
    fun getFullName(): String {
        return "${firstName.toLowerCase().capitalize()} ${lastName.toLowerCase().capitalize()}"
    }

    fun getAge(): String {
        val birthday =
            LocalDate.parse(getFormattedBirthday(), DateTimeFormatter.ofPattern(BIRTHDAY_PATTERN))

        return Period.between(
            LocalDate.of(birthday.year, birthday.monthValue, birthday.dayOfMonth),
            LocalDate.now()
        ).years.toString()
    }

    fun getFormattedBirthday(): String {
        birthday?.let {
            val birthdayFormats = listOf(
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy")
            )

            birthdayFormats.forEach lit@{ dateFormat ->
                try {
                    LocalDate.parse(it, dateFormat)?.let { date ->
                        return DateTimeFormatter.ofPattern(BIRTHDAY_PATTERN).format(date)
                    }
                } catch (e: Exception) {
                    return@lit
                }
            }
        }
        return "--"
    }

    companion object {
        const val BIRTHDAY_PATTERN = "dd.MM.yyyy"
    }
}
