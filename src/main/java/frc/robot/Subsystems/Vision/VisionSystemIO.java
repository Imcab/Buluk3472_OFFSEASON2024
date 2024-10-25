package frc.robot.Subsystems.Vision;

import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.util.LimelightHelpers;

public class VisionSystemIO implements VisionIO{

    //private final PhotonCamera camera1, camera2;

    private final String limeName;

    public VisionSystemIO(){

        limeName = "limelight-buluk";

        //camera1 = new PhotonCamera("ElevatorLeft");
        //camera2 = new PhotonCamera("ElevatorRight");
    }
    
    @Override
    public void updateInputs(VisionIOInputs inputs){

        //Limelight
        inputs.getLimelightY = LimelightHelpers.getTY(limeName);
        inputs.getLimelightX = LimelightHelpers.getTX(limeName);
        inputs.LimetargetFound = LimelightHelpers.getTV(limeName);
        inputs.LimePose2d = (DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get().equals(Alliance.Red) ? LimelightHelpers.getBotPose2d_wpiRed(limeName) : LimelightHelpers.getBotPose2d_wpiBlue(limeName));
        inputs.LimelightID = LimelightHelpers.getFiducialID(limeName);

        //PhotonCamera methods

        /* 
        //Latency
        inputs.cam1Latency = camera1.getLatestResult().getLatencyMillis()/1000;
        inputs.cam2Latency = camera2.getLatestResult().getLatencyMillis()/1000;
        
        //Connected status PhotonCamera
        inputs.cam1Connected = camera1.isConnected();
        inputs.cam2Connected = camera2.isConnected();

        //Check Targets
        inputs.cam1targets = camera1.getLatestResult().hasTargets();
        inputs.cam2targets = camera2.getLatestResult().hasTargets();
        
        //Get PhotonCamera's ficudial ID
        inputs.targetIDcam1 = camera1.getLatestResult().getBestTarget().getFiducialId();
        inputs.targetIDcam2 = camera2.getLatestResult().getBestTarget().getFiducialId();

        //Yaw & Pitch
        inputs.cam1yaw = camera1.getLatestResult().getBestTarget().getYaw();
        inputs.cam2yaw = camera2.getLatestResult().getBestTarget().getYaw();

        inputs.cam1pitch = camera1.getLatestResult().getBestTarget().getPitch();
        inputs.cam2pitch = camera2.getLatestResult().getBestTarget().getPitch();

        //Ambiguity poses
        inputs.cam1PoseAmbiguity = camera1.getLatestResult().getBestTarget().getPoseAmbiguity();
        inputs.cam2PoseAmbiguity = camera2.getLatestResult().getBestTarget().getPoseAmbiguity();

        //GetBest Pose
        inputs.cam1pose = camera1.getLatestResult().getBestTarget().getBestCameraToTarget();
        inputs.cam2pose = camera2.getLatestResult().getBestTarget().getBestCameraToTarget();
        */
    }

    /*@Override
    public void setDriverMode(boolean en1, boolean en2){
        /*camera1.setDriverMode(en1);
        camera2.setDriverMode(en2); 
    }
    */

    @Override
    public void limelightBlink(){
        LimelightHelpers.setLEDMode_ForceBlink(limeName);
    }

    /*@Override
    public void takeSnapshot(){
        //camera1.takeInputSnapshot();
        //camera2.takeInputSnapshot();
    }*/

}
