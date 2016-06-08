/**
 * Created by vks on 07.06.16.
 */

public class DistanceCalculator {

    public static double distance(double ax0, double ay0, double bx0, double by0, double r0,
                                  double ax1, double ay1, double bx1, double by1, double r1) {

        // Всевозможные расстояния между полуокружностями разных капсул
        double ans = Math.sqrt((ax0 - ax1) * (ax0 - ax1) + (ay0 - ay1) * (ay0 - ay1)) - (r0 + r1);
        ans = Math.min(ans, Math.sqrt((ax0 - bx1) * (ax0 - bx1) + (ay0 - by1) * (ay0 - by1)) - (r0 + r1));
        ans = Math.min(ans, Math.sqrt((bx0 - bx1) * (bx0 - bx1) + (by0 - by1) * (by0 - by1)) - (r0 + r1));
        ans = Math.min(ans, Math.sqrt((bx0 - ax1) * (bx0 - ax1) + (by0 - ay1) * (by0 - ay1)) - (r0 + r1));

        // Единичные нормали к отрезкам (ax0, ay0) -> (bx0, by0) и (ax1, ay1) -> (bx1, by1)
        double nx0 = -(by0 - ay0);
        double ny0 = (bx0 - ax0);
        double len0 = Math.sqrt(nx0 * nx0 + ny0 * ny0);
        nx0 /= len0;
        ny0 /= len0;

        double nx1 = -(by1 - ay1);
        double ny1 = (bx1 - ax1);
        double len1 = Math.sqrt(nx1 * nx1 + ny1 * ny1);
        nx1 /= len1;
        ny1 /= len1;

        // Всевозможные расстояния между полуокружностями и отрезками на границе капсул.
        // Координаты граничных отрезков получаются прибавлением (или вычитанием) нормали, умноженной на
        // соответствующи радиус, к координатам центров полуокружностей.
        ans = Math.min(ans, pointToSegmentDistance(ax0, ay0,
                ax1 + r1 * nx1, ay1 + r1 * ny1, bx1 + r1 * nx1, by1 + r1 * ny1) - r0);
        ans = Math.min(ans, pointToSegmentDistance(bx0, by0,
                ax1 + r1 * nx1, ay1 + r1 * ny1, bx1 + r1 * nx1, by1 + r1 * ny1) - r0);
        ans = Math.min(ans, pointToSegmentDistance(ax0, ay0,
                ax1 - r1 * nx1, ay1 - r1 * ny1, bx1 - r1 * nx1, by1 - r1 * ny1) - r0);
        ans = Math.min(ans, pointToSegmentDistance(bx0, by0,
                ax1 - r1 * nx1, ay1 - r1 * ny1, bx1 - r1 * nx1, by1 - r1 * ny1) - r0);

        ans = Math.min(ans, pointToSegmentDistance(ax1, ay1,
                ax0 + r0 * nx0, ay0 + r0 * ny0, bx0 + r0 * nx0, by0 + r0 * ny0) - r1);
        ans = Math.min(ans, pointToSegmentDistance(bx1, by1,
                ax0 + r0 * nx0, ay0 + r0 * ny0, bx0 + r0 * nx0, by0 + r0 * ny0) - r1);
        ans = Math.min(ans, pointToSegmentDistance(ax1, ay1,
                ax0 - r0 * nx0, ay0 - r0 * ny0, bx0 - r0 * nx0, by0 - r0 * ny0) - r1);
        ans = Math.min(ans, pointToSegmentDistance(bx1, by1,
                ax0 - r0 * nx0, ay0 - r0 * ny0, bx0 - r0 * nx0, by0 - r0 * ny0) - r1);

        // Если расстояние между центрами окружностей не больше суммы их радиусов, то они пересекаются либо вложены.
        // Если расстояние от центра окружности до отрезка не превосходит её радиус, то отрезок пересекает окружность
        // или касается её.
        // В любом из этих случаев ans будет не больше нуля.
        if (ans <= 0) {
            return 0.0;
        }

        //Если любые два граничных отрезка различных капсул пересекаются, расстояние равно нулю.
        if (segmentIntersection(
                ax0 + r0 * nx0, ay0 + r0 * ny0, bx0 + r0 * nx0, by0 + r0 * ny0,
                ax1 + r1 * nx1, ay1 + r1 * ny1, bx1 + r1 * nx1, by1 + r1 * ny1) ||
                segmentIntersection(
                        ax0 + r0 * nx0, ay0 + r0 * ny0, bx0 + r0 * nx0, by0 + r0 * ny0,
                        ax1 - r1 * nx1, ay1 - r1 * ny1, bx1 - r1 * nx1, by1 - r1 * ny1) ||
                segmentIntersection(
                        ax0 - r0 * nx0, ay0 - r0 * ny0, bx0 - r0 * nx0, by0 - r0 * ny0,
                        ax1 + r1 * nx1, ay1 + r1 * ny1, bx1 + r1 * nx1, by1 + r1 * ny1) ||
                segmentIntersection(
                        ax0 - r0 * nx0, ay0 - r0 * ny0, bx0 - r0 * nx0, by0 - r0 * ny0,
                        ax1 - r1 * nx1, ay1 - r1 * ny1, bx1 - r1 * nx1, by1 - r1 * ny1)) {
            return 0.0;
        }

        // Проверка на вложенность: если центр любой полуокружности лежит внутри прямоугольника, образованного
        // граничными отрезками другой капсулы, расстояние равно нулю.
        if (pointInSquare(ax0 + r0 * nx0, ay0 + r0 * ny0, bx0 + r0 * nx0, by0 + r0 * ny0,
                ax0 - r0 * nx0, ay0 - r0 * ny0, bx0 - r0 * nx0, by0 - r0 * ny0, ax1, ay1) ||
                pointInSquare(ax0 + r0 * nx0, ay0 + r0 * ny0, bx0 + r0 * nx0, by0 + r0 * ny0,
                        ax0 - r0 * nx0, ay0 - r0 * ny0, bx0 - r0 * nx0, by0 - r0 * ny0, bx1, by1) ||
                pointInSquare(ax1 + r1 * nx1, ay1 + r1 * ny1, bx1 + r1 * nx1, by1 + r1 * ny1,
                        ax1 - r1 * nx1, ay1 - r1 * ny1, bx1 - r1 * nx1, by1 - r1 * ny1, ax0, ay0) ||
                pointInSquare(ax1 + r1 * nx1, ay1 + r1 * ny1, bx1 + r1 * nx1, by1 + r1 * ny1,
                        ax1 - r1 * nx1, ay1 - r1 * ny1, bx1 - r1 * nx1, by1 - r1 * ny1, bx0, by0)) {
            return 0.0;
        }

        return ans;
    }

