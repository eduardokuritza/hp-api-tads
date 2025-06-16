package com.example.hpapiapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hpapiapp.databinding.ItemSpellBinding
import com.example.hpapiapp.model.Spell

class SpellAdapter(
    private val spells: List<Spell>,
    private val onClick: (Spell) -> Unit
): RecyclerView.Adapter<SpellAdapter.SpellViewHolder>() {

    inner class SpellViewHolder(val binding: ItemSpellBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(spell: Spell) {
            binding.tvName.text = spell.name
            binding.root.setOnClickListener { onClick(spell) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SpellViewHolder(ItemSpellBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: SpellViewHolder, position: Int) =
        holder.bind(spells[position])

    override fun getItemCount() = spells.size
}