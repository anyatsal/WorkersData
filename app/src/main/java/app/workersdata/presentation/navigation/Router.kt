package app.workersdata.presentation.navigation

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit

class Router {
    private val screenSubject = PublishSubject.create<NavigationAction>()

    fun setScreen(screen: NavigationAction) {
        Timber.tag("Router").d("Set screen: $screen")
        screenSubject.onNext(screen)
    }

    fun onScreenChanged(): Observable<NavigationAction> = screenSubject.hide()
        .throttleFirst(VIEW_CLICKS_INTERVAL, TimeUnit.MILLISECONDS)

    companion object {
        const val VIEW_CLICKS_INTERVAL = 1000L
    }
}