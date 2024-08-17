//Buluk3472 Offseason 2024 CRESCENDO
//https://github.com/Imcab/Buluk3472_OFFSEASON2024
//
/*
 ===============================================
PROYECTO DEL ÁREA DE SOFTWARE DE BULUK#3472. SE DESARROLLA UNA TORRETA INTELIGENTE
A BASE DE DATOS CONOCIDOS TALES COMO LA ODOMETRÍA, LA POSE DEL SPEAKER, ASÍ TRAZANDO
DOS VECTORES Y MEDIANTE EL PRODUCTO PUNTO SACAR EL ÁNGULO ENTRE ESTOS.
(ESTE CÓDIGO ES SÓLO PARA DARSE UNA IDEA Y DESARROLLAR MEJORES COMPETENCIAS)
 ===============================================
*/

package frc.robot.util;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class SmartTurret3472 {

    private static Supplier<Pose2d> robotPoseSupplier = () -> new Pose2d();
    private static Supplier<Rotation2d> turretyaw = ()-> new Rotation2d();
    private static final Translation2d blueSpeaker = new Translation2d(0.225, 5.55);
    private static final Translation2d redSpeaker = new Translation2d(16.317, 5.55);


    /**
    * Cambia el signo del ángulo dependiendo de la alianza
    *
    * @param setpoint El setpoint a cambiar el signo
    */
    public static double flip (double setpoint){
        
        boolean Redcolor  = DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get().equals(Alliance.Red);

        if (Redcolor == true){
            return -setpoint;
        }else {
            return setpoint;
        }
    }

    /**
    * Actualiza la pose del robot a la libreria
    * @param supplier La pose del robot en supplier
    */
    public static void setRobotPoseSupplier(Supplier<Pose2d> supplier) {
    robotPoseSupplier = supplier;
    }

    /**
    * Actualiza la pose de la torreta a la libreria
    * @param supplier La pose de la torreta en supplier
    */
    public static void setTurretPoseSupplier(Supplier<Rotation2d> supplier) {
        turretyaw = supplier;
    }
    /**
    *  Función que traza el vector de la relación entre el centro del robot-speaker
    */
    public static Pose2d ToSpeaker(){

        boolean Redcolor  = DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get().equals(Alliance.Red);
        
        Transform2d ToSpeakerPose = new Transform2d(Redcolor ? redSpeaker.getX() : blueSpeaker.getX(), Redcolor ? redSpeaker.getY() : blueSpeaker.getY() , new Rotation2d());

        return robotPoseSupplier.get().transformBy(ToSpeakerPose);

    }

    /**
    *  Función que traza el vector desde el centro de la torreta
    */
    public static Pose2d ToTurret(){
        Double psi = HPPMathLib.coterminalradianes(robotPoseSupplier.get().getRotation().getRadians() + turretyaw.get().getRadians());

        Pose2d superPopcorn = new Pose2d(Math.cos(psi), Math.sin(psi), new Rotation2d());

        return superPopcorn.transformBy(new Transform2d(robotPoseSupplier.get().getX(), robotPoseSupplier.get().getY(), turretyaw.get().minus(robotPoseSupplier.get().getRotation())));

            
    }

    /**
    *  Función que obtiene el ÁNGULO ENTRE LOS VECTORES mediante el producto punto
    */
    public static Double getOmega(){

        boolean Redcolor  = DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get().equals(Alliance.Red);

        Double  SpeakerX = Redcolor ? redSpeaker.getX() : blueSpeaker.getX() - robotPoseSupplier.get().getX();
        Double  SpeakerY = Redcolor ? redSpeaker.getY() : blueSpeaker.getY() - robotPoseSupplier.get().getY();
        
        Double psi = robotPoseSupplier.get().getRotation().getRadians() + turretyaw.get().getRadians();

        //System.out.println(psi);

        //Double turretyaw2 = HPPMathLib.coterminalradianes(turretyaw.get().getRadians());



        //System.out.println(turretyaw2);
        //omega esta en radianes, cambiar por constantes
        Double Omega = Math.acos( ((SpeakerX * Math.cos(psi)) + 
            (SpeakerY * Math.sin(psi))) / 
                Math.sqrt(((SpeakerX * SpeakerX) +
                (SpeakerY * SpeakerY))));


        return Omega;
    }

    /**
    *  Función que regresa la DIFERENCIA entre el ángulo calculado(omega) y la posición actual de la torrera
    */
    public static Double getSmartSetpoint(){

        Rotation2d CalculatedSetpoint = new Rotation2d((turretyaw.get().getRadians())).minus(new Rotation2d(getOmega()));
        System.out.println(CalculatedSetpoint.getDegrees());
        return CalculatedSetpoint.getRadians();
        
    }
  
}