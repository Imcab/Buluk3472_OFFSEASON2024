//TEST

package frc.robot.commands.DriveCommands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Swerve.Drive;

public class SwerveAutoAlign extends Command{

    private final Drive drive;
    private final Pose2d desiredPose2d;
    double xObjective, yObjective, zObjective;

    PIDController drivePID = new PIDController(0.2, 0.6, 0.8);
    PIDController turnPID = new PIDController(0.5, 0.0, 0.02);

    public SwerveAutoAlign(Drive drive, Pose2d desiredpose2d){

        this.drive = drive;
        this.desiredPose2d = desiredpose2d;

        addRequirements(drive);
    }

    @Override
    public void initialize(){

        xObjective = desiredPose2d.getX();
        yObjective = desiredPose2d.getY();
        zObjective = desiredPose2d.getRotation().getRadians();

        turnPID.enableContinuousInput(-Math.PI, Math.PI);

    }

    @Override
    public void execute(){

        double xSpeed = drivePID.calculate(drive.getX(), xObjective);
        double ySpeed = drivePID.calculate(drive.getY(), yObjective);
        double zSpeed = turnPID.calculate(drive.getRotation().getRadians(), zObjective);

        ChassisSpeeds desirSpeeds = new ChassisSpeeds(xSpeed, ySpeed, zSpeed);

        drive.runVelocity(desirSpeeds);

    }

    @Override
    public boolean isFinished(){

        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
