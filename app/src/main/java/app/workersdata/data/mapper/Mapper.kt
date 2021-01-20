package app.workersdata.data.mapper

import app.workersdata.data.model.*

object EmployeeMapper {

    fun toEntity(employees: List<Employee>): List<EmployeeEntity> {
        return with(employees) {
            mapIndexed { index, employee ->
                EmployeeEntity(
                    id = index,
                    firstName = employee.firstName,
                    lastName = employee.lastName,
                    birthday = employee.getFormattedBirthday(),
                    avatarUrl = employee.avatarUrl
                )
            }
        }
    }

    fun toEntity(employee: Employee, index: Int): EmployeeEntity {
        return EmployeeEntity(
            id = index,
            firstName = employee.firstName,
            lastName = employee.lastName,
            birthday = employee.getFormattedBirthday(),
            avatarUrl = employee.avatarUrl
        )
    }

    fun toObject(employee: EmployeeEntity): Employee {
        return Employee(
            firstName = employee.firstName,
            lastName = employee.lastName,
            birthday = employee.birthday,
            avatarUrl = employee.avatarUrl
        )
    }
}

object SpecialtyMapper {
    fun toEntity(specialty: Specialty): SpecialtyEntity {
        return SpecialtyEntity(
            id = specialty.specialtyId,
            name = specialty.name
        )
    }

    fun toObject(specialty: SpecialtyEntity): Specialty {
        return Specialty(
            specialtyId = specialty.id,
            name = specialty.name
        )
    }
}

object EmployeeWithSpecialtyMapper {
    fun toEntity(employeeIdWithSpecialtyId: EmployeeIdWithSpecialtyId): EmployeeIdWithSpecialtyIdEntity {
        return EmployeeIdWithSpecialtyIdEntity(
            employeeId = employeeIdWithSpecialtyId.employeeId,
            specialtyId = employeeIdWithSpecialtyId.specialityId
        )
    }
}