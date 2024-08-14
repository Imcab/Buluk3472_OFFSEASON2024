//Buluk3472 Offseason 2024 CRESCENDO
//https://github.com/Imcab/Buluk3472_OFFSEASON2024
//
//Proyecto del área de software donde se desarroya una torreta inteligente
//La torreta siempre sabra a donde apuntar aunque no este en su rango de visión
//UNDER CONSTRUCTION

package frc.robot.util;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;


//Prototipo torreta inteligente
public class SmartTurret3472 {

    private static Supplier<Pose2d> robotPoseSupplier = () -> new Pose2d();
    private static Supplier<Rotation2d> turretyaw = ()-> new Rotation2d();
    private static DoubleSupplier turretvalue = ()-> 0.0;
    private static final Translation2d blueSpeaker = new Translation2d(0.225, 5.55);

    public static double flip (double setpoint){
        
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
    public static void setValue(DoubleSupplier value){
        turretvalue = value;
    }

    public static double getPos(){
        return turretvalue.getAsDouble();
    }


    public static Pose2d ToSpeaker(){

        Transform2d aa = new Transform2d(blueSpeaker.getX(), blueSpeaker.getY() , new Rotation2d());
        return robotPoseSupplier.get().transformBy(aa);

       // return new Pose2d((blueSpeaker.getX() - robotPoseSupplier.get().getX()), 
           // (blueSpeaker.getY() - robotPoseSupplier.get().getY()), new Rotation2d());
    
    }

    public static Pose2d ToTurret(){
        Double psi = (robotPoseSupplier.get().getRotation().getRadians() + turretyaw.get().getRadians());

        Pose2d superPopcorn = new Pose2d(Math.cos(psi), Math.sin(psi), new Rotation2d());
        
      /*  return superPopcorn.transformBy(new Transform2d(robotPoseSupplier.get().getX(), robotPoseSupplier.get().getY(), 
            turretyaw.get().minus(robotPoseSupplier.get().getRotation())));*/

        //Pose2d Psi = (new Pose2d(Math.cos(robotPoseSupplier.get().getRotation().getRadians() + turretyaw.get().getRadians()), Math.sin(robotPoseSupplier.get().getRotation().getRadians() + turretyaw.get().getRadians()), new Rotation2d()));

        return superPopcorn.transformBy(new Transform2d(robotPoseSupplier.get().getX(), robotPoseSupplier.get().getY(), turretyaw.get().minus(robotPoseSupplier.get().getRotation())));

            
    }

    public static Double getOmega(){
        Double psi = (robotPoseSupplier.get().getRotation().getRadians() + turretyaw.get().getRadians());
        Double SpeakerX = blueSpeaker.getX() - robotPoseSupplier.get().getX();
        Double SpeakerY = blueSpeaker.getY() - robotPoseSupplier.get().getY();

        //omega esta en radianes, cambiar por constantes
        Double Omega = Math.acos( ((SpeakerX * Math.cos(psi)) + 
            (SpeakerY * Math.sin(psi))) / 
                Math.sqrt(((SpeakerX * SpeakerX) +
                (SpeakerY * SpeakerY))));


        return Omega;
    }

    public static Double getSmartSetpoint(){
        Rotation2d setpoint = robotPoseSupplier.get().getRotation().minus(new Rotation2d(getOmega()));
        return setpoint.getRadians();
        //return getOmega();
    }

}