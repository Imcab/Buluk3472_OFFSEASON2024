package frc.robot.Subsystems.Swerve;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.Constants;

import org.littletonrobotics.junction.Logger;

public class Module {

    
    double WHEELRADIUS = DriveConstants.WHEELRADIUS;
    ModuleIO io;
    ModuleIOInputsAutoLogged inputs = new ModuleIOInputsAutoLogged();
    int index;

    private final PIDController turnPID;

  public Module(ModuleIO io, int index){
      this.io = io;
      this.index = index;
        
      switch (Constants.currentMode) {
      case REAL:
      case REPLAY:
        turnPID = new PIDController(DriveConstants.kP, 0.0, 0.0);
        break;
      case SIM:
        turnPID = new PIDController(DriveConstants.kPSIM, 0.0, 0.0);
        break;
      default:
        turnPID = new PIDController(0.0, 0.0, 0.0);
        break;
    }
    turnPID.enableContinuousInput(-Math.PI, Math.PI);
  }

  public void periodic(){
    io.updateInputs(inputs);
    Logger.processInputs("Drive/Module" + Integer.toString(index), inputs);

    Logger.recordOutput("Drive/RawAngle" + Integer.toString(index), AngleEncoder());
    Logger.recordOutput("Drive/FixedAngle" + Integer.toString(index), AngleEncoderODOMETRY());
  }

  public void stop() {
    io.setTurn(0.0);
    io.setDrive(0.0);
  }
  public double getDrivePosition(){
    return inputs.drivePositionMeters;
  } 
  public Rotation2d AngleEncoder(){
  return Rotation2d.fromDegrees(inputs.AngleEncoderPosition);
  }
  public Rotation2d AngleEncoderODOMETRY(){
  return Rotation2d.fromDegrees(inputs.AngleEncoderPosition + 90);
  }
  public void setDesiredState(SwerveModuleState desiredState){

    desiredState = SwerveModuleState.optimize(desiredState, AngleEncoder());

    setSpeed(desiredState);
    setAngle(desiredState);
  }
  public void setSpeed(SwerveModuleState desiredState){
    io.setDrive(desiredState.speedMetersPerSecond);
  }

  public void setAngle(SwerveModuleState desiredState){
    double PIDValue = turnPID.calculate(AngleEncoder().getDegrees(), desiredState.angle.getDegrees());
    io.setTurn(PIDValue * inputs.MODULEPIDSTATUS);
  }
  public SwerveModulePosition getPosition(){
    return new SwerveModulePosition(getDrivePosition(), AngleEncoderODOMETRY());
  }
  public double getDriveVelocity(){
    return inputs.driveVelocity;
  }
  public SwerveModuleState gModuleState(){
    return new SwerveModuleState(getDriveVelocity(), new Rotation2d(AngleEncoderODOMETRY().getDegrees()));
  }


}
