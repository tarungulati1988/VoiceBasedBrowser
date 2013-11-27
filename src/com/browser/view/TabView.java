package com.browser.view;

import java.io.IOException;

import com.browser.controller.BrowserWindow;
import com.browser.main.VoiceBrowser;
import com.browser.view.ToolbarView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TabView extends Tab {

	private TabToolbarView tabToolbarViewObj;
	private BrowserWindow browserWindow;
	
	//testing
	private TextField searchBar;
    private Button findTagButton;
    private Button selectButton;
	

	public TabView() {
		
		tabToolbarViewObj = new TabToolbarView();

		browserWindow = new BrowserWindow();
		
		//sideBarViewObj = new SideBarView();
		//sideBarViewObj=SideBarView.getInstance();
		
		
		final BorderPane tabLayout = new BorderPane();
		
		tabLayout.setTop(tabToolbarViewObj.CreateNavToolbar());
		tabLayout.setCenter(browserWindow);

		browserWindow.getView().getEngine().titleProperty()
				.addListener(new ChangeListener<String>() {
					@Override
					public void changed(
							ObservableValue<? extends String> observableValue,
							String oldValue, String newTitle) {
						if (newTitle != null && !"".equals(newTitle)) {
							setText(newTitle);
						}
					}
				});

		browserWindow.getView().getEngine().getLoadWorker().stateProperty()
				.addListener(new ChangeListener<State>() {
					public void changed(ObservableValue ov, State oldState,
							State newState) {
						if (newState == State.RUNNING) {

							tabToolbarViewObj.getAddressBarField().setText(
									browserWindow.getView().getEngine()
											.getLocation());
						}

						if (newState == State.SUCCEEDED) {
							System.out.println("in tab: " + getText());
							System.out.println("Page "
									+ browserWindow.getView().getEngine()
											.getLocation() + " loaded");
						}

					}
				});

		/*tabLayout.getChildren().addAll(tabToolbarViewObj.CreateNavToolbar(),
				browserWindow);*/
		
		searchBar= new TextField();
		findTagButton = new Button("Find Tag");
		selectButton= new Button("Select");
		
		HBox selectTagLayout= new HBox();
		
		selectTagLayout.getChildren().addAll(searchBar, findTagButton, selectButton);
		HBox.setHgrow(searchBar, Priority.ALWAYS);
		
		VBox testLayout= new VBox();
		
		testLayout.getChildren().addAll(tabToolbarViewObj.CreateNavToolbar(),selectTagLayout);
		tabLayout.setTop(testLayout);
		
		//tabLayout.setTop(tabToolbarViewObj.CreateNavToolbar());
		tabLayout.setCenter(browserWindow);
		
		//tabLayout.setLeft(sideBarViewObj.getInstance().getSideBar());

		tabToolbarViewObj.getNavButton().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent actionEvent) {
				try {
					browserWindow.navTo(tabToolbarViewObj.getAddressBarField().getText());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		final VBox sideBarContainer= new VBox();
		
		setOnSelectionChanged(new EventHandler<Event>(){

            @Override
            public void handle(Event arg0) {
                // TODO Auto-generated method stub
                
                if(sideBarContainer.getChildren().contains(SideBarView.getBarDisplay()))
                {
                    sideBarContainer.getChildren().remove(SideBarView.getBarDisplay());
                }
                
                sideBarContainer.getChildren().add(SideBarView.getBarDisplay());
                
                
                System.out.println(SideBarView.getBarDisplay().toString());
            }
            
        });

		tabLayout.setLeft(sideBarContainer);
		setContent(tabLayout);
	}

}