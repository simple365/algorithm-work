import java.util.ArrayList;
import java.util.List;

public class BruteCollinearPoints {
	private LineSegment[] lineSegments;
	
	public BruteCollinearPoints(Point[] points) // finds all line segments containing 4 points
	{
		if(points ==null ) {
			throw new java.lang.IllegalArgumentException();
		}
		List<LineSegment> lineSegments=new ArrayList<>();

		for(int i=0;i<points.length;i++) {
			Point point1=points[i];
			if(point1==null) {
				throw new java.lang.IllegalArgumentException();
			}
			Point max=point1;
			Point min=point1;
			for(int j=i+1;j<points.length;j++) {
				Point point2=points[j];
				double slope2=checkPoint(point1, point2,max,min);
				for(int k=j+1;k<points.length;k++) {
					Point point3=points[j];
					double slope3=checkPoint(point1, point3,max,min);
					for(int l=k+1;k<points.length;l++) {
						Point point4=points[l];
						double slope4=checkPoint(point1, point4,max,min);
						if(slope2==slope3&&slope3==slope4) {
							lineSegments.add(new LineSegment(min, max));
						}
					}
				}
			}
		}
		this.lineSegments=(LineSegment[]) lineSegments.toArray();
	}
	
	private double checkPoint(Point point1,Point pointTarget,Point max,Point min) {
		if(pointTarget==null) {
			throw new java.lang.IllegalArgumentException();
		}
		double slope2=point1.slopeTo(pointTarget);
//		two points are the same
		if(Double.NEGATIVE_INFINITY==slope2) {
			throw new java.lang.IllegalArgumentException();
		}
		if(pointTarget.compareTo(max)>0)max=pointTarget;
		if(pointTarget.compareTo(min)<0)min=pointTarget;
		return slope2;
	}

	public int numberOfSegments() // the number of line segments
	{
		return lineSegments.length;
	}

	public LineSegment[] segments() // the line segments
	{
		return lineSegments;
	}
}
