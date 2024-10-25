package frc.robot.Subsystems.Hanger;
import org.littletonrobotics.junction.AutoLog;
public interface HangerIO {

    @AutoLog
    public static class HangerIOInputs {
        public double HangerVelocityRPM = 0;
        public double CurrentSpeed = 0; 
      }

    public default void updateInputs(HangerIOInputs inputs) {}
    
    public default void setHanger(double speed) {}

    public default void setHangerBrakeMode (boolean enable) {}



}
