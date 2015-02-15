package org.usfirst.frc.team1218.subsystem.swerve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Provides Mid/High level module control code.
 * @author afiolmahon
 */

public class SwerveDrive extends Subsystem {
    
    protected List<SwerveModule> module;
    
    private final SerialPort navSerialPort;
    protected final IMUAdvanced navModule;
    
	private static final double X_PERPENDICULAR_CONSTANT = 0.546;
	private static final double Y_PERPENDICULAR_CONSTANT = 0.837;
	
	protected static final double[] ALPHA_MODULE_ANGLE_OFFSET = {6.0, 161.0, -66.5, 128.0};
	protected static final double[] BETA_MODULE_ANGLE_OFFSET = {-2.0, 133.0, -15.0, -145.0};
	
    public SwerveDrive() {
    	boolean isBeta = Preferences.getInstance().getBoolean("isBeta", false);
    	module = new ArrayList<SwerveModule>(Arrays.asList(
    				new SwerveModule(0, (isBeta) ? BETA_MODULE_ANGLE_OFFSET[0] : ALPHA_MODULE_ANGLE_OFFSET[0]),
    				new SwerveModule(1, (isBeta) ? BETA_MODULE_ANGLE_OFFSET[1] : ALPHA_MODULE_ANGLE_OFFSET[1]),
    				new SwerveModule(2, (isBeta) ? BETA_MODULE_ANGLE_OFFSET[2] : ALPHA_MODULE_ANGLE_OFFSET[2]),
    				new SwerveModule(3, (isBeta) ? BETA_MODULE_ANGLE_OFFSET[3] : ALPHA_MODULE_ANGLE_OFFSET[3])
    				));	
		navSerialPort = new SerialPort(57600, SerialPort.Port.kMXP);
		navModule = new IMUAdvanced(navSerialPort);
        System.out.println("Swerve System Initialized");
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new C_Swerve());   
    }
    
    public void syncDashboard() {
    	SmartDashboard.putNumber("SwerveDrive: Robot_Heading", Angle.get360Angle(navModule.getYaw()));
    	module.stream().forEach(m -> m.syncDashboard());
	}
    
    /**
     * Creates angle and power for all swerve modules
     * @param translationVector vector with magnitude <= 1
     * @param rotation a value from 1 to -1 representing the amount of rotation to add to the robot angle
     * @param fieldCentricCompensator a gyroscope output that can let the robot drive field-centric, pass 0 if robot centric drive is desired.
     */
    public List<Vector> swerveVectorCalculator(Vector translationVector, double rotation, double fieldCentricCompensator) {
    	double xPerpendicular = rotation * X_PERPENDICULAR_CONSTANT;
    	double yPerpendicular = rotation * Y_PERPENDICULAR_CONSTANT;
    	
    	translationVector.pushAngle(-fieldCentricCompensator);
    	
    	List<Vector> moduleVector = new ArrayList<Vector>(Arrays.asList(
    			new Vector(translationVector.getX() + xPerpendicular, translationVector.getY() - yPerpendicular),
    			new Vector(translationVector.getX() - xPerpendicular, translationVector.getY() - yPerpendicular),
    			new Vector(translationVector.getX() - xPerpendicular, translationVector.getY() + yPerpendicular),
    			new Vector(translationVector.getX() + xPerpendicular, translationVector.getY() + yPerpendicular)
    			));
    	
    	double maxMagnitude = 0;
    	
    	for (int i = 0; i < 4; i++) maxMagnitude = (moduleVector.get(i).getMagnitude() > maxMagnitude) ? moduleVector.get(i).getMagnitude() : maxMagnitude;
    	
    	double scaleFactor = ((maxMagnitude > 1.0) ? 1.0 / maxMagnitude : 1.0);
    	
    	moduleVector.stream().forEach(v -> v.scaleMagnitude(scaleFactor));
    	return moduleVector;
    }
    
    public void powerDrive(Vector translationVector, double rotation, double fieldCentricCompensator) {
    	List<Vector> moduleVectors = swerveVectorCalculator(translationVector, rotation, fieldCentricCompensator);
    	module.stream().forEach(m -> m.setAngleAndPower(moduleVectors.get(m.moduleNumber)));
    }
    
    public void velocityDrive(Vector translationVector, double rotation, double fieldCentricCompensator) {
    	List<Vector> moduleVectors = swerveVectorCalculator(translationVector, rotation, fieldCentricCompensator);
    	module.stream().forEach(m -> m.setAngleAndVelocity(moduleVectors.get(m.moduleNumber)));
    }
    
    protected List<SwerveModule> getModuleList() {
    	return module;
    }
}