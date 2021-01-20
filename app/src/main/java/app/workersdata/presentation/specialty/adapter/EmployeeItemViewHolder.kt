package app.workersdata.presentation.specialty.adapter

import androidx.recyclerview.widget.RecyclerView
import app.workersdata.databinding.RvEmployeeItemBinding
import app.workersdata.databinding.RvSpecialtyItemBinding

class EmployeeItemViewHolder(private val binding: RvEmployeeItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(name: String, age: String) {
        binding.itemTextViewName.text = name
        binding.itemTextViewAge.text = age
    }
}