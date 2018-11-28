package glbs.xxl.com.glbs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * company 重庆庆云石油工程技术有限责任公司
 * FileName HomeActivity
 * Package glbs.xxl.com.glbs
 * Description ${DESCRIPTION}
 * author xxl
 * create 2018-11-28 22:15
 * version V1.0
 */
public class HomeActivity extends AppCompatActivity {
    private Button execute;
    private Button clear;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private TextView result;
    private EditText editText;
    private StringBuilder resultStr;
    private int position = 0;

    private boolean a5Model;

    private ArrayList<Integer> numberArray;
    private ArrayList<String> a5Array;
    private ArrayList<String> a1Array;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        execute = findViewById(R.id.execute);
        clear = findViewById(R.id.clear);
        radioButton1 = findViewById(R.id.radio__button_1);
        radioButton2 = findViewById(R.id.radio__button_2);
        result = findViewById(R.id.result);
        editText = findViewById(R.id.input_number);

        resultStr = new StringBuilder();

        clear.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                clear();
            }
        });

        execute.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (!TextUtils.isEmpty(result.getText().toString())){
                    position = 0;
                    resultStr = new StringBuilder();
                }
                a5Model = radioButton1.isChecked();
                execute.setClickable(false);
                clear.setClickable(false);
                makeNumbers();
                execute.setClickable(true);
                clear.setClickable(true);
            }
        });
        int[] intArray = getResources().getIntArray(R.array.number);
        numberArray = new ArrayList<>();
        for (int i : intArray) {
            numberArray.add(i);
        }

        a5Array = new ArrayList<>();
        String[] stringArray = getResources().getStringArray(R.array.a5);
        a5Array.addAll(Arrays.asList(stringArray));

        a1Array = new ArrayList<>();
        String[] stringArray1 = getResources().getStringArray(R.array.a1);
        a1Array .addAll(Arrays.asList(stringArray1));
    }

    private void clear(){
        editText.setText("");
        position = 0;
        resultStr = new StringBuilder();
        result.setText("");
    }

    private void makeNumbers() {
        String[] split = editText.getText().toString().split(",");
        if (split.length < 3) {
            Toast.makeText(this, "请输入3到50个数", Toast.LENGTH_LONG).show();
            return;
        }
        ArrayList<Double> numbers = new ArrayList<>();
        try {
            for (String s : split) {
                numbers.add(Double.parseDouble(s));
            }
        } catch (Exception e) {
            Toast.makeText(this, "请输入正确的数字", Toast.LENGTH_LONG).show();
        }
        play(numbers);
    }

    private void play(ArrayList<Double> numbers) {
        if (numbers.size() < 3) {
            return;
        }

        position++;

        double total = 0;
        for (Double number : numbers) {
            total += number;
        }
        double average = total / numbers.size();

        double v = 0;
        for (Double number : numbers) {
            v += (number - average) * (number - average);
        }

        double s = Math.pow(v / (numbers.size() - 1), 0.5);

        int index = numberArray.indexOf(numbers.size());
        if (index == -1) {
            return;
        }

        double G = Double.parseDouble(a5Model ? a5Array.get(index) : a1Array.get(index));

        int removeIndex = -1;
        for (int i = 0; i < numbers.size(); i++) {
            Double aDouble = numbers.get(i);
            if (Math.abs(aDouble - average) / s >= G) {
                removeIndex = i;
                resultStr.append("第").append(position).append("次淘汰的数为").append(aDouble).append("\n");
                result.setText(resultStr.toString());
                break;
            }
        }

        if (removeIndex != -1){
            numbers.remove(removeIndex);
            play(numbers);
        }else {
            if (position == 1){
                resultStr.append("该组数据没有异常值");
            }else {
                resultStr.append("已剔除所有异常值");
            }
            result.setText(resultStr.toString());
        }
    }

    public static double formatDouble(double d) {
        BigDecimal bg = new BigDecimal(d).setScale(3, RoundingMode.UP);
        return bg.doubleValue();
    }
}
