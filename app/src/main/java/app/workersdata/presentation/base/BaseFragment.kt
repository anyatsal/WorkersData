package app.workersdata.presentation.base

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseFragment<V, P : BasePresenter<V>>(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    abstract val presenter: P
    abstract val abstractView: V

    protected val onClickBack: PublishSubject<Unit> = PublishSubject.create()

    protected var isConsumeBackPress: () -> Boolean = {
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().let { activity ->
            activity.onBackPressedDispatcher.addCallback(this) {
                if (isConsumeBackPress()) {
                    isEnabled = false
                    activity.onBackPressed()
                    isEnabled = true
                }
                onClickBack.onNext(Unit)
            }
        }
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewAttached(abstractView)
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewDetached()
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        presenter.onViewWillShow(abstractView)
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        presenter.onViewWillHide()
    }
}