package app.workersdata.data.di

import app.workersdata.BuildConfig
import app.workersdata.core.ModuleProvider
import app.workersdata.data.datasource.api.CompanyService
import app.workersdata.data.datasource.database.DatabaseService
import app.workersdata.data.datasource.schedule.SchedulerProvider
import app.workersdata.data.datasource.schedule.ApplicationSchedulerProvider
import app.workersdata.data.mapper.ApiErrorMapper
import app.workersdata.data.repository.CompanyRepositoryImpl
import app.workersdata.data.repository.EmployeeRepositoryImpl
import app.workersdata.data.repository.SpecialtyRepositoryImpl
import app.workersdata.domain.repository.CompanyRepository
import app.workersdata.domain.repository.EmployeeRepository
import app.workersdata.domain.repository.SpecialtyRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object DataModule : ModuleProvider {

    private const val URL_BASE = BuildConfig.BASE_URL

    private fun loggerInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logger
    }

    private fun provideClient(): OkHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(loggerInterceptor())
        .build()

    private fun provideMoshi() = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .add(KotlinJsonAdapterFactory())
        .build()

    private fun provideRetrofitInstance(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(URL_BASE)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    private val scheduleModule = module {
        single<SchedulerProvider> { ApplicationSchedulerProvider() }
    }

    private val networkModule = module {
        single { ApiErrorMapper() }
        single { loggerInterceptor() }
        single { provideClient() }
        single { provideMoshi() }
        single { provideRetrofitInstance(okHttpClient = get(), moshi = get()) }
        single { CompanyService(get(), get()) }
    }

    private val databaseModule = module {
        single { DatabaseService(context = androidContext(), schedulerProvider = get()) }
    }

    private val repositoryModule = module {
        single<CompanyRepository> {
            CompanyRepositoryImpl(
                get(),
                get()
            )
        }

        single<EmployeeRepository> {
            EmployeeRepositoryImpl(
                get(),
                get()
            )
        }

        single<SpecialtyRepository> {
            SpecialtyRepositoryImpl(
                get()
            )
        }
    }

    override fun modules(): List<Module> {
        return listOf(
            scheduleModule,
            networkModule,
            databaseModule,
            repositoryModule
        )
    }
}