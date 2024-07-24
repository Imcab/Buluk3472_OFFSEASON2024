package frc.robot.Subsystems.Vision;

import org.littletonrobotics.junction.AutoLog;

public interface VisionIO {
    @AutoLog
    public static class VisionIOInputs{
        public double getY = 0.0;
        public boolean targetFound = false;
        public double getV = 0.0;
    }

    public default void updateInputs(VisionIOInputs inputs){}
}
