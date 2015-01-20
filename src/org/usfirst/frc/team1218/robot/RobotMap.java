package org.usfirst.frc.team1218.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//Legacy Port Mappings
	public static final int[] SM_DRIVE_MOTOR = {10, 12, 14, 16};
	public static final int[] SM_TURN_MOTOR = {11, 13, 15, 17};
	public static final int[] SM_ENCODER_A = {0, 2, 4, 6};
	public static final int[] SM_ENCODER_B = {1, 3, 5, 7};
	public static final int[] SM_ZERO = {8, 9, 10, 11};
	
	//New Robot Port Mappings
	public static final int[] SM_DRIVE_MOTOR_CAN = {10, 12, 14, 16};
	public static final int[] SM_TURN_MOTOR_CAN = {11, 13, 15, 17};
	//public static final int[] SM_ENCODER_A = {0, 3, 6, 9};
	//public static final int[] SM_ENCODER_B = {1, 4, 7, 10};
	public static final int[] SM_ENCODER_I = {2, 5, 8, 11};

	public static final int ELEVATOR_LIFT_L = 0;
	public static final int ELEVATOR_LIFT_R = 0;
	public static final int ELEVATOR_LIFT_LOWER_LIMIT = 0;
	public static final int ELEVATOR_LIFT_UPPER_LIMIT = 0;
	public static final int ELEVATOR_LIFT_ENCODER_A = 0;
	public static final int ELEVATOR_LIFT_ENCODER_B = 0;
	public static final int ELEVATOR_LIFT_ENCODER_I = 0;
	public static final int ELEVATOR_INTAKE_L = 0;
	public static final int ELEVATOR_INTAKE_R = 0;
	
    public static final int JOYSTICK_1 = 0;
    
    public static final int GYRO = 1;
	
}
