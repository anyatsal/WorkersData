package app.workersdata.data.repository

import app.workersdata.data.datasource.api.CompanyService
import app.workersdata.data.datasource.database.DatabaseService
import app.workersdata.domain.repository.CompanyRepository
import io.reactivex.rxjava3.core.Completable

class CompanyRepositoryImpl(
    private val companyService: CompanyService,
    private val databaseService: DatabaseService
): CompanyRepository {

    override fun refreshInfo(): Completable {
        return companyService.getInfo()
            .flatMapCompletable {
                databaseService.updateDatabase(it.employees)
            }
    }
}