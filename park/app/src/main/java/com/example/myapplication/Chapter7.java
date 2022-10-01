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

public class Chapter7 extends AppCompatActivity {
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
                Intent intent = new Intent(Chapter7.this, FinalPage.class);
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
                        Toast.makeText(Chapter7.this, "Please Select an option", Toast.LENGTH_SHORT).show();
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
        questionModelList.add(new QuestionModel("row","열","향기","중고의","행", 1));
        questionModelList.add(new QuestionModel("scent","사인","호의적인","현장","향기",4));
        questionModelList.add(new QuestionModel("second-hand","따분한","중고의","단념시키다","획득하다", 2));
        questionModelList.add(new QuestionModel("dull","만류하다","첨벙거리다","재미없는","얻다", 3));
        questionModelList.add(new QuestionModel("explore","탐험하다","만류하다","획득하다","삭제하다", 1));
        questionModelList.add(new QuestionModel("favorable","사인","호의적인","노골적인","거만한", 2));
        questionModelList.add(new QuestionModel("splash","첨벙거리다","만류하다","입금하다","완화시키다", 1));
        questionModelList.add(new QuestionModel("autograph","장수","엄청난","식욕","사인", 4));
        questionModelList.add(new QuestionModel("dissuade","단념시키다","외부의","산만하게하다","대면하다", 1));
        questionModelList.add(new QuestionModel("적절한","fabulous","properness","proper","longevity", 3));
        questionModelList.add(new QuestionModel("외부의","longevity","exterior","appetite","occasion", 2));
        questionModelList.add(new QuestionModel("얻다","ripe","gain","litter","arrogant", 2));
        questionModelList.add(new QuestionModel("멋진,굉장한","fabulous","exhale","export","expect", 1));
        questionModelList.add(new QuestionModel("장수","anesthesia","longevity","arrogant","bruise", 2));
        questionModelList.add(new QuestionModel("mindful","경우","마취","의식하는","산만하게 하다", 3));
        questionModelList.add(new QuestionModel("ripe","숙성한","성숙한","모호한","곤경", 1));
        questionModelList.add(new QuestionModel("novice","풀린","우울한","초보자","꼼곰한", 3));
        questionModelList.add(new QuestionModel("outspoken","솔직한","거만한","사소한","소심한", 1));
        questionModelList.add(new QuestionModel("anesthesia","마취","안개","공황","자세", 1));
        questionModelList.add(new QuestionModel("appetite","자세","식욕","식전","공감", 2));
        questionModelList.add(new QuestionModel("occasion","타박상","휴대용의","상황","주소", 3));
        questionModelList.add(new QuestionModel("appointment","약속","임명하다","사과하다","사과", 1));
        questionModelList.add(new QuestionModel("dwell on","~을 곱씹다","혼란시키다","~에 의존하다","괴롭히다", 1));
        questionModelList.add(new QuestionModel("seldom","드물게","썩은","자주","계좌", 1));
        questionModelList.add(new QuestionModel("쓰레기, 어지러져 있는 것","distract","litter","regard","specify", 2));
        questionModelList.add(new QuestionModel("거만한","arrogant","strict","tailored","dubious", 1));
        questionModelList.add(new QuestionModel("혼란시키다","drawback","distract","apparent","petition", 2));
        questionModelList.add(new QuestionModel("결점, 철수", "drawback","remorse","blush","mature", 1));
        questionModelList.add(new QuestionModel("채식주의자","vegetarian","bruise","mature","multiple", 1));
        questionModelList.add(new QuestionModel("얼굴을 붉히다","blow","bother","blush","advisory", 3));
        questionModelList.add(new QuestionModel("수상쩍은, 모호한","dubious","reimburse","monetary","detest", 1));
        questionModelList.add(new QuestionModel("걸작","masterpiece","minorpiece","mature","bruise", 1));
        questionModelList.add(new QuestionModel("멍","bruise","bruice","bluise","bluice", 1));
        questionModelList.add(new QuestionModel("mature","성숙한","호흡의","양육하다","중간의", 1));
        questionModelList.add(new QuestionModel("boundary","썩은","수량","경계선","맞춤의", 3));
        questionModelList.add(new QuestionModel("martial","긁다","당뇨","정착","결혼의", 4));
        questionModelList.add(new QuestionModel("rotten","썩은","익은","슬픔","완고한", 1));
        questionModelList.add(new QuestionModel("scratch","섭취","긁다","양육하다","취약한", 2));
        questionModelList.add(new QuestionModel("bring up","예비용의","거래","질책하다","양육하다", 4));




    }

}
