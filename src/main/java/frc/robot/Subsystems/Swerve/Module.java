package frc.robot.Subsystems.Swerve;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.Constants;

import org.littletonrobotics.junction.Logger;

public class Module {

    
    private final ModuleIO io;
    private final ModuleIOInputsAutoLogged inputs = new ModuleIOInputsAutoLogged();
    private final int index;

    private final PIDController turnPID;
    private final PIDController drivePID;
    private final SimpleMotorFeedforward drivFeedforward;
    private Rotation2d angleSetpoint = null; // Setpoint for closed loop control, null for open loop
    private Double speedSetpoint = null; // Setpoint for closed loop control, null for open loop

    

  public Module(ModuleIO io, int index){
      this.io = io;
      this.index = index;
        
      switch (Constants.currentMode) {
      case REAL:
      case REPLAY:
        drivePID = new PIDController(0.05, 0.0, 0.0);
        drivFeedforward = new SimpleMotorFeedforward(0.1, 0.13);
        turnPID = new PIDController(DriveConstants.kP, 0.0, 0.0);
        break;
      case SIM:
        drivePID = new PIDController(0.1, 0.0, 0.0);
        drivFeedforward = new SimpleMotorFeedforward(0.0, 0.13);
        turnPID = new PIDController(DriveConstants.kPSIM, 0.0, 0.0);
        break;
      default:
        drivePID = new PIDController(0.0, 0.0, 0.0);
        drivFeedforward = new SimpleMotorFeedforward(0.0, 0.0);
        turnPID = new PIDController(0.0, 0.0, 0.0);
        break;
    }
    turnPID.enableContinuousInput(-Math.PI, Math.PI);
    setBrakeMode(true);
  }

  public void periodic(){
    io.updateInputs(inputs);
    Logger.processInputs("Drive/Module" + Integer.toString(index), inputs);

    // Run closed loop turn control
    if (angleSetpoint != null) {
      io.setTurn(
          turnPID.calculate(getAngle().getRadians(), angleSetpoint.getRadians()));

      // Run closed loop drive control
      // Only allowed if closed loop turn control is running
      if (speedSetpoint != null) {
        // Scale velocity based on turn error
        //
        // When the error is 90°, the velocity setpoint should be 0. As the wheel turns
        // towards the setpoint, its velocity should increase. This is achieved by
        // taking the component of the velocity in the direction of the setpoint.
        double adjustSpeedSetpoint = speedSetpoint * Math.cos(turnPID.getPositionError());

        // Run drive controller
        double velocityRadPerSec = adjustSpeedSetpoint / DriveConstants.WHEELRADIUS;
        io.setDrive(
            drivFeedforward.calculate(velocityRadPerSec)
                + drivePID.calculate(inputs.driveVelocityRadPerSec, velocityRadPerSec));
      }
    }
  }

  public SwerveModuleState runSetpoint(SwerveModuleState state) {
    // Optimize state based on current angle
    // Controllers run in "periodic" when the setpoint is not null
    var optimizedState = SwerveModuleState.optimize(state, getAngle());

    // Update setpoints, controllers run in "periodic"
    angleSetpoint = optimizedState.angle;
    speedSetpoint = optimizedState.speedMetersPerSecond;

    return optimizedState;
  }

  public void stop() {
    io.setTurn(0.0);
    io.setDrive(0.0);

     // Disable closed loop control for turn and drive
     angleSetpoint = null;
     speedSetpoint = null;
  }

  public void setBrakeMode(boolean enabled) {
    io.setDriveBrakeMode(enabled);
    io.setTurnBrakeMode(enabled);
  }
  
  public Rotation2d getAngle(){
    return inputs.AngleEncoderPosition;
  }
  public Rotation2d AngleEncoderODOMETRY(){
    double angleFixed = getAngle().getDegrees();
    return Rotation2d.fromDegrees(angleFixed);
  }
  public void setSpeed(SwerveModuleState desiredState){
    io.setDrive(desiredState.speedMetersPerSecond);
  }

  public double getDrivePositionMeters(){
    return inputs.drivePositionRad * DriveConstants.WHEELRADIUS;
  }  
  public double getDriveVelocityMetersxSec(){
    return inputs.driveVelocityRadPerSec * DriveConstants.WHEELRADIUS;
  }
  public SwerveModulePosition getPosition(){
    return new SwerveModulePosition(getDrivePositionMeters(), new Rotation2d(AngleEncoderODOMETRY().getRadians()));
  }
  public SwerveModuleState getState(){
    return new SwerveModuleState(getDriveVelocityMetersxSec(), new Rotation2d(AngleEncoderODOMETRY().getRadians()));
  }
}
