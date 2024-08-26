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

public class Field3472 {
    
    private static Supplier<Double> robotX = () -> 0.0;

    /**
    * Actualiza la pose en X del robot a la libreria.
    * @param supplier El componente X de la pose 2d del robot.
    */
    public static void setRobotX(Supplier<Double> supplier) {
        robotX = supplier;
    }

    /**
    * Obtiene la coordenada en X del robot respecto a la cancha.
    * @return Los metros que ha avanzado el robot hacia adelante.
    */
    public static double getRobotPoseX(){
        return AllianceFlipUtil.apply(robotX.get());
    }

    /**
    * <p> Función que clasifica la coordenada en X del robot para delimitarla en 4 zonas:
    * <p>ZONE 1: De 0 a 4.5 metros (mitad de la ala de la cancha) 
    * <p>ZONE 2: De 4.5 a 7.0 metros (mitad entre la ala de la cancha y las notas de lejos)
    * <P>ZONE 3: De 7.0 a 9.5 metros (rango donde la línea de las notas de lejos esta en medio)
    * <p>ZONE 4: Mayores a 9.5 metros (hacia la alianza contraria)
    * @return El número de zona en la cual se encuentra el robot.
    */
    public static int getZone(){

        int zone = 0;

        if (getRobotPoseX() <= 4.5) {
            zone = 1;
        }
        if (getRobotPoseX() > 4.5 && getRobotPoseX() <= 7.1) {
            zone = 2;
        }
        if (getRobotPoseX() > 7.1 && getRobotPoseX() <= 9.5) {
            zone = 3;
        }
        if (getRobotPoseX() > 9.5) {
            zone  = 4;
        }

        return zone;
    }

    /**
    * <p> Función que clasifica la coordenada en X del robot para delimitarla en 4 prioridades:
    * <p>HIGH: De 0 a 4.5 metros (mitad de la ala de la cancha) 
    * <p>MEDIUM: De 4.5 a 7.0 metros (mitad entre la ala de la cancha y las notas de lejos)
    * <P>LOW: De 7.0 a 9.5 metros (rango donde la línea de las notas de lejos esta en medio)
    * <p>WARMING UP 4: Mayores a 9.5 metros (hacia la alianza contraria)
    * @return Current priority.
    */
    public static String getPriority(){
        String priority = "";

        if (getZone() == 1) {
            priority = "HIGH";
        }
        if (getZone() == 2) {
            priority = "MEDIUM";
        }
        if (getZone() == 3) {
            priority = "LOW";
        }
        if (getZone() == 4) {
            priority = "WARMING UP";
        }

        return priority;
    }
}
