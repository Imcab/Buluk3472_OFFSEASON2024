package frc.robot.Subsystems.Elevator.Rollers;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import frc.robot.Subsystems.Elevator.ConstantsElevator;


public class RollerIOSparkMax implements RollerIO {

    private final CANSparkMax RollerMotor; 
    private final AnalogInput Sensor; 
    private final boolean RollerReverse;
    private final RelativeEncoder encoder;
    
    public RollerIOSparkMax(){
        RollerMotor = new CANSparkMax(ConstantsElevator.RollerConstants.Port, MotorType.kBrushless);
        Sensor = new AnalogInput(ConstantsElevator.RollerConstants.sensorPort);
        RollerReverse = ConstantsElevator.RollerConstants.MotorReversed;

        RollerMotor.restoreFactoryDefaults();

        RollerMotor.setCANTimeout(250);

        RollerMotor.setInverted(RollerReverse);

        encoder = RollerMotor.getEncoder();

        encoder.setPosition(0.0);
        encoder.setMeasurementPeriod(10);
        encoder.setAverageDepth(2);

        RollerMotor.enableVoltageCompensation(12.0);

        RollerMotor.setCANTimeout(0);
        RollerMotor.burnFlash();

    }

    @Override
    public void updateInputs(RollerIOInputs inputs){
        inputs.RollerAppliedVolts = RollerMotor.getAppliedOutput() * RollerMotor.getBusVoltage();
        inputs.RollerCurrentAmps = new double[]{RollerMotor.getOutputCurrent()};
        inputs.RollerVelocityRPM = encoder.getVelocity();
    }

    @Override
    public void setRoller(double voltage){
        RollerMotor.setVoltage(voltage);
    }

    @Override
    public void setRollerBrakeMode(boolean enable){
        RollerMotor.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
    }

}
