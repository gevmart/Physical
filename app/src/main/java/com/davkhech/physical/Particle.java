package com.davkhech.physical;

import android.util.Log;

public class Particle {
    private double mass = 1;
    private double charge = 1;

    private double xCoordinate = 200;
    private double yCoordinate = 600;//Motion.getHeigth();

    private double xVelocity;
    private double yVelocity;

    private double xAcceleration;
    private double yAcceleration;

    private String field;

    private double dreyfSpeed = Constants.ELECTRIC_CONSTANT / Constants.MAGNETIC_CONSTANT;

    protected Particle(String field) {
        this.field = field;

        switch (field) {
            case Constants.GRAVITATION:
                Log.i("*****", "********************************************************************");
                this.xVelocity = 0;
                this.yVelocity = -0.65;
                break;
            case Constants.ELECTRICITY:
                this.xVelocity = 0;
                this.yVelocity = -0.65;
                break;
            case Constants.MAGNETISM:
                this.yCoordinate = 300;
                this.xVelocity = 0.5;
                this.yVelocity = 0;
                break;
            case Constants.ELECTROMAGNETISM:
                this.xCoordinate = 200;
                this.yCoordinate = 450;
                this.xVelocity = 0;
                this.yVelocity = 0;


        }
    }

    protected double getXCoordinate() {
        return this.xCoordinate;
    }

    protected double getYCoordinate() {
        return this.yCoordinate;
    }

    protected void setMass(double mass) {
        this.mass = mass;
    }

    protected void setCharge(double charge) {
        this.charge = charge;
    }

    protected void update() {
        xCoordinate += xVelocity * Constants.STEP_TIME;
        yCoordinate += yVelocity * Constants.STEP_TIME;

        xVelocity += xAcceleration * Constants.STEP_TIME;
        yVelocity += yAcceleration * Constants.STEP_TIME;

        if (field.equals(Constants.GRAVITATION)|| field.equals(Constants.ELECTRICITY))
            if (yCoordinate > 600)
                yVelocity = -0.7 * yVelocity;

        if(field.equals(Constants.MAGNETISM)){
            double velocity = Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity);
            xVelocity *= (0.5 / velocity);
            yVelocity *= (0.5 / velocity);

            double acc = Math.sqrt(xAcceleration * xAcceleration + yAcceleration * yAcceleration);
            xAcceleration *= (0.5 * Constants.MAGNETIC_CONSTANT / acc);
            yAcceleration *= (0.5 * Constants.MAGNETIC_CONSTANT / acc);
        }

        if(field.equals(Constants.ELECTROMAGNETISM)) {
//            double alpha = Math.asin(xVelocity / dreyfSpeed);
//            Log.i("Alpha", String.valueOf(alpha));
//            double neededVelocity = Math.sqrt(dreyfSpeed * dreyfSpeed * Math.sin(alpha) * Math.sin(alpha) + (dreyfSpeed * (1 + Math.cos(alpha))) * (dreyfSpeed * (1 + Math.cos(alpha))));
//            double velocity = Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity);
//
//            Log.i("Needed velocity", String.valueOf(neededVelocity));
//            Log.i("Velocity", String.valueOf(velocity));
//
//            xVelocity *= (neededVelocity / velocity);
//            yVelocity *= (neededVelocity / velocity);


        }

        xAcceleration = calculateXAcceleration();
        yAcceleration = calculateYAcceleration();
    }

    private double calculateXAcceleration() {
        switch (this.field) {
            case Constants.GRAVITATION:
                return 0;
            case Constants.ELECTRICITY:
                return 0;
            case Constants.MAGNETISM:
                return Constants.MAGNETIC_CONSTANT * charge / mass * yVelocity;
            case Constants.ELECTROMAGNETISM:
                return Constants.MAGNETIC_CONSTANT * charge / mass * yVelocity  + Constants.ELECTRIC_CONSTANT * charge / mass;
            default:
                return 0;
        }
    }

    private double calculateYAcceleration() {
        switch (this.field) {
            case Constants.GRAVITATION:
                return Constants.GRAVITATIONAL_CONST;
            case Constants.ELECTRICITY:
                return Constants.ELECTRIC_CONSTANT * charge / mass;
            case Constants.MAGNETISM:
                return -Constants.MAGNETIC_CONSTANT * charge / mass * xVelocity;
            case Constants.ELECTROMAGNETISM:
                return -Constants.MAGNETIC_CONSTANT * charge / mass * xVelocity;
            default:
                return 0;
        }
    }
}
