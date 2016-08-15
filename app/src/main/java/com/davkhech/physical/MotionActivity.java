package com.davkhech.physical;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MotionActivity extends AppCompatActivity {

    private Particle myParticle;
    private String field;
    private android.os.Handler handler = new android.os.Handler();
    //    private MyThread thread = new MyThread();
    private LinearLayout layout;
    private MyRunnable draw = new MyRunnable();

    private int played = 0;

    private EditText massField;
    private EditText chargeField;


//    protected static int getHeigth() {
//        return heigth;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion);

        this.layout = (LinearLayout) findViewById(R.id.part);

        MyListener listener = new MyListener();
        TextView name = (TextView) findViewById(R.id.name);
        Button play = (Button) findViewById(R.id.play);

        massField = (EditText) findViewById(R.id.mass);
        chargeField = (EditText) findViewById(R.id.charge);


        play.setOnClickListener(listener);

        Bundle extras = getIntent().getExtras();


        this.field = extras.getString(Constants.KEY);

        name.setText(this.field);

//        name.setText(this.field);
//        play.setOnClickListener(listener);

        myParticle = new Particle(this.field);


//        handler.post(thread);
        draw.start();
    }

//    private Thread thread = new Thread() {
//        @Override
//        public void run() {
//            super.run();
//
//            LinearLayout layout = (LinearLayout) findViewById(R.id.part);
//            layout.addView(new MyView(MotionActivity.this, null));
//
//            Log.i("Thread", "Thread is running");
//
//            try {
//                Thread.sleep(500);
//
//            } catch (Exception e) {
//                Log.e("Thread error", e.toString());
//            }
//
//            try {
//                layout.removeViewAt(0);
//            } catch (Exception e) {
//
//            }
//            handler.post(thread);
//        }
//    };

//    private class MyThread extends Thread {
////        public LinearLayout layout = (LinearLayout) findViewById(R.id.part);
//
//        Thread thread1;
//
//        @Override
//        public void run() {
////            LinearLayout layout = (LinearLayout) findViewById(R.id.part);
//            layout.addView(new MyView(MotionActivity.this, null));
//
//            Log.i("Thread", "Thread is running");
//
//            try {
//                Thread.sleep(Constants.STEP_TIME);
//
//            } catch (Exception e) {
//                Log.e("Thread error", e.toString());
//            }
//
//            try {
//                layout.removeViewAt(0);
//            } catch (Exception e) {
//
//            }
//            handler.post(draw);
//        }
//
//        public void start() {
//            Log.i("Thread", "new thread started");
//            run();
//        }
//    }

    private class MyRunnable implements Runnable {
        private Thread thread;

        @Override
        public void run() {
            LinearLayout layout = (LinearLayout) findViewById(R.id.part);


            try {
                layout.removeViewAt(0);
            } catch (Exception e) {

            }
            layout.addView(new MyView(MotionActivity.this, null));

            Log.i("Thread", "Thread is running");

            try {
                Thread.sleep(Constants.STEP_TIME);

            } catch (Exception e) {
                Log.e("Thread error", e.toString());
            }
            if (played == 1) {
                handler.post(draw);
            }

        }

        public void start() {
            if (thread == null) {
                thread = new Thread(this, "Draw");
                thread.start();
            }
        }
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.play:
                    //start playing
                    String mass;
                    String charge;

                    mass = massField.getText().toString();
                    charge = chargeField.getText().toString();
                    try {
                    double doubleMass = Double.parseDouble(mass);
                    double doubleCharge = Double.parseDouble(charge);

                    myParticle.setMass(doubleMass);
                    myParticle.setCharge(doubleCharge);
                    played = 1;
                    handler.post(draw);
                    } catch(Exception e) {

                    }
                    break;
            }
        }
    }

    class MyView extends View {
        private Bitmap b;

//        protected MyView(Context c, AttributeSet a) {
//            super(c, a);
//
//            b = BitmapFactory.decodeResource(getResources(), R.drawable.particle);
//        }

        public MyView(Context context, AttributeSet attr) {
            super(context, attr);

            b = BitmapFactory.decodeResource(getResources(), R.drawable.particle);
            Log.i("a", "******************");

//            draw(canvas);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            double a = myParticle.getYCoordinate();
            Log.i("Coord **************", String.valueOf(a));
            myParticle.update();

            canvas.drawBitmap(b,(float) myParticle.getXCoordinate(), (float) myParticle.getYCoordinate(), null);
        }
    }
}
