package frc.robot.Subsystems.Shooter;

import frc.robot.util.Gains;

public class ShooterConstants {

    public static final class TurretConstants{

        public static final double TurretReduction = 12; 

        public static final Gains turretConfig = new Gains(0.001959, 0.0, 0.000);
        public static final Gains limeConfig = new Gains(0.001959, 0.0, 0.000);

        public static final Gains simTurretConfig = new Gains(4, 0.0, 0.08);

        public static final int TurretPort = 19;
        public static final int EncDIOPORT = 4;
        public static final double offset = 0;
        public static final boolean TurretReversed = false;
        
    }
    public static final class AngleShooterConstants{

        public static final double AngleGearing = 0.4848; 

        public static final Gains angleConfig = new Gains(0.025, 0.01, 0.0015);

        public static final Gains limelightConfig = new Gains(0.006, 0.0, 0.000);

        public static final Gains simAngleConfig = new Gains(4, 0, 2.0);

        public static final int AngleShooterPort = 12;
        public static final int EncPort = 3;
        public static final double offset = 298;
        public static final boolean AngleMotorReversed = false;

    }
     public static final class OutakeConstants{

        public static final double WheelReduction = 1; //1:1

        public static final Gains wheelsConfig = new Gains(13, 0.0, 0.0, 2.2, 1.0);

        public static final int LeftWHeelsPort = 13;
        public static final int RightWHeelsPort = 14;
        public static final boolean LeftMotorReversed = true; 
        public static final boolean RightMotorReversed = false;

        ////////////indexador////////////////
        public static final int IndexerPort = 11;
        public static final boolean IndexerMotorReversed = true;
        public static final int BEAMSENSOR_RECIEVER_DIOPIN = 4;
        
    }
}
