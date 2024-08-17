package frc.robot.Subsystems.Shooter.Turret;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Subsystems.Shooter.ShooterConstants.TurretConstants;
import frc.robot.util.HPPMathLib;
import frc.robot.util.NoteVisualizer;
import frc.robot.util.SmartTurret3472;

public class Turret extends SubsystemBase{
   private final TurretIO io;
   private final TurretIOInputsAutoLogged inputs = new TurretIOInputsAutoLogged();
   private final PIDController PIDController;
   private Rotation2d setpoint = null;
   private boolean limelight;
   private Double joystickValue = null;


   public Turret(TurretIO io){
        this.io = io;
        joystickValue = null;

        switch (Constants.currentMode) {
      case REAL:
      case REPLAY:
        PIDController = new PIDController(TurretConstants.kP, 0, TurretConstants.KD);
        break;
      case SIM:
        PIDController = new PIDController(TurretConstants.kPSIM, 0, TurretConstants.KDSIM);
        break;
      default:
        PIDController = new PIDController(0, 0, 0);
        break;
    }
    PIDController.enableContinuousInput(-Math.PI, Math.PI);
    
    setBrakeMode(true);

   }
   
   
   public void periodic(){
        io.updateInputs(inputs);

        Logger.processInputs("Shooter/Turret" , inputs);
        Logger.recordOutput("Shooter/Turret/pose3d" , getPose3d());
        Logger.recordOutput("Shooter/Turret/LimelightBased", limelight);
        Logger.recordOutput("SmartTurret/TurretOmega", SmartTurret3472.getOmega());
        Logger.recordOutput("SmartTurret/Vectors/TurretVector", SmartTurret3472.ToTurret());
        Logger.recordOutput("SmartTurret/Vectors/CalculatedSetpoint", SmartTurret3472.getSmartSetpoint());
        Logger.recordOutput("SmartTurret/Vectors/TurretValue", getYaw());
        Logger.recordOutput("Shooter/Turret/PIDVALUE", new Rotation2d().getRadians());
        Logger.recordOutput("Shooter/Turret/SETPOINTSFINDED", "");

        if (setpoint == null) {
          Logger.recordOutput("Shooter/Turret/SETPOINTSFINDED", "NO_SETPOINT");
        }

        if (setpoint != null) {
          Logger.recordOutput("Shooter/Turret/PIDVALUE", setpoint.getRadians());
          Logger.recordOutput("Shooter/Turret/SETPOINTSFINDED", "FIND_SETPOINT");
          joystickValue = null;
          if (limelight == false){
              io.setTurret(PIDController.calculate(getYaw().getRadians(), setpoint.getRadians()));
          }/*if(limelight == true){
              io.setTurret(-(PIDController.calculate(setpoint.getRadians(), 0)));
          }*/
        }
        if(setpoint == null && joystickValue != null){
            io.setTurret(joystickValue);
        }

        //SetupNoteVisualizer
        NoteVisualizer.setturretyawPoseSupplier(this::getYaw);
        //SetupSmartTurret
        SmartTurret3472.setTurretPoseSupplier(this::getYaw);
        /////////////////////


   }
   public Pose3d getPose3d(){
      Pose2d turretpose = new Pose2d(new Translation2d(), new Rotation2d(HPPMathLib.coterminalradianes(inputs.TurretPosition.getRadians())));
      return new Pose3d(turretpose);
   }
   public Rotation2d getYaw(){
      return new Rotation2d(HPPMathLib.coterminalradianes(inputs.TurretPosition.getRadians()));
   }
   public Rotation2d runTurret(double angle){
        setpoint = new Rotation2d(angle);
        return setpoint;
   }
   public double runWithJoystick(double speed){
      joystickValue = speed;
      return joystickValue;
   }
   public Rotation2d getTurretPosition(){
      return inputs.TurretPosition;
   }
   public boolean VisionStatus(boolean status){
      limelight = status;
      return limelight;
   }

   public void stop() {
    io.setTurret(0.0);
    setpoint = null;
    joystickValue = null;
    
  }
   public void setBrakeMode(boolean enabled) {
    io.setTurretBrakeMode(enabled);
  }
}
