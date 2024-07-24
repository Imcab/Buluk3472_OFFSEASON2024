package frc.robot.Subsystems.Shooter;

public class ShooterConstants {

    public static final class TurretConstants{

        public static final double TurretReduction = 12; //12:1

        public static final double kP = 0;
        public static final double KD = 0;

        public static final double kPSIM = 4;
        public static final double KDSIM = 0.08;

        public static final int TurretPort = 0;
        public static final int EncPort = 0;
        public static final double offset = 0;
        public static final boolean TurretReversed = false;


    }

    public static final class AngleShooterConstants{

        public static final double AngleReduction = 1/0.4848; 

        public static final double kP = 0;
        public static final double KD = 0;

        public static final double kPSIM = 0.3; //4
        public static final double kISIM = 0.1;
        public static final double KDSIM = 0.08; //0.08

        public static final int AngleShooterPort = 0;
        public static final int EncPort = 0;
        public static final double offset = 0;
        public static final boolean AngleMotorReversed = false;


    }
}
