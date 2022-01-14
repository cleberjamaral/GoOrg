package annotations;

import java.text.DecimalFormat;

/**
 * @author cleber
 *
 */
public class Coordinates extends Annotation {

    protected double x;
    protected double y;

	public Coordinates(String id, double x, double y) {
        super(id);
        this.x = x;
        this.x = y;
	}
	
	public Object getX() {
		return (double) this.x;
	}
	
	public void setX(Object x) {
		this.x = (double) x;
	}
    
	public Object getY() {
		return (double) this.y;
	}
	
	public void setY(Object y) {
		this.y = (double) y;
	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.##");
		return this.getClass().getSimpleName().substring(0, 1) + "[" + this.id + ":" + df.format(x) + "," + df.format(y) + "]";
    }
    
	public Coordinates clone() {
		Coordinates clone = new Coordinates(this.id, (double) this.x, (double) this.y);
	
	    return clone;
	}

}
