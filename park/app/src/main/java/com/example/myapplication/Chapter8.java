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

public class Chapter8 extends AppCompatActivity {
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
                Intent intent = new Intent(Chapter8.this, FinalPage.class);
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
                        Toast.makeText(Chapter8.this, "Please Select an option", Toast.LENGTH_SHORT).show();
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
        questionModelList.add(new QuestionModel("come across","발견하다","지나가다","우연히 만나다","마무리 짓다",3));
        questionModelList.add(new QuestionModel("strict","통제하다","엄격한","중단하다","틀림없는",2));
        questionModelList.add(new QuestionModel("multiple","연결망","많은, 다양한","독특한","드문",2));
        questionModelList.add(new QuestionModel("tailored","옷이 딱 맞는","임명 된","만기 된","무료의",1));
        questionModelList.add(new QuestionModel("tow","의견","발가락","불평","견인하다",4));
        questionModelList.add(new QuestionModel("난기류","lucid","turbulence","vicious","accentuate",2));
        questionModelList.add(new QuestionModel("bystander","행인","기다리다","외과의","바라보다",1));
        questionModelList.add(new QuestionModel("exquisite","파리","구류","부지","정교한",4));
        questionModelList.add(new QuestionModel("landscape","땅","망원경","풍경, 풍겨화","지도",3));
        questionModelList.add(new QuestionModel("pursue","까먹다","벌레","따라가다","애매하다",3));
        questionModelList.add(new QuestionModel("stumble","역경","발이 걸리다","번성하다, 번창하다","맞서다, 직면하다",2));
        questionModelList.add(new QuestionModel("bare","숨기다","벌거벗은, 있는 그대로의","심오한, 깊은","방해하다, 훼방을 놓다",2));
        questionModelList.add(new QuestionModel("placate","화를 달래다, 위로하다","달램, 위로","위로가 되는","화를 달래는",1));
        questionModelList.add(new QuestionModel("regret","섭섭해하다","후회하다","믿을 만한","다시 만나다",2));
        questionModelList.add(new QuestionModel("posture","인물의 위치", "자리","자세","찍다",3));
        questionModelList.add(new QuestionModel("blink","간과하다","놀라다","누르다","눈을 깜빡거리다",4));
        questionModelList.add(new QuestionModel("tremble","털다","(음식을) 덜다","흔들리다","돌리다",3));
        questionModelList.add(new QuestionModel("간과하다, 바라보다","erudite","overlook","delve","fragrant",2));
        questionModelList.add(new QuestionModel("유심히 보다, 응시하다, 또래","lag","scheme","peer","banquet",3));
        questionModelList.add(new QuestionModel("의사, 내과 의사","physician","orthopedist","obsterician","pediatrician",1));
        questionModelList.add(new QuestionModel("휴대용의","relinquish","pertinent","portable","disdain",3));
        questionModelList.add(new QuestionModel("~을 탓하다, 비난하다","blame","anarchy","oligarchy","hierarchy",1));
        questionModelList.add(new QuestionModel("초래하다","fallacy" ,"allot","unify","effect",4));
        questionModelList.add(new QuestionModel("잡티, 오점","flee","blemish","procure","copious",2));
        questionModelList.add(new QuestionModel("환기시키다, 통풍시키다","convene","summon","capricious","ventilate",4));
        questionModelList.add(new QuestionModel("감정이입,  공감","empathy","proponent","defy","emulate",1));
        questionModelList.add(new QuestionModel("강타, 충격","blew","bang","blow","blaw",3));
        questionModelList.add(new QuestionModel("솔직한, 간단한","straightforward","disparity", "impound","tempt",1));
        questionModelList.add(new QuestionModel("유혹하다, 부추기다","pseudonym","entice","secular","shred",2));
        questionModelList.add(new QuestionModel("작은, 소수의","minor","minus","litter","little",1));
        questionModelList.add(new QuestionModel("멀리하다, 떼어 놓다","blur","colossal","estrange","abort",3));
        questionModelList.add(new QuestionModel("엉망진창, 곤경","probe","mess","aboriginal","abstain",2));
        questionModelList.add(new QuestionModel("싸우다, 분투하다","ratify","heredity","struggle","abhor",3));
        questionModelList.add(new QuestionModel("minute","수익","대단히 작은, 섬세한","만료되다","소수의",2));
        questionModelList.add(new QuestionModel("neat","정돈된, 멋진","지저분한","나방","지옥",1));
        questionModelList.add(new QuestionModel("cater","케잌","요리사","자르다","음식을 제공하다",4));
        questionModelList.add(new QuestionModel("sore","짠","쓴","아픈","차가운",3));
        questionModelList.add(new QuestionModel("suburb","외부의","불가피한","근교","교회",3));
        questionModelList.add(new QuestionModel("난처하게 하다, 당혹하게 하다","enigma","cling","perplex","improvise",3));
        questionModelList.add(new QuestionModel("완고힌","stbborn","conpensate","query","sanguine",1));

    }

}
