package app.workersdata.presentation.specialty

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import app.workersdata.R
import app.workersdata.data.model.Employee
import app.workersdata.databinding.FragmentSpecialtyBinding
import app.workersdata.presentation.base.BaseFragment
import app.workersdata.presentation.specialty.adapter.EmployeesAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import io.reactivex.rxjava3.core.Observable
import org.koin.android.ext.android.inject

class SpecialtyFragment :
    BaseFragment<SpecialtyPresenter.View, SpecialtyPresenter>(R.layout.fragment_specialty),
    SpecialtyPresenter.View {

    override val presenter: SpecialtyPresenter by inject()
    override val abstractView: SpecialtyPresenter.View
        get() = this

    private val binding: FragmentSpecialtyBinding by viewBinding(FragmentSpecialtyBinding::bind)
    private val employeesAdapter = EmployeesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.specialtyRecyclerView.apply {
            adapter = employeesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onSelectEmployee(): Observable<Employee> {
        return employeesAdapter.onSelectItem()
    }

    override fun updateList(employees: List<Employee>) {
        employeesAdapter.updateList(employees)
    }
}