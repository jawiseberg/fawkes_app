#Vendetta

##What it does
- provides better motors and servos with more safe implementations
- provides structure to hardware definitions
- provides simple sensor interfacing and battery checks

##How to use Sentinel
1. Add a @DefSentinel and extend the Sentinel class.
``` java
@DefSentinel(
    drive="Type of drive",
    description="Description of drive"
) //if it is more than just a chassis drive set specialized=true above
public class HardwareNAME extends Sentinel {
```
2. Write the constructor, pass on a HardwareMap hwMap
``` java
public HardwareNAME(HardwareMap hwMap) { super(hwMap); }
```
3. Add any necessary motors as global instance variables and write the hardwareSetup.
``` java
public FawkesMotor motor1, motor2;
public boolean hardwareSetup() {
    motor1 = retrieveMotor("motor 1").unencode().power(0);
    motor2 = retrieveMotor("motor 2").unencode().power(0);
    return true;
}
```
4. Add a drive system.
``` java
public boolean tank(float left, float right) {
        fl.power(left); bl.power(left);
        fr.power(right); br.power(right);
        return true;
}
```

You can now extend this class in the future.
``` java
@DefSentinel (
    drive="Tank",
    specialized = true,
    description="Tank drive with added claw servos"
)
public class HardwareClawbot extends HardwareTank {
```