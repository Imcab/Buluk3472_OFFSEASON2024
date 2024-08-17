package frc.robot.util;

import java.lang.Math;

public class HPPMathLib {

    public static double coterminalgrados(double angle){
        if (angle >= 0 && angle < 360) {
            return angle;
        }
        else if (angle > 360) {
            return angle - Math.floor(angle / 360) * 360;
        }
        else {
            return angle + (1 + Math.floor(-angle / 360)) * 360;
        }
    }
    public static double coterminalradianes(double angle){
        if (angle >= 0 && angle < 2 * Math.PI) {
            return angle;
            //return Math.asin( Math.sin(angle));
        }
        else if (angle >  2 * Math.PI) {
            return angle - Math.floor(angle /  (2 * Math.PI)) *  (2 * Math.PI);
            //return Math.asin(Math.sin(angle - Math.floor(angle /  (2 * Math.PI)) *  (2 * Math.PI)));
        }
        else {
            return angle + (1 + Math.floor(-angle /  (2 * Math.PI))) *  (2 * Math.PI);
            //return Math.asin(Math.sin(angle + (1 + Math.floor(-angle /  (2 * Math.PI))) *  (2 * Math.PI)));
        }
    }

    public static double MinAngleDeg(double ang_from, double ang_to) {
        if (Math.abs(ang_to - ang_from) < coterminalgrados(ang_to - ang_from)) {
            return ang_to - ang_from;
        }
        else {
            return coterminalgrados(ang_to - ang_from);
        }
    }
    public static double MinAngleRad(double ang_from, double ang_to) {
        if (Math.abs(ang_to - ang_from) < coterminalradianes(ang_to - ang_from)) {
            return ang_to - ang_from;
        }
        else {
            return coterminalradianes(ang_to - ang_from);
        }
    }
    
}

