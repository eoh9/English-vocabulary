package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;//Button 클래스를 가져옴.
import android.widget.RadioButton;//RadioButton 클래스를 가져옴.
import android.widget.RadioGroup;//RadioGroup 클래스를 가져옴.
import android.widget.TextView;//TextView 클래스를 가져옴
import android.widget.Toast;//Toast 클래스를 가져옴.

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chapter6 extends AppCompatActivity {
    //AppCompatActivity를 상속받는 QuizActivity 클래스를 구현.
    private TextView tvQuestion, tvScore, tvQuestionNo, tvTimer;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button btnNext;
    //private으로 테스트 내부 기능(TextView, RadioGroup, RadioButton, Button)을 생성.

    int totalQuestions;
    int qCounter = 0;
    int score;
    //정수형 변수들을 생성.

    ColorStateList dfRbColor;
    boolean answered;

    CountDownTimer countDownTimer;
    //Timer함수를 쓰기 위한, CountDownTimer를 생성.
    private QuestionModel currentQuestion;
    private List<QuestionModel> questionModelList;


    public void FinalPage(){
        btnNext=findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent intent = new Intent(Chapter6.this, FinalPage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate(), Create Table 명령으로 테이블을 생성
        super.onCreate(savedInstanceState);//savedInstanceState를 임의로 호출.

        setContentView(R.layout.activity_quiz);//activity_quiz.xml에 만든 레이아웃이 출력될 수 있도록 불러옴.


        tvQuestion = findViewById(R.id.textQuestion);
        tvScore = findViewById(R.id.textScore);
        tvQuestionNo = findViewById(R.id.textQuestionNo);
        tvTimer = findViewById(R.id.textTimer);
        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        btnNext = findViewById(R.id.btnNext);
        //activity_quiz.xml에서 TextView타입으로 캐스팅 한 변수들을 불러옴.

        questionModelList = new ArrayList<>();
        //questionModelList를 새로운 ArrayList<>()d로 선언.

        dfRbColor = rb1.getTextColors();
        //dfRbCOlot를 rb1에 지정된 색으로 가져오기.

        addQuestions();
        totalQuestions = questionModelList.size();
        showNextQuestion();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answered == false) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        checkAnswer();//만약 rb1 ~ rb4(단어 보기 옵션들)중 하나가 체크되었을 경우, Answer(정답)으로 인식.
                        countDownTimer.cancel();//진행중인 타이머를 초기화.
                    } else {
                        Toast.makeText(Chapter6.this, "Please Select an option", Toast.LENGTH_SHORT).show();
                        //버튼을 누르지 않고 넘어갈 경우 Toast 메세지를("Please Select an option") 짧게 띄움.
                    }
                } else {
                    showNextQuestion();//다음 테스트 문제로 넘어감.
                }
            }
        });
    }

    private void checkAnswer() {
        answered = true;//Answer은 true로 지정.
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo = radioGroup.indexOfChild(rbSelected) + 1;
        if (answerNo == currentQuestion.getCorrectAnsNo()) {
            score++;//만약, answerNo(선택한 단어)가 CorrectAnsNo(정답인 단어)와 같다면 score(점수)는 +1씩 저장됨.
            tvScore.setText("Score: " + score);//화면으로  맞은 개수를 함께 보여줌.
        }
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);
        //기본적으로 모든 보기의 단어를 빨간색으로 설정.
        switch (currentQuestion.getCorrectAnsNo()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                break;
        }//switch문을 이용해서 맞은 단어의 색은 초록색으로 표시.
        if (qCounter < totalQuestions) {
            btnNext.setText("NEXT");//만약 qCounter가 total Question의 수보다 작으면, 다음 문제로 넘어갈 수 있게 'NEXT'문을 생성.
        } else {
            btnNext.setText("FINISH");//만약 qCounter가 total Question의 수와 일치하면, 테스트를 끝낼 수 있게 'FINISH'문을 생성.
            FinalPage();//FInISH

        }
    }

    private void showNextQuestion() {//다음 페이지로 넘어갈 함수 구현.




        radioGroup.clearCheck();
        rb1.setTextColor(dfRbColor);
        rb2.setTextColor(dfRbColor);
        rb3.setTextColor(dfRbColor);
        rb4.setTextColor(dfRbColor);

        if (qCounter < totalQuestions) {
            timer();
            //timer를 불러옴.
            currentQuestion = questionModelList.get(qCounter);
            tvQuestion.setText(currentQuestion.getQuestion());

            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            qCounter++;
            btnNext.setText("Submit");//버튼을 누른 후, 버튼에 'Submit'을 불러오게 함.

            tvQuestionNo.setText("Question: " + qCounter + "/" + totalQuestions);//qCounter(푼 문제 수)와 totalQuestions(전체 문제 수)를 보여줌.
            answered = false;
        } else {
            finish();
        }

    }


    private void timer() {
        countDownTimer = new CountDownTimer(15000, 1000) {
            //첫번째 파라미터는 제한시간을 뜻하며 15000(15초)동안만 작동을 하도록 설정.
            // 두번째 파라미터는 몇초마다 타이머가 작동할것인지를 표시하며, 1초단위로 타이머가 작동되도록 설정.

            @Override
            //추상 클래스로 onTick() 함수와 onFinish() 함수를 오버라이딩함
            public void onTick(long millisUntilFinished) {
                //onTick() 함수로 생성자 인수 millisUntilFinished로 지정된 시간 간격마다 호출되는 함수를 구현.

                int num = (int) (millisUntilFinished / 1000);//num을 millisUntilFinished에서 1000을 나눈 값으로 부터 시작.
                tvTimer.setText("00:" + Integer.toString(num + 1));//num + 1부터 다운 카운트 실시.
            }

            @Override
            public void onFinish() {
                showNextQuestion();//다음문제로 넘어갈 함수를 불러옴.

            }
        }.start();
    }
    private void addQuestions() {
        questionModelList.add(new QuestionModel("gadget", "장례식", "도구", "귀찮게하다", "이익", 1));
        questionModelList.add(new QuestionModel("allergic", "짜증", "알레르기상의", "엄격한", "후세", 2));
        questionModelList.add(new QuestionModel("annoy", "귀찮게하다", "구매", "유지하다", "이루다", 1));
        questionModelList.add(new QuestionModel("impulsive", "충동적인", "이민가다", "고발하다", "일어서다", 1));
        questionModelList.add(new QuestionModel("immigration", "이민", "지우다", "증오하다", "기소하다", 1));
        questionModelList.add(new QuestionModel("accuse", "떄리다", "고발하다", "쓰러지다", "두통을호소하다", 2));
        questionModelList.add(new QuestionModel("direction", "방향", "정신없는", "숙지하다", "동일한", 1));
        questionModelList.add(new QuestionModel("hectic", "흥분한", "행복한", "익히다", "반대하는", 1));
        questionModelList.add(new QuestionModel("impress", "감동을주다", "똑같다", "계산하다", "청혼을하다", 1));
        questionModelList.add(new QuestionModel("acquaint", "쇠퇴하다", "굽다", "조심하다", "익히다", 4));
        questionModelList.add(new QuestionModel("hostile", "반대로", "전하다", "적대적인", "터지다", 3));
        questionModelList.add(new QuestionModel("동일한", "identical", "identies", "entity", "entitle", 1));
        questionModelList.add(new QuestionModel("여명", "twist", "twilight", "wight", "whole", 2));
        questionModelList.add(new QuestionModel("snatch", "잡아채다", "뽑다", "구멍을내다", "소리를내다", 1));
        questionModelList.add(new QuestionModel("sobriety", "맨정신", "경계", "영감", "빛", 1));
        questionModelList.add(new QuestionModel("incredible", "믿다", "놀라운", "속이다", "경계", 2));
        questionModelList.add(new QuestionModel("wary", "경계하는", "놀라운", "훌륭한", "신경쓰는", 1));
        questionModelList.add(new QuestionModel("melancholy", "영감", "우울한", "지능의", "험한", 2));
        questionModelList.add(new QuestionModel("inspiration", "격려", "방언", "파괴하다", "잠이오다", 1));
        questionModelList.add(new QuestionModel("accidental", "험한", "우연한", "사투리", "훼손하다", 2));
        questionModelList.add(new QuestionModel("dialect", "방언", "성향", "비난", "궂은", 1));
        questionModelList.add(new QuestionModel("destroy", "손해보다", "훼손하다", "파티를하다", "사다", 2));
        questionModelList.add(new QuestionModel("intellectual", "무정한", "지능의", "분별있는", "분리되는", 2));
        questionModelList.add(new QuestionModel("험한", "inclement", "inpulse", "imune", "incilent", 1));
        questionModelList.add(new QuestionModel("sensible", "진로", "이지적인", "분리하는", "분별있는", 4));
        questionModelList.add(new QuestionModel("치료", "toast", "teach", "mentalist", "treatment", 4));
        questionModelList.add(new QuestionModel("dietary", "음식물의", "운동하다", "요추", "강인하다", 1));
        questionModelList.add(new QuestionModel("await", "거리", "대출하다", "대기하다", "번호순", 3));
        questionModelList.add(new QuestionModel("심한", "dire", "top", "delete", "wipe", 1));
        questionModelList.add(new QuestionModel("avenue", "거리", "애정", "대기", "대우", 1));
        questionModelList.add(new QuestionModel("애정", "effect", "affection", "adopt", "toast", 2));
        questionModelList.add(new QuestionModel("산산이부서지다", "shatter", "float", "organize", "chip", 1));
        questionModelList.add(new QuestionModel("idyllic", "목가적인", "허용하다", "홀짝이다", "묵살하다", 1));
        questionModelList.add(new QuestionModel("allow", "거대한", "허락하다", "사라지다", "마시다", 2));
        questionModelList.add(new QuestionModel("ignore", "묵살하다", "꺠뜨리다", "홀짝이다", "전원풍의", 1));
        questionModelList.add(new QuestionModel("sip", "조금씩마시다", "대기하다", "취하다", "아프다", 1));
        questionModelList.add(new QuestionModel("측면", "aspect", "espect", "execption", "orient", 2));
        questionModelList.add(new QuestionModel("골동품인", "antique", "beverage", "trim", "gadget", 1));

    }

}
