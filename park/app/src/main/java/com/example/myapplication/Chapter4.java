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

public class Chapter4 extends AppCompatActivity {
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
                Intent intent = new Intent(Chapter4.this, FinalPage.class);
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
                        Toast.makeText(Chapter4.this, "Please Select an option", Toast.LENGTH_SHORT).show();
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
        questionModelList.add(new QuestionModel("rust","녹","단지","수분","달리다",1));
        questionModelList.add(new QuestionModel("scan","살피다, 훑어보다","조사하다","노력하다","입장",1));
        questionModelList.add(new QuestionModel("entry","가입비, 입회비","입문의, 초보의","문","입장, 가입",4));
        questionModelList.add(new QuestionModel("remind","마음","뒤 돌아보다","비치되다","상기시키다",4));
        questionModelList.add(new QuestionModel("flat","평평한, 김빠진","돌아다니다","아군","넙치류",1));
        questionModelList.add(new QuestionModel("expiration","설명","만기, 숨을 내쉼","종료","기만하다",  2));
        questionModelList.add(new QuestionModel("precious","이전의","정확한","귀중한, 소중한","선행하다",3));
        questionModelList.add(new QuestionModel("furnished","얼룩진","떼, 무리","둘러싸다","가구가 비치된",4));
        questionModelList.add(new QuestionModel("convenience","편의점","편리한","이득","편리, 편의시설",4));
        questionModelList.add(new QuestionModel("ingredient","정부","재료, 구성 요소","경사도","수술, 작동",2));
        questionModelList.add(new QuestionModel("require","요구하다","찾아가다","묻다","둘러보다",1));
        questionModelList.add(new QuestionModel("구체적인,특정한","specify","specificity","special","specific",4));
        questionModelList.add(new QuestionModel("등록하다","lean","adorn","enroll","enrollment",3));
        questionModelList.add(new QuestionModel("희미한","faint","faintish","fiant","flant",1));
        questionModelList.add(new QuestionModel("망치다, 성격을 버리다","spoilage","spoiling","spoilable","spoil",4));
        questionModelList.add(new QuestionModel("무상한, 순식간의","long_lasting","prior","fleeting","inhale",3));
        questionModelList.add(new QuestionModel("싹이 나다, 생기다","perpetual","sprout","arbitrary","instantaneous",2));
        questionModelList.add(new QuestionModel("괴롭히다","insidious","dilute","torment","nebulous",3));
        questionModelList.add(new QuestionModel("보수적인, 전통적인","conservative","radical","prosecute","gross",1));
        questionModelList.add(new QuestionModel("아첨하다, 돋보이게 하다","flater","flature","flattar","fletter",3));
        questionModelList.add(new QuestionModel("운 좋은, 행운을 가져다주는","fortune","lucky","fortunately","fortunate",4));
        questionModelList.add(new QuestionModel("staggering","믿기 어려운, 비틀거리는","싸우다","불평하다","넘어지다",1));
        questionModelList.add(new QuestionModel("timid","소심한","소심함","소심하다","소심",1));
        questionModelList.add(new QuestionModel("dairy","유제품","일기","하루의","더러운",1));
        questionModelList.add(new QuestionModel("grumble","전쟁","반란자","투덜대다","대포",3));
        questionModelList.add(new QuestionModel("haphazard","부실","무계획적인","고갈된","위험한",2));
        questionModelList.add(new QuestionModel("damp","폐지하다","번성하다","눅눅한, 축축한","폭락하다",3));
        questionModelList.add(new QuestionModel("utter","완전한, 말을 하다","치명적인","변하다, 고치다", "넣다, 싣다",1));
        questionModelList.add(new QuestionModel("valuable","가치","가치가 큰","가치 없는","유용한",2));
        questionModelList.add(new QuestionModel("deadly","위험한, 모험적인","치명적인, 따분한","버릇없는, 무례한", "닳아 해진, 써서 낡은",2));
        questionModelList.add(new QuestionModel("위험한","allude","hazardous","vein","hanuted",2));
        questionModelList.add(new QuestionModel("10년간","nonagon","hexagon","decade","heptachord",3));
        questionModelList.add(new QuestionModel("아주 좋은, 멋진","vein","vain","congenial","terrific",4));
        questionModelList.add(new QuestionModel("부재, 없음","gem","absence","greet","wither",2));
        questionModelList.add(new QuestionModel("반복하다, 되풀이하다","reiterate","restate","restore","reflect",1));
        questionModelList.add(new QuestionModel("갈고리, 유혹하다","fraud","hook","levy","stow",2));
        questionModelList.add(new QuestionModel("자원 봉사","voluntary","involuntary","volunteer","voluntarily",3));
        questionModelList.add(new QuestionModel("청소년기, 사춘기","adolescence","vendor","jet lag","vow",1));
        questionModelList.add(new QuestionModel("끈질긴, 냉혹한","evade","relentless","ailing","lure",2));
        questionModelList.add(new QuestionModel("연상시키는","alloy","divulge","reminiscent","fortify",2));

    }
}