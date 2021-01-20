package app.workersdata.domain.usecases

import app.workersdata.data.model.Employee
import app.workersdata.domain.repository.EmployeeRepository
import io.reactivex.rxjava3.core.Observable

class GetEmployeesUseCase(
    private val employeeRepository: EmployeeRepository
) : BaseObservableUseCaseWithParam<Int, List<Employee>> {
    override fun execute(param: Int): Observable<List<Employee>> {
        return employeeRepository.employees(param)
    }
}