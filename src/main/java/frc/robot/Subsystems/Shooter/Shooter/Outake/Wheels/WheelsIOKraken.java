package frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels;

import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Subsystems.Shooter.ShooterConstants.OutakeConstants;

public class WheelsIOKraken implements WheelsIO{
private final TalonFX right;
    private final TalonFX left;

    public WheelsIOKraken(){
        right = new TalonFX(OutakeConstants.RightWHeelsPort);
        left = new TalonFX(OutakeConstants.LeftWHeelsPort);
    }

    @Override
    public void updateInputs(WheelsIOInputs inputs){
        inputs.RWheelsAppliedVolts = right.getMotorVoltage().getValueAsDouble();
        inputs.RWheelsCurrentAmps = new double[]{};
    }
}
