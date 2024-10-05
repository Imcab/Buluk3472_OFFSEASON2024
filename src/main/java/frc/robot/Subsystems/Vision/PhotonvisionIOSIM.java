/*package frc.robot.Subsystems.Vision;

import org.photonvision.PhotonCamera;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.simulation.SimCameraProperties;
import org.photonvision.simulation.VisionSystemSim;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PhotonvisionIOSIM extends SubsystemBase{

    private final VisionSystemSim visionSim;
    private final AprilTagFieldLayout field;
    private final SimCameraProperties Intake1Prop;
    private final PhotonCameraSim IntakeCamSim;
    PhotonCamera IntakeCam;

    Translation3d Robot2CamTranslation = new Translation3d((-1.866/100), (63.478/100), (13.397/100));
    Rotation3d Robot2CamRot = new Rotation3d(0, Math.toRadians(-15), 0);
    Transform3d Robot2Cam = new Transform3d(Robot2CamTranslation, Robot2CamRot);
    public PhotonvisionIOSIM(){

        field = AprilTagFieldLayout.loadField(AprilTagFields.k2024Crescendo);
        visionSim = new VisionSystemSim("main");

        visionSim.addAprilTags(field);

        //Configure Intake cam
        Intake1Prop = new SimCameraProperties();
        
        Intake1Prop.setFPS(100);
        Intake1Prop.setCalibration(1280, 800, Rotation2d.fromDegrees(70));

        IntakeCam = new PhotonCamera("CameraSim");

        IntakeCamSim = new PhotonCameraSim(IntakeCam, Intake1Prop);

        visionSim.addCamera(IntakeCamSim, Robot2Cam);

        IntakeCamSim.enableDrawWireframe(true);

    }
    
    public void periodic() {
        
    }

    public void update(Pose2d robot){
        visionSim.update(robot);
        visionSim.getDebugField();
    }


    
}
*/