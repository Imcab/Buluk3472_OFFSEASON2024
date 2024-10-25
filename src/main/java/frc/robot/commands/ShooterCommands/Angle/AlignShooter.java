package frc.robot.commands.ShooterCommands.Angle;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.Angle;

public class AlignShooter extends Command{

    private final Angle shooterangle;
    Double setpoint;
    boolean atun;

  
    //con enncoder
    public AlignShooter(Angle shooterangle, Double setpoint){

        this.shooterangle = shooterangle;
        this.setpoint = setpoint;

        addRequirements(shooterangle);
    }

    @Override
    public void initialize(){}
    @Override
    public void execute(){

        shooterangle.runShooterAngle(setpoint);  
        SmartDashboard.putNumber("Grados", shooterangle.getDegrees());
    }
    
    @Override
    public void end(boolean interrupted) {
        if (shooterangle.getDegrees() <= 15 && shooterangle.getDegrees() >= 2){
            shooterangle.runSpeed(1);
        }
        if (shooterangle.getDegrees() < 2) {
            shooterangle.stop();
        }
        
        
    }
    
    @Override
    public boolean isFinished(){

        /*if(setpoint != null){
            if(shooterangle.getShooterPosition().getRadians() >= new Rotation2d(setpoint).getRadians() - 0.087 && shooterangle.getShooterPosition().getRadians() <= new Rotation2d(setpoint).getRadians() + 0.087){
                atun = true;
                //System.out.println("Osito bimbo");
                return atun;
                
            }else{
                atun = false;
                return atun;
            }
        }else{
            atun = false;
            //System.out.println("NO OSITOS BIMBO");
            return atun;
        }/* */
        return false;
    }

}
