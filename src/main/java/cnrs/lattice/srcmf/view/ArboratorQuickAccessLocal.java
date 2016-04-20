package cnrs.lattice.srcmf.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cnrs.lattice.srcmf.beans.MaltFormat;
import cnrs.lattice.srcmf.model.Sentence;
import cnrs.lattice.srcmf.model.Word;
import cnrs.lattice.srcmf.utils.Tools;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import netscape.javascript.JSObject;
//import cnrs.lattice.tools.utils.Tools;


/**
 * Embeds jQuery in a document loaded into a WebView. Uses jQuery to run an
 * animation to hide each link in the document as it is clicked.
 */
public class ArboratorQuickAccessLocal {
	final FileChooser fileChooser = new FileChooser();
	public Stage primaryStage1;
	public WebEngine engine1;
	public boolean header = false;
	public static MaltFormat maltFormat = new MaltFormat();
	public static final String DEFAULT_JQUERY_MIN_VERSION = "1.7.1";
	public static final String JQUERY_LOCATION = ArboratorQuickAccessLocal.class.getResource("/html/js/jquery-1.12.0.min.js")
			.toExternalForm();
	public Scene scene1;
	public static StackPane root1;
	public Browser mainBrowser;
	public String content ;
	public String id;
	
	public void setMainBrowser(Browser browser){
		this.mainBrowser = browser;
	}
	
	public void setContent(String content, String id){
		this.content = content;
		this.id = id;
	}
	
