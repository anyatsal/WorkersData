package app.workersdata.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.workersdata.data.model.EmployeeEntity
import app.workersdata.data.model.EmployeeIdWithSpecialtyIdEntity
import app.workersdata.data.model.SpecialtyEntity

@Database(
    entities = [EmployeeEntity::class, SpecialtyEntity::class, EmployeeIdWithSpecialtyIdEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun companyJobDao(): CompanyDao
}