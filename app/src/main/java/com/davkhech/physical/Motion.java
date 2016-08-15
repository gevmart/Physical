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

public class Motion extends AppCompatActivity {

    private Particle myParticle;
    private String field;
    private android.os.Handler handler = new android.os.Handler();
    private MyRunnable thread = new MyRunnable();


//    protected static int getHeigth() {
//        return heigth;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion);

        MyListener listener = new MyListener();
        TextView name = (TextView) findViewById(R.id.name);
        Button play = (Button) findViewById(R.id.play);
        EditText massField = (EditText) findViewById(R.id.mass);
        EditText chargeField = (EditText) findViewById(R.id.charge);
        String mass;
        String charge;

        mass = massField.getText().toString();
        charge = chargeField.getText().toString();

        LinearLayout layout = (LinearLayout) findViewById(R.id.motion);
//        heigth = layout.getHeight();

        Bundle extras = getIntent().getExtras();

        this.field = extras.getString(Constants.KEY);
        name.setText(this.field);
        play.setOnClickListener(listener);

        myParticle = new Particle(this.field);


//        thread.start();
        handler.post(thread);
    }

    private class MyRunnable implements Runnable {
        private View particleView = new MyView(Motion.this, null);

        @Override
        public void run() {
            try {


                double imageX;
                double imageY;

//                while(true) {

                Log.i("Thread", "Thread is running");

                myParticle.update();

                particleView.draw(new Canvas());

                Thread.sleep(Constants.STEP_TIME);

                LinearLayout layout = (LinearLayout) findViewById(R.id.motion);

                layout.removeViewAt(6);
                handler.post(thread);

//                }
                //
            } catch (Exception e) {
                Log.e("Thread error", e.toString());
            }

        }

        public void start() {
            Log.i("Thread", "new thread started");
        }
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.play:
                    //start playing
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
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap(b, (float) myParticle.getXCoordinate(), (float) myParticle.getYCoordinate(), null);
        }
    }
}
