package frc.robot.Subsystems.Swerve;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.AnalogInput;


public class ModuleIOSparkMax implements ModuleIO{

  
    private final CANSparkMax driveSparkMax;
    private final CANSparkMax turnSparkMax;

    private final RelativeEncoder enc_drive, enc_turn;
  
    private final AnalogInput AbsoluteEncoder;

  
    private final boolean isTurnMotorInverted;
    private final boolean isDriveMotorInverted;
    private final double Offset;

    public ModuleIOSparkMax(int index) {
        switch (index) {
          case 0:
            driveSparkMax = new CANSparkMax(DriveConstants.frontLeft.DrivePort, MotorType.kBrushless);
            turnSparkMax = new CANSparkMax(DriveConstants.frontLeft.TurnPort, MotorType.kBrushless);
            AbsoluteEncoder = new AnalogInput(DriveConstants.frontLeft.EncPort);
            isDriveMotorInverted = DriveConstants.frontLeft.DrivemotorReversed;
            isTurnMotorInverted = DriveConstants.frontLeft.TurnmotorReversed;
            Offset = DriveConstants.frontLeft.offset;
            

            break;
          case 1:
            driveSparkMax = new CANSparkMax(DriveConstants.frontRight.DrivePort, MotorType.kBrushless);
            turnSparkMax = new CANSparkMax(DriveConstants.frontRight.TurnPort, MotorType.kBrushless);
            AbsoluteEncoder = new AnalogInput(DriveConstants.frontRight.EncPort);
            isDriveMotorInverted = DriveConstants.frontRight.DrivemotorReversed;
            isTurnMotorInverted = DriveConstants.frontRight.TurnmotorReversed;
            Offset = DriveConstants.frontRight.offset; 
            

            break;
          case 2:
            driveSparkMax = new CANSparkMax(DriveConstants.backLeft.DrivePort, MotorType.kBrushless);
            turnSparkMax = new CANSparkMax(DriveConstants.backLeft.TurnPort, MotorType.kBrushless);
            AbsoluteEncoder = new AnalogInput(DriveConstants.backLeft.EncPort);
            isDriveMotorInverted = DriveConstants.backLeft.DrivemotorReversed;
            isTurnMotorInverted = DriveConstants.backLeft.TurnmotorReversed;
            Offset = DriveConstants.backLeft.offset;
            

            break;
          case 3:
            driveSparkMax = new CANSparkMax(DriveConstants.backRight.DrivePort, MotorType.kBrushless);
            turnSparkMax = new CANSparkMax(DriveConstants.backRight.TurnPort, MotorType.kBrushless);
            AbsoluteEncoder = new AnalogInput(DriveConstants.backRight.EncPort);
            isDriveMotorInverted = DriveConstants.backRight.DrivemotorReversed;
            isTurnMotorInverted = DriveConstants.backRight.TurnmotorReversed;
            Offset = DriveConstants.backRight.offset;
            

            break;
          default:
            throw new RuntimeException("Invalid module index");
        }

        driveSparkMax.restoreFactoryDefaults();
        turnSparkMax.restoreFactoryDefaults();

        driveSparkMax.setCANTimeout(250);
        turnSparkMax.setCANTimeout(250);

        enc_drive = driveSparkMax.getEncoder();
        enc_turn = driveSparkMax.getEncoder();

        driveSparkMax.setInverted(isDriveMotorInverted);
        turnSparkMax.setInverted(isTurnMotorInverted);

        driveSparkMax.setSmartCurrentLimit(40);
        turnSparkMax.setSmartCurrentLimit(30);
        driveSparkMax.enableVoltageCompensation(12.0);
        turnSparkMax.enableVoltageCompensation(12.0);

        enc_drive.setPosition(0.0);
        enc_drive.setMeasurementPeriod(10);
        enc_drive.setAverageDepth(2);

        enc_turn.setPosition(0.0);
        enc_turn.setMeasurementPeriod(10);
        enc_turn.setAverageDepth(2);
        
        driveSparkMax.setCANTimeout(0);
        turnSparkMax.setCANTimeout(0);

        driveSparkMax.burnFlash();
        turnSparkMax.burnFlash();
        
    }

    public Rotation2d AngleEncoder(){

      double encoderBits = AbsoluteEncoder.getValue();
      double angleEncoder = (encoderBits * 360) / 4096;

      return Rotation2d.fromDegrees(angleEncoder - Offset);

  }

    @Override
    public void updateInputs(ModuleIOInputs inputs){

        inputs.driveAppliedVolts = driveSparkMax.getAppliedOutput() * driveSparkMax.getBusVoltage();
        inputs.drivePositionRad = Units.rotationsToRadians(enc_drive.getPosition()) / DriveConstants.DriveReduction;
        inputs.driveVelocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(enc_drive.getVelocity()) / DriveConstants.DriveReduction;
        inputs.driveCurrentAmps = new double[]{driveSparkMax.getOutputCurrent()};

        inputs.turnAppliedVolts = turnSparkMax.getAppliedOutput() * turnSparkMax.getBusVoltage();
        inputs.TurningPosition = Rotation2d.fromRotations(enc_turn.getPosition() / DriveConstants.TurnReduction);
        inputs.AngleEncoderPosition = new Rotation2d(AngleEncoder().getRadians());
        inputs.turnVelocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(enc_turn.getVelocity()) / DriveConstants.TurnReduction;
        inputs.TurnCurrentAmps = new double[]{turnSparkMax.getOutputCurrent()};
      
    }
    @Override
    public void setDrive(double voltage){
        driveSparkMax.setVoltage(voltage);
    }
    @Override
    public void setTurn(double voltage){
        turnSparkMax.setVoltage(voltage);
    }

    @Override
    public void setDriveBrakeMode(boolean enable) {
      driveSparkMax.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
    }
    @Override
    public void setTurnBrakeMode(boolean enable) {
      turnSparkMax.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
    }

}
