package frc.robot.commands.ShooterCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Shooter.Shooter.AngleShooter.Angle;
import frc.robot.Subsystems.Shooter.Turret.Turret;
import frc.robot.Subsystems.Vision.Vision;

public class AlignTurret extends Command{

    private final Turret turret;
    private final Vision limelightVision;
    private final Angle shooterangle;
    private final Double setpoint;
    private final DoubleSupplier joystickSupplier;
    private final Double SMARTsetpoint;
    boolean arroz;


    //con limelight
    public AlignTurret(Turret turret, Vision limelightVision, Angle shooterangle){

        this.turret = turret;
        this.shooterangle = shooterangle;
        this.setpoint = null;
        this.limelightVision = limelightVision;
        this.joystickSupplier = null;
        this.SMARTsetpoint = null;
        addRequirements(turret,limelightVision);
    }
    //con enncoder
    public AlignTurret(Turret turret, Double setpoint, Angle shooterangle){

        this.turret = turret;
        this.shooterangle = shooterangle;
        this.setpoint = setpoint;
        this.limelightVision = null;
        this.joystickSupplier = null;
        this.SMARTsetpoint = null;
        addRequirements(turret);
    }
    //con joystick
    public AlignTurret(Turret turret, DoubleSupplier joystickSupplier, Angle shooterangle){

        this.turret = turret;
        this.shooterangle = shooterangle;
        this.setpoint = null;
        this.limelightVision = null;
        this.joystickSupplier = joystickSupplier;
        this.SMARTsetpoint = null;
        addRequirements(turret);
    }
    @Override
    public void initialize(){}
    @Override
    public void execute(){

        shooterangle.UpdateTurretZ(turret.getTurretPosition());

        if (limelightVision != null){
            boolean targetFound = limelightVision.targetFound();
            turret.VisionStatus(true);
            System.out.println(targetFound);
            
            if(targetFound == true){
                turret.runTurret(limelightVision.getY());

            }else{
                turret.stop();
            }
        }

        if(setpoint != null){

            turret.VisionStatus(false);
            turret.runTurret(setpoint);
            
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

        //System.out.println(arroz);
        //System.out.print(setpoint);
        //System.out.print(", ");
 
        //System.out.println(turret.getTurretPosition());
        if(setpoint != null){
          
            if(turret.getTurretPosition().getRadians() >= new Rotation2d(setpoint).getRadians() -0.008  && turret.getTurretPosition().getRadians() <= new Rotation2d(setpoint).getRadians() + 0.008 ){
                arroz = true;
                return arroz;
            }else{
                arroz = false;
                return arroz;
            }
        }else{
            arroz = false;
            return arroz;
        }

    }

    

}
