package frc.robot.Subsystems.Hanger;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

public class HangerIOSparkMax implements HangerIO{

    private final CANSparkMax Hanger;
    private final RelativeEncoder enc_Hanger;
    private final boolean HangerReversed;

    public HangerIOSparkMax(){
        Hanger = new CANSparkMax(ConstantsHanger.HangerPort, MotorType.kBrushless);
        HangerReversed = ConstantsHanger.HangerReversed;
        
        Hanger.restoreFactoryDefaults();

        Hanger.setCANTimeout(250);

        enc_Hanger = Hanger.getEncoder();

        Hanger.setInverted(HangerReversed);

        Hanger.enableVoltageCompensation(12.0);

        enc_Hanger.setPosition(0.0);
        enc_Hanger.setMeasurementPeriod(10);
        enc_Hanger.setAverageDepth(2);

        Hanger.setCANTimeout(0);
        
        Hanger.burnFlash();

    }

    @Override
    public void updateInputs(HangerIOInputs inputs){
        inputs.HangerAppliedVolts = Hanger.getAppliedOutput() * Hanger.getBusVoltage();
        inputs.HangerCurrentAmps = new double[]{Hanger.getOutputCurrent()};
        inputs.CurrentSpeed = Hanger.get();
        inputs.HangerVelocityRPM = enc_Hanger.getVelocity();
    }

    @Override
    public void setHanger(double voltage){
        Hanger.setVoltage(voltage);
    }

    @Override
    public void setHangerBrakeMode(boolean enable) {
      Hanger.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
    }

}

