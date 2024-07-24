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
        Logger.processInputs("Vision/LimelightVision", inputs);
   }

   public double getY(){
     return inputs.getY;
   }
   public double getV(){
     return inputs.getV;
   }
   public boolean targetFound(){
     return inputs.targetFound;
   }
} 
