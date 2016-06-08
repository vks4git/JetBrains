/**
 * Created by vks on 07.06.16.
 */

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class Tests {
    @Test
    public void testNotIntersecting0() {
        double dist = DistanceCalculator.distance(0, 0, 0, 1, 1, 3, 0, 3, 2, 1);
        assertThat(dist, is(1.0));
    }

    @Test
    public void testNotIntersecting1() {
        double dist = DistanceCalculator.distance(0, 0, 0, 1, 1, -5, 5, 5, 5, 2);
        assertThat(dist, is(1.0));
    }

    @Test
    public void testNotIntersecting2() {
        double dist = DistanceCalculator.distance(-42, 0, 0, 0, 1, 3, 0, 42, 0, 1);
        assertThat(dist, is(1.0));
    }

    @Test
    public void testNotIntersecting3() {
        double dist = DistanceCalculator.distance(0, 0, 1, 1, 1, 4, 0, 4, 2, 1);
        assertThat(dist, is(1.0));
    }

    @Test
    public void testNotIntersecting4() {
        double dist = DistanceCalculator.distance(0, 0, 1, 1, 1, 2, -2, 3, -1, 1);
        assertThat(dist, is(Math.sqrt(8) - 2));
    }

    @Test
    public void testNotIntersecting5() {
        double dist = DistanceCalculator.distance(0, 0, 2, 4, 1, 4, 1, 5, -1, 1);
        assertThat(dist, is(7 / Math.sqrt(5) - 2));
    }

    @Test
    public void testNotIntersecting6() {
        double dist = DistanceCalculator.distance(4, 1, 5, -1, 1, 0, 0, 2, 4, 1);
        assertThat(dist, is(7 / Math.sqrt(5) - 2));
    }

    @Test
    public void testNotIntersecting7() {
        double dist = DistanceCalculator.distance(4, 0, 4, 2, 1, 0, 0, 1, 1, 1);
        assertThat(dist, is(1.0));
    }

    @Test
    public void testNotIntersecting8() {
        double dist = DistanceCalculator.distance(0, 0, 2, 2, 1, 2, -2, 6, 0, 1);
        assertThat(dist, is(Math.sqrt(8) - 2));
    }

    @Test
    public void testNotIntersecting9() {
        double dist = DistanceCalculator.distance(0, 0, 2, 2, 1, 5, 3, 9, 4, 2);
        assertThat(dist, is(Math.sqrt(10) - 3));
    }

    @Test
    public void testTouching0() {
        double dist = DistanceCalculator.distance(0, 0, 0, 2, 1, 3, 0, 3, -10, 2);
        assertThat(dist, is(0.0));
    }

    @Test
    public void testTouching1() {
        double dist = DistanceCalculator.distance(0, 0, 2, 0, 1, 5, 0, 10, 0, 2);
        assertThat(dist, is(0.0));
    }

    @Test
    public void testTouching2() {
        double dist = DistanceCalculator.distance(0, 0, 2, 0, 1, 5, 0, 5, -10, 2);
        assertThat(dist, is(0.0));
    }

    @Test
    public void testTouching3() {
        double dist = DistanceCalculator.distance(0, 0, 0, -2, 1, 2, 0, 5, 9, 1);
        assertThat(dist, is(0.0));
    }

    @Test
    public void testTouching4() {
        double dist = DistanceCalculator.distance(5, 0, 10, 0, 2, 0, 0, 2, 0, 1);
        assertThat(dist, is(0.0));
    }

    @Test
    public void testIntersecting0() {
        double dist = DistanceCalculator.distance(0, 0, 0, 2, 2, 2, 0, 5, 9, 1);
        assertThat(dist, is(0.0));
    }

    @Test
    public void testIntersecting1() {
        double dist = DistanceCalculator.distance(-5, -5, 5, 5, 2, 5, -5, -5, 5, 2);
        assertThat(dist, is(0.0));
    }

    @Test
    public void testIntersecting2() {
        double dist = DistanceCalculator.distance(-500, -500, 500, 500, 0.1, 500, -500, -500, 500, 2);
        assertThat(dist, is(0.0));
    }

    @Test
    public void testIntersecting3() {
        double dist = DistanceCalculator.distance(0, 0, 2, 0, 2, 5, -42, 5, 42, 2);
        assertThat(dist, is(0.0));
    }

    @Test
    public void testIntersecting4() {
        double dist = DistanceCalculator.distance(5, -42, 5, 42, 2, 0, 0, 2, 0, 2);
        assertThat(dist, is(0.0));
    }

    @Test
    public void testNested0() {
        double dist = DistanceCalculator.distance(0, -5, -5, 5, 5, 0, -1, 0, 1, 2);
        assertThat(dist, is(0.0));
    }

    @Test
    public void testNested1() {
        double dist = DistanceCalculator.distance(0, -5, -5, 5, 5, -1, 0, 1, 0, 2);
        assertThat(dist, is(0.0));
    }

    @Test
    public void testNested2() {
        double dist = DistanceCalculator.distance(-1, 0, 1, 0, 2, 0, -5, -5, 5, 5);
        assertThat(dist, is(0.0));
    }

    @Test
    public void testNested3() {
        double dist = DistanceCalculator.distance(-100, 0, 100, 0, 50, 0, -1, 0, 1, 1);
        assertThat(dist, is(0.0));
    }

    @Test
    public void testNested4() {
        double dist = DistanceCalculator.distance(-100, 0, 100, 0, 50, -1, 0, 1, 0, 1);
        assertThat(dist, is(0.0));
    }

}
