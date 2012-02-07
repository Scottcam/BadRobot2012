/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobots.y2012.technetium.subsystems;

import com.badrobots.y2012.technetium.RobotMap;
import com.badrobots.y2012.technetium.commands.GatherBalls;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *  CODE IS UNTESTED -- MUST HAVE SPECIFIC RobotMap CHANNELS FILLED IN AND CLASS DEBUGGED
 * @author Jon Buckley
 */
public class BallGatherer extends Subsystem 
{
    BallGatherer instance;
    AnalogChannel garageSensor;
    Victor conveyor, bottomRoller, topRoller;
    double threshold = 200; // voltage readout from the analog channel
    
    /**
     * Singleton static getter method for the class -- only one instance of BallGatherer
     * in program
     * @return the instance of itself--if not already initialized, this method also
     * calls its constructor
     */
    public BallGatherer getInstance()
    {
        if (instance == null)
        {
           instance = new BallGatherer(); 
        }
        
        return instance;
    }
        
    public BallGatherer()
    {
        super();
        garageSensor = new AnalogChannel(RobotMap.garage);
        conveyor = new Victor(RobotMap.conveyor);
        bottomRoller = new Victor(RobotMap.bottomRoller);
        topRoller = new Victor(RobotMap.topRoller);
    }
    
    /*
     * Runs the bottomRoller and conveyor motor: the bottom half of the gatherer
     */
    public void runConveyor(double speed)
    {
        conveyor.set(-speed);
        bottomRoller.set(speed);
    }
    
    /*
     * Runs just the topRoller (the motor that pulls the ball out of the conveyor)
     */
    public void runTopRoller(double speed)
    {
        topRoller.set(speed);
    }
    
    /*
     * Runs just the bottomRoller (the motor that pulls the ball into the conveyor)
     */
    public void runBottomRoller(double speed)
    {
        bottomRoller.set(speed);
    }
    
    /*
     * @return whether the garage door sensor is obscured. 
     * This method uses analog threshold to detect this
     */
    public boolean channelBlocked()
    {
        if (garageSensor.getAverageVoltage() > threshold)
            return false;
        
        return true;
    }
    
    public void initDefaultCommand()
    {
        setDefaultCommand(new GatherBalls());
    }
}
