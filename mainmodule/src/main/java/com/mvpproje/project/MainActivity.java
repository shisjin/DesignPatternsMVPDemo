package com.mvpproje.project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mvpproje.shijin.project.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
private boolean isChecked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((CheckBox)findViewById(R.id.checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.this.isChecked=isChecked;
            }
        });
    }

    public void changtext(View view) {
        String textStr = ((TextView) findViewById(R.id.text)).getText().toString();
        if (isChecked){
            textStr=replaceBlank(textStr);
        }else {
            textStr= ((TextView) findViewById(R.id.text)).getText().toString();
        }
        ((TextView) findViewById(R.id.chang_text)).setText(textStr);

    }

    public  String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

}
