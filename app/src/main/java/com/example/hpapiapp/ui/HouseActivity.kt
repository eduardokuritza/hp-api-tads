package com.example.hpapiapp.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hpapiapp.databinding.ActivityHouseBinding
import com.example.hpapiapp.network.HttpClient
import kotlinx.coroutines.launch
import org.json.JSONArray

class HouseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHouseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHouseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFetch.setOnClickListener {
            val house = when (binding.radioGroup.checkedRadioButtonId) {
                binding.rbGryffindor.id -> "gryffindor"
                binding.rbSlytherin.id  -> "slytherin"
                binding.rbRavenclaw.id   -> "ravenclaw"
                binding.rbHufflepuff.id  -> "hufflepuff"
                else -> null
            }
            if (house == null) {
                AlertDialog.Builder(this@HouseActivity)
                    .setMessage("Por favor, selecione uma casa.")
                    .setPositiveButton("OK", null)
                    .show()
                return@setOnClickListener
            }
            house?.let { fetchHouse(it) }
        }
    }

    private fun fetchHouse(house: String) {
        lifecycleScope.launch {
            val json = HttpClient.get("https://hp-api.onrender.com/api/characters/house/$house")
            val arr = JSONArray(json)
            val names = List(arr.length()) { i -> arr.getJSONObject(i).optString("name") }
            binding.tvResult.text = names.joinToString("\n")
        }
    }
}