//Buluk3472 Offseason 2024 CRESCENDO
//https://github.com/Imcab/Buluk3472_OFFSEASON2024
//
//Proyecto del área de software donde se desarroya una torreta inteligente
//La torreta siempre sabra a donde apuntar aunque no este en su rango de visión
//UNDER CONSTRUCTION

package frc.robot.util;

import java.util.function.Supplier;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.AutoLogOutput;
import org.opencv.core.Mat;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;



//Prototipo torreta inteligente
public class SmartTurret3472 {

    private static Supplier<Pose2d> robotPoseSupplier = () -> new Pose2d();
    private static Supplier<Rotation2d> turretyaw = ()-> new Rotation2d();
    private static final Translation2d blueSpeaker = new Translation2d(0.225, 5.55);

    public double flip (double setpoint){
        
        boolean Redcolor  = DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get().equals(Alliance.Red);

        if (Redcolor == true){
            return -setpoint;
        }else {
            return setpoint;
        }
    }

    public static void setRobotPoseSupplier(Supplier<Pose2d> supplier) {
    robotPoseSupplier = supplier;
    }

    public static void setTurretPoseSupplier(Supplier<Rotation2d> supplier) {
        turretyaw = supplier;
    }


    public static Pose2d ToSpeaker(){
      Transform2d Transform = new Transform2d(blueSpeaker.getX(), blueSpeaker.getY(), new Rotation2d(0, 0));

      Pose2d aa = robotPoseSupplier.get();
      return aa.transformBy(Transform);
       
  }
  
  public static Pose2d ToTurret(){

        Transform2d transform2d = new Transform2d(  robotPoseSupplier.get().getY(), -turretyaw.get().getRadians(), turretyaw.get());

       Pose2d aa = new Pose2d(robotPoseSupplier.get().getX(),robotPoseSupplier.get().getY(), robotPoseSupplier.get().getRotation().minus(turretyaw.get()));
       return aa.transformBy(transform2d);
  }

  public void periodic(){
    
  }

  

  

}


