package frc.robot.Subsystems.Vision;

import org.littletonrobotics.junction.AutoLog;

public interface VisionIO {
    @AutoLog
    public static class VisionIOInputs{
        public double getY = 0.0;
        public double getX = 0.0;
        public boolean targetFound = false;
    }

    public default void updateInputs(VisionIOInputs inputs){}
}
