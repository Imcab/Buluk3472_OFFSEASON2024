package frc.robot.Subsystems.Vision;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase{
    private final VisionIO io;
    private final VisionIOInputsAutoLogged inputs = new VisionIOInputsAutoLogged();

   public Vision(VisionIO io){
        this.io = io;
   }
   public void periodic(){
    
        io.updateInputs(inputs);
        Logger.processInputs("Vision/VisionInputs", inputs);
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