	public Scene getScene(Stage primaryStage) throws Exception{
		
		  primaryStage1 = primaryStage;
		    final WebView webView = new WebView();
		    final WebEngine engine = webView.getEngine();
		    engine1 = engine;

		    Tools tool = new Tools();
		    String html = tool.accessRessourceFile("/html/Arborator-Quickedit.html");
//		    String html = tool.accessRessourceFile("/html/testhtml.html");

			StringBuilder sb = new StringBuilder();
//			sb.append(tool.accessRessourceFile("/html/css/bootstrap.min.css") + "\n");
//			sb.append(tool.accessRessourceFile("/html/css/bootstrap-toggle.min.css") + "\n");
			sb.append(tool.accessRessourceFile("/html/css/arborator/arborator.css") + "\n");
			sb.append(tool.accessRessourceFile("/html/css/arborator/colpick.css") + "\n");
			sb.append(tool.accessRessourceFile("/html/css/arborator/jquery-ui-1.8.18.custom.css") + "\n");
			String css = sb.toString();
			
//			StringBuilder js = new StringBuilder();
//			js.append(new Tools().accessRessourceFile("/html/js/jquery-1.12.0.min.js"));
//			js.append(new Tools().accessRessourceFile("/html/js/arborator/jquery.js"));
//			js.append(new Tools().accessRessourceFile("/html/js/arborator/raphael.min.js"));
//
//			js.append(new Tools().accessRessourceFile("/html/js/arborator/jquery-ui-1.8.18.custom.min.js"));
//
//			js.append(new Tools().accessRessourceFile("/html/js/arborator/jquery.cookie.js"));
//
//			js.append(new Tools().accessRessourceFile("/html/js/arborator/arborator.draw.js"));
//
//			js.append(new Tools().accessRessourceFile("/html/js/arborator/q.js"));
//
//			js.append(new Tools().accessRessourceFile("/html/js/arborator/jsundoable.js"));
//
//			js.append(new Tools().accessRessourceFile("/html/js/arborator/colpick.js"));
//			String jsstr = js.toString();
			
			html = html.replace("{csshere}", css)
//					.replace("{javascripthere}", jsstr)
					.replaceAll("\\{redo\\}","<img src=\""+getClass().getResource("/html/img/arborator/redo.png").toExternalForm()+"\" border=\"0\" title=\"Arborator Main Page\">")
					.replaceAll("\\{export\\}","<img src=\""+getClass().getResource("/html/img/arborator/export.png").toExternalForm()+"\" border=\"0\" title=\"Arborator Main Page\">")
					.replaceAll("\\{cut\\}","<img src=\""+getClass().getResource("/html/img/arborator/cut.png").toExternalForm()+"\" >")
					.replaceAll("\\{arboratorNano\\}","<img src=\""+getClass().getResource("/html/img/arborator/arboratorNano.png").toExternalForm()+"\" border=\"0\" title=\"Arborator Main Page\">")
					.replaceAll("\\{help\\}","<img src=\""+getClass().getResource("/html/img/arborator/help.png").toExternalForm()+"\" border=\"0\" style=\"vertical-align: text-top\">")
					.replaceAll("\\{style\\}","<img id=\"style\" src=\""+getClass().getResource("/html/img/arborator/style.png").toExternalForm()+"\" border=\"0\" title=\"style\" style=\"top:0px;\">")
					.replaceAll("\\{undo\\}","<img src=\""+getClass().getResource("/html/img/arborator/undo.png").toExternalForm()+"\" border=\"0\" title=\"Arborator Main Page\">")
					.replaceAll("\\{q\\}","<img src=\""+getClass().getResource("/html/img/arborator/q.png").toExternalForm()+"\" border=\"0\" title=\"Arborator – Quickedit\">")
					.replaceAll("\\{conll10\\}",getClass().getResource("/html/img/arborator/conll10.png").toExternalForm())
//					.replaceAll("\\{jquery-ui-css\\}",getClass().getResource("/html/css/arborator/jquery-ui-1.8.18.custom.css").toExternalForm())
//					.replaceAll("\\{jquery-ui-js\\}",getClass().getResource("/html/js/arborator/jquery-ui-1.8.18.custom.min.js").toExternalForm().replace("file:/","file:///"))
//					.replaceAll("\\{jquery-cookie\\}",getClass().getResource("/html/js/arborator/jquery.cookie.js").toExternalForm().replace("file:/","file:///"))
//					.replaceAll("\\{raphael\\}",getClass().getResource("/html/js/arborator/raphael.js").toExternalForm().replace("file:/","file:///"))
//					.replaceAll("\\{jquery\\}",getClass().getResource("/html/js/jquery-1.12.0.min.js").toExternalForm().replace("file:/","file:///"))
//					.replaceAll("\\{arborator-draw\\}",getClass().getResource("/html/js/arborator/arborator.draw.js").toExternalForm().replace("file:/","file:///"))
//					.replaceAll("\\{q-js\\}",getClass().getResource("/html/js/arborator/q.js").toExternalForm().replace("file:/","file:///"))
//					.replaceAll("\\{jsundoable\\}",getClass().getResource("/html/js/arborator/jsundoable.js").toExternalForm().replace("file:/","file:///"))
//					.replaceAll("\\{colpick\\}",getClass().getResource("/html/js/arborator/colpick.js").toExternalForm().replace("file:/","file:///"))
//					
					
					;
			Tools.ecrire("temp", html);
			engine.loadContent(html);
		    
//		    engine.load("http://arborator.ilpga.fr/q.cgi");
		    
		    
		    engine.setOnAlert(new EventHandler<WebEvent<String>>() {
		        @Override public void handle(WebEvent<String> event) {
		          Stage popup = new Stage();
		          popup.initOwner(primaryStage);
		          popup.initStyle(StageStyle.UTILITY);
		          popup.initModality(Modality.WINDOW_MODAL);
		          
		          StackPane content = new StackPane();
		          content.getChildren().setAll(
		            new Label(event.getData())
		          );
		          content.setPrefSize(200, 100);
		          
		          popup.setScene(new Scene(content));
		          popup.show();
		        }
		      });
		   
		    
		    engine.documentProperty().addListener(new ChangeListener<Document>() {
		      @Override public void changed(ObservableValue<? extends Document> prop, Document oldDoc, Document newDoc) {
		    	  try{
//		    		  initializeJS(engine);
		    		  
//		    		  String script = new Tools().accessRessourceFile("/html/js/arborator/jquery.js");
//		    			engine.executeScript(script);

		    			String script = new Tools().accessRessourceFile("/html/js/jquery-1.12.0.min.js");
						engine.executeScript(script);
		    			script = new Tools().accessRessourceFile("/html/js/arborator/raphael.min.js");
		    			engine.executeScript(script);
		    			script = new Tools().accessRessourceFile("/html/js/arborator/jquery-ui-1.8.18.custom.min.js");
		    			engine.executeScript(script);
		    			script = new Tools().accessRessourceFile("/html/js/arborator/jquery.cookie.js");
		    			engine.executeScript(script);
		    			script = new Tools().accessRessourceFile("/html/js/arborator/arborator.draw.js");
		    			engine.executeScript(script);
		    			script = new Tools().accessRessourceFile("/html/js/arborator/q.js");
		    			script = script.replaceAll("\\{connect\\}",getClass().getResource("/html/img/arborator/connect.png").toExternalForm())
		    					
		    					;
		    			engine.executeScript(script);
		    			script = new Tools().accessRessourceFile("/html/js/arborator/jsundoable.js");
		    			engine.executeScript(script);
		    			script = new Tools().accessRessourceFile("/html/js/arborator/colpick.js");
		    			engine.executeScript(script);
		    			
		    			
						//for inside jar access
//		    		  script = new Tools().accessRessourceFile("/html/js/jquery-1.12.0.min.js");
//						engine.executeScript(script);

//						String sent = new Tools().accessRessourceFile("/html/temp1");
		    		  script = "$( document ).ready(function() {"
		    		  		+ "$('#conll').html('"+content.replaceAll("\n", "&#10;")+"');"
		    		  				+ "$('#conll').css({\"overflow\":\"auto\"});"
		    		  				+ "var r= '<input id=\"retrieve\" type=\"button\" onclick=\"call.getText()\" value=\"KEEP CHANGES\""
		    		  				+ "style=\"background-color: #4CAF50; border: none; color: white; cursor:pointer;"
		    		  				+ "text-align: center; text-decoration: none; display: inline-block; font-size: 16px;\"/>';"
		    		  				+ "$('#navigation').append(r);"
									+ "var p = '<p id=\"ding\" style=\"display:none;\"></p>';"
									+ "$('#body').append(p);"
//									+ "$('#body').click();"
		    		  				+ "$('#conll').blur();"
		    		  				+ "});"
		    		  				
									;
		    		  				
//						engine.executeScript(script);
						executejQuery(engine, script);

				    	  }catch(Exception e){
			    		  
			    	  }
		      }
		    });
		    //
		    
		    JSObject window = (JSObject) engine.executeScript("window");
		    window.setMember("call", new Call());
		    
		    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		    double width = screenSize.getWidth();
		    double height = screenSize.getHeight();
		    scene1 = new Scene(webView, width, height);
		return scene1;
	}
	
	

