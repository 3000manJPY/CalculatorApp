package com.example.tsukasa.departmentandroidapp1;

import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.icu.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    // 入力中の値や計算結果を格納する変数
    private BigDecimal mCalcValue = BigDecimal.ZERO;
    // オペレータを押す前に入力された値を格納する変数
    private BigDecimal mPreValue = BigDecimal.ZERO;
    // オペレータを格納する変数
    private int mOp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onNumberClick(View view){
        // ボタンの文字列を取得
        String text = ((Button) view).getText().toString();
        // String型からint型にキャスト
        int value = Integer.parseInt(text);

        // もし'='の後に押されたなら、そのまま代入
        if(mOp == R.id.equal_button){
            mCalcValue = new BigDecimal(value);
            mOp = 0;
        }else{
            // 以前入力された値を10倍する
            mCalcValue = mCalcValue.multiply(BigDecimal.TEN);
            // 入力された値を加える
            mCalcValue = mCalcValue.add(new BigDecimal(value));
        }
        // TextViewの再描画
        updateDisplayTextView();
    }

    public void onOperandClick(View view) {
        // '='ボタンのメソッドを呼び出して過去の式を計算させる
        onEqualClick(null);
        // 選択されたオペランドのidを格納
        mOp = view.getId();
        // 現在の値を別の変数に移す
        mPreValue = mCalcValue;
        mCalcValue = BigDecimal.ZERO;
    }

    public void onEqualClick(View view){
        // オペランドに応じて処理を分岐する
        switch(mOp){
            case R.id.plus_button:
                mCalcValue = mPreValue.add(mCalcValue);
                break;
            case R.id.minus_button:
                mCalcValue = mPreValue.subtract(mCalcValue);
                break;
            case R.id.multi_button:
                mCalcValue = mPreValue.multiply(mCalcValue);
                break;
            case R.id.div_button:
                if(!BigDecimal.ZERO.equals(mCalcValue)) {
                    mCalcValue = mPreValue.divide(mCalcValue, 11, BigDecimal.ROUND_HALF_UP);
                }
                break;
        }
        // イコールのidを代入する
        mOp = R.id.equal_button;
        // TextViewの再描画
        updateDisplayTextView();
    }

    public void onClearClick(View view){
        mOp = 0;
        mCalcValue = BigDecimal.ZERO;
        mPreValue = BigDecimal.ZERO;
        // TextViewの再描画
        updateDisplayTextView();
    }

    private void updateDisplayTextView() {
        // idで指定したテキストビューのインスタンスを取得
        TextView textView = (TextView) findViewById(R.id.display_textView);
        // mCalcValueの値をディスプレイする
        textView.setText(format(mCalcValue));
    }

    private String format(BigDecimal value) {
        DecimalFormat df = new DecimalFormat(",###.###########");
        return df.format(value);
    }
}
