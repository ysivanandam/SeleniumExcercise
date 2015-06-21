package com.siva.excercise.testscript.ju.testrunner;

import org.junit.runner.JUnitCore;
//import org.junit.runner.Result;
//import org.junit.runner.notification.Failure;

import com.siva.excercise.testscript.ju.testsuite.ExcerciseSuite;

public class ExcerciseRunner {

	public static void main(String[] args) {
		JUnitCore.runClasses(ExcerciseSuite.class);		
	}

}
