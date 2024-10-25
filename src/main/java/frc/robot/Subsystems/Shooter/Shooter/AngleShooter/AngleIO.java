package frc.robot.Subsystems.Shooter.Shooter.AngleShooter;

import org.littletonrobotics.junction.AutoLog;

public interface AngleIO {
@AutoLog
    public static class AngleIOInputs {

        public double AngleAppliedVolts = 0.0;
        public double ShooterPosition = 0.0;
        public double AngleVelocityRadPerSec = 0.0;
        public double  AngleCurrentAmps = 0.0;

    }

    /** Updates the set of loggable inputs. */
    public default void updateInputs(AngleIOInputs inputs) {}

    /** Run the motor at the specified voltage. */
    public default void setAngleShooter(double voltage) {}

    /** Run the motor only with and static value 0 to 1. */
    public default void set(double value) {}

}
