package frc.robot.commands.ShooterCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.Angle;
import frc.robot.Subsystems.Shooter.Turret.Turret;
import frc.robot.Subsystems.Vision.Vision;

public class AlignShooter extends Command{

    private final Vision limelightVision;
    private final Angle shooterangle;
    Double setpoint;
    DoubleSupplier joystickSupplier;

    //con limelight
    public AlignShooter(Angle shooterangle, Vision limelightVision){

        this.shooterangle = shooterangle;
        this.setpoint = null;
        this.limelightVision = limelightVision;
        this.joystickSupplier = null;
        addRequirements(shooterangle,limelightVision);
    }
    //con enncoder
    public AlignShooter(Angle shooterangle, Double setpoint){

        this.shooterangle = shooterangle;
        this.setpoint = setpoint;
        this.limelightVision = null;
        this.joystickSupplier = null;
        addRequirements(shooterangle);
    }
    //con joystick
    public AlignShooter(Angle shooterangle, DoubleSupplier joystickSupplier){

        this.shooterangle = shooterangle;
        this.setpoint = null;
        this.limelightVision = null;
        this.joystickSupplier = joystickSupplier;
        addRequirements(shooterangle);
    }
    @Override
    public void initialize(){}
    @Override
    public void execute(){


        if (limelightVision != null){
            boolean targetFound = limelightVision.targetFound();
            shooterangle.VisionStatus(true);
            System.out.println(targetFound);
            
            if(targetFound == true){
                shooterangle.runShooterAngle(limelightVision.getY());

            }else{
                shooterangle.stop();
            }
        }

        if(setpoint != null){
            shooterangle.VisionStatus(false);
            shooterangle.runShooterAngle(setpoint);
        }

        if(joystickSupplier != null){
            double joystickValue = joystickSupplier.getAsDouble();
            
            if (Math.abs(joystickValue) < 0.1){
                joystickValue = 0;
            }
                

                if(shooterangle.getShooterPosition().getDegrees() >= -2  && shooterangle.getShooterPosition().getDegrees() <15){
                    shooterangle.runWithJoystick(joystickValue);
                }
                if(shooterangle.getShooterPosition().getDegrees() <= -2  && shooterangle.getShooterPosition().getDegrees() >15){
                    shooterangle.stopjoystick();
                }
                    
                
            }
        }
    
    @Override
    public void end(boolean interrupted) {
        shooterangle.stop();
    }

}
