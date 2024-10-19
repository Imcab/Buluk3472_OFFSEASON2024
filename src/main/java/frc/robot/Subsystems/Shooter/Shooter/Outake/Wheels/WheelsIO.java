package frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels;

import org.littletonrobotics.junction.AutoLog;

public interface WheelsIO {
    @AutoLog
    public static class WheelsIOInputs{

      public double LWheelsAppliedVolts = 0.0;
      public double LWheelsVelocityRadPerSec = 0;
      public double LWheelsVelocityRPM = 0;
      public double [] LWheelsCurrentAmps = new double[] {};

      public double RWheelsAppliedVolts = 0.0;
      public double RWheelsVelocityRadPerSec = 0;
      public double RWheelsVelocityRPM = 0;
      public double [] RWheelsCurrentAmps = new double[] {};

  }

  /** Updates the set of loggable inputs. */
  public default void updateInputs(WheelsIOInputs inputs) {}

  /** Run the motor at the specified voltage. */
  public default void setWheels(double voltage) {}

  public default void setWheelsBrakeMode(boolean enable) {}

}
