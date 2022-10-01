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

public class Chapter3 extends AppCompatActivity {
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
                Intent intent = new Intent(Chapter3.this, FinalPage.class);
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
                        Toast.makeText(Chapter3.this, "Please Select an option", Toast.LENGTH_SHORT).show();
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
        questionModelList.add(new QuestionModel("informative", "알리다","통치하다","유익한","일시적인", 3));
        questionModelList.add(new QuestionModel("blackout", "탄탄한", "교차점", "정전", "고체", 3));
        questionModelList.add(new QuestionModel("solid", "탄탄한, 고체의", "양념", "유익한", "모욕적인", 1));
        questionModelList.add(new QuestionModel("intersection", "단순한", "교차점", "재검토", "출판", 2));
        questionModelList.add(new QuestionModel("contact", "연락", "출판", "양념", "떠맡다", 1));
        questionModelList.add(new QuestionModel("offensive", "잘 들리는", "모욕적인", "견디다","완전히", 2));
        questionModelList.add(new QuestionModel("publication", "늦은", "출판", "우회도로", "갈고리", 2));
        questionModelList.add(new QuestionModel("audible", "애정", "대단히", "움직이는", "잘 들리는", 4));
        questionModelList.add(new QuestionModel("bear", "참다", "방해하다", "간지럽히다", "예방하다", 1));
        questionModelList.add(new QuestionModel("spice", "약간의", "내용", "참사", "양념", 4));
        questionModelList.add(new QuestionModel("칸막이, 객실", "courteous", "keen", "compartment", "mock", 3));
        questionModelList.add(new QuestionModel("경유", "layover", "soothe", "concierge", "stagnation", 1));
        questionModelList.add(new QuestionModel("방해하다", "restrict", "warrant", "recede", "disturb", 4));
        questionModelList.add(new QuestionModel("달래다, 진정시키다", "mingle", "soothe", "recede","disturb", 2));
        questionModelList.add(new QuestionModel("조롱하다", "mock", "precise", "mockery", "modification", 1));
        questionModelList.add(new QuestionModel("공손한", "potent", "courteous", "anarchy", "courtage", 2));
        questionModelList.add(new QuestionModel("열망하는", "disperse", "testify", "keen", "kean", 3));
        questionModelList.add(new QuestionModel("단조로운", "monotonous", "inhale", "alike", "necessity", 1));
        questionModelList.add(new QuestionModel("필요","innocent", "necessity","grooss", "compliment", 2));
        questionModelList.add(new QuestionModel("ache","풍경", "가려움","아픔", "귀중한",3));
        questionModelList.add(new QuestionModel("alike","평가하다", "좋아하는","하향의","비슷한", 4));
        questionModelList.add(new QuestionModel("inhale","흡입하다", "굳히다","막다", "다양화하다", 1));
        questionModelList.add(new QuestionModel("adorn","얼룩","무모한","꾸미다","짜다", 3));
        questionModelList.add(new QuestionModel("irritate","거슬리다","어색한","추정하다","평가하다", 1));
        questionModelList.add(new QuestionModel("leap","감사하는","도약","입금","내용", 2));
        questionModelList.add(new QuestionModel("squeeze","짜다","오징어","참사","완화시키다", 1));
        questionModelList.add(new QuestionModel("awkward","배상하다","당대의","청소년의","어색한", 4));
        questionModelList.add(new QuestionModel("stain","얼룩","위험한","위원회","속도", 1));
        questionModelList.add(new QuestionModel("reckless","매력적인","무모한","주장하다","폐지하는", 2));
        questionModelList.add(new QuestionModel("떨쳐버리다","belated","fare","dispel","trim", 3));
        questionModelList.add(new QuestionModel("재앙","disaster","scratch","reimburse","invoice", 1));
        questionModelList.add(new QuestionModel("요금","fare","fair","fale","feal", 1));
        questionModelList.add(new QuestionModel("늦은","belated","default","confer","mundane", 1));
        questionModelList.add(new QuestionModel("완전히","complete","altogether","admonish","dismal", 2));
        questionModelList.add(new QuestionModel("굽히다","bend","discard","grateful","conditional", 1));
        questionModelList.add(new QuestionModel("조심하다","beware","aware","ware","wear", 1));
        questionModelList.add(new QuestionModel("좌절시키다","agitate","disappoint","extravagant","immune", 2));
        questionModelList.add(new QuestionModel("불안하게하다, 주장하다","settle","agitate","enforce","surmount", 2));
        questionModelList.add(new QuestionModel("낭비하는","chill","extravagant","obtain","antagonist", 2));
        questionModelList.add(new QuestionModel("무고한,순진한","innocent","distract","enclose","curb", 1));


    }

}
