package app.workersdata.presentation.di

import app.workersdata.core.ModuleProvider
import app.workersdata.data.model.Employee
import app.workersdata.data.model.Specialty
import app.workersdata.presentation.SinglePresenter
import app.workersdata.presentation.employee.EmployeePresenter
import app.workersdata.presentation.main.MainPresenter
import app.workersdata.presentation.navigation.Router
import app.workersdata.presentation.specialty.SpecialtyPresenter
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule : ModuleProvider {

    private val presentationModule = module {
        single { Router() }

        factory {
            SinglePresenter(
                router = get(),
                schedulerProvider = get()
            )
        }

        factory {
            MainPresenter(
                getSpecialtiesUseCase = get(),
                router = get(),
                schedulerProvider = get(),
                refreshInfoUseCase = get()
            )
        }

        factory { (speciality: Specialty) ->
            SpecialtyPresenter(
                specialty = speciality,
                getEmployeesUseCase = get(),
                router = get(),
                schedulerProvider = get()
            )
        }

        factory { (employee: Employee) ->
            EmployeePresenter(
                employee = employee,
                router = get(),
                schedulerProvider = get()
            )
        }
    }

    override fun modules(): List<Module> {
        return listOf(presentationModule)
    }
}