package frc.robot.Subsystems.Vision;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform3d;

public interface VisionIO {
    @AutoLog
    public static class VisionIOInputs{

        //LIMELIGHT
        public double getLimelightY = 0.0;
        public double getLimelightX = 0.0;
        public boolean LimetargetFound = false;
        public Pose2d LimePose2d = new Pose2d();
        public double LimelightID = 0;

        //CAMERA 1
        /* 
        public boolean cam1Connected = false;
        public boolean cam1targets = false;
        public int targetIDcam1 = 0;
        public Transform3d cam1pose = new Transform3d();
        public double cam1Latency = 0.0;
        public double cam1yaw = 0.0;
        public double cam1pitch = 0.0;
        public double cam1PoseAmbiguity = 0.0;

        //CAMERA 2
        public boolean cam2Connected = false;
        public boolean cam2targets = false;
        public int targetIDcam2 = 0;
        public Transform3d cam2pose = new Transform3d();
        public double cam2Latency = 0.0;
        public double cam2yaw = 0.0;
        public double cam2pitch = 0.0;
        public double cam2PoseAmbiguity = 0.0; */


    }

    public default void updateInputs(VisionIOInputs inputs){}

  
    //Blinks limelight
    public default void limelightBlink(){}

    


}
