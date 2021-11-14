package tests;

import base.Browser;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import shared.functions.tests.Methods;

import java.io.IOException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests extends Methods {

    @Test
    public void A_() {
        step1();
        step2();
        step3();
        step4();
        step5();
        try {
            step6();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
