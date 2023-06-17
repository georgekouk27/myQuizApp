package gr.georkouk.myquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), OnClickListener {

    private var progressBar: ProgressBar? = null
    private var tvProgress: TextView? = null
    private var tvQuestion: TextView? = null
    private var ivImage: ImageView? = null
    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionFour: TextView? = null
    private var btSubmit: Button? = null
    private var currentPosition = 1
    private var questions: ArrayList<Question>? = null
    private var selectedOptionPosition: Int = 0
    private var userName: String? = null
    private var correctAnswers: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        userName = intent.getStringExtra(Constants.USER_NAME)

        initialize()

        questions = Constants.getQuestions()

        setQuestion()

    }

    private fun setQuestion() {
        defaultOptionsView()

        val tmpQuestion = questions!!.get((currentPosition - 1))
        tvQuestion?.text = tmpQuestion.question
        progressBar?.progress = currentPosition
        tvProgress?.text = "$currentPosition" + "/" + progressBar?.max
        tvOptionOne?.text = tmpQuestion.optionOne
        tvOptionTwo?.text = tmpQuestion.optionTwo
        tvOptionThree?.text = tmpQuestion.optionThree
        tvOptionFour?.text = tmpQuestion.optionFour
        ivImage?.setImageResource(tmpQuestion.image)

        btSubmit?.text = if (currentPosition == questions!!.size) "FINISH" else "SUBMIT"
    }

    private fun initialize(){
        progressBar = findViewById(R.id.progressbar)
        tvProgress = findViewById(R.id.tvProgress)
        tvQuestion = findViewById(R.id.tvQuestion)
        ivImage = findViewById(R.id.ivImage)
        tvOptionOne = findViewById(R.id.tvOptionOne)
        tvOptionTwo = findViewById(R.id.tvOptionTwo)
        tvOptionThree = findViewById(R.id.tvOptionThree)
        tvOptionFour = findViewById(R.id.tvOptionFour)
        btSubmit = findViewById(R.id.btSubmit)

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btSubmit?.setOnClickListener(this)
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()

        tvOptionOne?.let {
            options.add(0, it)
        }

        tvOptionTwo?.let {
            options.add(1, it)
        }

        tvOptionThree?.let {
            options.add(2, it)
        }

        tvOptionFour?.let {
            options.add(3, it)
        }

        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptonNum: Int){
        defaultOptionsView()

        selectedOptionPosition = selectedOptonNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tvOptionOne -> {
                tvOptionOne?.let {
                    selectedOptionView(it, 1)
                }
            }

            R.id.tvOptionTwo -> {
                tvOptionTwo?.let {
                    selectedOptionView(it, 2)
                }
            }

            R.id.tvOptionThree -> {
                tvOptionThree?.let {
                    selectedOptionView(it, 3)
                }
            }

            R.id.tvOptionFour -> {
                tvOptionFour?.let {
                    selectedOptionView(it, 4)
                }
            }

            R.id.btSubmit -> {
                if(selectedOptionPosition == 0){
                    currentPosition++

                    when{
                        currentPosition <= questions!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, userName)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, questions?.size)
                            intent.putExtra(Constants.CORRECT_ANSWERS, correctAnswers)

                            startActivity(intent)
                            finish()
                        }
                    }
                }
                else{
                    val question = questions?.get(currentPosition - 1)
                    if(question!!.correctAnswer != selectedOptionPosition){
                        answerView(selectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    else{
                        correctAnswers++
                    }

                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if(currentPosition == questions!!.size){
                        btSubmit?.text = "FINISH"
                    }
                    else{
                        btSubmit?.text = "GO TO NEXT QUESTION"
                    }

                    selectedOptionPosition = 0;
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int){
        when(answer){
            1 -> {
                tvOptionOne?.background = ContextCompat.getDrawable(this, drawableView)
            }

            2 -> {
                tvOptionTwo?.background = ContextCompat.getDrawable(this, drawableView)
            }

            3 -> {
                tvOptionThree?.background = ContextCompat.getDrawable(this, drawableView)
            }

            4 -> {
                tvOptionFour?.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }

}