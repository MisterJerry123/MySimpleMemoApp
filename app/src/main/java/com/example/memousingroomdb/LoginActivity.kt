package com.example.memousingroomdb

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.memousingroomdb.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private val TAG = "LoginActivity"
    private val auth = Firebase.auth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefs = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("isLoginUser",true).apply()

        binding.btnLogin.setOnClickListener {//로그인
            val id = binding.etId.text.toString()
            val pw = binding.etPw.text.toString()
            if (id.isEmpty() || pw.isEmpty()) {
                Toast.makeText(this, "아이디 또는 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                Log.d(TAG, "onCreate: id:$id, pw:$pw")
                auth.signInWithEmailAndPassword(id, pw).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "로그인 실패: 아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT)
                            .show()
                        Log.d(TAG, "${task.exception?.message}")

                    }
                }
            }
        }
        binding.btnSignup.setOnClickListener {//회원가입
            val id = binding.etId.text.toString()
            val pw = binding.etPw.text.toString()
            if (id.isEmpty() || pw.isEmpty()) {
                Toast.makeText(this, "아이디 또는 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(id, pw).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        when (task.exception?.message) {
                            "The email address is already in use by another account." -> {
                                Toast.makeText(this, "회원가입 실패: 이미 가입된 이메일입니다.", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            "The given password is invalid. [ Password should be at least 6 characters ]" -> {
                                Toast.makeText(
                                    this,
                                    "회원가입 실패: 비밀번호는 6자 이상이어야 합니다.",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            else -> {
                                Toast.makeText(this, "회원가입 실패: 기타 오류 발생", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                        Log.d(TAG, "${task.exception?.message}")
                    }
                }
            }
        }
        binding.tvWithoutSignup.setOnClickListener {
            //TODO 비회원 로직 구현예정
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            prefs.edit().putBoolean("isLoginUser",false).apply()
            startActivity(intent)
            finish()


        }
    }
}
