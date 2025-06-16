package com.example.hpapiapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hpapiapp.databinding.ActivitySpellDetailBinding

class SpellDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpellDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpellDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvName.text = intent.getStringExtra("name")
        binding.tvDesc.text = intent.getStringExtra("desc")
    }
}