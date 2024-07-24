package frc.robot.Subsystems.Swerve;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.geometry.Rotation2d;

public interface ModuleIO{
    @AutoLog
    public static class ModuleIOInputs {
    
      public double driveAppliedVolts = 0.0;
      public double drivePositionRad = 0.0;
      public double driveVelocityRadPerSec = 0.0;
      public double[] driveCurrentAmps = new double[] {};

      public double turnAppliedVolts = 0.0;
      public Rotation2d TurningPosition = new Rotation2d();
      public Rotation2d AngleEncoderPosition = new Rotation2d();
      public double turnVelocityRadPerSec = 0.0;
      public double[] TurnCurrentAmps = new double[] {};

  }

  /** Updates the set of loggable inputs. */
  public default void updateInputs(ModuleIOInputs inputs) {}

  /** Run the drive motor at the specified voltage. */
  public default void setDrive(double voltage) {}

  /** Run the turn motor at the specified voltage. */
  public default void setTurn(double voltage) {}

  public default void setDriveBrakeMode(boolean enable) {}

  public default void setTurnBrakeMode(boolean enable) {}

}

    
    

