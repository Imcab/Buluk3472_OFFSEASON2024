package frc.robot.Subsystems.Swerve;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.geometry.Rotation2d;

public interface GyroIO {
@AutoLog
    public static class GyroIOInputs {
      public boolean connected = false;
      public double Gyroangle = 0.0;
      public Rotation2d Gyrorotation2d = new Rotation2d();
  }

  /** Updates the set of loggable inputs. */
  public default void updateInputs(GyroIOInputs inputs) {}

}
