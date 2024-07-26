package frc.robot.Subsystems.Vision;

import frc.robot.util.LimelightHelpers;

public class VisionLimelightIO implements VisionIO{

    public VisionLimelightIO(){}

    @Override
    public void updateInputs(VisionIOInputs inputs){
        inputs.getY = LimelightHelpers.getTY("");
        
        inputs.targetFound = LimelightHelpers.getTV("");
        
    }
}
