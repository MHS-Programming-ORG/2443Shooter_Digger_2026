package frc.robot.shoooterSubsystems;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//http://photonvision.local:5800/#/dashboard
public class ArduCam extends SubsystemBase{
    private PhotonCamera camera = new PhotonCamera("Arducam_OV9782_USB_Camera");
    private PhotonPipelineResult result = new PhotonPipelineResult();
    private PhotonTrackedTarget target;
    private Transform3d bestCameraToTarget = target.getBestCameraToTarget();

    public double getX(){
        return bestCameraToTarget.getX();
    }

    public boolean cameraVisable(){
        return result.hasTargets();
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean(
            "NT Connected",
            NetworkTableInstance.getDefault().isConnected()
        );

        SmartDashboard.putBoolean(
            "Photon Camera Connected",
            camera.isConnected()
        );

        SmartDashboard.putNumber(
            "Photon Timestamp",
            result.getTimestampSeconds()
        );
        SmartDashboard.putBoolean(
            "Photon Has Target",
            result.hasTargets()
        );
        SmartDashboard.putNumber(
            "Photon Target Count",
            result.getTargets().size()
        );

        if (result.hasTargets()) {
            PhotonTrackedTarget target = result.getBestTarget();
            var result = camera.getLatestResult();
            int targetID = target.getFiducialId();
            double poseAmbiguity = target.getPoseAmbiguity();
            Transform3d bestCameraToTarget = target.getBestCameraToTarget();
            double yaw = target.getYaw();
            double pitch = target.getPitch();
            double area = target.getArea();
            double skew = target.getSkew();

            //System.out.println(bestCameraToTarget);
            SmartDashboard.putNumber("Target X", bestCameraToTarget.getX());
            SmartDashboard.putNumber("Target Y", bestCameraToTarget.getY());
            SmartDashboard.putNumber("Target Z", bestCameraToTarget.getZ());
        }
    }
}