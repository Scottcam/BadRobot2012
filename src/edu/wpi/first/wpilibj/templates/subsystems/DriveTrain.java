package edu.wpi.first.wpilibj.templates.subsystems;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.templates.commands.MechanumDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 * @author Jon Buckley, Lucas Beaufore, Gautam RiCantSpellYourLastName
 */
public class DriveTrain extends Subsystem
{

    private static DriveTrain instance;
    private static RobotDrive drive;
    public Joystick lJoystick, rJoystick;
    public Victor lFront, lBack, rFront, rBack;

    public static DriveTrain getInstance()
    {
        if (instance == null)
        {
            instance = new DriveTrain();
        }
        return instance;
    }

    /*
     * Initailizes four Victors, and makes a local instance of joysticks for
     * convenience of access
     */
    private DriveTrain()
    {
        super();
        lJoystick = OI.leftJoystick;
        rJoystick = OI.rightJoystick;

        lFront = new Victor(RobotMap.lFront);
        lBack = new Victor(RobotMap.lBack);
        rFront = new Victor(RobotMap.rFront);
        rBack = new Victor(RobotMap.rBack);

        drive = new RobotDrive(lFront, lBack, rFront, rBack);
        drive.setSafetyEnabled(false);
    }

    /*
     * Takes in four values from the joysticks, and converts it into tank drive
     * Status:Untested
     */
    public void mechanumDrive()
    {
        //WORKS: Right stick controls movement, left stick controls rotation.  Needs ground test. #w00t
       drive.mecanumDrive_Cartesian(deadzone(OI.getXboxRightX()), deadzone(OI.getXboxLeftX()), deadzone(OI.getXboxRightY()), 0);// (deadzone(-rJoystick.getX()), deadzone(-lJoystick.getX()), deadzone(rJoystick.getY()), 0);
    }

    /*
     * @param mag the speed desired to be moved,
     *        theta the angle that the robot will move towards,
     *        rate the speed at which the robot is turning
     *
     * Moves the robot using polar coordinates - takes in three components and moves
     * the robot accordingly
     * Status: Untested
     */
    public void polarMechanum(double mag, double theta, double rate)
    {
        drive.mecanumDrive_Polar(mag, theta, rate);
    }

    /*
     * Tank drives using joystick controls, sets left side to Y value of left joystick
     * and right side as Y value of right joystick
     */
    public void tankDrive()
    {
        lFront.set(OI.getLeftY()); //deadzone(OI.leftJoystick.getY()));
        lBack.set(OI.getLeftY()); //-deadzone(OI.leftJoystick.getY()));

        rFront.set(-OI.getRightY()); //deadzone(OI.rightJoystick.getY()));
        rBack.set(-OI.getRightY()); //deadzone(OI.rightJoystick.getY()));
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(new MechanumDrive());
    }

    public double deadzone(double d)
    {
        if (Math.abs(d) < 0.10)
            return 0;
        //return d / Math.abs(d) * ((Math.abs(d) - 0.5) / .5);
        return d / Math.abs(d) * ((Math.abs(d) - .10) / .90);
    }
}