	/**
	 * Class to receive call events. ex: onclick="call.method()"
	 * 
	 * @author gael
	 *
	 */
	public class Call {

		public void getText() throws IOException {
			engine1.executeScript("document.getElementById('ding').innerHTML=document.getElementById('conll').value;");
			Element el = engine1.getDocument().getElementById("ding");
			// NodeList listEl = el.getElementsByTagName("img");
			String res = el.getTextContent();
			
			List<String> linesRes = Tools.StringToList(res);
			
			Sentence sentence = new Sentence();
			List<Word> words = Browser.sentences.get(Integer.parseInt(id)).getWords();
			int i = 0;
			for (Word w : words){
				String[] colsRes = linesRes.get(i).split("\t");
				w.setHead(colsRes[6]);
				w.setDeprel(colsRes[7]);

				sentence.addWord(w);
				
				i++;
			}
			
			Browser.sentences.set(Integer.parseInt(id), sentence);
			
			Notifier.INSTANCE.notifySuccess("Updated", "Change updated.\nDon't forget to export later!"
					+ "\nOtherwise it will not be saved. ");
			
			primaryStage1.close();

		}
		
		public void print(String str){
			System.out.println(str);
		}
	}

	/**
	 * Enables Firebug Lite for debugging a webEngine.
	 * 
	 * @param engine
	 *            the webEngine for which debugging is to be enabled.
	 */
	private static void enableFirebug(final WebEngine engine) {
		engine.executeScript(
				"if (!document.getElementById('FirebugLite')){E = document['createElement' + 'NS'] && document.documentElement.namespaceURI;E = E ? document['createElement' + 'NS'](E, 'script') : document['createElement']('script');E['setAttribute']('id', 'FirebugLite');E['setAttribute']('src', 'https://getfirebug.com/' + 'firebug-lite.js' + '#startOpened');E['setAttribute']('FirebugLite', '4');(document['getElementsByTagName']('head')[0] || document['getElementsByTagName']('body')[0]).appendChild(E);E = new Image;E['setAttribute']('src', 'https://getfirebug.com/' + '#startOpened');}");
	}

