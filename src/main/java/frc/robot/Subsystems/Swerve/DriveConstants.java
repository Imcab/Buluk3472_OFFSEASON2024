package frc.robot.Subsystems.Swerve;

import edu.wpi.first.math.util.Units;

public class DriveConstants {

    public static final double kP = 0.008;

    public static final double kPSIM = 0.008;

    public static final double WHEELRADIUS = Units.inchesToMeters(2.0);

    public static final double WHEELDIAMETER = Units.inchesToMeters(4.0);

    public static final double maxspeedMetersPerSecond = 5.7912;

    public static final double driveGratio = 1 / 5.35;
    public static final double turnGratio = 1 / 18.75;
    public static final double encDrot2met = driveGratio * Math.PI * WHEELDIAMETER;
    public static final double metToMs = encDrot2met/60;

    
    
    public static final class frontLeft{

        public static final int DrivePort = 3; //3 
        public static final int TurnPort = 4; //4
        public static final int EncPort = 0;
        public static final double offset = 139;                                                                                                                                                                                                                                                                                                                                                                                                                                                                        ; //48     //93  //138      //48 o 138 o 228
 
        public static final boolean DrivemotorReversed = false;
        public static final boolean TurnmotorReversed = true;
        public static final double PIDSTATUS = 1.0;

    }

    public static final class frontRight{

        public static final int DrivePort = 1; //1
        public static final int TurnPort = 11; //11
        public static final int EncPort = 1; //1
        public static final double offset = 94; //2     //47   //92 //2 o 92 o 182
 
        public static final boolean DrivemotorReversed = false;
        public static final boolean TurnmotorReversed = true; //false
        public static final double PIDSTATUS = 1.0; //-1

    }

    public static final class backLeft{

        public static final int DrivePort = 6; //6
        public static final int TurnPort = 9; //9
        public static final int EncPort = 2; //2
        public static final double offset = 344; //250    //295   //340           // 250.50 o 340 o 430
 
        public static final boolean DrivemotorReversed = false;
        public static final boolean TurnmotorReversed = true; //false
        public static final double PIDSTATUS = 1.0; //-1

    }

    public static final class backRight{

        public static final int DrivePort = 7; //7
        public static final int TurnPort = 5; //5
        public static final int EncPort = 3; //3
        public static final double offset = 330; //246   //291 //336     //246 o 336 o 426
 
        public static final boolean DrivemotorReversed = true;
        public static final boolean TurnmotorReversed = true; // fasle
        public static final double PIDSTATUS = 1.0; //-1

    }
}
