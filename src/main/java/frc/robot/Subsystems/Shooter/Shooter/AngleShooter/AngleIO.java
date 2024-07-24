package frc.robot.Subsystems.Shooter.Shooter.AngleShooter;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.geometry.Rotation2d;


public interface AngleIO {
@AutoLog
    public static class AngleIOInputs {

        public double AngleAppliedVolts = 0.0;
        public Rotation2d ShooterPosition = new Rotation2d();
        public double AngleVelocityRadPerSec = 0.0;
        public double [] AngleCurrentAmps = new double[]{};

    }

    /** Updates the set of loggable inputs. */
    public default void updateInputs(AngleIOInputs inputs) {}

    /** Run the motor at the specified voltage. */
    public default void setAngleShooter(double voltage) {}

    /** Run the motor at the specified voltage. */
    public default void setAngleBrakeMode(boolean enable) {}
}
