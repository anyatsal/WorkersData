package app.workersdata.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity
data class SpecialtyEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "name") val name: String
)