package app.workersdata.presentation.navigation

import app.workersdata.data.model.Employee
import app.workersdata.data.model.Specialty

sealed class NavigationAction {
    data class OpenSpecialty(val specialty: Specialty) : NavigationAction()
    data class OpenEmployee(val employee: Employee): NavigationAction()
    object Back : NavigationAction()
}
