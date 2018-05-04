package com.example.administrator.guhao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        String ss=appContext.getResources().getString(R.string.loginuser);
        System.out.println(ss);
        assertEquals("com.example.administrator.guhao", appContext.getPackageName());
        assertEquals("http://192.168.2.241:8080/test/ajax/loginuser.action",ss);
    }
}
