package app.workersdata.presentation.specialty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.workersdata.data.model.Employee
import app.workersdata.data.model.Specialty
import app.workersdata.databinding.RvEmployeeItemBinding
import app.workersdata.databinding.RvSpecialtyItemBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class EmployeesAdapter : RecyclerView.Adapter<EmployeeItemViewHolder>() {
    private val items: MutableList<Employee> = ArrayList()
    private val viewClickSubject: PublishSubject<Employee> =
        PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeItemViewHolder {
        return EmployeeItemViewHolder(
            RvEmployeeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EmployeeItemViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            viewClickSubject.onNext(item)
        }
        holder.bind(item.getFullName(), item.getAge())
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        viewClickSubject.onComplete()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateList(employees: List<Employee>) {
        this.items.clear()
        this.items.addAll(employees)
        this.notifyDataSetChanged()
    }

    fun onSelectItem(): Observable<Employee> {
        return viewClickSubject.hide()
    }
}