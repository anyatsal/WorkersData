package app.workersdata.data.datasource.database

import androidx.room.TypeConverter
import app.workersdata.data.model.SpecialtyEntity

class Converters {
    @TypeConverter
    fun toSpecialties(value: String): List<SpecialtyEntity> {
        TODO("Not yet implemented")
    }

    @TypeConverter
    fun fromSpecialties(specialties: List<SpecialtyEntity>): String {
        TODO("Not yet implemented")
    }
}