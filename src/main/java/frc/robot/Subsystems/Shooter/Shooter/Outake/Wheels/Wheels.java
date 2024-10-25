package frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Subsystems.Shooter.ShooterConstants.OutakeConstants;
import frc.robot.util.Field3472;

public class Wheels extends SubsystemBase{
    private final WheelsIO io;
    private final WheelsIOInputsAutoLogged inputs = new WheelsIOInputsAutoLogged();
    private final SimpleMotorFeedforward ff;
    
    public Wheels(WheelsIO io){
        this.io = io;
        ff = new SimpleMotorFeedforward(OutakeConstants.wheelsConfig.getS(), OutakeConstants.wheelsConfig.getV());
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
        
        SmartDashboard.putNumber("RPS", getShooterRPS());
  }

  public void setGoalRPS(double RPS){
        io.setWheels(RPS, ff.calculate(RPS));
  }

  public void setSpeed(double speed){
        io.runSpeed(speed);
  }

  public double getShooterRPS(){
      return inputs.RWheelsVelocityRPS;
  }

  public void stop(){
        io.stop();
  }

}
