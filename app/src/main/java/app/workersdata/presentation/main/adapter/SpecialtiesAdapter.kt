package app.workersdata.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.workersdata.data.model.Specialty
import app.workersdata.databinding.RvSpecialtyItemBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class SpecialtiesAdapter : RecyclerView.Adapter<SpecialtyItemViewHolder>() {
    private val items: MutableList<Specialty> = ArrayList()
    private val viewClickSubject: PublishSubject<Specialty> =
        PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtyItemViewHolder {
        return SpecialtyItemViewHolder(
            RvSpecialtyItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SpecialtyItemViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            viewClickSubject.onNext(item)
        }
        holder.bind(item.name)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        viewClickSubject.onComplete()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateList(specialties: List<Specialty>) {
        this.items.clear()
        this.items.addAll(specialties)
        this.notifyDataSetChanged()
    }

    fun onSelectItem(): Observable<Specialty> {
        return viewClickSubject.hide()
    }
}