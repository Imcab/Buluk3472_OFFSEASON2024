package frc.robot.Subsystems.Swerve;

import org.littletonrobotics.junction.AutoLog;

public interface ModuleIO{
    @AutoLog
    public static class ModuleIOInputs {
    
      public double driveAppliedVolts = 0.0;

      public double turnAppliedVolts = 0.0;

      public double drivePositionMeters = 0.0;

      public double driveVelocity = 0.0;

      public double AngleEncoderPosition = 0.0;

      public double  MODULEPIDSTATUS = 0.0;

  }

  /** Updates the set of loggable inputs. */
  public default void updateInputs(ModuleIOInputs inputs) {}

  /** Run the drive motor at the specified voltage. */
  public default void setDrive(double speed) {}

  /** Run the turn motor at the specified voltage. */
  public default void setTurn(double speed) {}

}

    
    

