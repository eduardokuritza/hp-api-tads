package com.example.hpapiapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hpapiapp.databinding.ActivitySpellsBinding
import com.example.hpapiapp.model.Spell
import com.example.hpapiapp.network.HttpClient
import kotlinx.coroutines.launch
import org.json.JSONArray

class SpellsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpellsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpellsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.layoutManager = LinearLayoutManager(this)

        fetchSpells()
    }

    private fun fetchSpells() {
        lifecycleScope.launch {
            val json = HttpClient.get("https://hp-api.onrender.com/api/spells")
            val arr = JSONArray(json)
            val list = List(arr.length()) { i ->
                Spell(
                    name = arr.getJSONObject(i).optString("name"),
                    description = arr.getJSONObject(i).optString("description")
                )
            }
            binding.recycler.adapter = SpellAdapter(list) { spell ->
                startActivity(Intent(this@SpellsActivity, SpellDetailActivity::class.java).apply {
                    putExtra("name", spell.name)
                    putExtra("desc", spell.description)
                })
            }
        }
    }
}