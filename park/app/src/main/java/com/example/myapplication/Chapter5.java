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

public class Chapter5 extends AppCompatActivity {
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
                Intent intent = new Intent(Chapter5.this, FinalPage.class);
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
                        Toast.makeText(Chapter5.this, "Please Select an option", Toast.LENGTH_SHORT).show();
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
        questionModelList.add(new QuestionModel("auditorium", "청력", "객석, 대강의실", "오디션", "들을 수 있는", 2));
        questionModelList.add(new QuestionModel("belongings", "유지하다", "~에 속하다", "재산, 소유물", "소유하다", 3));
        questionModelList.add(new QuestionModel("destination", "목적지", "경유지", "도착하다", "경유하다", 1));
        questionModelList.add(new QuestionModel("cast", "서투르다", "의심을 불러일으키다", "~을 내쫓다", "(시선) 던지다, (그림자를) 드리우다", 4));
        questionModelList.add(new QuestionModel("clumsy", "어설픈, 다루기 힘든", "통통한", "귀여운, 어린", "표현하다", 1));
        questionModelList.add(new QuestionModel("weird", "징그러운", "특별한", "수상한, 기이한", "가녀린", 3));
        questionModelList.add(new QuestionModel("dramatic", "영광스러운", "극적인, 인상적인", "선정적인", "재밌는", 2));
        questionModelList.add(new QuestionModel("bypass", "돌아가다", "우회 도로", "우회전", "좌회전", 2));
        questionModelList.add(new QuestionModel("lately", "어색한", "뛰어가다", "뒤늦은", "최근에", 4));
        questionModelList.add(new QuestionModel("trim", "다듬다, 잘라내다", "트림을 하다", "터널을 지나다", "이용하다", 1));
        questionModelList.add(new QuestionModel("uniform", "일관적인, 균등한", "감격적인, 인상적인", "자각하는", "수집품", 1));
        questionModelList.add(new QuestionModel("crew", "항공사", "승무원, 팀", "제복, 교복", "통일하다", 2));
        questionModelList.add(new QuestionModel("forward", "앞으로 나아가다", "앞으로, 미래의", "앞당기다", "고대하다", 2));
        questionModelList.add(new QuestionModel("aware", "돈을 빌리다", "의식하는", "인식, 자각", "~을 알고 있는", 4));
        questionModelList.add(new QuestionModel("chill", "찬물을 끼얹다", "오싹하게 하다", "냉기, 오한", "으스스한", 3));
        questionModelList.add(new QuestionModel("procrastinate", "미룸", "미루다", "본성", "낡아서 해진", 2));
        questionModelList.add(new QuestionModel("shabby", "허름한, 누추한", "내쫓다", "비난하다", "간절히 필요한", 1));
        questionModelList.add(new QuestionModel("urgent", "긴급한", "긴급, 위기", "대담한", "굵은", 1));
        questionModelList.add(new QuestionModel("plumber", "수리하다", "막혀있다", "배관공", "수도관", 3));
        questionModelList.add(new QuestionModel("pros and cons", "~에 관한하여", "가위바위보", "찬반양론", "논의하다", 3));
        questionModelList.add(new QuestionModel("bold", "궁서체", "두려움", "대담하게", "대담한, 선명한", 4));
        questionModelList.add(new QuestionModel("stunning", "아름다운, 멋진", "기묘한, 이상한", "반짝이는", "빛을 내다", 1));
        questionModelList.add(new QuestionModel("tune", "소리를 켜다", "선율, 음", "조율", "수동적인", 2));
        questionModelList.add(new QuestionModel("형식을 따지지 않는, 편안한", "informal", "formal", "discussion", "inclination", 1));
        questionModelList.add(new QuestionModel("경사도, 기울기", "steeper", "gentle", "slope", "slippery", 3));
        questionModelList.add(new QuestionModel("움켜쥐다, 흥미를 주다", "resentment", "grievance", "grab", "bite", 3));
        questionModelList.add(new QuestionModel("원한, 유감", "against", "grudge", "stain", "smudge", 2));
        questionModelList.add(new QuestionModel("더러움, 얼룩, 떼", "smudge", "freak", "stainless", "tiny", 1));
        questionModelList.add(new QuestionModel("매혹하다", "private", "magnificent", "captivate", "captivating", 3));
        questionModelList.add(new QuestionModel("재채기하다", "therapy", "freezing", "sneeze", "etiquette", 3));
        questionModelList.add(new QuestionModel("아주 흔한, 진부한", "convoke", "commonplace", "common", "comfortable", 2));
        questionModelList.add(new QuestionModel("평온, 평정심", "provoke", "tranquil", "evoke", "tranquility", 4));
        questionModelList.add(new QuestionModel("번역하다, 바꾸다", "translate", "translation", "proverb", "change", 1));
        questionModelList.add(new QuestionModel("유발하다, 도발하다", "problem", "provoke", "provocative", "provocation", 2));
        questionModelList.add(new QuestionModel("세련된, 정제된", "refined", "fluent", "refine", "fined", 1));
        questionModelList.add(new QuestionModel("대충 훑어보다", "steal a glance", "glassic", "glance", "balance", 3));
        questionModelList.add(new QuestionModel("화려한, 멋진", "splendid", "splendor", "special", "spyder", 1));
        questionModelList.add(new QuestionModel("강박상태, 집착", "observation", "obsessed", "obvious", "obsession", 4));
        questionModelList.add(new QuestionModel("안내원, 관리인", "peep", "peer", "gourmet", "concierge", 4));
        questionModelList.add(new QuestionModel("잠깐 봄, 일견", "glare", "glimpse", "gaze", "hideous", 2));

    }
}