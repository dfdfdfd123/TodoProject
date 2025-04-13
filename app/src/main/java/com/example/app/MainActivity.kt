package com.example.app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    // Fragment 변수 선언
    private lateinit var mainFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root) // ✅ binding.root 사용

        // Insets 설정
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Fragment 객체 할당 및 FrameLayout(container)에 추가
        mainFragment = MainFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, mainFragment)
            .commit()

        // saveButton 클릭 이벤트 설정
        binding.saveButton.setOnClickListener {
            saveToDo()
            Toast.makeText(applicationContext, "추가되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // 임시 저장 메서드 정의 (나중에 DB 기능과 연결 예정)
    private fun saveToDo() {
        // 나중에 구현할 부분
    }
}
