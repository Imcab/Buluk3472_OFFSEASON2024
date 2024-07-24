package frc.robot.Subsystems.Vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.util.LimelightHelpers;

public class VisionLimelightIO implements VisionIO{

    NetworkTable table;

    NetworkTableEntry ty;
    NetworkTableEntry tv;

    public VisionLimelightIO(){
        table = NetworkTableInstance.getDefault().getTable("limelight");

        ty = table.getEntry("ty");

        tv = table.getEntry("tv");
        
    }

    @Override
    public void updateInputs(VisionIOInputs inputs){
        inputs.getY = ty.getDouble(0);
        

        if (tv.getDouble(0) == 0.0f){

        inputs.targetFound = false;

        }else{

         inputs.targetFound = true;

        }

        inputs.getV = tv.getDouble(0);
    }
}
