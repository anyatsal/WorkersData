package app.workersdata.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import app.workersdata.R
import app.workersdata.databinding.ActivityMainBinding
import app.workersdata.presentation.main.MainFragmentDirections
import app.workersdata.presentation.navigation.NavigationAction
import app.workersdata.presentation.specialty.SpecialtyFragmentDirections
import org.koin.android.ext.android.inject
import timber.log.Timber

class SingleActivity : AppCompatActivity(), SinglePresenter.View {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val presenter: SinglePresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment)
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewWillShow(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.onViewWillHide()
    }

    override fun navigateTo(screen: NavigationAction) {
        Timber.d("Navigate to: $screen")
        when (screen) {
            is NavigationAction.OpenSpecialty -> navController.navigate(
                MainFragmentDirections.openSpecialty(
                    speciality = screen.specialty
                )
            )
            is NavigationAction.OpenEmployee -> navController.navigate(
                SpecialtyFragmentDirections.openEmployee(
                    employee = screen.employee
                )
            )
            is NavigationAction.Back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        when (navController.currentDestination!!.id) {
            R.id.main -> finish()
            else -> super.onBackPressed()
        }
    }
}