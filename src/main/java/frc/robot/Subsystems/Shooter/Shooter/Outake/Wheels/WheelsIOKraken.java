package frc.robot.Subsystems.Shooter.Shooter.Outake.Wheels;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.util.Units;
import frc.robot.Subsystems.Shooter.ShooterConstants.OutakeConstants;

public class WheelsIOKraken implements WheelsIO{
    private final TalonFX right;
    private final TalonFX left;

    public WheelsIOKraken(){
        right = new TalonFX(OutakeConstants.RightWHeelsPort);
        left = new TalonFX(OutakeConstants.LeftWHeelsPort);

        right.setInverted(OutakeConstants.RightMotorReversed);
        left.setInverted(OutakeConstants.LeftMotorReversed);

        var slot0Config = new Slot0Configs();

        slot0Config.kP = OutakeConstants.wheelsConfig.getP();
        slot0Config.kI = OutakeConstants.wheelsConfig.getI();
        slot0Config.kD = OutakeConstants.wheelsConfig.getD();
        slot0Config.kS = OutakeConstants.wheelsConfig.getS();
        slot0Config.kV = OutakeConstants.wheelsConfig.getV();

        right.getConfigurator().apply(slot0Config);
        left.getConfigurator().apply(slot0Config);

    }

    @Override
    public void updateInputs(WheelsIOInputs inputs){

        
        inputs.RWheelsAppliedVolts = right.getMotorVoltage().getValueAsDouble();
        inputs.RWheelsCurrentAmps = right.getSupplyCurrent().getValueAsDouble();
        inputs.RWheelsVelocityRPS = right.getVelocity().getValueAsDouble();
        inputs.RWheelsVelocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(right.getVelocity().getValueAsDouble());

        inputs.LWheelsAppliedVolts = left.getMotorVoltage().getValueAsDouble();
        inputs.LWheelsCurrentAmps = left.getSupplyCurrent().getValueAsDouble();
        inputs.LWheelsVelocityRPS = left.getVelocity().getValueAsDouble();
        inputs.LWheelsVelocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(left.getVelocity().getValueAsDouble());

        

    }

    @Override
    public void setWheels(double rps, double ff)
    {
        final VelocityVoltage m_request = new VelocityVoltage(0).withSlot(0);

        right.setControl(m_request.withVelocity(rps).withFeedForward(ff));
        left.setControl(m_request.withVelocity(rps).withFeedForward(ff));
    }

    @Override
    public void runSpeed(double speed){
        right.set(speed);
        left.set(speed);
    }

    @Override
    public void stop(){
        right.set(0);
        left.set(0);
    }

    
}
