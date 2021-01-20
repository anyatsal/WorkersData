package app.workersdata.domain.usecases

import app.workersdata.data.model.Specialty
import app.workersdata.domain.repository.SpecialtyRepository
import io.reactivex.rxjava3.core.Observable

class GetSpecialtiesUseCase(
    private val specialtyRepository: SpecialtyRepository
) : BaseObservableUseCase<List<Specialty>> {
    override fun execute(): Observable<List<Specialty>> {
        return specialtyRepository.specialties()
    }
}