    private static double pointToSegmentDistance(double ptx, double pty,
                                                 double ax, double ay,
                                                 double bx, double by) {
        double scalar1 = (ax - ptx) * (bx - ax) + (ay - pty) * (by - ay);
        double scalar2 = (bx - ptx) * (ax - bx) + (by - pty) * (ay - by);

        // Один из углов тупой или прямой — проекция точки на отрезок лежит вне его либо в одной из вершин.
        // Расстояние равно меньшей из длин отрезков, соединяющих точку и вершины.
        if (scalar1 * scalar2 <= 0) {
            return Math.min(
                    Math.sqrt((ax - ptx) * (ax - ptx) + (ay - pty) * (ay - pty)),
                    Math.sqrt((bx - ptx) * (bx - ptx) + (by - pty) * (by - pty)));
        }

        // Если проекция точки лежит на отрезке, считаем расстояние по формуле расстояния от точки до прямой.
        double nx = -(by - ay);
        double ny = (bx - ax);
        double c = -(nx * ax + ny * ay);
        return Math.abs(nx * ptx + ny * pty + c) / Math.sqrt(nx * nx + ny * ny);
    }

    private static boolean segmentIntersection(double ax0, double ay0, double bx0, double by0,
                                               double ax1, double ay1, double bx1, double by1) {
        return (signedArea(ax0, ay0, bx0, by0, ax1, ay1) * signedArea(ax0, ay0, bx0, by0, bx1, by1) < 0) &&
                (signedArea(ax1, ay1, bx1, by1, ax0, ay0) * signedArea(ax1, ay1, bx1, by1, bx0, by0) < 0);
    }

    private static boolean pointInSquare(double ax0, double ay0, double bx0, double by0,
                                         double ax1, double ay1, double bx1, double by1,
                                         double ptx, double pty) {
        return signedArea(ax0, ay0, bx0, by0, ptx, pty) * signedArea(ax1, ay1, bx1, by1, ptx, pty) < 0 &&
                signedArea(ax0, ay0, ax1, ay1, ptx, pty) * signedArea(bx0, by0, bx1, by1, ptx, pty) < 0;
    }

    private static double signedArea(double x1, double y1, double x2, double y2, double x3, double y3) {
        return (x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1);
    }
}
