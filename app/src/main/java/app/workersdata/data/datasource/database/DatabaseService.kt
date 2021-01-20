package app.workersdata.data.datasource.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import app.workersdata.data.datasource.schedule.SchedulerProvider
import app.workersdata.data.mapper.EmployeeMapper
import app.workersdata.data.mapper.EmployeeWithSpecialtyMapper
import app.workersdata.data.mapper.SpecialtyMapper
import app.workersdata.data.model.Employee
import app.workersdata.data.model.EmployeeIdWithSpecialtyId
import app.workersdata.data.model.Specialty
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import timber.log.Timber

class DatabaseService(context: Context, private val schedulerProvider: SchedulerProvider) {

    private val db: AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "database-name")
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Timber.tag("Room").d("Database is created: ${db.path}")
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    Timber.tag("Room").d("Database is opened")
                }
            })
            .build()

    private val companyDao: CompanyDao by lazy {
        db.companyJobDao()
    }

    fun updateDatabase(employees: List<Employee>): Completable {
        val specialities = mutableListOf<Specialty>()
        val employeesWithSpecialities = mutableListOf<EmployeeIdWithSpecialtyId>()
        employees.forEachIndexed { index, employee ->
            specialities.addAll(employee.specialties)
            employee.specialties.forEach { speciality ->
                employeesWithSpecialities.add(
                    EmployeeIdWithSpecialtyId(
                        employeeId = index,
                        specialityId = speciality.specialtyId
                    )
                )
            }
        }
        return companyDao.update(
            employees.mapIndexed { index, employee ->
                EmployeeMapper.toEntity(employee, index)
            },
            specialities.map {
                SpecialtyMapper.toEntity(it)
            }.distinct(),
            employeesWithSpecialities.map {
                EmployeeWithSpecialtyMapper.toEntity(it)
            }
        )
            .subscribeOn(schedulerProvider.computation())
    }

    fun getAllEmployees(specialtyId: Int): Observable<List<Employee>> {
        return companyDao.findEmployees(specialtyId)
            .map { it.map { entity -> EmployeeMapper.toObject(entity) } }
            .toObservable()
            .subscribeOn(schedulerProvider.computation())
    }

    fun getAllSpecialties(): Observable<List<Specialty>> {
        return companyDao.getAllSpecialties()
            .map { it.map { entity -> SpecialtyMapper.toObject(entity) } }
            .toObservable()
            .subscribeOn(schedulerProvider.computation())
    }
}