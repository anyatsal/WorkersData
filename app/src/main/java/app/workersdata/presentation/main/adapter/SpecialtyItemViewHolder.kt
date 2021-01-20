package app.workersdata.presentation.main.adapter

import androidx.recyclerview.widget.RecyclerView
import app.workersdata.databinding.RvSpecialtyItemBinding

class SpecialtyItemViewHolder(private val binding: RvSpecialtyItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(name: String) {
        binding.itemTextViewSpecialty.text = name
    }
}