package frc.robot.Subsystems.Shooter.Turret;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.robot.Subsystems.Shooter.ShooterConstants.TurretConstants;

public class TurretIOSparkMax implements TurretIO{

    private final CANSparkMax Turret;
    private final RelativeEncoder enc_turret;
    private final DutyCycleEncoder ThroughBore;
    private final boolean TurretReversed;
    private final double Offset;

    public TurretIOSparkMax(){
        Turret = new CANSparkMax(TurretConstants.TurretPort, MotorType.kBrushless);
        Offset = (TurretConstants.offset/360);
        TurretReversed = TurretConstants.TurretReversed;
        ThroughBore = new DutyCycleEncoder(TurretConstants.EncDIOPORT);
        ThroughBore.setPositionOffset(Offset);


        Turret.restoreFactoryDefaults();

        Turret.setCANTimeout(250);

        enc_turret = Turret.getEncoder();

        Turret.setInverted(TurretReversed);

        Turret.enableVoltageCompensation(12.0);

        enc_turret.setPosition(0.0);
        enc_turret.setMeasurementPeriod(10);
        enc_turret.setAverageDepth(2);

        Turret.setCANTimeout(0);
        
        Turret.burnFlash();
    }

    @Override
    public void updateInputs(TurretIOInputs inputs){

        double Value = Units.rotationsToDegrees(ThroughBore.getAbsolutePosition());

        Rotation2d angle = new Rotation2d(Value);

        inputs.TurretAppliedVolts = Turret.getAppliedOutput() * Turret.getBusVoltage();
        inputs.TurretPosition = new Rotation2d(angle.getRadians());
        inputs.TurretVelocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(enc_turret.getVelocity()) / TurretConstants.TurretReduction;
        inputs.TurretLaps = enc_turret.getPosition() / TurretConstants.TurretReduction;
        inputs.TurretCurrentAmps = new double[]{Turret.getOutputCurrent()};
        inputs.CurrentSpeed = Turret.get();
    }
    
    @Override
    public void setTurret(double voltage){
        Turret.setVoltage(voltage);
    }

    @Override
    public void setTurretBrakeMode(boolean enable) {
      Turret.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
    }

}
