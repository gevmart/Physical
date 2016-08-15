package com.davkhech.physical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button gravity = (Button) findViewById(R.id.gravity);
        Button electric = (Button) findViewById(R.id.electric);
        Button magnetic = (Button) findViewById(R.id.magnetic);
        Button electromagnetic = (Button) findViewById(R.id.electromagnetic);


        MyListener listener = new MyListener();

        gravity.setOnClickListener(listener);
        electric.setOnClickListener(listener);
        magnetic.setOnClickListener(listener);
        electromagnetic.setOnClickListener(listener);

    }

    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, MotionActivity.class);
            switch (v.getId()) {
                case R.id.gravity:
                    intent.putExtra(Constants.KEY, Constants.GRAVITATION);
                    startActivity(intent);
                    break;
                case R.id.electric:
                    intent.putExtra(Constants.KEY, Constants.ELECTRICITY);
                    startActivity(intent);
                    break;
                case R.id.magnetic:
                    intent.putExtra(Constants.KEY, Constants.MAGNETISM);
                    startActivity(intent);
                    break;
                case R.id.electromagnetic:
                    intent.putExtra(Constants.KEY, Constants.ELECTROMAGNETISM);
                    startActivity(intent);
                    break;
            }
        }
    }


}







