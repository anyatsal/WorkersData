package app.workersdata.presentation.specialty

import app.workersdata.data.datasource.schedule.SchedulerProvider
import app.workersdata.data.model.Employee
import app.workersdata.data.model.Specialty
import app.workersdata.domain.usecases.GetEmployeesUseCase
import app.workersdata.presentation.base.BasePresenter
import app.workersdata.presentation.navigation.NavigationAction
import app.workersdata.presentation.navigation.Router
import io.reactivex.rxjava3.core.Observable
import timber.log.Timber

class SpecialtyPresenter(
    private val specialty: Specialty,
    private val router: Router,
    private val schedulerProvider: SchedulerProvider,
    private val getEmployeesUseCase: GetEmployeesUseCase
) : BasePresenter<SpecialtyPresenter.View>() {

    override fun onViewAttached(view: View) {
        super.onViewAttached(view)

        disposeOnViewDetached(
            getEmployeesUseCase.execute(specialty.specialtyId)
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    view.updateList(it)
                }, {
                    Timber.e(it, "Can't update list")
                })
        )
    }

    override fun onViewWillShow(view: View) {
        super.onViewWillShow(view)
        disposeOnViewWillHide(
            view.onSelectEmployee()
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    router.setScreen(NavigationAction.OpenEmployee(it))
                }, {
                    Timber.d(it, "Cant navigate to employee")
                })
        )
    }

    interface View {
        fun onSelectEmployee(): Observable<Employee>
        fun updateList(employees: List<Employee>)
    }
}