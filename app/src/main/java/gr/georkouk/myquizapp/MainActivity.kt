package gr.georkouk.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btStart: Button = findViewById(R.id.btStart)
        btStart.setOnClickListener {
            startClick()
        }
    }

    private fun startClick(){
        val etName: EditText = findViewById(R.id.etName)
        if(etName.text.isEmpty()){
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_LONG).show()
            return
        }

        val intent = Intent(this, QuizQuestionsActivity::class.java)
        intent.putExtra(Constants.USER_NAME, etName.text.toString())

        startActivity(intent)
        finish()
    }
}