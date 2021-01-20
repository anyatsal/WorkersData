package app.workersdata.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity
data class EmployeeEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "firstName") val firstName: String,
    @ColumnInfo(name = "lastName") val lastName: String,
    @ColumnInfo(name = "birthday") val birthday: String,
    @ColumnInfo(name = "avatarUrl") val avatarUrl: String
)