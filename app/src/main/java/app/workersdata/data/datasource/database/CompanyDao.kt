package app.workersdata.data.datasource.database

import androidx.room.*
import app.workersdata.data.model.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.toFlowable

@Dao
interface CompanyDao {
    @Query("SELECT * FROM SpecialtyEntity")
    fun getAllSpecialties(): Flowable<List<SpecialtyEntity>>

    fun findEmployees(specialtyId: Int): Flowable<List<EmployeeEntity>> {
        val employeesWithSpeciality = findEmployeesWithSpecialities(specialtyId)

        val employees = mutableListOf<EmployeeEntity>()
        employeesWithSpeciality.map {
            it.forEach {
                getEmployee(it.employeeId)
                    .map {
                        employees.add(it)
                    }
            }
        }

        return Flowable.just(employees)
    }

    @Query("SELECT * FROM EmployeeIdWithSpecialtyIdEntity WHERE specialtyId = :specialtyId")
    fun findEmployeesWithSpecialities(specialtyId: Int): Flowable<List<EmployeeIdWithSpecialtyIdEntity>>

    fun findAllEmployees(employeesId: List<Int>): Flowable<List<EmployeeEntity>> {
        val employees = employeesId.map {
            getEmployee(it).blockingGet()
        }
        return Flowable.just(employees)
    }

    @Query("SELECT * FROM EmployeeEntity WHERE id = :id")
    fun getEmployee(id: Int): Single<EmployeeEntity>

    @Query("SELECT * FROM EmployeeEntity")
    fun getAllEmployees(): Flowable<List<EmployeeEntity>>

    @Query("SELECT * FROM SpecialtyEntity WHERE id = :specialtyId")
    fun findSpeciality(specialtyId: Int): Single<SpecialtyEntity>


    fun update(
        employees: List<EmployeeEntity>,
        specialties: List<SpecialtyEntity>,
        employeesWithSpecialties: List<EmployeeIdWithSpecialtyIdEntity>
    ): Completable {
        return Completable.fromCallable {
            updateSync(employees, specialties, employeesWithSpecialties)
        }
    }

    @Transaction
    fun updateSync(
        employees: List<EmployeeEntity>,
        specialties: List<SpecialtyEntity>,
        employeesWithSpecialties: List<EmployeeIdWithSpecialtyIdEntity>
    ) {
        deleteAllEmployees()
        deleteAllSpecialities()
        deleteAllEmployeesWithSpecialities()
        employees.forEach {
            addEmployee(it)
        }
        specialties.forEach {
            addSpeciality(it)
        }
        employeesWithSpecialties.forEach {
            addEmployeeWithSpeciality(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSpeciality(specialty: SpecialtyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEmployee(employee: EmployeeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEmployeeWithSpeciality(employeeIdWithSpecialtyIdEntity: EmployeeIdWithSpecialtyIdEntity)

    @Query("DELETE FROM EmployeeEntity")
    fun deleteAllEmployees()

    @Query("DELETE FROM SpecialtyEntity")
    fun deleteAllSpecialities()

    @Query("DELETE FROM EmployeeIdWithSpecialtyIdEntity")
    fun deleteAllEmployeesWithSpecialities()
}