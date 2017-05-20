package com.example.d1mys1klapo4ka.tipcalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 0.0; //Сумма счета введенная пользователем
    private double percent = 0.15; //Исходный процент чаевых

    private TextView amountTV; // Для отформатированной суммы счета
    private TextView percentTV; // Для вывода процента чаевых
    private TextView tipTV; //Для вывода вычисленных чаевых
    private TextView totalTV; //Для вычисления общей суммы


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountTV = (TextView)findViewById(R.id.tv_amount);
        percentTV = (TextView)findViewById(R.id.tv_percent);
        tipTV = (TextView)findViewById(R.id.tv_tip);
        totalTV = (TextView)findViewById(R.id.tv_total);

        tipTV.setText(currencyFormat.format(0));
        totalTV.setText(currencyFormat.format(0));

        //Назначение слушателя TextWatcher для amount
        EditText amountET = (EditText)findViewById(R.id.et_amout);
        amountET.addTextChangedListener(amountETWatcher);

        //Назначение слушателя OnSeekBarChangeListener для percentSB
        SeekBar percentSB = (SeekBar)findViewById(R.id.sb_percent);
        percentSB.setOnSeekBarChangeListener(sbListener);
    }

    private void calculate(){
        //Форматирование процентов и вывод в percentTV
        percentTV.setText(percentFormat.format(percent));

        //Вычисление чаевых и общей суммы
        double tip = billAmount * percent;
        double total = tip + billAmount;

        //Вывод чаевых и общей суммы в формате денежных едениц
        tipTV.setText(currencyFormat.format(tip));
        totalTV.setText(currencyFormat.format(total));
    }

    private final SeekBar.OnSeekBarChangeListener sbListener = new
            SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    percent = progress / 100.0;//Назначаются чаевые пользователем
                    calculate();//Вычисление и выыод чаевых и сумм
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };

    private final TextWatcher amountETWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try { //Получить счет и вывести в формате денежной суммы

                billAmount = Double.parseDouble(s.toString()) / 100.0;
                amountTV.setText(currencyFormat.format(billAmount));
            }catch (NumberFormatException n){

                amountTV.setText("");
                billAmount = 0.0;
            }

            calculate();//Обновление полей с суммой и чаевыми
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
