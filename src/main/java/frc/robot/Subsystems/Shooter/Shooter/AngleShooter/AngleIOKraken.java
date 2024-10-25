package frc.robot.Subsystems.Shooter.Shooter.AngleShooter;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.robot.Subsystems.Shooter.ShooterConstants.AngleShooterConstants;

public class AngleIOKraken implements AngleIO{

    private final TalonFX angulador;
    private final DutyCycleEncoder encoder;
    double offset = Units.degreesToRotations(AngleShooterConstants.offset);
 
    public AngleIOKraken(){

        angulador = new TalonFX(AngleShooterConstants.AngleShooterPort);
        encoder = new DutyCycleEncoder(AngleShooterConstants.EncPort);

        angulador.setInverted(AngleShooterConstants.AngleMotorReversed);

        /*var slot0Config = new Slot0Configs();

        slot0Config.kP = AngleShooterConstants.angleConfig.getP();
        slot0Config.kI = AngleShooterConstants.angleConfig.getI();
        slot0Config.kD = AngleShooterConstants.angleConfig.getD();
        slot0Config.kS = AngleShooterConstants.angleConfig.getS();
        slot0Config.kV = AngleShooterConstants.angleConfig.getV();

        angulador.getConfigurator().apply(slot0Config);
        */

    }

    @Override
    public void updateInputs(AngleIOInputs inputs){

        inputs.AngleAppliedVolts = angulador.getMotorVoltage().getValueAsDouble();
        inputs.AngleCurrentAmps =  angulador.getSupplyCurrent().getValueAsDouble();
        inputs.ShooterPosition = -Units.rotationsToDegrees(encoder.getAbsolutePosition() - offset);
        inputs.AngleVelocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(angulador.getVelocity().getValueAsDouble());

    }

    @Override
    public void setAngleShooter(double value){
        angulador.set(value);
    }

    


}
