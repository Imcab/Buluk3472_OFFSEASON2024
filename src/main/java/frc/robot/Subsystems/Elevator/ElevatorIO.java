package frc.robot.Subsystems.Elevator;

import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {
    @AutoLog
    public static class ElevatorIOInputs {

      public double ElevatorAppliedVolts = 0.0;
      public double ElevatorMeters = 0.0;
      public double ElevatorVelocityRadPerSec = 0;
      public double [] ElevatorCurrentAmps = new double[] {};

    }

    /** Updates the set of loggable inputs. */
  public default void updateInputs(ElevatorIOInputs inputs) {}

  /** Run the motor at the specified voltage. */
  public default void setElevator(double voltage) {}

  public default void setElevatorBrakeMode(boolean enable) {}

}
