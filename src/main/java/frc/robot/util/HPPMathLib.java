package frc.robot.util;

import java.lang.Math;

public class HPPMathLib {

    public static double coterminal(double angle){
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

    public static double MinAngle(double ang_from, double ang_to) {
        if (Math.abs(ang_to - ang_from) < coterminal(ang_to - ang_from)) {
            return ang_to - ang_from;
        }
        else {
            return coterminal(ang_to - ang_from);
        }
    }
    
}

