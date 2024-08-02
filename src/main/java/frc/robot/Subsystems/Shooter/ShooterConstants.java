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

        public static final double AngleGearing = 0.4848; 

        public static final double kP = 0;
        public static final double KD = 0;
        ;

        public static final double kPSIM = 4; 
        public static final double kISIM = 0.0;
        public static final double KDSIM = 2.0; //0.08

        public static final int AngleShooterPort = 0;
        public static final int EncPort = 0;
        public static final double offset = 0;
        public static final boolean AngleMotorReversed = false;

    }

     public static final class OutakeConstants{

        public static final double WheelReduction = 1; //1:1

        public static final double kP = 0;
        public static final double KD = 0;
        public static final double kV = 0.0;

        public static final double kPSIM = 2.0; 
        public static final double kISIM = 0.0;
        public static final double KDSIM = 0.00; 
        public static final double kVSIM = 0.14;

        public static final int LeftWHeelsPort = 0;
        public static final int RightWHeelsPort = 0;
        public static final boolean LeftMotorReversed = false; 
        public static final boolean RightMotorReversed = false;

        ////////////indexador////////////////
        public static final int IndexerPort = 0;
        public static final boolean IndexerMotorReversed = false;
        public static final double IndexerReduction = 1; // 1:1

        public static final double IkP = 0;
        public static final double IKD = 0;
        public static final double IkV = 0.0;

    }
}