	/**
	 * Executes a script which may reference jQuery function on a document.
	 * Checks if the document loaded in a webEngine has a version of jQuery
	 * corresponding to the minimum required version loaded, and, if not, then
	 * loads jQuery into the document from the default JQUERY_LOCATION.
	 * 
	 * @param engine
	 *            the webView engine to be used.
	 * @Param jQueryLocation the location of the jQuery script to be executed.
	 * @param minVersion
	 *            the minimum version of jQuery which needs to be included in
	 *            the document.
	 * @param script
	 *            provided javascript script string (which may include use of
	 *            jQuery functions on the document).
	 * @return the result of the script execution.
	 */
	private static Object executejQuery(final WebEngine engine, String minVersion, String jQueryLocation,
			String script) {
		return engine.executeScript("(function(window, document, version, callback) { " + "var j, d;"
				+ "var loaded = false;" + "if (!(j = window.jQuery) || version > j.fn.jquery || callback(j, loaded)) {"
				+ "  var script = document.createElement(\"script\");" + "  script.type = \"text/javascript\";"
				+ "  script.src = \"" + jQueryLocation + "\";"
				+ "  script.onload = script.onreadystatechange = function() {"
				+ "    if (!loaded && (!(d = this.readyState) || d == \"loaded\" || d == \"complete\")) {"
				+ "      callback((j = window.jQuery).noConflict(1), loaded = true);" + "      j(script).remove();"
				+ "    }" + "  };" + "  document.documentElement.childNodes[0].appendChild(script) " + "} "
				+ "})(window, document, \"" + minVersion + "\", function($, jquery_loaded) {" + script + "});");
	}

	private static Object executejQuery(final WebEngine engine, String minVersion, String script) {
		return executejQuery(engine, DEFAULT_JQUERY_MIN_VERSION, JQUERY_LOCATION, script);
	}

	private Object executejQuery(final WebEngine engine, String script) {
		return executejQuery(engine, DEFAULT_JQUERY_MIN_VERSION, script);
	}

	public static void initializeJS(WebEngine engine) throws Exception {
		String script = new Tools().accessRessourceFile("/html/js/arborator/jquery.js");
		engine.executeScript(script);
		script = new Tools().accessRessourceFile("/html/js/arborator/raphael.js");
		engine.executeScript(script);
		script = new Tools().accessRessourceFile("/html/js/arborator/jquery-ui-1.8.18.custom.min.js");
		engine.executeScript(script);
		script = new Tools().accessRessourceFile("/html/js/arborator/jquery.cookie.js");
		engine.executeScript(script);
		script = new Tools().accessRessourceFile("/html/js/arborator/arborator.draw.js");
		engine.executeScript(script);
		script = new Tools().accessRessourceFile("/html/js/arborator/q.js");
		engine.executeScript(script);
		script = new Tools().accessRessourceFile("/html/js/arborator/jsundoable.js");
		engine.executeScript(script);
		script = new Tools().accessRessourceFile("/html/js/arborator/colpick.js");
		engine.executeScript(script);
//		script = new Tools().accessRessourceFile("/html/js/jquery.blockUI.js");
//		executejQuery(engine, DEFAULT_JQUERY_MIN_VERSION, JQUERY_LOCATION, script);
//		script = new Tools().accessRessourceFile("/html/js/srcmf-error.js");
//		executejQuery(engine, DEFAULT_JQUERY_MIN_VERSION, JQUERY_LOCATION, script);
	}
}