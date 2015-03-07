package com.pdfcorr;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by joeymadich on 2/3/15.
 */
public class SimpleTest {

    @Test
    public void ensureTestsCanRun() {
        Integer testValue = 15;

        assertEquals("test value does not equal the expected value", 15, testValue.intValue());
    }

}
