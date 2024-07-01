package frc.robot.Subsystems.Swerve;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.AnalogInput;


public class ModuleIOSparkMax implements ModuleIO{

  
    private final CANSparkMax driveSparkMax;
    private final CANSparkMax turnSparkMax;

    private final RelativeEncoder enc_drive;
  
    private final AnalogInput AbsoluteEncoder;

  
    private final boolean isTurnMotorInverted;
    private final boolean isDriveMotorInverted;
    private final double Offset;
    private final double PIDSTATUS;

    public ModuleIOSparkMax(int index) {
        switch (index) {
          case 0:
            driveSparkMax = new CANSparkMax(DriveConstants.frontLeft.DrivePort, MotorType.kBrushless);
            turnSparkMax = new CANSparkMax(DriveConstants.frontLeft.TurnPort, MotorType.kBrushless);
            AbsoluteEncoder = new AnalogInput(DriveConstants.frontLeft.EncPort);
            isDriveMotorInverted = DriveConstants.frontLeft.DrivemotorReversed;
            isTurnMotorInverted = DriveConstants.frontLeft.TurnmotorReversed;
            Offset = DriveConstants.frontLeft.offset;
            PIDSTATUS = DriveConstants.frontLeft.PIDSTATUS;

            break;
          case 1:
            driveSparkMax = new CANSparkMax(DriveConstants.frontRight.DrivePort, MotorType.kBrushless);
            turnSparkMax = new CANSparkMax(DriveConstants.frontRight.TurnPort, MotorType.kBrushless);
            AbsoluteEncoder = new AnalogInput(DriveConstants.frontRight.EncPort);
            isDriveMotorInverted = DriveConstants.frontRight.DrivemotorReversed;
            isTurnMotorInverted = DriveConstants.frontRight.TurnmotorReversed;
            Offset = DriveConstants.frontRight.offset; 
            PIDSTATUS = DriveConstants.frontRight.PIDSTATUS;

            break;
          case 2:
            driveSparkMax = new CANSparkMax(DriveConstants.backLeft.DrivePort, MotorType.kBrushless);
            turnSparkMax = new CANSparkMax(DriveConstants.backLeft.TurnPort, MotorType.kBrushless);
            AbsoluteEncoder = new AnalogInput(DriveConstants.backLeft.EncPort);
            isDriveMotorInverted = DriveConstants.backLeft.DrivemotorReversed;
            isTurnMotorInverted = DriveConstants.backLeft.TurnmotorReversed;
            Offset = DriveConstants.backLeft.offset;
            PIDSTATUS = DriveConstants.backLeft.PIDSTATUS;

            break;
          case 3:
            driveSparkMax = new CANSparkMax(DriveConstants.backRight.DrivePort, MotorType.kBrushless);
            turnSparkMax = new CANSparkMax(DriveConstants.backRight.TurnPort, MotorType.kBrushless);
            AbsoluteEncoder = new AnalogInput(DriveConstants.backRight.EncPort);
            isDriveMotorInverted = DriveConstants.backRight.DrivemotorReversed;
            isTurnMotorInverted = DriveConstants.backRight.TurnmotorReversed;
            Offset = DriveConstants.backRight.offset;
            PIDSTATUS = DriveConstants.backRight.PIDSTATUS;

            break;
          default:
            throw new RuntimeException("Invalid module index");
        }

        driveSparkMax.restoreFactoryDefaults();
        turnSparkMax.restoreFactoryDefaults();

        driveSparkMax.setInverted(isDriveMotorInverted);
        turnSparkMax.setInverted(isTurnMotorInverted);
    
        driveSparkMax.setCANTimeout(250);
        turnSparkMax.setCANTimeout(250);

        enc_drive = driveSparkMax.getEncoder();

        enc_drive.setPositionConversionFactor(DriveConstants.encDrot2met);
        enc_drive.setVelocityConversionFactor(DriveConstants.metToMs);

        enc_drive.setPosition(0.0);
        
        driveSparkMax.setCANTimeout(0);
        turnSparkMax.setCANTimeout(0);

        driveSparkMax.burnFlash();
        turnSparkMax.burnFlash();
        
    }

    public Rotation2d AngleEncoder(){

        double encoderBits = AbsoluteEncoder.getValue();
        double angleEncoder = (encoderBits * 360) / 4096;

        return Rotation2d.fromDegrees(angleEncoder-Offset);
    }

    @Override
    public void updateInputs(ModuleIOInputs inputs){
        inputs.AngleEncoderPosition = AngleEncoder().getDegrees();
        inputs.MODULEPIDSTATUS = PIDSTATUS;
        inputs.driveVelocity = enc_drive.getVelocity();
        inputs.drivePositionMeters = enc_drive.getPosition();
        inputs.driveAppliedVolts = driveSparkMax.getAppliedOutput() * driveSparkMax.getBusVoltage();
        inputs.turnAppliedVolts = turnSparkMax.getAppliedOutput() * turnSparkMax.getBusVoltage();
    }
    @Override
    public void setDrive(double speed){
        driveSparkMax.set(speed);
    }
    @Override
    public void setTurn(double speed){
        turnSparkMax.set(speed);
    }
}
