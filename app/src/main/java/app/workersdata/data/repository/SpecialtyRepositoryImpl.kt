package app.workersdata.data.repository

import app.workersdata.data.datasource.database.DatabaseService
import app.workersdata.data.model.Specialty
import app.workersdata.domain.repository.SpecialtyRepository
import io.reactivex.rxjava3.core.Observable

class SpecialtyRepositoryImpl(
    private val databaseService: DatabaseService
): SpecialtyRepository {

    override fun specialties(): Observable<List<Specialty>> {
        return databaseService.getAllSpecialties()
    }
}