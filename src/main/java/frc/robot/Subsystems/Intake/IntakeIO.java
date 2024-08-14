package frc.robot.Subsystems.Intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO{

    @AutoLog
    public static class IntakeIOInputs{

      public double IntakeAppliedVolts = 0.0;
      public double IntakeVelocityRadPerSec = 0.0;
      public double [] IntakeCurrentAmps = new double[] {};

    }
        /** Updates the set of loggable inputs. */
    public default void updateInputs(IntakeIOInputs inputs) {}

      /** Run the motor at the specified voltage. */
    public default void setIntake(double voltage) {}

    public default void setIntakeBrakeMode(boolean enable) {}


}
