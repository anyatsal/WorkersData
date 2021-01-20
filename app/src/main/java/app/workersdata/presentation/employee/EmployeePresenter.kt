package app.workersdata.presentation.employee

import app.workersdata.data.datasource.schedule.SchedulerProvider
import app.workersdata.data.model.Employee
import app.workersdata.presentation.base.BasePresenter
import app.workersdata.presentation.navigation.Router

class EmployeePresenter(
    private val employee: Employee,
    private val router: Router,
    private val schedulerProvider: SchedulerProvider
) : BasePresenter<EmployeePresenter.View>() {

    interface View
}