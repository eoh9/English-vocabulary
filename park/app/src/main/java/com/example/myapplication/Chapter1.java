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

public class Chapter1 extends AppCompatActivity {
    //AppCompatActivity를 상속받는 QuizActivity 클래스를 구현.
    private TextView tvQuestion, tvScore, tvQuestionNo, tvTimer;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4;

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

    private Button btnNext;
    public void FinalPage(){//단어 시험이 끝나면 "수고하셨습니다"가 나오는 페이지로 넘어감
        btnNext=findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent intent = new Intent(Chapter1.this, FinalPage.class);
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
                        Toast.makeText(Chapter1.this, "Please Select an option", Toast.LENGTH_SHORT).show();
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
    private void addQuestions(){
        questionModelList.add(new QuestionModel("reserve", "달아나다", "위조하다", "예약하다", "소개하다", 3));
       questionModelList.add(new QuestionModel("dose", "표, 기호", "복용량, 양", "전망, 풍경", "경험", 2));
       questionModelList.add(new QuestionModel("reserve", "달아나다", "위조하다", "예약하다", "소개하다", 3));
       questionModelList.add(new QuestionModel("dose", "표, 기호", "복용량, 양", "전망, 풍경", "경험", 2));
       questionModelList.add(new QuestionModel("overseas", "수출하다", "해외로", "앞바다", "항구", 2));
       questionModelList.add(new QuestionModel("지연, 지체, 연기", "delay", "depart", "abroad", "describe", 1));
       questionModelList.add(new QuestionModel("출발하다, 떠나다", "prescribe", "departure", "postponement", "depart", 4));
       questionModelList.add(new QuestionModel("처방전, 처방된 약", "instruction", "direction", "prescription", "prescribe", 3));
       questionModelList.add(new QuestionModel("stir", "찾다", "(저어 가며)섞다, 휘젓다)", "녹이다", "부드럽게하다", 2));
       questionModelList.add(new QuestionModel("tenant", "노동자", "세입자", "관리인", "채무자", 2));
       questionModelList.add(new QuestionModel("dine", "식사를 하다", "세팅을 하다", "요리하다", "음식", 1));
       questionModelList.add(new QuestionModel("전기 콘센트, 배출구, 할인점", "outflow", "overflow", "outlet", "output", 3));
       questionModelList.add(new QuestionModel("피로하다", "fatigue", "occupant", "blend", "resident", 1));
       questionModelList.add(new QuestionModel("여행 일정표, 여정", "itinerary", "exhaust", "weary", "ascribe", 1));
       questionModelList.add(new QuestionModel("upcoming", "나오다", "위로 올라가다", "기대하다", "다가오는, 곧 있을", 4));
       questionModelList.add(new QuestionModel("attraction", "긴장감", "명소, 매력", "잡지", "마음을 끌다", 2));
       questionModelList.add(new QuestionModel("stroll", "산책하다", "굴리다", "여유롭다", "들이마시다", 1));
       questionModelList.add(new QuestionModel("입장, 입학, 인정", "admit", "amble", "administration", "admission", 4));
       questionModelList.add(new QuestionModel("설치하다, 설비하다", "indicator", "interest", "install", "installment", 3));
       questionModelList.add(new QuestionModel("약, 제약", "pharmaceution", "pharmaceutical", "pharmacy", "medical", 2));
       questionModelList.add(new QuestionModel("detour", "우회로", "좌회로", "여행하다", "산책하다", 1));
       questionModelList.add(new QuestionModel("landmark", "중심의", "주목할 만한", "주요 지형지물, 획기적인 사건", "건설하다", 3));
       questionModelList.add(new QuestionModel("disgust", "불행", "혐오감", "우울한", "무심한", 2));
       questionModelList.add(new QuestionModel("경이적인, 경탄할 만한", "loathing", "phenomenal", "hatred", "extrovert", 2));
       questionModelList.add(new QuestionModel("외향적인, 사교적인", "outgoing", "disgusting", "upcoming", "outcome", 1));
       questionModelList.add(new QuestionModel("미식가의, 고급의", "gourmet", "gourment", "goverment", "gorgeous", 1));
       questionModelList.add(new QuestionModel("nutrition", "영양실조", "생물", "미생물", "영양", 4));
       questionModelList.add(new QuestionModel("ornament", "자석", "장식품", "장롱", "모조품", 2));
       questionModelList.add(new QuestionModel("casual", "평상시의, 무심한", "위대한", "고급의, 미식가의", "격식을 차리는", 1));
       questionModelList.add(new QuestionModel("기념일", "universal", "celebrate", "anniversary", "annual", 3));
       questionModelList.add(new QuestionModel("진단, 분류, 식별", "dialog", "diagnostic", "diagnosis", "diagnose", 3));
       questionModelList.add(new QuestionModel("탑승한, (기차, 버스 등)에 타고", "jubilee", "abroad", "get in", "aboard", 4));
       questionModelList.add(new QuestionModel("사전의, ~전에", "prior", "precious", "absurd", "priority", 1));
       questionModelList.add(new QuestionModel("ridiculous", "진단의", "터무니없는, 웃기는", "조롱, 조소", "비웃다", 2));
       questionModelList.add(new QuestionModel("subscription", "자막", "구독하다", "자막을 보다", "구독, 기부금", 4));
       questionModelList.add(new QuestionModel("hospitality", "환대하는", "(병원의)접수절차", "환대, 접대", "(의료)시술", 3));
       questionModelList.add(new QuestionModel("용돈, 수당, (세금)공제액", "allowance", "allow", "pocket", "tax", 1));
       }
}
