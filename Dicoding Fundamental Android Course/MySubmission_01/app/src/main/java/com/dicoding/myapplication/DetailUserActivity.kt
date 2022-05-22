package com.dicoding.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.myapplication.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityDetailUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detail = intent.getParcelableExtra<User>(EXTRA_USER) as User

        binding.apply {
            tvName.text = detail.name
            tvUsername.text = StringBuilder("@"). append(detail.userName)
            tvFollowers.text = detail.followers
            tvFollowing.text = detail.following
            tvRepository.text = StringBuilder(" Repository • "). append(detail.repository)
            tvLocation.text = StringBuilder(" Location • "). append(detail.location)
            tvCompany.text = StringBuilder(" Company • "). append(detail.company)

            imgAvatar.setImageResource(detail.avatar)
        }
    }

//    findViewById(R.id.tvName)
}