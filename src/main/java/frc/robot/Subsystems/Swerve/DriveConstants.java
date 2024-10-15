package frc.robot.Subsystems.Swerve;

import edu.wpi.first.math.util.Units;

public class DriveConstants {

    public static final double kP = 7.0; 

    public static final double kPSIM = 10.0;

    public static final double WHEELRADIUS = Units.inchesToMeters(2.0);

    public static final double WHEELDIAMETER = Units.inchesToMeters(4.0);

    public static final double maxspeedMetersPerSecond = 5.7912;

    public static final double DriveReduction = 5.36;
    public static final double TurnReduction = 18.75;

    public static final class frontLeft{

        public static final int DrivePort = 6; 
        public static final int TurnPort = 5; 
        public static final int EncPort = 3;
        public static final double offset = 46;                                                                                                                                                                                                                                                                                                                                                                                                                                                                        ; //48     //93  //138      //48 o 138 o 228
 
        public static final boolean DrivemotorReversed = false;
        public static final boolean TurnmotorReversed = true;


    }

    public static final class frontRight{

        public static final int DrivePort = 8; 
        public static final int TurnPort = 7; 
        public static final int EncPort = 2; 
        public static final double offset = 69.6; 
 
        public static final boolean DrivemotorReversed = false;
        public static final boolean TurnmotorReversed = true;

    }

    public static final class backLeft{

        public static final int DrivePort = 2; 
        public static final int TurnPort = 1; 
        public static final int EncPort = 0; 
        public static final double offset = 0;
 
        public static final boolean DrivemotorReversed = false;
        public static final boolean TurnmotorReversed = true; 

    }

    public static final class backRight{

        public static final int DrivePort = 4; 
        public static final int TurnPort = 3; 
        public static final int EncPort = 1; 
        public static final double offset = 60.2; 
 
        public static final boolean DrivemotorReversed = false;
        public static final boolean TurnmotorReversed = true;


    }
}
