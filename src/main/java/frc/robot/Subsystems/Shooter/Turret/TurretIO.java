package frc.robot.Subsystems.Shooter.Turret;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.geometry.Rotation2d;

public interface TurretIO {
    @AutoLog
    public static class TurretIOInputs {

      public double TurretAppliedVolts = 0.0;
      public Rotation2d TurretPosition = new Rotation2d();
      public double TurretPositionnorot = 0.0;
      public double TurretVelocityRadPerSec = 0;
      public double [] TurretCurrentAmps = new double[] {};
      public double CurrentSpeed = 0;

  }

  /** Updates the set of loggable inputs. */
  public default void updateInputs(TurretIOInputs inputs) {}

  /** Run the motor at the specified voltage. */
  public default void setTurret(double voltage) {}

  public default void setTurretBrakeMode(boolean enable) {}

}
