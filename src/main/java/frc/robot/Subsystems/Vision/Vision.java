package frc.robot.Subsystems.Vision;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase{
    private final VisionIO io;
    private final VisionIOInputsAutoLogged inputs = new VisionIOInputsAutoLogged();

    AprilTagFieldLayout aprilTagFieldLayout; 

   public Vision(VisionIO io){
        this.io = io;
        aprilTagFieldLayout = AprilTagFields.k2024Crescendo.loadAprilTagLayoutField();
   }
   
   public void periodic(){

        io.updateInputs(inputs);
        Logger.processInputs("Vision/VisionInputs", inputs);
   }
   public AprilTagFieldLayout gFieldLayout(){
      return aprilTagFieldLayout;
   }
   public double getLimelightY(){
     return inputs.getLimelightY;
   }
   public double getLimelightX(){
    return inputs.getLimelightX;
   }
   public boolean LimelighttargetFound(){
     return inputs.LimetargetFound;
   }
   
} 
