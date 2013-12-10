package com.browser.command;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import javafx.collections.ObservableList;

import com.browser.view.SideBarView;
import com.browser.view.TabView;

public class SaveCommand implements Command{

	@Override
	public void execute() {
		System.out.println("Saving smart notes: ");
		ObservableList<CharSequence> paragraph = SideBarView.getTextArea().getParagraphs();
	    Iterator<CharSequence>  iter = paragraph.iterator();
	    try
	    {
	        BufferedWriter bf = new BufferedWriter(new FileWriter(new File("smartnotes.txt")));
	        while(iter.hasNext())
	        {
	            CharSequence seq = iter.next();
	            bf.append(seq);
	            bf.newLine();
	        }
	        bf.flush();
	        bf.close();
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	}

}