package frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Subsystems.Shooter.ShooterConstants.OutakeConstants;
import frc.robot.util.Field3472;

public class Wheels extends SubsystemBase{
    private final WheelsIO io;
    private final WheelsIOInputsAutoLogged inputs = new WheelsIOInputsAutoLogged();
    private final PIDController PIDController;
    private final SimpleMotorFeedforward FeedForwardController;
    private Double joystickValue = null;

    
    public Wheels(WheelsIO io){
        this.io = io;
        joystickValue = null;

        switch (Constants.currentMode) {
      case REAL:
      case REPLAY:
        PIDController = new PIDController(OutakeConstants.kP, 0, OutakeConstants.KD);
        FeedForwardController = new SimpleMotorFeedforward(0, OutakeConstants.kV);
        break;
      case SIM:
        PIDController = new PIDController(OutakeConstants.kPSIM, 0, OutakeConstants.KDSIM);
        FeedForwardController = new SimpleMotorFeedforward(0, OutakeConstants.kVSIM);
        break;
      default:
        PIDController = new PIDController(0, 0, 0);
        FeedForwardController = new SimpleMotorFeedforward(0, 0);
        break;
        
        
    }
    setOutakeBrakeMode(true);
  }

  public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Shooter/Outake/Wheels", inputs);
        Logger.recordOutput("Shooter/Outake/Setpoint", 0.0);
        Logger.recordOutput("Shooter/Outake/SetpointRadPerSec", 0.0);

        Logger.recordOutput("Field3472/Zone/Current", Field3472.getZone());
        Logger.recordOutput("Field3472/Zone/Priority", Field3472.getPriority());
        Logger.recordOutput("Field3472/RobotX", Field3472.getRobotPoseX());

        SmartDashboard.putString("PriorityFieldZone", Field3472.getPriority());
        
  }

  public void setGoalRPM(double RPM){
      double velocitysetpoint = Units.rotationsPerMinuteToRadiansPerSecond(RPM);
      Logger.recordOutput("Shooter/Outake/SetpointRPM", RPM);
      Logger.recordOutput("Shooter/Outake/SetpointRadPerSec", velocitysetpoint);
      io.setWheels(FeedForwardController.calculate(velocitysetpoint) + PIDController.calculate(inputs.WheelsVelocityRadPerSec, velocitysetpoint));
  }

  public double getShooterRPM(){
      return Units.radiansPerSecondToRotationsPerMinute(inputs.WheelsVelocityRadPerSec);
  }

  public void stop(){
    io.setWheels(0.0);
    joystickValue = null;
  }
  public void setOutakeBrakeMode(boolean enabled){
        io.setWheelsBrakeMode(enabled);
    }

}
