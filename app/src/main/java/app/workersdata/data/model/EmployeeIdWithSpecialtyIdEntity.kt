package app.workersdata.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity
data class EmployeeIdWithSpecialtyIdEntity(
    @PrimaryKey
    @ColumnInfo(name = "employeeId") val employeeId: Int,
    @ColumnInfo(name = "specialtyId") val specialtyId: Int
)