package frc.robot.commands.ShooterCommands.Turret;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.Angle;
import frc.robot.Subsystems.Shooter.Turret.Turret;
import frc.robot.util.SmartTurret3472;
import frc.robot.util.HPPMathLib;

public class AlignTurret extends Command{

    private final Turret turret;
    private final Angle shooterangle;
    private final Double setpoint;
    private final DoubleSupplier joystickSupplier;
    boolean isFinished;


    //con enncoder
    public AlignTurret(Turret turret, Double setpoint, Angle shooterangle){

        this.turret = turret;
        this.shooterangle = shooterangle;
        this.setpoint = setpoint;
        this.joystickSupplier = null;
        addRequirements(turret);
    }
    //con joystick
    public AlignTurret(Turret turret, DoubleSupplier joystickSupplier, Angle shooterangle){

        this.turret = turret;
        this.shooterangle = shooterangle;
        this.setpoint = null;
        this.joystickSupplier = joystickSupplier;
        addRequirements(turret);
    }
    @Override
    public void initialize(){}
    @Override
    public void execute(){

        shooterangle.UpdateTurretZ(turret.getTurretPosition());

        if(setpoint != null){

            turret.runTurret(SmartTurret3472.flip(setpoint));            
        }

        if(joystickSupplier != null){
            double joystickValue = joystickSupplier.getAsDouble();
            
            if (Math.abs(joystickValue) < 0.05){
                joystickValue = 0;
            }
                turret.runWithJoystick(joystickValue);
            }
        }
    
    @Override
    public void end(boolean interrupted) {
        turret.stop();
    }

     @Override
    public boolean isFinished(){

        if(setpoint != null){
          
            if((turret.getYaw().getRadians() -  HPPMathLib.coterminalradianes(new Rotation2d(setpoint).getRadians()) >= - 0.010)  &&  (turret.getYaw().getRadians() -  HPPMathLib.coterminalradianes(new Rotation2d(setpoint).getRadians())<=  0.010)){
                isFinished = true;
                System.out.println("Tonoto");
                return isFinished;
            }else{
                isFinished = false;
                return isFinished;
            }
        }else{
            isFinished = false;
            System.out.println("Adios tonotos");
            return false;
        }

    }
    
}
