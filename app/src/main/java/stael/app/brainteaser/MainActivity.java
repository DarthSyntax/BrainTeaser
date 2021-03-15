package stael.app.brainteaser;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int left;
    int right;
    int answer;
    double correct = 0;
    double numQuestions = 0;
    TextView timer;
    TextView score;
    TextView problem;
    Random rand;
    CountDownTimer countTimer;
    Button[] btns;
    Button play;

    public void play(View view) {
        play = (Button) findViewById(R.id.play);
        correct = 0;
        numQuestions = 0;
        score.setText((int) correct + "/" + (int) numQuestions);
        countTimer.start();
        for(int j = 0; j < btns.length; j++) {
            btns[j].setClickable(true);
        }
        play.setVisibility(View.INVISIBLE);
        play.setClickable(false);
    }

    public void chooseProblem() {
        rand = new Random();
        int choose = rand.nextInt(4);
        left = rand.nextInt(22);
        right = rand.nextInt(22);
        answer = left+right;
        problem.setText(left + " + " + right);
        btns[choose].setText(answer + "");
        for(int i = 0; i < btns.length; i++){
            if(i == choose)
                continue;
            int wrongAnswers = rand.nextInt(49);
            btns[i].setText(wrongAnswers + "");
        }
    }

    public void check(View view) {
        Button btn = (Button) view;
        if(Integer.parseInt(btn.getText().toString()) == answer) {
            score.setText((int) ++correct + "/" + (int) ++numQuestions);
        } else if(Integer.parseInt(btn.getText().toString()) != answer) {
            score.setText((int) correct + "/" + (int) ++numQuestions);
        }
        if(correct/numQuestions >= .5) {
            score.setBackgroundColor(Color.GREEN);
        } else if(correct/numQuestions < .5) {
            score.setBackgroundColor(Color.RED);
        }
        chooseProblem();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = (TextView) findViewById(R.id.timer);
        score = (TextView) findViewById(R.id.score);
        problem = (TextView) findViewById(R.id.problem);
        timer.setBackgroundColor(Color.CYAN);
        score.setBackgroundColor(Color.GREEN);
        btns = new Button[4];
        btns[0] = (Button) findViewById(R.id.button1);
        btns[1] = (Button) findViewById(R.id.button2);
        btns[2] = (Button) findViewById(R.id.button3);
        btns[3] = (Button) findViewById(R.id.button4);
        for(int j = 0; j < btns.length; j++) {
            btns[j].setClickable(false);
        }
        chooseProblem();
        final int timeLimit = 30000;
        timer.setText(timeLimit/1000 + "s");

        countTimer = new CountDownTimer(timeLimit, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText((int) millisUntilFinished/1000 + "s");
            }

            @Override
            public void onFinish() {
                for(int j = 0; j < btns.length; j++) {
                    btns[j].setClickable(false);
                }
                timer.setText("0s");
                Toast.makeText(MainActivity.this, "Your final score is " + (correct/numQuestions)*100 + "% with " + (int) correct + " points", Toast.LENGTH_LONG).show();
                play.setClickable(true);
                play.setVisibility(View.VISIBLE);
            }
        };

    }
}
