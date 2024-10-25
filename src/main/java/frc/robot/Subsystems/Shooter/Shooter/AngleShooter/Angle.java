package frc.robot.Subsystems.Shooter.Shooter.AngleShooter;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Subsystems.Shooter.ShooterConstants;
import frc.robot.Subsystems.Shooter.ShooterConstants.AngleShooterConstants;
import frc.robot.util.NoteVisualizer;

public class Angle extends SubsystemBase{

   private final AngleIO io;
   private final AngleIOInputsAutoLogged inputs = new AngleIOInputsAutoLogged();
   private Rotation2d turretRotation2d = new Rotation2d();
   private final PIDController pid,lim;
   
   public Angle(AngleIO io){ 
        this.io = io;
        pid = new PIDController(AngleShooterConstants.angleConfig.getP(), AngleShooterConstants.angleConfig.getI(), AngleShooterConstants.angleConfig.getD());
        lim = new PIDController(AngleShooterConstants.limelightConfig.getP(), AngleShooterConstants.limelightConfig.getI(), AngleShooterConstants.limelightConfig.getD());

   }

   public void periodic(){

     //PIDController.setTolerance(0);

     io.updateInputs(inputs);
     Logger.processInputs("Shooter/AngleShooter" , inputs);
     Logger.recordOutput("Shooter/AngleShooter/pose3d" , getPose3d());
     Logger.recordOutput("Shooter/AngleShooter/Setpoint", new Rotation2d().getRadians());

     SmartDashboard.putNumber("Angulador", inputs.ShooterPosition);
     //Setup NoteVisualizer
     NoteVisualizer.setshooterpitchPoseSupplier(this::getShooterPitch);
     //////////////////////

     Logger.recordOutput("Shooter/AngleShooter/PID/error", pid.getPositionError());
     Logger.recordOutput("Shooter/AngleShooter/PID/P", pid.getP());
     Logger.recordOutput("Shooter/AngleShooter/PID/I", pid.getI());
     Logger.recordOutput("Shooter/AngleShooter/PID/D", pid.getD());
     Logger.recordOutput("Shooter/AngleShooter/PID/Poisiton", inputs.ShooterPosition);
     Logger.recordOutput("Shooter/AngleShooter/PID/vel", inputs.AngleVelocityRadPerSec);

   }

   public Pose3d getPose3d(){
     
        Rotation3d rotation3d = new Rotation3d(0, -inputs.ShooterPosition, turretRotation2d.getRadians());

        return new Pose3d(0.0, 0, 0.35, rotation3d);
   }
   public Rotation2d getShooterPitch(){
     return new Rotation2d(inputs.ShooterPosition);
   }
   public Rotation2d UpdateTurretZ(Rotation2d TurretPosition){
        turretRotation2d = TurretPosition;
        return turretRotation2d;
   }
   public void runShooterAngle(double angle){
          Rotation2d goal = new Rotation2d(angle);
          Logger.recordOutput("Shooter/AngleShooter/Setpoint", goal.getRadians());
          io.setAngleShooter(pid.calculate(inputs.ShooterPosition, angle));

   }

   public void runSpeed(double speed){
        io.setAngleShooter(speed);    
   }

   public double getDegrees(){
     return inputs.ShooterPosition;
   }

   public void RunVisionStatus(double TY){
        io.setAngleShooter(-lim.calculate(TY, 0) * 0.6);
   }
   public Rotation2d getShooterPosition(){
        return new Rotation2d(inputs.ShooterPosition);
   }

   public void stop(){
        io.setAngleShooter(0);
   }

}   
