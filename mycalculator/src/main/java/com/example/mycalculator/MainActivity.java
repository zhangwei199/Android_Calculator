package com.example.mycalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView display;   //显示屏
    Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9; //数字按钮
    Button btn_point;  //小数点按钮
    Button btn_clean;  //清空按钮
    Button btn_del;    //删除按钮
    Button btn_add, btn_minus, btn_multiply, btn_divide;//加减乘除按钮
    Button btn_equal;  //等于号按钮
    Button btn_history;     //历史记录按钮
    boolean clean_flag;//清空标识
    int i = 0;      //记录添加历史的次数
    String[] history =new String[15];   //存放15条记录的数组

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myset();

    }

    //自定义方法
    public void myset() {
        display = (TextView) findViewById(R.id.display);//实例化显示屏
        btn_0 = (Button) findViewById(R.id.btn_0);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_8 = (Button) findViewById(R.id.btn_8);       //实例化按钮
        btn_9 = (Button) findViewById(R.id.btn_9);
        btn_point = (Button) findViewById(R.id.btn_point);
        btn_clean = (Button) findViewById(R.id.btn_clean);
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_minus = (Button) findViewById(R.id.btn_minus);
        btn_multiply = (Button) findViewById(R.id.btn_multiply);
        btn_divide = (Button) findViewById(R.id.btn_divide);
        btn_equal = (Button) findViewById(R.id.btn_equal);
        btn_history = (Button) findViewById(R.id.btn_history);


        //文字超出显示屏范围可滚动
        display.setMovementMethod(ScrollingMovementMethod.getInstance());
        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);       //设置按钮的点击事件
        btn_9.setOnClickListener(this);
        btn_point.setOnClickListener(this);
        btn_clean.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_multiply.setOnClickListener(this);
        btn_divide.setOnClickListener(this);
        btn_equal.setOnClickListener(this);
        btn_history.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String str = display.getText().toString();
        switch (v.getId()) {
            case R.id.btn_clean:
                str = "";
                display.setText("");
                break;
            case R.id.btn_del:
                try {
                    str = str.substring(0, str.length() - 1);
                } catch (Exception e) {
                    str = "";
                }
                display.setText(str + "");
                break;
            case R.id.btn_point:
                if (str.length() == 0) {
                    str = "0.";
                } else {
                    str += ".";
                }
                display.setText(str + "");
                break;
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                if (clean_flag) {
                    str="";
                    display.setText("");
                    clean_flag = false;
                }
                display.setText(str + ((Button) v).getText());
                break;
            //加减乘除
            case R.id.btn_add:
            case R.id.btn_minus:
            case R.id.btn_multiply:
            case R.id.btn_divide:
                if (str.length() != 0 && !str.contains(" ")) {
                    display.setText(str + " " + ((Button) v).getText() + " ");
                }
                if (clean_flag) {
                    clean_flag = false;
                    str = str.substring(str.indexOf("=")+1).replace(" ","");
                    display.setText( str + " " + ((Button) v).getText() + " ");
                }
                break;
            //等于
            case R.id.btn_equal:
                if (str.length() != 0 && !str.contains("=")) {
                    display.setText(str + " = " + getResult());
                    str = display.getText().toString();
                    while (true) {
                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                        Date date = new Date(System.currentTimeMillis());
                        String time = formatter.format(date);
                        history[i]=str+"\n   ---- "+time;
                        i++;
                        break;
                    }
                    if(i==15){
                        i=0;
                    }
                }
                break;
            //历史记录
            case R.id.btn_history:
                Intent intent = new Intent(this, HistoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArray("history",history);
                intent.putExtras(bundle);
                MainActivity.this.startActivity(intent);
                break;
            default:
                break;

        }
    }



    //计算方法
    private String getResult() {
        clean_flag = true;
        String text = display.getText().toString();
        String s1, s2, op;
        try {
            s1 = text.substring(0, text.indexOf(" "));//截取运算符前面的字符串
            op = text.substring(text.indexOf(" ") + 1, text.indexOf(" ") + 2); //截取运算符
            s2 = text.substring(text.indexOf(" ") + 3); //截取运算符后面的字符串
        } catch (Exception e) {
            return text;
        }
        if(".".equals(s2)||"".equals(s2))
            s2="0";
        double d1 = Double.parseDouble(s1);
        double d2 = Double.parseDouble(s2);
        double result = d1;
        switch (op) {
            case "+":
                result = d1 + d2;
                break;
            case "-":
                result = d1 - d2;
                break;
            case "×":
                result = d1 * d2;
                break;
            case "÷":
                if (d2 == 0) {
                    Toast.makeText(MainActivity.this, "0不能作除数", Toast.LENGTH_LONG).show();
                    return "无效";
                } else {
                    result = d1 / d2;
                }
                break;
            default:
                break;
        }

        if (result % 1 == 0) {
            return "" + (int) result;
        } else {
            return String.format("%.2f", result); //返回结果保留两位小数
        }

    }

}
