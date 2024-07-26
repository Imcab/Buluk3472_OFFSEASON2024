package frc.robot.Subsystems.Elevator;

public class ConstantsElevator {
    public static final class ElevatorConstants{

        public static final double ElevatorReduction = 3;  //3:1

        public static final double kP = 0;
        public static final double KD = 0;
        public static final double kV = 0;

        public static final double kPSIM = 45;
        public static final double KDSIM = 0; //0.08
        public static final double kVSim = 150;

        public static final int ElevatorPort = 0;
        public static final boolean ElevatorMotorReversed = false;

    }
    public static final class RollerConstants{

        public static final int Port = 0;
        public static final boolean MotorReversed = false;

    }
}
