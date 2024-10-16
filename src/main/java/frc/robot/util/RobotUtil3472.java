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

import edu.wpi.first.hal.can.CANStatus;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.DriverStation.MatchType;
import edu.wpi.first.wpilibj.Timer;

/** Funciones de utilidad en enfoque general en los estados del robot, cancha e información:
 * <p>DRIVERSTATION: Obtiene datos de la driverstation, tales como la alianza, los modos y mandar alertas
 * <p>ROBOTSTATE: Obtiene estados del robot, la temperatura,el CAN y si esta en Brownout
 * <p>REV_PDH: Obtiene lecturas de la PDH, Electrica, y diagnostica errores
 */
public class RobotUtil3472 {

    /**FUNCIONES QUE OCUPAN LA DRIVERSTATION */
    public class DRIVERSTATION {
        /**
        * Obtiene el tiempo restante del partido
        * @return El tiempo restante en segundos.
        */
        public static double getRemainingTime(){
            double matchTime = Timer.getMatchTime();

            if (matchTime == -1) {
                System.out.println("Match time is not available");
                return -1;
            }
            return matchTime;
        }
        /**
        * Obtiene la alianza azul
        * @return True si es azul, false si es rojo.
        */
        public static boolean isBlue(){
            return DriverStation.getAlliance().isPresent()
            && DriverStation.getAlliance().get() == Alliance.Blue;
        }
        /**
        * Obtiene la alianza roja
        * @return True si es roja, false si es azul.
        */
        public static boolean isRed(){
            return DriverStation.getAlliance().isPresent()
            && DriverStation.getAlliance().get() == Alliance.Red;
        }
        /**
        * Obtiene el modo teleop
        * @return Boolean
        */
        public static boolean isTeleop(){
            return DriverStation.isTeleop();
        }
        /**
        * Obtiene el modo auto
        * @return Boolean
        */
        public static boolean isAuto(){
            return DriverStation.isAutonomous();
        }
        /**
        * Obtiene si el robot esta prendido o no
        * @return Boolean
        */
        public static boolean isRobotEnabled(){
            return DriverStation.isEnabled();
        }
        /**
        * El teleop esta prendido o no
        * @return Boolean
        */
        public static boolean isTeleopEnabled(){
            return DriverStation.isTeleopEnabled();
        }
        /**
        * El autonomo esta prendido o no
        * @return Boolean
        */
        public static boolean isAutonomousEnabled(){
            return DriverStation.isAutonomousEnabled();
        }
        /**
        * Manda una alerta a la driverStation
        * @param warning El texto a mostrar 
        * @param printTrace true, print with stackTrace
        */
        public static void SendAlert(String warning, boolean printTrace){
            DriverStation.reportWarning(warning, printTrace);
        }
        /**
        * Obtiene el tipo de partida que se esta jugando:
        * <p>-Practice
        * <p>-Qualification
        *<p>-Elimination
        *<p>-None
        * @return La partida que se esta jugando.
        */
        public static MatchType getMatch(){
        return DriverStation.getMatchType();
        }
            
    }

    /**FUNCIONES QUE OCUPAN LA ROBORIO */
    public class ROBOTSTATE{
        /**
        * Obtiene el Voltaje actual de la bateria
        * @return Battery Current Volts 
        */
        public static double getBatteryVoltage(){
            return RobotController.getBatteryVoltage();
        }
        /**
        * Obtiene el status del CAN
        * @return CAN Status
        */
        public static CANStatus getCANStatus(){
            return RobotController.getCANStatus();
        }
        public static double percentBusUtilization(){
            return getCANStatus().percentBusUtilization;
        }
        public static int busOffCount(){
            return getCANStatus().busOffCount;
        }
        public static int receiveErrorCount(){
            return getCANStatus().receiveErrorCount;
        }
        public static int transmitErrorCount(){
            return getCANStatus().transmitErrorCount;
        }
        public static int txFullCount(){
            return getCANStatus().txFullCount;
        }

