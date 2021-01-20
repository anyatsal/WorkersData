package app.workersdata

import android.app.Application
import app.workersdata.data.di.DataModule
import app.workersdata.domain.di.DomainModule
import app.workersdata.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@App)
            koin.loadModules(
                DataModule.modules() + DomainModule.modules() + PresentationModule.modules()
            )
            koin.createRootScope()
        }
    }
}