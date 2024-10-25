package frc.robot.Subsystems.Shooter.Turret;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.LimitYaw3472;
import frc.robot.Constants;
import frc.robot.Subsystems.Shooter.ShooterConstants.TurretConstants;
import frc.robot.util.NoteVisualizer;
import frc.robot.util.SmartTurret3472;

public class Turret extends SubsystemBase{

   private final TurretIO io;
   private final TurretIOInputsAutoLogged inputs = new TurretIOInputsAutoLogged();
   private final PIDController PIDController, lim;

   public Turret(TurretIO io){
        this.io = io;
  
        switch (Constants.currentMode) {
      case REAL:
      case REPLAY:
        PIDController = new PIDController(TurretConstants.turretConfig.getP(), TurretConstants.turretConfig.getI(), TurretConstants.turretConfig.getD());
        lim = new PIDController(TurretConstants.limeConfig.getP(), TurretConstants.limeConfig.getI(), TurretConstants.limeConfig.getD());
        break;
      case SIM:
        PIDController = new PIDController(TurretConstants.simTurretConfig.getP(), TurretConstants.simTurretConfig.getI(), TurretConstants.simTurretConfig.getD());
        lim = new PIDController(TurretConstants.limeConfig.getP(), TurretConstants.limeConfig.getI(), TurretConstants.limeConfig.getD());
        break;
      default:
        PIDController = new PIDController(0, 0, 0);
        lim = new PIDController(TurretConstants.limeConfig.getP(), TurretConstants.limeConfig.getI(), TurretConstants.limeConfig.getD());
        break;
    }
    PIDController.enableContinuousInput(-180, 180);
    
    //PIDController.setTolerance(4);

    setBrakeMode(true);
   }

   public void periodic(){
        io.updateInputs(inputs);
        
        Logger.processInputs("Shooter/Turret" , inputs);
        Logger.recordOutput("Shooter/Turret/pose3d" , getPose3d());
        Logger.recordOutput("SmartTurret/TurretOmega", SmartTurret3472.getOmega());
        Logger.recordOutput("SmartTurret/Vectors/TurretVector", SmartTurret3472.ToTurret());
        Logger.recordOutput("SmartTurret/Vectors/TurretValue", getYaw());
        Logger.recordOutput("Shooter/Turret/PIDVALUE", new Rotation2d().getRadians());
        Logger.recordOutput("Shooter/Turret/TURRETLAPS", getLaps());
        Logger.recordOutput("SmartTurret/Vectors/SpeakerVector", SmartTurret3472.ToSpeaker());

        //SetupNoteVisualizer
        NoteVisualizer.setturretyawPoseSupplier(this::getYaw);
        //SetupSmartTurret
        SmartTurret3472.setTurretPoseSupplier(this::getYaw);
        //SetupLimiYaw
        LimitYaw3472.setLaps(this::getLaps);
        /////////////////////

        SmartDashboard.putNumber("Encoder Torreta", getDegrees());  
   }
   public Pose3d getPose3d(){
      Pose2d turretpose = new Pose2d(new Translation2d(), new Rotation2d(inputs.TurretPosition));
      return new Pose3d(turretpose);
   }
   public Rotation2d getYaw(){
      return new Rotation2d((getRadians()));
   }
   public double getDegrees(){
      return inputs.TurretPosition;
   }
   public double getRadians(){
      return Units.degreesToRadians(inputs.TurretPosition);
   }

   public double getLaps(){
      return inputs.TurretLaps;
   }

   public void runTurret(double angle){
      
      io.setTurret(-PIDController.calculate(getDegrees(), angle));
      Logger.recordOutput("Shooter/Turret/PIDVALUE", angle);
      Logger.recordOutput("Shooter/Turret/SETPOINTSFINDED", "FIND_SETPOINT");
   }

   public void runSmart(){
      io.setTurret(-PIDController.calculate(SmartTurret3472.getOmega(), 0));
      SmartDashboard.putNumber("PID", -PIDController.calculate(SmartTurret3472.getOmega(), 0));
   }

   public void runWithJoystick(double speed){
      io.setTurret(speed);
   }
   public void runVision(double TX){
      io.setTurret(-(lim.calculate(TX, 0)));   
   }
   public void stop() {
    io.setTurret(0.0);
  }
   public void setBrakeMode(boolean enabled) {
    io.setTurretBrakeMode(enabled);
  }
}
