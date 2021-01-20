package app.workersdata.domain.usecases

import app.workersdata.domain.model.SafeResult
import app.workersdata.domain.model.SafeResult.Companion.success
import app.workersdata.domain.repository.CompanyRepository
import io.reactivex.rxjava3.core.Single

class RefreshInfoUseCase(
    private val companyRepository: CompanyRepository
) : BaseSingleUseCase<SafeResult<Unit>> {
    override fun execute(): Single<SafeResult<Unit>> {
        return companyRepository.refreshInfo()
            .andThen(Single.just(success(Unit)))
            .onErrorReturn { SafeResult.Error(it) }
    }
}