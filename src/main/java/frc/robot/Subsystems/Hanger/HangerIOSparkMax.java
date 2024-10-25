package frc.robot.Subsystems.Hanger;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

public class HangerIOSparkMax implements HangerIO{

    private final CANSparkMax Hanger, Hanger2;
    private final RelativeEncoder enc_Hanger, enc_Hanger2;
    private final boolean HangerReversed, HangerReversed2;

    public HangerIOSparkMax(){
        Hanger = new CANSparkMax(ConstantsHanger.LeftHangerPort, MotorType.kBrushless);
        HangerReversed = ConstantsHanger.LeftHangerReversed;

        Hanger2 = new CANSparkMax(ConstantsHanger.RightHangerPort, MotorType.kBrushless);
        HangerReversed2 = ConstantsHanger.RightHangerReversed;
        
        Hanger.restoreFactoryDefaults();
        Hanger2.restoreFactoryDefaults();

        Hanger.setCANTimeout(250);
        Hanger2.setCANTimeout(250);

        enc_Hanger = Hanger.getEncoder();
        enc_Hanger2 = Hanger.getEncoder();

        Hanger.setInverted(HangerReversed);
        Hanger2.setInverted(HangerReversed2);

        enc_Hanger.setPosition(0.0);
        enc_Hanger.setMeasurementPeriod(10);
        enc_Hanger.setAverageDepth(2);

        enc_Hanger2.setPosition(0.0);
        enc_Hanger2.setMeasurementPeriod(10);
        enc_Hanger2.setAverageDepth(2);

        Hanger.setCANTimeout(0);
        Hanger2.setCANTimeout(0);
        
        Hanger.burnFlash();
        Hanger2.burnFlash();

    }

    @Override
    public void updateInputs(HangerIOInputs inputs){
        inputs.CurrentSpeed = Hanger.get();
        inputs.HangerVelocityRPM = enc_Hanger.getVelocity();
    }

    @Override
    public void setHanger(double speed){
        Hanger.set(speed);
        Hanger2.set(speed);
    }

    @Override
    public void setHangerBrakeMode(boolean enable) {
        Hanger.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
        Hanger2.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
    }

}

