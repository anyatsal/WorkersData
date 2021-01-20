package app.workersdata.domain.repository

import app.workersdata.data.model.Employee
import io.reactivex.rxjava3.core.Observable

interface EmployeeRepository {
    fun employees(id: Int): Observable<List<Employee>>
}