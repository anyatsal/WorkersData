package app.workersdata.presentation.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import app.workersdata.R
import app.workersdata.data.model.Specialty
import app.workersdata.databinding.FragmentMainBinding
import app.workersdata.presentation.base.BaseFragment
import app.workersdata.presentation.main.adapter.SpecialtiesAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import io.reactivex.rxjava3.core.Observable
import org.koin.android.ext.android.inject

class MainFragment : BaseFragment<MainPresenter.View, MainPresenter>(R.layout.fragment_main),
    MainPresenter.View {

    override val presenter: MainPresenter by inject()
    override val abstractView: MainPresenter.View
        get() = this

    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private val specialtiesAdapter = SpecialtiesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainRecyclerView.apply {
            adapter = specialtiesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onSelectSpecialty(): Observable<Specialty> {
        return specialtiesAdapter.onSelectItem()
    }

    override fun updateList(specialties: List<Specialty>) {
        specialtiesAdapter.updateList(specialties)
    }

    override fun onRefreshError() {
        Toast.makeText(
            requireContext(),
            getText(R.string.error_refresh_database),
            Toast.LENGTH_LONG
        )
    }

    override fun setRefreshing(isRefreshing: Boolean) {
        binding.mainSwipeRefreshLayout.isRefreshing = isRefreshing
    }
}