package frc.robot.Subsystems.Vision;

import frc.robot.util.LimelightHelpers;

public class VisionSystemIO implements VisionIO{

    public VisionSystemIO(){}

    @Override
    public void updateInputs(VisionIOInputs inputs){
        
        inputs.getLimelightY = LimelightHelpers.getTY("");
        inputs.getLimelightX = LimelightHelpers.getTX("");
        inputs.LimetargetFound = LimelightHelpers.getTV("");
        inputs.LimePose2d = LimelightHelpers.getBotPose2d("");
        
    }
}
