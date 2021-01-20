package app.workersdata.domain.repository

import io.reactivex.rxjava3.core.Completable

interface CompanyRepository {
    fun refreshInfo(): Completable
}