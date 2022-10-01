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

public class Chapter2 extends AppCompatActivity {
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


    public void FinalPage(){//단어 시험이 끝나면 "수고하셨습니다"가 나오는 페이지로 넘어감
        btnNext=findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent intent = new Intent(Chapter2.this, FinalPage.class);
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
                        Toast.makeText(Chapter2.this, "Please Select an option", Toast.LENGTH_SHORT).show();
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
            FinalPage();//FinalPage 함수를 불러 마지막 페이지로 넘어감

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
        questionModelList.add(new QuestionModel("alumni", "졸업생", "홍수", "기호", "취소하다", 1));
        questionModelList.add(new QuestionModel("prestigious", "명망있는", "실패", "교수", "학위", 1));
        questionModelList.add(new QuestionModel("comfort", "고치다", "주의를주다", "안락", "화를내다", 3));
        questionModelList.add(new QuestionModel("robust", "튼튼한", "아픈", "위로", "눕다", 1));
        questionModelList.add(new QuestionModel("amuse", "즐겁게", "덮다", "시적인", "가난한", 1));
        questionModelList.add(new QuestionModel("weep", "즐겁다", "위로하다", "입다", "울다", 4));
        questionModelList.add(new QuestionModel("골동품인", "antique", "beverage", "trim", "gadget", 1));
        questionModelList.add(new QuestionModel("마주치다", "apparent", "erode", "commute", "encounter", 4));
        questionModelList.add(new QuestionModel("음료", "vestige", "beverage", "entail", "migle", 2));
        questionModelList.add(new QuestionModel("route", "길", "수학", "승강기", "옷장", 1));
        questionModelList.add(new QuestionModel("bill", "가난하다", "구멍", "계산서", "모으다", 3));
        questionModelList.add(new QuestionModel("gather", "모이다", "옷을입다", "t쓰다", "버리다", 1));
        questionModelList.add(new QuestionModel("freezing", "영하의", "꺠뜨리다", "소름끼치다", "터지다", 1));
        questionModelList.add(new QuestionModel("의류", "chilly", "scorching", "apparel", "frosty", 3));
        questionModelList.add(new QuestionModel("rough", "풍경의", "평화로운", "위로를 하다", "힘든", 4));
        questionModelList.add(new QuestionModel("scenic", "장난하는", "그림그리다", "풍경의", "훌륭한", 3));
        questionModelList.add(new QuestionModel("artifact", "공예품", "증상", "건물", "빛", 1));
        questionModelList.add(new QuestionModel("증상", "symptom", "deadly", "impede", "drench", 1));
        questionModelList.add(new QuestionModel("sculpture", "팔찌", "조각", "칠판", "안경", 2));
        questionModelList.add(new QuestionModel("tease", "장난하다", "배우다", "장을보다", "낮잠을자다", 1));
        questionModelList.add(new QuestionModel("brilliant", "멍청한", "게으름이많은", "멋진", "잔머리가많은", 3));
        questionModelList.add(new QuestionModel("bully", "괴롭히다", "밥을먹다", "화가나다", "잠이오다", 1));
        questionModelList.add(new QuestionModel("addicted", "중독된", "교육하는", "추가하는", "수동적인", 1));
        questionModelList.add(new QuestionModel("수동적인", "passive", "beverage", "trim", "gadget", 1));
        questionModelList.add(new QuestionModel("float", "열망하다", "박수를치다", "뜨다", "염원하다", 3));
        questionModelList.add(new QuestionModel("applaud", "갈채를보내다", "열망하다", "땀을흘리다", "충돌하다", 1));
        questionModelList.add(new QuestionModel("aspire", "열망하다", "편리하다", "생산하다", "꿈을꾸다", 1));
        questionModelList.add(new QuestionModel("perspiration", "진열", "접시", "교수", "땀", 4));
        questionModelList.add(new QuestionModel("사적인", "private", "slight", "passionate", "innocent", 1));
        questionModelList.add(new QuestionModel("치료", "therapy", "ground", "rate", "reimburse", 1));
        questionModelList.add(new QuestionModel("outlook", "치료", "약간의", "충돌하다", "견해", 4));
        questionModelList.add(new QuestionModel("열정", "tiny", "passion", "individual", "account", 2));
        questionModelList.add(new QuestionModel("slight", "약간의", "얻다", "배상하다", "찌르다", 1));
        questionModelList.add(new QuestionModel("bump", "부딪침", "미세한", "안전함", "신중한", 1));
        questionModelList.add(new QuestionModel("유창한", "effective", "fluent", "organize", "collide", 2));
        questionModelList.add(new QuestionModel("협박,위험", "threat", "forbid", "depoly", "tumult", 1));
        questionModelList.add(new QuestionModel("tolerant", "관대한", "거대한", "사랑스러운", "사라지다", 1));
        questionModelList.add(new QuestionModel("organize", "조직하다", "금지하다", "꺠뜨리다", "없어지다", 1));
        questionModelList.add(new QuestionModel("금지하다", "forebid", "forbid", "divide", "porebid", 2));
        questionModelList.add(new QuestionModel("명백한,소박한", "plein", "one", "plain", "plight", 3));
    }
}