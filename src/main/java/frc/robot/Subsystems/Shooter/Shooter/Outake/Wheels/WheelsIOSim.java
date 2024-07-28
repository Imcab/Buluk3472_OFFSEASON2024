package frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import frc.robot.Subsystems.Shooter.ShooterConstants.OutakeConstants;

public class WheelsIOSim implements WheelsIO{
    private static final double LOOP_PERIOD_SECS = 0.02;

    private final DCMotorSim Wheels = new DCMotorSim(DCMotor.getKrakenX60(1), OutakeConstants.WheelReduction, 0.01998);

    private double WheelAppliedVolts = 0.0;

    @Override
    public void updateInputs(WheelsIOInputs inputs){
        Wheels.update(LOOP_PERIOD_SECS);

        inputs.WheelsAppliedVolts = WheelAppliedVolts;
        inputs.WheelsVelocityRadPerSec = Wheels.getAngularVelocityRadPerSec();
        inputs.WheelsVelocityRPM = Wheels.getAngularVelocityRPM();
        inputs.WheelsCurrentAmps = new double[]{Math.abs(Wheels.getCurrentDrawAmps())};
    }

    @Override
    public void setWheels(double voltage){
        WheelAppliedVolts = MathUtil.clamp(voltage, -12.0, 12.0);
        Wheels.setInputVoltage(voltage);
    }
}


