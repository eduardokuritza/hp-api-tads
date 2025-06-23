package com.example.hpapiapp.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hpapiapp.databinding.ActivityStaffBinding
import com.example.hpapiapp.model.Character
import com.example.hpapiapp.network.HttpClient
import kotlinx.coroutines.launch
import org.json.JSONArray

class StaffActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStaffBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStaffBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFetch.setOnClickListener {
            val nameQuery = binding.etName.text.toString().trim().lowercase()

            if (nameQuery.isNotEmpty()) {
                fetchStaff (nameQuery)
            } else {
                AlertDialog.Builder(this@StaffActivity)
                    .setMessage("Por favor, informe um nome.")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }

    private fun fetchStaff(query: String) {
        lifecycleScope.launch {
            val json = HttpClient.get("https://hp-api.onrender.com/api/characters/staff")
            val arr = JSONArray(json)
            val filtered = mutableListOf<Character>()

            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                val name = obj.optString("name")
                if (name.lowercase().contains(query)) {
                    filtered += Character(
                        name = name,
                        species = obj.optString("species"),
                        house = obj.optString("house"),
                        image = null,
                        alternate_names = obj.optJSONArray("alternate_names")?.let { alt ->
                            List(alt.length()) { idx -> alt.getString(idx) }
                        }
                    )
                }
            }

            if (filtered.isEmpty()) {
                binding.tvResult.text = ""
                AlertDialog.Builder(this@StaffActivity)
                    .setTitle("Nenhum personagem encontrado")
                    .setMessage("Nenhum personagem foi encontrado com o nome solicitado.")
                    .setPositiveButton("OK", null)
                    .show()
            } else {
                binding.tvResult.text = filtered.joinToString("\n\n") {
                    "Nome: ${it.name}\n" +
                            "Apelidos: ${it.alternate_names}\n" +
                            "Espécie: ${it.species}\n" +
                            "Casa: ${it.house}"
                }
            }
        }
    }
}