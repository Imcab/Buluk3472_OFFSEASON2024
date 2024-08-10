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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
      Transform2d SpeakerTransform = new Transform2d(blueSpeaker.getX(), blueSpeaker.getY(), new Rotation2d(0, 0));

      Pose2d robotpose = robotPoseSupplier.get();
      return robotpose.transformBy(SpeakerTransform);
       
  }
  
  public static Pose2d ToTurret(){

        Transform2d transform2d = new Transform2d(robotPoseSupplier.get().getX(), turretyaw.get().getRadians(), robotPoseSupplier.get().getRotation().rotateBy(turretyaw.get()));

        Pose2d pose = new Pose2d(robotPoseSupplier.get().getX(), robotPoseSupplier.get().getY(), turretyaw.get().rotateBy(robotPoseSupplier.get().getRotation()));

        Pose2d aa = new Pose2d(robotPoseSupplier.get().getX(), robotPoseSupplier.get().getY(), turretyaw.get());
       return aa;
  }

   @AutoLogOutput(key = "SMARTTURRET/DESIREDANGLE")
  public static Double getDesiredSetpoint(){
        double a1 = ToTurret().getX();
        double a2 = ToTurret().getY();

        double b1 = ToSpeaker().getX();
        double b2 = ToSpeaker().getY();

        double AB = (a1 * b2) + (b1 * b2); 

        double A = Math.sqrt(Math.pow(a1, 2.0) + Math.pow(a2, 2.0));
        double B = Math.sqrt(Math.pow(b1, 2.0) + Math.pow(b2, 2.0));

        double DesiredAngle = (AB/(A*B));

        double VALUE = Math.cos(DesiredAngle);

        double REALVALUE = Math.acos(DesiredAngle);

        SmartDashboard.putNumberArray("VALUES", new Double[]{a1,a2,b1,b2,AB,A,B, DesiredAngle, REALVALUE});
        
        if(REALVALUE == Double.NaN){
        }

        return DesiredAngle;

        
  }
}


