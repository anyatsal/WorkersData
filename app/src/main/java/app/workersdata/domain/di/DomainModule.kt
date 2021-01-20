package app.workersdata.domain.di

import app.workersdata.core.ModuleProvider
import app.workersdata.domain.usecases.GetEmployeesUseCase
import app.workersdata.domain.usecases.GetSpecialtiesUseCase
import app.workersdata.domain.usecases.RefreshInfoUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule : ModuleProvider {

    private val useCaseModule = module {
        single {
            RefreshInfoUseCase(
                companyRepository = get()
            )
        }

        single {
            GetEmployeesUseCase(
                employeeRepository = get()
            )
        }

        single {
            GetSpecialtiesUseCase(
                specialtyRepository = get()
            )
        }
    }

    override fun modules(): List<Module> {
        return listOf(useCaseModule)
    }
}