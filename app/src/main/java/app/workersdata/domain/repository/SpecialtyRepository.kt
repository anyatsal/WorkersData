package app.workersdata.domain.repository

import app.workersdata.data.model.Specialty
import io.reactivex.rxjava3.core.Observable

interface SpecialtyRepository {
    fun specialties(): Observable<List<Specialty>>
}