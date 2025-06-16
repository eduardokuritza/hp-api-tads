package com.example.hpapiapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hpapiapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCharacter.setOnClickListener {
            startActivity(Intent(this, CharacterActivity::class.java))
        }
        binding.btnStaff.setOnClickListener {
            startActivity(Intent(this, StaffActivity::class.java))
        }
        binding.btnHouse.setOnClickListener {
            startActivity(Intent(this, HouseActivity::class.java))
        }
        binding.btnSpells.setOnClickListener {
            startActivity(Intent(this, SpellsActivity::class.java))
        }
        binding.btnExit.setOnClickListener {
            finishAffinity()
        }
        binding.btnExitCenter.setOnClickListener {
            finishAffinity()
        }
    }
}