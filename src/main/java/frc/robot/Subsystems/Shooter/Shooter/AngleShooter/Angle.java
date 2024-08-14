package frc.robot.Subsystems.Shooter.Shooter.AngleShooter;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Subsystems.Shooter.ShooterConstants.AngleShooterConstants;
import frc.robot.util.NoteVisualizer;

public class Angle extends SubsystemBase{

   private final AngleIO io;
   private final AngleIOInputsAutoLogged inputs = new AngleIOInputsAutoLogged();
   private final PIDController PIDController;
   private Rotation2d setpoint = null;
   private boolean limelight;
   private Double joystickValue = null;
   private Rotation2d turretRotation2d = new Rotation2d();
   
   public Angle(AngleIO io){
        
        this.io = io;
        joystickValue = null;

        switch (Constants.currentMode) {
      case REAL:
      case REPLAY:
        PIDController = new PIDController(AngleShooterConstants.kP, 0, AngleShooterConstants.KD);
        break;
      case SIM:
        PIDController = new PIDController(AngleShooterConstants.kPSIM, 0, AngleShooterConstants.KDSIM);
        break;
      default:
        PIDController = new PIDController(0, 0, 0);
        break;
    }

    setBrakeMode(true);

   }

   public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Shooter/AngleShooter" , inputs);
        Logger.recordOutput("Shooter/AngleShooter/pose3d" , getPose3d());
        Logger.recordOutput("Shooter/AngleShooter/LimelightBased", limelight);

        SmartDashboard.putNumber("AngleShooter", inputs.ShooterPosition.getDegrees());


        if (setpoint != null) {
          joystickValue = null;
          if (limelight == false){
              io.setAngleShooter(PIDController.calculate(inputs.ShooterPosition.getRadians(), setpoint.getRadians()));
          }/*if(limelight == true){
              io.setTurret(-(PIDController.calculate(setpoint.getRadians(), 0)));
          }*/
        }
        if(setpoint == null && joystickValue != null){
            io.setAngleShooter(joystickValue * 0.1);
        }

        //Setup NoteVisualizer
        NoteVisualizer.setshooterpitchPoseSupplier(this::getShooterPitch);
        //////////////////////
   }

   public Pose3d getPose3d(){
     
        Rotation3d rotation3d = new Rotation3d(0, -inputs.ShooterPosition.getRadians(), turretRotation2d.getRadians());

        return new Pose3d(0.0, 0, 0.35, rotation3d);
   }
   public Rotation2d getShooterPitch(){
     return new Rotation2d(inputs.ShooterPosition.getRadians());
   }
   public Rotation2d UpdateTurretZ(Rotation2d TurretPosition){
        turretRotation2d = TurretPosition;
        return turretRotation2d;
   }
   public Rotation2d runShooterAngle(double angle){
        setpoint = new Rotation2d(angle);
        return setpoint;
   }
   public double runWithJoystick(double speed){
        joystickValue = speed;
        return joystickValue;
   }

   public boolean VisionStatus(boolean status){
        limelight = status;
        return limelight;
   }
   public Rotation2d getShooterPosition(){
        return inputs.ShooterPosition;
   }

   public void stop(){
        io.setAngleShooter(0);
        setpoint = null;
        joystickValue = null;
   }
   public void stopjoystick(){
        io.setAngleShooter(0);
        joystickValue = null;
   }


   public void setBrakeMode(boolean enabled){
    io.setAngleBrakeMode(enabled);
   }

}   
