package frc.robot.Subsystems.Vision;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;

public interface VisionIO {
    @AutoLog
    public static class VisionIOInputs{

        //LIMELIGHT
        public double getLimelightY = 0.0;
        public double getLimelightX = 0.0;
        public boolean LimetargetFound = false;
        public Pose2d LimePose2d = new Pose2d();

        //CAMERA 1
        public boolean cam1targets = false;
        public int targetIDcam1 = 0;
        public Transform2d cam1pose = new Transform2d();
        public double cam1Latency = 0.0;
        public double getCam1yaw = 0.0;
        public double getCam1pitch = 0.0;
        public double Cam1PoseAmbiguity = 0.0;
        public Transform3d robot_toCam1 = new Transform3d();

        //CAMERA 2
        public boolean cam2targets = false;
        public int targetIDcam2 = 0;
        public Transform2d cam2pose = new Transform2d();
        public double cam2Latency = 0.0;
        public double getCam2yaw = 0.0;
        public double getCam2pitch = 0.0;
        public double Cam2PoseAmbiguity = 0.0;
        public Transform3d robot_toCam2 = new Transform3d();

        //CAMERA 3
        public boolean cam3targets = false;
        public int targetIDcam3 = 0;
        public Transform2d cam3pose = new Transform2d();
        public double cam3Latency = 0.0;
        public double getCam3yaw = 0.0;
        public double getCam3pitch = 0.0;
        public double Cam3PoseAmbiguity = 0.0;
        public Transform3d robot_toCam3 = new Transform3d();

    }

    public default void updateInputs(VisionIOInputs inputs){}
}
