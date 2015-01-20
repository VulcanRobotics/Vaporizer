package org.usfirst.frc.team1218.subsystem.swerve.legacyModule;

import org.usfirst.frc.team1218.math.Angle;
import org.usfirst.frc.team1218.math.Vector;
import org.usfirst.frc.team1218.robot.Robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LegacyModule extends Object {
	public static final int[] SM_DRIVE_MOTOR = {10, 12, 14, 16};
	public static final int[] SM_TURN_MOTOR = {11, 13, 15, 17};
	public static final int[] SM_ENCODER_A = {0, 2, 4, 6};
	public static final int[] SM_ENCODER_B = {1, 3, 5, 7};
	public static final int[] SM_ZERO = {8, 9, 10, 11};
	private final int moduleNumber; //Used to retrieve module specific offsets and modifiers
	
	
	private boolean isZeroing = false;

	private final CANTalon angleMotor;
	private final DigitalInput zeroSensor;
	private final AngleEncoder angleEncoder;
	private final PIDController angleController;
	private static final double ANGLE_CONTROLLER_P = -0.01;
	private static final double ANGLE_CONTROLLER_I = 0.0;
	private static final double ANGLE_CONTROLLER_D = 0.0;
	private static final double[] MODULE_ANGLE_OFFSET = {40.0, -36.0, -22.0, 85.0};
	private double angle = 0; //Current module angle
	
	private static final boolean[] MODULE_REVERSED = {false, false, true, true};
	
	private boolean invertModule = false;
	
	private final CANTalon driveMotor;
	private static final double RESET_TURN_POWER = 0.25;
	private static final double ANGLE_MOTOR_OUTPUT_RANGE = 1.0;
	//private static final double DRIVE_POWER_SCALE = 0.4;
	
	public LegacyModule(int moduleNumber) {
		this.moduleNumber = moduleNumber;
		this.driveMotor = new CANTalon(SM_DRIVE_MOTOR[moduleNumber]);
		this.angleMotor = new CANTalon(SM_TURN_MOTOR[moduleNumber]);
		this.angleEncoder = new AngleEncoder(SM_ENCODER_A[moduleNumber], SM_ENCODER_B[moduleNumber], MODULE_REVERSED[moduleNumber]);
		this.zeroSensor = new DigitalInput(SM_ZERO[moduleNumber]);
		this.angleController = new PIDController(ANGLE_CONTROLLER_P, ANGLE_CONTROLLER_I, ANGLE_CONTROLLER_D, angleEncoder, angleMotor);
		this.angleController.setInputRange(0.0, 360.0);
		this.angleController.setOutputRange(-ANGLE_MOTOR_OUTPUT_RANGE, ANGLE_MOTOR_OUTPUT_RANGE);
		this.angleController.setContinuous();
		this.angleEncoder.reset();
		this.angleController.enable();
	}
	
	/**
	 * Update the swerve module wheel power and angle.
	 * @param angle Desired module angle
	 * @param power Desired power for module drive motor
	 */
	public void setValues(double angle, double power) {
		if (Math.abs(power) > 0.1) setAngle(angle); //Prevents Module from setting wheels to zero when joystick is released
		setPower(power);
	}
	
	public void setVector(Vector vector) {
		setValues(vector.getAngle(), vector.getMagnitude());
	}

	/**
	 * angleController setpoint should always be set through this method in order to apply zeroing offsets
	 * @param angle Desired wheel angle. Can be any value
	 */
	public void setAngle(double angle) {
		if(Angle.diffBetweenAngles(angle, this.angle) > 90) {
			invertModule = !invertModule;
		}
		this.angle = angle;
		angle += (invertModule) ? 180 : 0;
		angle += MODULE_ANGLE_OFFSET[moduleNumber]; //adds angle zeroing point offset to the modules written angle.
		this.angleController.setSetpoint(Angle.get360Angle(((MODULE_REVERSED[moduleNumber]) ? 360 - angle : angle))); //applies module specific direction preferences
	}
	
	public void setPower(double power) {
		if (Math.abs(power) > 1){
			System.out.println("Illegal power " + power + " written to module: " + moduleNumber);
		} else {
			this.driveMotor.set(power * Robot.swerveSystem.Module_Power * ((invertModule) ? -1.0 : 1.0) * ((MODULE_REVERSED[moduleNumber]) ? 1.0 : -1.0)); //Applies module specific motor preferences
		}
	}
	
	public void setZeroing() {
		this.isZeroing = true;
	}
	
	public boolean getZeroing() {
		return this.isZeroing;
	}
	
	/**
	 * State Machine that drives C_ResetModules
	 */
	public void zeroModule() {
		this.driveMotor.set(0.0);
		if (zeroSensor.get()) {
			//At Zero
			this.angleEncoder.reset();
			this.angleMotor.set(0.0);
			this.setAngle(0);
			this.angleController.enable();
			this.isZeroing = false;
		}else {
			//Finding Zero
			this.angleController.disable();
			this.angleMotor.set(RESET_TURN_POWER);
			this.setZeroing();
		}
	}
	
	public class AngleEncoder extends Encoder {
		private static final double ENCODER_COUNTS_PER_ROTATION = 500;
		private static final double WHEEL_ENCODER_RATIO = 24.0 / 42.0;
		private static final double ENCODER_CLICK_DEGREE_RATIO = (360.0 / ENCODER_COUNTS_PER_ROTATION) * WHEEL_ENCODER_RATIO;
		
		public AngleEncoder(int aChannel, int bChannel, boolean reverseDirection) {
			super(aChannel, bChannel, reverseDirection);
		}
		
		@Override
		public double pidGet() {
			return Angle.get360Angle(get() * ENCODER_CLICK_DEGREE_RATIO);
		}
	}
	
	public void publishValues() {
		SmartDashboard.putNumber("SM" + moduleNumber + " Angle", angleEncoder.pidGet());
		SmartDashboard.putNumber("SM_" + moduleNumber + " WheelPower", driveMotor.get());
		SmartDashboard.putBoolean("SM_" + moduleNumber + " isZeroing", isZeroing);
		SmartDashboard.putBoolean("SM_" + moduleNumber + " AngleControllerEnabled", angleController.isEnable());
		SmartDashboard.putBoolean("SM_" + moduleNumber + " ZeroSensor", zeroSensor.get());
	}
}