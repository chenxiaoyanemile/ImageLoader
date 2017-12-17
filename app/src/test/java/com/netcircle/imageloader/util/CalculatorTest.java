package com.netcircle.imageloader.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sweetgirl on 2017/12/17
 */
public class CalculatorTest {

    private Calculator mCalculator;
    @Before
    public void setUp() throws Exception {
        mCalculator = new Calculator();

    }

    @Test
    public void sum() throws Exception {

        assertEquals(6d, mCalculator.sum(1d, 5d), 0);

    }

    @Test
    public void subtraction() throws Exception {
        assertEquals(1d, mCalculator.subtraction(5d, 4d), 0);
    }

    @Test
    public void division() throws Exception {
        assertEquals(5d, mCalculator.division(20d, 4d), 0);
    }

    @Test
    public void multiplication() throws Exception {
        assertEquals(20d, mCalculator.multiplication(5d, 4d), 0);
    }

}