//Buluk3472 Offseason 2024 CRESCENDO
//https://github.com/Imcab/Buluk3472_OFFSEASON2024
//
/*
 ===============================================
PROYECTO DEL ÁREA DE SOFTWARE DE BULUK#3472.
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@@@@@///////////////@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@@@@@///////////////@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@@@@@////@@@@@@/////@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@@@@@////@@///@/////@@@@@@@@@@@@@@@@
@@@@@@@@/////@@@@/////////@/////@@@@@@@@@@@@@@@@
@@@@@@@@/////@@@@/////////@/////@@@@@@@@@@@@@@@@
@@@@@@@@////////@@@@@@@@@@@/////@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@///////////////////@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@///////////////////@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 ===============================================
*/

package frc.robot.util;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class SmartTurret3472 {
    private static Supplier<Pose2d> robotPoseSupplier = () -> new Pose2d();
    private static Supplier<Rotation2d> turretyaw = ()-> new Rotation2d();
    private static final Translation2d blueSpeaker = new Translation2d(0.225, 5.55);
    private static final Translation2d redSpeaker = new Translation2d(16.317, 5.55);
    /**
    * <p>Cambia el signo del setpoint de la torreta dependiendo de la alianza
    *
    * @return El setpoint corregido
    */
    public static double flip (double setpoint){
        
        boolean Redcolor  = DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get().equals(Alliance.Red);
        
        if (Redcolor == true){
            return HPPMathLib.coterminalradianes(-setpoint);
        }else {
            return setpoint;
        }
    }

    /**
    * Actualiza la pose del robot a la libreria
    * @param supplier La pose2d del robot
    */
    public static void setRobotPoseSupplier(Supplier<Pose2d> supplier) {
    robotPoseSupplier = supplier;
    }
    /**
    * Actualiza la pose de la torreta a la libreria
    * @param supplier La rotacion2d de la torreta
    */
    public static void setTurretPoseSupplier(Supplier<Rotation2d> supplier) {
        turretyaw = supplier;
    }

    /**
    * Obtiene la pose del robot y la corrige dependiendo la alianza
    * @return Pose corregida de la torreta
    */
    public static Pose2d getRobotPose(){
        return AllianceFlipUtil.apply(robotPoseSupplier.get());
    }
    /**
    *  Función que traza el vector de la relación entre el centro del robot al speaker
    * @return el vector en forma de Pose2d (componentes X y Y)
    */
    public static Pose2d ToSpeaker(){

        boolean Redcolor  = DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get().equals(Alliance.Red);
        
        Pose2d ToSpeakerPose = new Pose2d((Redcolor ? redSpeaker.getX() : blueSpeaker.getX()), (Redcolor ? redSpeaker.getY() : blueSpeaker.getY()) , new Rotation2d());

        return ToSpeakerPose;

    }

    /**
    *  Función que traza el vector desde el centro de la torreta
    * @return El vector en forma de Pose2d (componentes X y Y)
    */
    public static Pose2d ToTurret(){

        Double psi = HPPMathLib.coterminalradianes(robotPoseSupplier.get().getRotation().getRadians() + turretyaw.get().getRadians());

        Pose2d ToTurret = new Pose2d(Math.cos(psi), Math.sin(psi), new Rotation2d());

        return ToTurret.transformBy(new Transform2d(robotPoseSupplier.get().getX(), robotPoseSupplier.get().getY(), turretyaw.get().minus(robotPoseSupplier.get().getRotation())));

    }

    /**
    *  Función que obtiene los radianes que la torreta tiene que girar para alinearse respecto al centro del speaker
    * @return Omega: angulo deseado a girar.
    */
    public static Double getOmega(){

        boolean Redcolor  = DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get().equals(Alliance.Red);

        Double  SpeakerX = (Redcolor ? redSpeaker.getX() : blueSpeaker.getX()) - robotPoseSupplier.get().getX();
        Double  SpeakerY = (Redcolor ? redSpeaker.getY() : blueSpeaker.getY()) - robotPoseSupplier.get().getY();
        
        Double psi = HPPMathLib.coterminalradianes(robotPoseSupplier.get().getRotation().getRadians() + turretyaw.get().getRadians());

        Double S_angle = HPPMathLib.coterminalradianes(Math.atan2(SpeakerY, SpeakerX));
        
        Double Omega = S_angle - psi;

        return Units.radiansToDegrees(-Omega);      
    }
  
}