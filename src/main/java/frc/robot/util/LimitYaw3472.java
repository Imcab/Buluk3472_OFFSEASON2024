package frc.robot.util;

import java.util.function.Supplier;

//Buluk3472 Offseason 2024 CRESCENDO
//https://github.com/Imcab/Buluobot.util;k3472_OFFSEASON2024
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
public class LimitYaw3472 {

    private static Supplier<Double> laps = () -> 0.0;
    /**
    * Actualiza las rotaciones del motor de la torreta a la libreria.
    * @param supplier Las rotaciones del motor divididas entre la reducción
    */
    public static void setLaps(Supplier<Double> supplier) {
        laps = supplier;
    }

     /**
    * <p> Limita el número de vueltas que puede dar físicamente la torreta
    * @return Boolea: Se debe de correjir si o no
    */
    public static boolean shouldFix(){

        double MAXTURRETLAPS = 1.1;
        if (Math.abs(laps.get()) <= MAXTURRETLAPS) {
            return false;
        }
        return true;
    }
}