package frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels;

import org.littletonrobotics.junction.AutoLog;

public interface WheelsIO {
    @AutoLog
    public static class WheelsIOInputs{

      public double LWheelsAppliedVolts = 0.0;
      public double LWheelsVelocityRadPerSec = 0;
      public double LWheelsVelocityRPS = 0;
      public double LWheelsCurrentAmps = 0.0;

      public double RWheelsAppliedVolts = 0.0;
      public double RWheelsVelocityRadPerSec = 0;
      public double RWheelsVelocityRPS = 0;
      public double RWheelsCurrentAmps = 0.0;

  }

  /** Updates the set of loggable inputs. */
  public default void updateInputs(WheelsIOInputs inputs) {}

  /** Run the motor at the specified voltage. */
  public default void setWheels(double voltage, double ff) {}

  public default void setWheelsBrakeMode(boolean enable) {}

  public default void runSpeed(double speed){}
  
  public default void stop(){}

}
