package org.usfirst.frc.team1218.auton;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author lcook
 */
public class C_Wait extends Command {
	double waitTime;
    public C_Wait(double waitTime) {
    	this.waitTime = waitTime;
    }

    protected void initialize() {
    	setTimeout(waitTime);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
