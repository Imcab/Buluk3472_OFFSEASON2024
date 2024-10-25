package frc.robot.util;

public class Gains {
    
    private final double kP,kI,kD,kS,kV;

    public Gains(double kP, double kI, double kD){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kV = 0;
        this.kS = 0;
    }

    public Gains(double kP, double kI, double kD, double kS, double kV){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kV = kV;
        this.kS = kS;
    }

    public double getP(){
        return kP;
    }

    public double getI(){
        return kI;
    }
    public double getD(){
        return kD;
    }
    public double getS(){
        return kS;
    }
    public double getV(){
        return kV;
    }
}
