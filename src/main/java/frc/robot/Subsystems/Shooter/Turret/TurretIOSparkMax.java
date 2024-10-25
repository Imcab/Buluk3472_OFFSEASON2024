package frc.robot.Subsystems.Shooter.Turret;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import frc.robot.Subsystems.Shooter.ShooterConstants.TurretConstants;

public class TurretIOSparkMax implements TurretIO{

    private final CANSparkMax Turret;
    private final RelativeEncoder enc_turret;
    private final SparkAbsoluteEncoder ThroughBore;
    private final boolean TurretReversed;

    public TurretIOSparkMax(){
        Turret = new CANSparkMax(TurretConstants.TurretPort, MotorType.kBrushless);
        TurretReversed = TurretConstants.TurretReversed;
        ThroughBore = Turret.getAbsoluteEncoder();
 
        Turret.restoreFactoryDefaults();

        Turret.setCANTimeout(250);

        enc_turret = Turret.getEncoder();

        Turret.setInverted(TurretReversed);

        Turret.enableVoltageCompensation(12.0);

        Turret.setCANTimeout(0);
        
        Turret.burnFlash();

    }

    @Override
    public void updateInputs(TurretIOInputs inputs){
        
        inputs.TurretAppliedVolts = Turret.getAppliedOutput() * Turret.getBusVoltage();
        inputs.TurretPosition =  Units.rotationsToDegrees(ThroughBore.getPosition());
        inputs.TurretVelocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(enc_turret.getVelocity()) / TurretConstants.TurretReduction;
        inputs.TurretLaps = enc_turret.getPosition() / TurretConstants.TurretReduction;
        inputs.TurretCurrentAmps = new double[]{Turret.getOutputCurrent()};
        inputs.CurrentSpeed = Turret.get();
    }
    
    @Override
    public void setTurret(double voltage){
        Turret.set(voltage);
    }

    @Override
    public void setTurretBrakeMode(boolean enable) {
      Turret.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
    }

}
