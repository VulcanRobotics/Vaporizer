package org.usfirst.frc.team1218.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * @author afiol-mahon
 */
public class RobotMap {
	//Swerve Drive
	public static final int[] SM_DRIVE_MOTOR = {10, 12, 14, 16};
	public static final int[] SM_TURN_MOTOR = {11, 13, 15, 17};
	public static final int[] SM_ANGLE_ENCODER_X = {0, 3, 6, 9};
	public static final int[] SM_ANGLE_ENCODER_A = {1, 4, 7, 10};
	public static final int[] SM_ANGLE_ENCODER_B = {2, 5, 8, 11};
	
	//Elevator
	public static final int ELEVATOR_LIFT_MASTER = 30;
	
	public static final int TOTE_INTAKE_L = 32;
	public static final int TOTE_INTAKE_R = 33;
	
	//Four Bar
	public static final int FOUR_BAR_LEFT_DART = 20;
	public static final int FOUR_BAR_RIGHT_DART = 21;
	public static final int BIN_INTAKE_L = 22;
	public static final int BIN_INTAKE_R = 23;
	public static final int BIN_INTAKE_SOLENOID = 0;
	
	public static final int LEFT_DART_POTENTIOMETER = 0;
	public static final int RIGHT_DART_POTENTIOMETER = 1;
	
	//Hooks
	public static final int HOOK_DEPLOY_SOLENOID = 1;
	
	//Driver Control Mapping-----------------------------------------------
	public static final int DRIVER_JOYSTICK = 0;
	public static final int BUTTON_RESET_GYRO = OI.ButtonType.B;
	public static final int BUTTON_MAINTAIN_HEADING = OI.ButtonType.L1;
	public static final int BUTTON_INDEX_SWERVE = OI.ButtonType.A;
	public static final int BUTTON_FIELD_CENTRIC_TOGGLE = OI.ButtonType.LEFT_THUMB;
	public static final int BUTTON_LOCK_DRIVE = OI.ButtonType.R1;
	
	public static final int BUTTON_DEPLOY_HOOKS = OI.ButtonType.Y;
	
	//Operator Control Mapping---------------------------------------------
	public static final int OPERATOR_JOYSTICK = 1;
	//Elevator Controls
	public static final int BUTTON_ELEVATOR_DROP_STACK = 12;
	public static final int BUTTON_ELEVATOR_RAISE_STACK = 8;
	public static final int BUTTON_ELEVATOR_STEP_POSITION = 10;
	public static final int BUTTON_ELEVATOR_MANUAL_RAISE = 9;
	public static final int BUTTON_ELEVATOR_MANUAL_LOWER = 11;
	public static final int BUTTON_ELEVATOR_ZERO_POSITION = 7;
	//Tote Intake Controls
	public static final int BUTTON_ELEVATOR_RUN_TOTE_INTAKE = 5;
	public static final int BUTTON_ELEVATOR_REVERSE_TOTE_INTAKE = 3;
	//Four Bar Controls
	public static final int AXIS_FOUR_BAR_CONTROL = 1;
	public static final int BUTTON_FOUR_BAR_RUN_BIN_INTAKE = 2;
	public static final int BUTTON_FOUR_BAR_OPEN_GRABBER = 1;
	public static final int BUTTON_FOUR_BAR_HIGH_POSITION = 6;
	public static final int BUTTON_FOUR_BAR_LOW_POSITION = 4;
}