        public static void setCANStatus(double percentBusUtilization, int busOffCount,int txFullCount, int receiveErrorCount, int transmitErrorCount){
            getCANStatus().setStatus(percentBusUtilization,busOffCount, txFullCount, receiveErrorCount, transmitErrorCount);
        }
        /**
        * Obtiene el porcentaje de can utilizado
        * @return percentBusUtilization
        */
        public static double getCANUtilization(){
            return getCANStatus().percentBusUtilization;
        }
        
        /**
        * Obtiene la temperatura del CPU
        * @return La temperatura en Celcius
        */
        public static double getTemperature(){
            return RobotController.getCPUTemp();
        }
        /**
        * Obtiene si el robot entró en un estado de Brownout
        * @return Boolean
        */
        public static boolean isSystemBrownout(){
            return RobotController.isBrownedOut();
        }
        /**
        * Obtiene los amperios y los voltios en una lista:
        *<p> Primer dato: La corriente del robot (En Amperios)
        *<p> Segundo dato: El voltaje del robot expresado en volt
        * @return Lista de valores, AMPS, VOLTS
        */
        public static double getCurrent(){
            return RobotController.getInputCurrent();
        }
        public static double getVoltage(){
            return RobotController.getInputVoltage();
        }
    }

    /**FUNCIONES QUE OCUPAN LA PDH DE REV */
    public class REV_PDH {

        /**CAN ID a la cual al PDH esta conectada*/
        public static final int PDH_CAN_ID = 1;
        /**Declara la PDH */

        private static Supplier<PowerDistribution> PDH = ()-> new PowerDistribution();
        //private final PowerDistribution PDH = new PowerDistribution(PDH_CAN_ID, ModuleType.kRev);



         /**TIPOS DE ERRORES EN LA PDH*/
        public enum DIAGNOSTIC{
            BreakerFault, Brownout, CanWarning , HardWare,NONE
            
        }

        public static void getPDH(Supplier<PowerDistribution> Supplier){
            PDH = Supplier;
        }
        /**
        * Obtiene la temperatura de la PDH
        * @return Temperatura actual en Celsius
        */
        public static double getPDH_Temp(){
            return PDH.get().getTemperature();
        }
        /**
        * Obtiene la energia de la PDH en Joules
        * @return Energía que agarra la PDH
        */
        public static double getPDH_Energy(){
            return PDH.get().getTotalEnergy();
        }
        /**
        * Obtiene los Watts de potencia de la PDH
        * @return Watts
        */
        public static double getPDH_Watts(){
            return PDH.get().getTotalPower();
        }
        /**
        * Obtiene los Amperios de corriente que jala la PDH
        * @return AMPS
        */
        public static double getPDH_AMPS(){
            return PDH.get().getTotalCurrent();
        }

        /**
        * Resetea la energia total a 0
        */
        public static void PDHResetEnergy(){
            PDH.get().resetTotalEnergy();
        }
        /**
         * Limpia las fallas que ya no sean necesarias
         */
        public static void CrealStickyFaults(){
            PDH.get().clearStickyFaults();
        }
        /**
         * Diagnostica fallos en el breaker con un canal especifico.
         */
        public static DIAGNOSTIC BreakerChannelDiagnostic(int channel){
            if (PDH.get().getFaults().getBreakerFault(channel)) {
                DRIVERSTATION.SendAlert("Breaker Fault at: " + channel, false);
                return DIAGNOSTIC.BreakerFault;
            }
            return DIAGNOSTIC.NONE;
        }
        /**
        * @return El numero de canales de la PDH (24)
        */
        public static int getPDHChannels(){
            return PDH.get().getNumChannels();
        }
        /**
         * Obtiene los Amperios de un canal en específico.
         * @param channel
         * @return La corriente que agarra el canal
         */
        public static double checkChannel(int channel){
            return PDH.get().getCurrent(channel);
        }
        /**
         * Obtiene el estado de el switch en la PDH (ON/OFF)
         * @return El estado de el switch de la PDH (LEDS)
         */
        public static boolean checkSwitchableChannel(){
            return PDH.get().getSwitchableChannel();
        }
        /**
         * Cambia el estado del switch de la PDH(ON/OFF)
         */
        public static void ToggleSwitchableChannel(boolean status){
            PDH.get().setSwitchableChannel(status);
        }
    }
    
}

