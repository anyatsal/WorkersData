package app.workersdata.data.repository

import app.workersdata.data.datasource.api.CompanyService
import app.workersdata.data.datasource.database.DatabaseService
import app.workersdata.data.model.Employee
import app.workersdata.domain.repository.EmployeeRepository
import app.workersdata.domain.repository.SpecialtyRepository
import io.reactivex.rxjava3.core.Observable

class EmployeeRepositoryImpl(
    private val companyService: CompanyService,
    private val databaseService: DatabaseService
) : EmployeeRepository {

    override fun employees(id: Int): Observable<List<Employee>> {
        return databaseService.getAllEmployees(id)
    }
}