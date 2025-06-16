package com.example.hpapiapp.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hpapiapp.databinding.ActivityCharacterBinding
import com.example.hpapiapp.model.Character
import com.example.hpapiapp.network.HttpClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.json.JSONArray
import java.net.URL
import android.app.AlertDialog

class CharacterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCharacterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFetch.setOnClickListener {
            val id = binding.etId.text.toString().trim()
            if (id.isNotEmpty()) {
                fetchCharacter(id)
            } else {
                AlertDialog.Builder(this@CharacterActivity)
                    .setTitle("ID inválido")
                    .setMessage("Por favor, informe um ID.")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }

    private fun fetchCharacter(id: String) {
        lifecycleScope.launch {
            val json = HttpClient.get("https://hp-api.onrender.com/api/character/$id")
            val arr = JSONArray(json)
            if (arr.length() == 0) {
                AlertDialog.Builder(this@CharacterActivity)
                    .setTitle("Personagem não encontrado")
                    .setMessage("Nenhum personagem foi encontrado com o ID solicitado.")
                    .setPositiveButton("OK", null)
                    .show()
                return@launch
            }
            val obj = arr.getJSONObject(0)
            val character = Character(
                name = obj.optString("name"),
                species = obj.optString("species"),
                house = obj.optString("house"),
                image = obj.optString("image"),
                alternate_names = null
            )
            binding.tvResult.text =
                "Nome: ${character.name}\nEspécie: ${character.species}\nCasa: ${character.house}"
            character.image?.takeIf { it.isNotEmpty() }?.let { url ->
                withContext(Dispatchers.IO) {
                    val bmp = URL(url).openStream().use { stream ->
                        BitmapFactory.decodeStream(stream)
                    }
                    withContext(Dispatchers.Main) {
                        binding.ivCharacter.setImageBitmap(bmp)
                    }
                }
            }
        }
    }
}