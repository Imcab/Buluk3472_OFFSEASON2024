package frc.robot.Subsystems.Vision;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.util.LimelightHelpers;

public class VisionSystemIO implements VisionIO{

    private Boolean ShouldFlip;

    public VisionSystemIO(){
        ShouldFlip = DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get().equals(Alliance.Red);
        
    }

    @Override
    public void updateInputs(VisionIOInputs inputs){

        inputs.getLimelightY = LimelightHelpers.getTY("");
        inputs.getLimelightX = LimelightHelpers.getTX("");
        inputs.LimetargetFound = LimelightHelpers.getTV("");
        inputs.LimePose2d = (ShouldFlip ? LimelightHelpers.getBotPose2d_wpiRed("") : LimelightHelpers.getBotPose2d_wpiBlue(""));
        
    }
}
