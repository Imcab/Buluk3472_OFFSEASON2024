package frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels;

import org.littletonrobotics.junction.AutoLog;

public interface WheelsIO {
    @AutoLog
    public static class WheelsIOInputs{

      public double WheelsAppliedVolts = 0.0;
      public double WheelsVelocityRadPerSec = 0;
      public double WheelsVelocityRPM = 0;
      public double [] WheelsCurrentAmps = new double[] {};

  }

  /** Updates the set of loggable inputs. */
  public default void updateInputs(WheelsIOInputs inputs) {}

  /** Run the motor at the specified voltage. */
  public default void setWheels(double voltage) {}

  public default void setWheelsBrakeMode(boolean enable) {}

}
