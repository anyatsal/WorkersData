package app.workersdata.presentation.main

import app.workersdata.data.datasource.schedule.SchedulerProvider
import app.workersdata.data.model.Specialty
import app.workersdata.domain.model.SafeResult
import app.workersdata.domain.usecases.GetSpecialtiesUseCase
import app.workersdata.domain.usecases.RefreshInfoUseCase
import app.workersdata.presentation.base.BasePresenter
import app.workersdata.presentation.navigation.NavigationAction
import app.workersdata.presentation.navigation.Router
import io.reactivex.rxjava3.core.Observable
import timber.log.Timber

class MainPresenter(
    private val router: Router,
    private val schedulerProvider: SchedulerProvider,
    private val refreshInfoUseCase: RefreshInfoUseCase,
    private val getSpecialtiesUseCase: GetSpecialtiesUseCase
) : BasePresenter<MainPresenter.View>() {

    override fun onViewAttached(view: View) {
        super.onViewAttached(view)

        disposeOnViewDetached(
            refreshInfoUseCase.execute()
                .doOnSubscribe {
                    view.setRefreshing(true)
                }
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    view.setRefreshing(false)
                    if (it is SafeResult.Error) {
                        view.onRefreshError()
                    }
                }, {
                    Timber.wtf(it, "Refresh is failed")
                })
        )

        disposeOnViewDetached(
            getSpecialtiesUseCase.execute()
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
            view.onSelectSpecialty()
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    router.setScreen(NavigationAction.OpenSpecialty(it))
                }, {
                    Timber.d(it, "Cant navigate to specialty")
                })
        )
    }

    interface View {
        fun onSelectSpecialty(): Observable<Specialty>
        fun updateList(specialties: List<Specialty>)

        fun onRefreshError()
        fun setRefreshing(isRefreshing: Boolean)
    }
}