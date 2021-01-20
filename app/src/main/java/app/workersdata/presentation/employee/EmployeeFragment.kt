package app.workersdata.presentation.employee

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import app.workersdata.R
import app.workersdata.databinding.FragmentEmployeeBinding
import app.workersdata.presentation.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class EmployeeFragment :
    BaseFragment<EmployeePresenter.View, EmployeePresenter>(R.layout.fragment_employee),
    EmployeePresenter.View {

    override val presenter: EmployeePresenter by inject {
        parametersOf(
            args.employee
        )
    }

    override val abstractView: EmployeePresenter.View
        get() = this

    private val binding: FragmentEmployeeBinding by viewBinding(FragmentEmployeeBinding::bind)
    private val args: EmployeeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            employeeImageAvatar.load(args.employee.avatarUrl)
            employeeTextViewName.text = args.employee.getFullName()
            employeeTextViewBirthday.text = args.employee.getFormattedBirthday()
            employeeTextViewAge.text = args.employee.getAge()
        }
    }
}