package frc.robot.Subsystems.Intake; 

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.util.Units;
import frc.robot.Subsystems.Intake.ConstantsIntake; 

public class IntakeIOSparkMax implements IntakeIO{

    private final CANSparkMax Intake; 

    public IntakeIOSparkMax(){

        Intake = new CANSparkMax(ConstantsIntake.Intakeport, MotorType.kBrushless);

        Intake.setInverted(ConstantsIntake.);

        Intake.restoreFactoryDefaults();

        Intake.setCANTimeout(0);

        Intake.setInverted(ConstantsIntake.IntakeMotorReversed);

        Intake.enableVoltageCompensation(12.0);

        Intake.burnFlash();

    }

     @Override
    public void updateInputs(IntkaeIOInputs inputs){
        inputs.IntakeAppliedVolts = Intake.getAppliedOutput() * Intake.getBusVoltage();
        inputs.IntakeVelocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(inputs.IntakeVelocityRPM);
        inputs.IntakeCurrentAmps = new double[]{Intake.getOutputCurrent()};
    }

    @Override
     public void setIntake(double voltage){
        Intake.setVoltage(voltage);

     }

 @Override 
     public void setIntakeBrakeMode(boolean enable){
        Intake.setIdleMode(enable ? IdleMode.Brake : IdleMode.KCoast);
     }
}
