package com.example.vamsij.travelingsalesman;

import android.content.Context;
import android.graphics.*;
import android.os.AsyncTask;
import android.util.*;
import android.view.*;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.*;
import android.view.View.OnTouchListener;
import android.graphics.*;

public class GameView extends View implements OnTouchListener  {

    private Paint p;
    private Paint paint = new Paint();
    private Canvas c = new Canvas();
    private Path path = new Path();
    protected HashMap<Integer,Point> points;
    protected LinkedList<Point> tempPoints = new LinkedList();
    protected LinkedList<Point> pointList = new LinkedList();
    protected LinkedList<Point> validPointList = new LinkedList();
    protected LinkedList<Point> currentPoints = new LinkedList();
    protected ArrayList<Point> shortestPathPoints = new ArrayList<Point>();
    protected HashMap<Point,Integer> pointConnections = new HashMap<Point,Integer>();
    protected GameView thisView = this;
    protected int numPoints;
    protected int spinnerNum;
    protected ArrayList<Point> t;
    protected int attempt = 0;
    private static boolean clearState;
    private static boolean undoState;
    private static boolean isValid = true;


    public ViewGroup.LayoutParams params;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet as) {
        super(context, as);
    }

    public void addPoints() {
        points.put(1, new Point(160,75));
        points.put(2, new Point(160, 155));
        points.put(3, new Point(160, 255));
        points.put(4, new Point(100, 255));
        points.put(5, new Point(490, 120));
        points.put(6, new Point(800, 120));
        points.put(7, new Point(1100, 170));
        points.put(8, new Point(1350, 220));
        points.put(9, new Point(1300, 250));
        points.put(10, new Point(1450, 220));
        points.put(11, new Point(1450, 290));
        points.put(12, new Point(1350, 370));
        points.put(13, new Point(1425, 50));
        points.put(14, new Point(1725, 65));
        points.put(15, new Point(1725, 300));
        points.put(16, new Point(1410, 700));
        points.put(17, new Point(1250, 523));
        points.put(18, new Point(1100, 285));
        points.put(19, new Point(1050, 385));
        points.put(20, new Point(1030, 555));
        points.put(21, new Point(1020, 700));
        points.put(22, new Point(1111, 700));
        points.put(23, new Point(1111, 660));
        points.put(24, new Point(820, 650));
        points.put(25, new Point(840, 450));
        points.put(26, new Point(840, 220));
        points.put(27, new Point(530, 645));
        points.put(28, new Point(530, 475));
        points.put(29, new Point(530, 775));
        points.put(30, new Point(300, 645));
        points.put(31, new Point(280, 545));
        points.put(32, new Point(290, 255));
    }
    public void init() {
        setBackgroundResource(R.drawable.campus);
        p = new Paint();
        p.setColor(Color.RED);
        p.setStrokeWidth(20);
        points = new HashMap<Integer, Point>();
        addPoints();
        Point[] pointArray = new Point[spinnerNum];
        Random r = new Random();
        Point q;
        for (int i = 0; i < spinnerNum; i++) {
            int n = r.nextInt(32) + 1;
            q = points.get(n);
            currentPoints.add(q);
            pointConnections.put(q, 0);
            pointArray[i] = q;
        }
        ShortestPath shortest = new ShortestPath();
        shortestPathPoints = ShortestPath.shortestPath(pointArray);
    }

    public boolean compareDist(LinkedList<Point> t1, ArrayList<Point> t2) {
        ShortestPath shortestpath = new ShortestPath();
        double distance1 = 0;
        double distance2 = 0;
        boolean isSame = false;
        int percentDiff;
        for (int i = 0; i < t1.size() - 1; i++) {
            distance1 = distance1 + shortestpath.dist(t1.get(i), t1.get(i+1));
        }
        for (int j = 0; j < t2.size() - 1; j++) {
            distance2 = distance2 + shortestpath.dist(t2.get(j), t2.get(j+1));
        }
        distance2 = distance2 + shortestpath.dist(t2.get(t2.size()-1), t2.get(0));
        percentDiff = (int)  ((Math.abs(distance1 - distance2)/ distance2) * 100D);

        if (percentDiff == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public int getDifferencePercent(LinkedList<Point> t1, ArrayList<Point> t2) {
            ShortestPath shortestpath = new ShortestPath();
            double distance1 = 0;
            double distance2 = 0;
            int percentDiff = 0;
            boolean isSame = false;
            for (int i = 0; i < t1.size() - 1; i++) {
                distance1 = distance1 + shortestpath.dist(t1.get(i), t1.get(i+1));
            }
            for (int j = 0; j < t2.size() - 1; j++) {
                distance2 = distance2 + shortestpath.dist(t2.get(j), t2.get(j+1));
            }
           distance2 = distance2 + shortestpath.dist(t2.get(t2.size()-1), t2.get(0));
        percentDiff = (int)  ((Math.abs(distance1 - distance2)/ distance2) * 100D);
        return percentDiff;
    }

    public boolean pathComplete(LinkedList<Point> points) {
        if ((points.size() > spinnerNum)
                && points.get(0).equals(points.get(points.size() - 1))) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        ShortestPath path = new ShortestPath();
        t = new ArrayList<Point>();
        Point[] pts = new Point[currentPoints.size()];
        int it = 0;
        for (Point pq : currentPoints) {
            pts[it] = pq;
            it++;
        }

        t = path.shortestPath(pts);
        Point q;
        for (int i = 0; i < currentPoints.size(); i++) {
            q = currentPoints.get(i);
            canvas.drawPoint(q.getX(), q.getY(), p);
        }
        paint.setStrokeWidth(5);
        paint.setColor(Color.YELLOW);

        if (pathComplete(validPointList)) {
                if (compareDist(validPointList, t)) {
                    Toast endToast = Toast.makeText(getContext(), "You have found the shortest Path!", Toast.LENGTH_SHORT);
                    endToast.show();
                }
                else if (attempt < 3) {
                        Toast endToast = Toast.makeText(getContext(), "Not Quite! Your solution is about " + getDifferencePercent(validPointList, t)
                                + " % longer than the shortest path", Toast.LENGTH_SHORT);
                        endToast.show();
                        attempt = attempt + 1;
                }

                //endThread.start();
        }
        if (validPointList.size() > 1 && attempt < 3){
            for (int i = 0; i < validPointList.size() - 1; i++) {
                Point t1 = validPointList.get(i);
                Point t2 = validPointList.get(i+1);
                canvas.drawLine(t1.getX(), t1.getY(), t2.getX(), t2.getY(), p);
            }
        }
        else if (validPointList.size() > 1 && attempt >= 3) {
                for (int k = 0; k < t.size() - 1; k++) {
                    Point pt1 = t.get(k);
                    Point pt2 = t.get(k + 1);
                    canvas.drawLine(pt1.getX(), pt1.getY(), pt2.getX(), pt2.getY(), p);
                }
                canvas.drawLine(t.get(t.size()-1).getX(), t.get(t.size()-1).getY(), t.get(0).getX(), t.get(0).getY(), p);
                Toast endToast = Toast.makeText(getContext(), "Here is the correct solution. Press Clear to try " +
                        "the same map or Quit to try another one", Toast.LENGTH_SHORT);
                endToast.show();
                attempt = 0;
        }
        if (isValid == false && (validPointList.size() == t.size())) {
            Toast endToast = Toast.makeText(getContext(), "Invalid Circuit!", Toast.LENGTH_SHORT);
            endToast.show();
            isValid = true;
        }
        if (numPoints > 1) {
            for (int i = 0; i < numPoints-1; i++) {
                Point p1 = pointList.get(i);
                Point p2 = pointList.get(i + 1);
                canvas.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY(), paint);
            }
        }
    }

    public void onClickUndo() {
        if (clearState == true) {
            clearState = false;
            // tempPointList = new LinkedList();
            invalidate();
        }
        if (validPointList.size() > 1) {
            validPointList.remove(validPointList.size()-1);
            tempPoints.remove(tempPoints.size()-1);
            invalidate();
        }
        else  {
                Toast endToast = Toast.makeText(getContext(), "There is nothing to undo!", Toast.LENGTH_SHORT);
                endToast.show();
                invalidate();
        }
    }

    public void onClickClear() {
        clearState = true;
        validPointList.clear();
        tempPoints.clear();
        invalidate();
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent event){
        return onTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            Point newPoint = new Point(event.getX(), event.getY());
            pointList.add(newPoint);
            numPoints++;
            for(Point p : currentPoints) {
                for(Point q : pointList) {
                    float x = q.getX();
                    float y = q.getY();
                    if ((q.getX() > p.getX() - 10) && (q.getX() < p.getX() + 10)
                            && (q.getY() > p.getY() - 10) && (q.getY() < p.getY() + 10)) {
                        if (!validPointList.contains(p)) {
                            validPointList.addLast(p);
                        }
                        else if (validPointList.getFirst().equals(p) && (validPointList.size() == currentPoints.size())) {
                            validPointList.addLast(p);
                        }

                    }
                }
            }
            invalidate();
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            Point newPoint = new Point(event.getX(), event.getY());
            pointList.add(newPoint);
            numPoints++;
            invalidate();
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP){
            for(Point p : currentPoints) {
                for(Point q : pointList) {
                    float x = q.getX();
                    float y = q.getY();
                    if ((q.getX() > p.getX() - 10) && (q.getX() < p.getX() + 10)
                            && (q.getY() > p.getY() - 10) && (q.getY() < p.getY() + 10)) {
                        if (!validPointList.contains(p)) {
                                validPointList.addLast(p);
                                tempPoints.addLast(p);
                        }

                        else if (validPointList.getFirst().equals(p) && (validPointList.size() == currentPoints.size())) {
                            validPointList.addLast(p);
                            tempPoints.addLast(p);
                        }

                    }
                }
            }
            pointList.clear();
            numPoints = 0;
            invalidate();
            return true;
        }
        return true;

    }

    protected void setSpinnerNum(int i) {
        Log.v("GAME VIEW", "set spinnerNum");
        spinnerNum = i;
        init();
    }
}