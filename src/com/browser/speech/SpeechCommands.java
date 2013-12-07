package com.browser.speech;
/*
 * Copyright 1999-2004 Carnegie Mellon University.
 * Portions Copyright 2004 Sun Microsystems, Inc.
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 *
 */


import com.browser.controller.BrowserWindow;
import com.browser.main.VoiceBrowser;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;


/**
 * A simple HelloWorld demo showing a simple speech application built using Sphinx-4. This application uses the Sphinx-4
 * endpointer, which automatically segments incoming audio into utterances and silences.
 */
public class SpeechCommands {
	
	private static BrowserWindow browserWindow;
	
    public static String CommandSpoken() {
        ConfigurationManager cm;
        String resultText = null;
        
            cm = new ConfigurationManager(SpeechCommands.class.getResource("speechcommands.config.xml"));

        Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
        recognizer.allocate();

        // start the microphone or exit if the programm if this is not possible
        Microphone microphone = (Microphone) cm.lookup("microphone");
        if (!microphone.startRecording()) {
            System.out.println("Cannot start microphone.");
            recognizer.deallocate();
            //System.exit(1);
        }

        System.out.println("Say: (Good morning | Hello | Go | Back | Refresh | Forward | Close | Yahoo .Dot Com | Smart Notes | Read | Bing .Dot Com | Save | Cnn .Dot com | Book Mark) ");

        // loop the recognition until the programm exits.
        //while (true) {
            System.out.println("Start speaking.\n");
            Result result = recognizer.recognize();

            if (result != null) {
                resultText = result.getBestFinalResultNoFiller();
                System.out.println("You said: " + resultText + '\n');
            } else {
                System.out.println("I can't hear what you said.\n");
            }
            return resultText;
       // }
        
    }
}
