package frc.robot.Subsystems.Vision;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.geometry.Pose2d;

public interface VisionIO {
    @AutoLog
    public static class VisionIOInputs{
        public double getLimelightY = 0.0;
        public double getLimelightX = 0.0;
        public boolean LimetargetFound = false;
        public Pose2d LimePose2d = new Pose2d();
    }

    public default void updateInputs(VisionIOInputs inputs){}
}
