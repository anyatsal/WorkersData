package app.workersdata.presentation

import app.workersdata.data.datasource.schedule.SchedulerProvider
import app.workersdata.presentation.base.BasePresenter
import app.workersdata.presentation.navigation.NavigationAction
import app.workersdata.presentation.navigation.Router
import timber.log.Timber

class SinglePresenter(
    private val router: Router,
    private val schedulerProvider: SchedulerProvider
) : BasePresenter<SinglePresenter.View>() {

    override fun onViewWillShow(view: View) {
        super.onViewWillShow(view)
        disposeOnViewWillHide(
            router.onScreenChanged()
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    view.navigateTo(it)
                }, {
                    Timber.e(it, "Can't navigate to screen")
                })
        )
    }

    interface View {
        fun navigateTo(screen: NavigationAction)
    }
}