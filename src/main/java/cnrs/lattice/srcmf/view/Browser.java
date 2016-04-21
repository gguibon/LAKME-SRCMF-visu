package cnrs.lattice.srcmf.view;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Joiner;

import cnrs.lattice.srcmf.beans.MaltFormat;
import cnrs.lattice.srcmf.engines.DataProcess;
import cnrs.lattice.srcmf.engines.Format4MaltEval;
import cnrs.lattice.srcmf.model.Sentence;
import cnrs.lattice.srcmf.model.Tag;
import cnrs.lattice.srcmf.utils.Tools;
import cnrs.lattice.tools.corpus.Corpus;
import cnrs.lattice.tools.corpus.Error;
import cnrs.lattice.tools.corpus.Fixer;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import netscape.javascript.JSObject;
import se.vxu.msi.malteval.MaltEvalConsole;

public class Browser extends Region {

	public static final String DEFAULT_JQUERY_MIN_VERSION = "1.12.0";
	public static final String JQUERY_LOCATION = Browser.class.getResource("/html/js/jquery-1.12.0.min.js")
			.toExternalForm();
	final FileChooser fileChooser = new FileChooser();
	public static MaltFormat maltFormat = new MaltFormat();
	public static List<Sentence> sentences = new ArrayList<Sentence>();
	public static String js = "";
	WebView webView = new WebView();
	WebEngine engine = webView.getEngine();
	Stage stage;

	public Browser(String url) throws Exception {
		webView.setContextMenuEnabled(false);
		createContextMenu(webView);
		
		Tools tool = new Tools();
		String html = tool.accessRessourceFile(url);

		StringBuilder sb = new StringBuilder();
//		sb.append(tool.accessRessourceFile("/html/css/bootstrap.min.css") + "\n");
//		sb.append(tool.accessRessourceFile("/html/css/bootstrap-theme.min.css") + "\n");
		sb.append(tool.accessRessourceFile("/html/css/bootstrap-united.min.css") + "\n");
//		sb.append(tool.accessRessourceFile("/html/css/bootstrap-paper.min.css") + "\n");
//		sb.append(tool.accessRessourceFile("/html/css/bootstrap-spacelab.min.css") + "\n");
//		sb.append(tool.accessRessourceFile("/html/css/bootstrap-readable.min.css") + "\n");
		sb.append(tool.accessRessourceFile("/html/css/bootstrap-toggle.min.css") + "\n");
//		sb.append(tool.accessRessourceFile("/html/css/font-awesome.css") + "\n");
		sb.append(tool.accessRessourceFile("/html/css/srcmf-error.css") + "\n");
//		sb.append(tool.accessRessourceFile("/html/css/jasny-bootstrap.min.css") + "\n");
//		sb.append(tool.accessRessourceFile("/html/startbootstrap-creative-1.0.2/css/animate.min.css") + "\n");
//		sb.append(tool.accessRessourceFile("/html/startbootstrap-creative-1.0.2/css/creative.css") + "\n");
		String css = sb.toString();
		html = html.replace("{csshere}", css)
				.replaceAll("\\{upload\\}","<img src="+getClass().getResource("/html/img/glyphicons/light/upload-arrow-with-bar.png").toExternalForm()+" height=\"24\" width=\"24\">")
				.replaceAll("\\{download\\}","<img src="+getClass().getResource("/html/img/glyphicons/light/download-arrow-with-bar.png").toExternalForm()+" height=\"24\" width=\"24\">")
				.replaceAll("\\{question\\}","<img src="+getClass().getResource("/html/img/glyphicons/light/question-mark.png").toExternalForm()+" height=\"24\" width=\"24\">")
				.replaceAll("\\{refresh\\}","<img src="+getClass().getResource("/html/img/glyphicons/light/refresh-curve-arrows.png").toExternalForm()+" height=\"24\" width=\"24\">")
				.replaceAll("\\{reload\\}","<img src="+getClass().getResource("/html/img/glyphicons/light/reload-arrow.png").toExternalForm()+" height=\"24\" width=\"24\">")
				.replaceAll("\\{settings\\}","<img src="+getClass().getResource("/html/img/glyphicons/light/settings-bars.png").toExternalForm()+" height=\"24\" width=\"24\">")
				.replaceAll("\\{power\\}","<img src="+getClass().getResource("/html/img/glyphicons/color/power-button-off-red.png").toExternalForm()+" height=\"24\" width=\"24\">")
				.replaceAll("\\{book\\}","<img src="+getClass().getResource("/html/img/glyphicons/light/hardbound-book-variant.png").toExternalForm()+" height=\"24\" width=\"24\">")
				.replaceAll("\\{delete\\}","<img src="+getClass().getResource("/html/img/glyphicons/light/rubbish-bin.png").toExternalForm()+" height=\"24\" width=\"24\">")
				.replaceAll("\\{play\\}","<img src="+getClass().getResource("/html/img/glyphicons/light/press-play-button.png").toExternalForm()+" height=\"24\" width=\"24\">")
				.replaceAll("\\{grade\\}","<img src="+getClass().getResource("/html/img/glyphicons/dark/a-plus-mark.png").toExternalForm()+" height=\"24\" width=\"24\">")
				.replaceAll("\\{arc\\}","<img src="+getClass().getResource("/html/img/glyphicons/light/curved-downward-arrow.png").toExternalForm()+" height=\"24\" width=\"24\">")
				.replaceAll("\\{window\\}","<img src="+getClass().getResource("/html/img/glyphicons/dark/open-computer-window.png").toExternalForm()+" height=\"24\" width=\"24\">")
				.replaceAll("\\{letter\\}","<img src="+getClass().getResource("/html/img/glyphicons/light/letter.png").toExternalForm()+" height=\"24\" width=\"24\">")
				//home
				.replaceAll("\\{psl\\}","<img src="+getClass().getResource("/html/img/logo-psl.png").toExternalForm()+" class=\"img-responsive\" alt=\"\" height=\"250\" width=\"300\">")
				.replaceAll("\\{lattice\\}","<img src="+getClass().getResource("/html/img/logo-lattice.jpg").toExternalForm()+" class=\"img-responsive\" alt=\"\" height=\"250\" width=\"300\">")
				.replaceAll("\\{enc\\}","<img src="+getClass().getResource("/html/img/logo-enc.jpg").toExternalForm()+" class=\"img-responsive\" alt=\"\" height=\"250\" width=\"300\">")
				.replaceAll("\\{ens\\}","<img src="+getClass().getResource("/html/img/logo-ens.jpg").toExternalForm()+" class=\"img-responsive\" alt=\"\" height=\"250\" width=\"300\">")
				.replaceAll("\\{sorbonne\\}","<img src="+getClass().getResource("/html/img/logo-sorbonne.jpg").toExternalForm()+" class=\"img-responsive\" alt=\"\" height=\"250\" width=\"300\">")
				.replaceAll("\\{sorbonne-nouvelle\\}","<img src="+getClass().getResource("/html/img/logo-sorbonne-nouvelle.png").toExternalForm()+" class=\"img-responsive\" alt=\"\" height=\"250\" width=\"300\">")
				;
		engine.loadContent(html);
		engine.setOnAlert(new EventHandler<WebEvent<String>>() {
			@Override
			public void handle(WebEvent<String> event) {
				Stage popup = new Stage();
				popup.initOwner(stage);
				popup.initStyle(StageStyle.UTILITY);
				popup.initModality(Modality.WINDOW_MODAL);

				StackPane content = new StackPane();
				content.getChildren().setAll(new Label(event.getData()));
				content.setPrefSize(200, 100);

				popup.setScene(new Scene(content));
				popup.show();
			}
		});

		engine.documentProperty().addListener(new ChangeListener<Document>() {
			@Override
			public void changed(ObservableValue<? extends Document> prop, Document oldDoc, Document newDoc) {
				try {
					initializeJS(engine);

				} catch (Exception e) {

				}
			}
		});

		JSObject window = (JSObject) engine.executeScript("window");
		window.setMember("call", new Call());
		window.setMember("data", new MaltFormat());
		// add the web view to the scene
		getChildren().add(webView);

	}

	private Node createSpacer() {
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		return spacer;
	}

	@Override
	protected void layoutChildren() {
		double w = getWidth();
		double h = getHeight();
		layoutInArea(webView, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
	}

	@Override
	protected double computePrefWidth(double height) {
		return 750;
	}

	@Override
	protected double computePrefHeight(double width) {
		return 500;
	}

	private void createContextMenu(WebView webView) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem goHome = new MenuItem("Go back to Home");
        goHome.setOnAction(e -> {
			try {
				new Call().goToIndex();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        MenuItem reload = new MenuItem("Reload Page");
        reload.setOnAction(e -> webView.getEngine().reload());
        MenuItem goPreviousPage = new MenuItem("Previous Page");
        goPreviousPage.setOnAction(e -> new Call().goBack());
        MenuItem goNextPage = new MenuItem("Next Page");
        goNextPage.setOnAction(e -> new Call().goForward());
        contextMenu.getItems().addAll(goHome, reload, goPreviousPage, goNextPage);

        webView.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(webView, e.getScreenX(), e.getScreenY());
            } else {
                contextMenu.hide();
            }
        });
    }

	
	/**
	 * Class to receive call events. ex: onclick="call.method()"
	 * 
	 * @author gael
	 *
	 */
	public class Call {

		String path1 = "";
		String path2 = "";
		String res1 = "";
		String res2 = "";
		String var1 = "";
		String var2 = "";
		String var3 = "";
		List<Tag> posTagsScores = new ArrayList<Tag>();
		List<Tag> synTagsScores = new ArrayList<Tag>();
		
		
		
		/**
		 * use webHistory to go back to the previous page
		 * @return
		 */
		public String goBack()
		  {    
		    final WebHistory history=engine.getHistory();
		    ObservableList<WebHistory.Entry> entryList=history.getEntries();
		    int currentIndex=history.getCurrentIndex();

		    Platform.runLater(new Runnable() { public void run() { history.go(-1); } });
		    return entryList.get(currentIndex>0?currentIndex-1:currentIndex).getUrl();
		  }
		
		/**
		 * use webHistory to go forward to the next page
		 * @return
		 */
		  public String goForward()
		  {    
		    final WebHistory history=engine.getHistory();
		    ObservableList<WebHistory.Entry> entryList=history.getEntries();
		    int currentIndex=history.getCurrentIndex();

		    Platform.runLater(new Runnable() { public void run() { history.go(1); } });
		    return entryList.get(currentIndex<entryList.size()-1?currentIndex+1:currentIndex).getUrl();
		  }
		
		public void fc(String id) {
			File currentDir = new File(System.getProperty("user.dir")); // System.getProperty("user.dir")
			fileChooser.setTitle("Select a file");
			fileChooser.setInitialDirectory(currentDir);
			File file = fileChooser.showOpenDialog(stage);
			if (file != null) {
				Element el = engine.getDocument().getElementById(id);
				el.setTextContent(file.getAbsolutePath());
				growl("success", "File selected", file.getName());
			}
		}

		/**
		 * Opens a FileChooser to let the user select a file to save to.
		 * 
		 * @throws Exception
		 */
		public void export(String deleteHeader, String contextExport) throws Exception {
			FileChooser fileChooser = new FileChooser();

			// Define extension filter
			FileChooser.ExtensionFilter CONLL = new FileChooser.ExtensionFilter("CoNLL files (*.conll)", "*.conll");
			FileChooser.ExtensionFilter TSV = new FileChooser.ExtensionFilter("TSV files (*.tsv)", "*.tsv");
			FileChooser.ExtensionFilter ALL = new FileChooser.ExtensionFilter("All files (*)", "*");

			fileChooser.getExtensionFilters().addAll(TSV, CONLL, ALL);

			// Show save file dialog
			fileChooser.setTitle("Define your exported file !");
			File file = fileChooser.showSaveDialog(stage);

			if (file != null) {
				getExportContent(file, deleteHeader, contextExport);

			}
		}

		private void getExportContent(File file, String deleteHeader, String contextExport) {
			Element el = engine.getDocument().getElementById("alertfcerror");
			path1 = el.getTextContent();
			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					// makes the long-running API call
					HashMap<String, Integer> mappy = maltFormat.map;

					if (deleteHeader.equals("true")) {
						path1 = Tools.tempFile("path1", ".temp",
								Format4MaltEval.removeFirstLine(Tools.readFile(path1)));
					}
					String contentContext = "";
					if (contextExport.equals("true")) {
						contentContext = Error.getContextIndependent(path1, mappy.get("form"));
					} else {
						contentContext = Tools.readFile(path1);
					}
					int[] values = { mappy.get("cpostag"), mappy.get("postag"), mappy.get("head"), mappy.get("phead"),
							mappy.get("deprel"), mappy.get("pdeprel") };
					List<Integer> listValues = new ArrayList<Integer>();
					for (int val : values) {
						if (val > ((mappy.get("form")) + 1)) {
							listValues.add((val + 2));
						} else {
							listValues.add(val);
						}
					}

					String contentContextErrors = "";
					if (contextExport.equals("true")) {
						contentContextErrors = Error.tagErrors(Tools.tempFile("tmpcontent", ".tmp", contentContext),
								listValues.get(0), listValues.get(1), listValues.get(2), listValues.get(3),
								listValues.get(4), listValues.get(5));
					} else {
						contentContextErrors = Error.tagErrors(Tools.tempFile("tmpcontent", ".tmp", contentContext),
								values[0], values[1], values[2], values[3], values[4], values[5]);
					}

					Tools.freeMemory(contentContext);
					Tools.ecrire(file.getPath(), contentContextErrors);

					return null;
				}
			};
			task.setOnRunning((WorkerStateEvent event) -> {
				// disable ui
				try {
					enterLoading();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			task.setOnCancelled((WorkerStateEvent event) -> {
				// reenable ui
				// handle cancel
			});
			task.setOnSucceeded((WorkerStateEvent event) -> {
				// reenable ui
				// handle succeed
				exitLoading();
				growl("success", "Saved ! ", "Data saved to " + file.getPath() + " !");
			});

			task.setOnFailed((WorkerStateEvent event) -> {
				// reenable ui
				// handle failed task e.g.:
				executejQuery(engine, "$.unblockUI(); ");
				executejQuery(engine, "alert('Oops, Error: " + task.getException() + "' ); ");
				System.err.println("Oops, Error:");
				task.getException().printStackTrace(System.err);
			});

			Thread t = new Thread(task);
			// thread shouldn't prevent program shutdown
			t.setDaemon(true);
			t.start();
		}

		public void setListTags(String sourceId, String targetId, String deleteHeader, String col) throws IOException {

			Element el = engine.getDocument().getElementById(sourceId);
			String path = el.getTextContent();

			String content = Tools.readFile(path);
			if (deleteHeader.equals("true")) {
				content = Format4MaltEval.removeFirstLine(content);
			}
			content = Format4MaltEval.removeFalseEmptyLines(content);
			List<String> lines = Tools.StringToList(content);
			List<String> synTags = Tools.ListeNonRedondante(lines, Integer.parseInt(col));
			StringBuilder sb = new StringBuilder();
			for (String tag : synTags) {
				sb.append(String.format("<option>%s</option>", tag));
			}
			String html = sb.toString();
			executejQuery(engine, "$('#" + targetId + "').html('" + html + "');");

		}

		public void print(String str) {
			System.out.println(str);
		}

		public void goTo(String url) throws Exception {
			String pageContent = new Tools().accessRessourceFile(url);
			pageContent = pageContent.replaceAll("/", "\\/");
//			System.out.println("yo1\n" + pageContent.replaceAll("\"", "\\\""));
			engine.loadContent(pageContent);
			// engine.load(Browser.class.getResource(url).toExternalForm());
		}

		public void goToIndex() throws Exception {
			StartApp.root1.getChildren().add(new Browser("/html/srcmf-lakme-viewer.html"));
		}

		public void quit() {
			System.exit(0);
		}

		public void submit(String id, String str) {
			System.out.println(id + ":\t" + str);
			maltFormat.map.put(id, Integer.valueOf(str));
		}

		public void setId(int id) {
			maltFormat.setId(id);
			maltFormat.map.put("id", id);
		}

		public void setForm(int form) {
			maltFormat.setForm(form);
			maltFormat.map.put("form", form);
		}

		public void setLemma(int id) {
			maltFormat.setLemma(id);
			maltFormat.map.put("lemma", id);
		}

		public void setCPostag(int id) {
			maltFormat.setCPostag(id);
			maltFormat.map.put("cpostag", id);
		}

		public void setPostag(int id) {
			maltFormat.setPostag(id);
			maltFormat.map.put("postag", id);
		}

		public void setFeats(int id) {
			maltFormat.setFeats(id);
			maltFormat.map.put("feats", id);
		}

		public void setHead(int id) {
			maltFormat.setHead(id);
			maltFormat.map.put("head", id);
		}

		public void setDeprel(int id) {
			maltFormat.setDeprel(id);
			maltFormat.map.put("deprel", id);
		}

		public void setPHead(int id) {
			maltFormat.setPHead(id);
			maltFormat.map.put("phead", id);
		}

		public void setPDeprel(int id) {
			maltFormat.setPDeprel(id);
			maltFormat.map.put("pdeprel", id);
		}

		public void setPresetVal(int[] values) {
			setId(values[0]);
			setForm(values[1]);
			setLemma(values[2]);
			setCPostag(values[3]);
			setPostag(values[4]);
			setFeats(values[5]);
			setHead(values[6]);
			setPDeprel(values[7]);
		}

		public void setPreset(String id) {
			int[] values = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			String[] entries = { "id", "form", "lemma", "cpostag", "postag", "feats", "head", "deprel", "phead",
					"pdeprel" };
			switch (id) {
			case "srcmf":
				int[] conll = { 1, 2, 4, 5, 6, 5, 10, 12, 10, 12 };
				values = conll;
				setPresetVal(conll);
				System.out.println("conll");
				break;
			case "malt":
				int[] malt = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
				values = malt;
				setPresetVal(values);
				System.out.println("malt");
				break;
			case "mate":
				int[] mate = { 0, 1, 3, 4, 5, 4, 8, 10, 9, 11 };
				values = mate;
				setPresetVal(values);
				System.out.println("mate");
				break;
			}
			for (int i = 0; i < entries.length; i++) {
				engine.executeScript("var nbnames = document.getElementsByName(\"" + entries[i] + "\").length;"
						+ "for (i = 0; i < nbnames; i++) { " + "document.getElementsByName(\"" + entries[i]
						+ "\")[i].value = \"" + values[i] + "\";" + "}");
				maltFormat.map.put(entries[i], values[i]);
			}
		}

		public void tsv2MaltEval(String deleteHeader) {

			path1 = engine.getDocument().getElementById("alertfc1").getTextContent();
			path2 = engine.getDocument().getElementById("alertfc2").getTextContent();

			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					// makes the long-running API call

					try {
						if (deleteHeader.equals("true")) {
							path1 = Tools.tempFile("path1", ".temp",
									Format4MaltEval.removeFirstLine(Tools.readFile(path1)));
							path2 = Tools.tempFile("path2", ".temp",
									Format4MaltEval.removeFirstLine(Tools.readFile(path2)));
						}
						String malt1 = Format4MaltEval.convert2malt(maltFormat.map, path1);

						String malt2 = Format4MaltEval.convert2malt(maltFormat.map, path2);
						String temp1 = Tools.tempFile("temp1", ".temp", malt1);
						String temp2 = Tools.tempFile("temp2", ".temp", malt2);
						Thread t = new Thread("New Thread") {
							public void run() {
								try {
									cnrs.lattice.tools.corpus.Error.startMaltEval(temp1, temp2);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						};
						t.start();

						growl("success", "Success", "MaltEval is now open !");

					} catch (Exception e) {
						e.printStackTrace();
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("ERROR");
						alert.setHeaderText("Exception thrown");
						alert.setContentText(e.toString());
						alert.initModality(Modality.WINDOW_MODAL);
						alert.showAndWait();
					}

					return null;
				}
			};
			task.setOnRunning((WorkerStateEvent event) -> {
				// disable ui
				System.out.println("start");
				try {
					enterLoading();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			task.setOnCancelled((WorkerStateEvent event) -> {
				// reenable ui
				// handle cancel
			});
			task.setOnSucceeded((WorkerStateEvent event) -> {
				// reenable ui
				// handle succeed
				System.out.println("exit");
				exitLoading();
			});

			task.setOnFailed((WorkerStateEvent event) -> {
				// reenable ui
				// handle failed task e.g.:
				executejQuery(engine, "$.unblockUI(); ");
				executejQuery(engine, "alert('Oops, Error: " + task.getException() + "'); ");
				System.err.println("Oops, Error:");
				task.getException().printStackTrace(System.err);
			});

			Thread t = new Thread(task);
			// thread shouldn't prevent program shutdown
			t.setDaemon(true);
			t.start();
		}

		/**
		 * Allows to isolate errors and opens with MaltEval only the collection
		 * of sentences containing errors on the selected syntactic function
		 * 
		 * @param deleteHeader
		 * @param tag
		 * @throws Exception
		 */
		public void ErrorViewer(String deleteHeader, String tag, String scoresPos, String scoresSyn) throws Exception {
			Element el = engine.getDocument().getElementById("alertfcerror");
			path1 = el.getTextContent();

			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					// makes the long-running API call
//					System.out.println(tag + " " + deleteHeader);
					
					if (deleteHeader.equals("true")) {
						path1 = Tools.tempFile("path1", ".temp",
								Format4MaltEval.removeFirstLine(Tools.readFile(path1)));
					}
				
					String malt = Format4MaltEval.convert2malt(maltFormat.map, path1);
					
					malt = Fixer.fixSyntaxDuplicataAndAnnotatorTag(malt, 6);
					malt = Fixer.fixSyntaxDuplicataAndAnnotatorTag(malt, 8);

					// needed to access malt content inside threads
					var1 = malt;
					var2 = malt;
					var3 = malt;

					Thread t1 = new Thread("syn scores Thread") {
						public void run() {
							try {
								// // syn scores
								String scoresSyn = Error.getDeprelScores(Tools.tempFile("malt", ".malt", var1));
								List<String> lines = Tools.StringToList(scoresSyn);
								StringBuilder sb = new StringBuilder();
								sb.append(
										"<div style=\"overflow-y: scroll; height:400px;\"><table border=\"0\" style=\"width:100%\" "
										+ "class=\"table table-condensed table-responsive\"> "
												+ "<th>Tag</th><th>Correct Tags</th>" + "<tbody>");
								for (String line : lines) {
									String[] cols = line.split("\t");
									Tag tag = new Tag();
									tag.setName(cols[0]);
									tag.setScore(Double.parseDouble(
											cols[1].replaceAll("%", "").replaceAll(" ", "").replaceAll(",", ".")));
									synTagsScores.add(tag);
									sb.append("<tr class=\"hoveractive\"><td>" + cols[0] + "</td><td>" + cols[1] + "</td></tr>");
								}
								sb.append("</tbody></table></div>");
								res1 = sb.toString();
								// scores process end
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};

					Thread t2 = new Thread("pos scores Thread") {
						public void run() {
							try {
								// //pos scores process
								String scoresPos = Error.getTagsAccuracy(Tools.tempFile("malt2", ".malt", var2), 3, 4);
								List<String> linesPos = Tools.StringToList(scoresPos);
								StringBuilder posSb = new StringBuilder();
								posSb.append(
										"<div style=\"overflow-y: scroll; height:400px;\"><table border=\"0\" style=\"width:100%\" "
										+ "class=\"table table-condensed table-responsive\"> "
												+ "<th>PoS tag</th><th>Correct PoS tags</th>" + "<tbody>");
								for (String line : linesPos) {
									String[] cols = line.split("\t");
									Tag tag = new Tag();
									tag.setName(cols[0]);
									tag.setScore(Integer.parseInt(cols[1].replaceAll("%", "").replaceAll(" ", "")));
									posTagsScores.add(tag);
									posSb.append("<tr class=\"hoveractive\"><td>" + cols[0] + "</td>" + "<td>" + cols[1] + " %" + "</td>"
											+ "<td>" + "</tr>");
								}
								posSb.append("</tbody></table></div>");
								res2 = posSb.toString();
								// pos scores process end
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};

					// System.out.println(malt);
					/*
					 * Thread t3 = new Thread("maltEval Thread") { public void
					 * run() { try { String[] paths =
					 * Error.isolateLasError(Tools.tempFile("malt3", ".malt",
					 * var3), tag); // Error.startMaltEval(paths[3], paths[2]);
					 * startMaltEval(paths[3], paths[2]); } catch (Exception e)
					 * { e.printStackTrace(); } } }; t3.start(); t3.join();
					 */
					if (scoresSyn.equals("true")) {
						t1.start();
						t1.join();
					}
					if (scoresPos.equals("true")) {
						t2.start();
						t2.join();
					}

					return null;
				}
			};
			task.setOnRunning((WorkerStateEvent event) -> {
				// disable ui
				try {
					enterLoading();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			task.setOnCancelled((WorkerStateEvent event) -> {
				// reenable ui
				// handle cancel
			});
			task.setOnSucceeded((WorkerStateEvent event) -> {
				// reenable ui
				// handle succeed

				Thread t3 = new Thread("maltEval Thread") {
					public void run() {
						try {
							String[] paths = Error.isolateLasError(Tools.tempFile("malt3", ".malt", var3), tag);
							// Error.startMaltEval(paths[3], paths[2]);
							startMaltEval(paths[3], paths[2]);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				t3.start();

				executejQuery(engine, "$('#scores').html('" + res1 + "');");
				executejQuery(engine, "$('#synscorestab').html('" + res1 + "');");
				executejQuery(engine, "$('#posscorestab').html('" + res2 + "');");
				executejQuery(engine, "$('.nav-tabs a[href=\"#tabChartsErrors\"]').tab('show');");
				growl("success", "Scores Updated", "You can access scores in its tab.");
				exitLoading();

			});

			task.setOnFailed((WorkerStateEvent event) -> {
				// reenable ui
				// handle failed task e.g.:
				executejQuery(engine, "$.unblockUI(); ");
				executejQuery(engine, "alert('Oops, Error: " + task.getException() + "'); ");
				System.err.println("Oops, Error:");
				task.getException().printStackTrace(System.err);
			});

			Thread t = new Thread(task);
			// thread shouldn't prevent program shutdown
			t.setDaemon(true);
			t.start();

		}

		public void startMaltEval(String str1, String str2) {
			
			Thread t0 = new Thread("maltEval only Thread") {
				public void run() {
					try {
						//Error.startMaltEval(str1, str2);
						String[] arguments = {"-s",str1,"-g",str2,"-v","1"};
						MaltEvalConsole.main(arguments);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			t0.setDaemon(true);
			
			Task<Void> taskMaltEval = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					// makes the long-running API call
					
					t0.start();
					t0.join();
					
					return null;
				}
			};
			taskMaltEval.setOnRunning((WorkerStateEvent event) -> {
				// disable ui
				System.out.println("start");
				try {
					enterLoading();
				} catch (Exception e) {	e.printStackTrace();}
			});
			taskMaltEval.setOnCancelled((WorkerStateEvent event) -> {
				// reenable ui
				// handle cancel

			});
			taskMaltEval.setOnSucceeded((WorkerStateEvent event) -> {
				// reenable ui
				// handle succeed
				exitLoading();
				growl("success", "Success", "MaltEval is now open !");
			});

			taskMaltEval.setOnFailed((WorkerStateEvent event) -> {
				// reenable ui
				// handle failed task e.g.:
				System.err.println("Oops, Error:");
				taskMaltEval.getException().printStackTrace(System.err);
			});
			Thread t = new Thread(taskMaltEval);
			// thread shouldn't prevent program shutdown
			t.setDaemon(true);
			t.start();
		}

		public void enterLoading() throws Exception {

			String pathGif = Browser.class.getResource("/html/img/loading.gif").toExternalForm();
			pathGif = pathGif.replaceAll("file:/", "file:///");
			executejQuery(engine,
					"$.blockUI({" + "css: {" + "border: 'none'," + "padding: '15px'," + "backgroundColor: '#000',"
							+ "'-webkit-border-radius': '10px'," + "'-moz-border-radius': '10px'," + "opacity: .4,"
							+ "color: '#fff'" + " }," + "message: '<h1><img src=\"" + pathGif
							// +
							// "file:///home/gael/workspacefx/SRCMF-errorViewer/src/main/resources/html/img/ajax-loader2.gif"
							+ "\" " + " alt=\"Loading\" \"> Loading...</h1>'" + "}); ");
			// executejQuery(engine, "$('body').addClass('loading');");
		}

		public void exitLoading() {
			System.out.println("exit");
			executejQuery(engine, "$.unblockUI(); ");
			// executejQuery(engine, "$('body').removeClass('loading');");
		}

		/**
		 * Give access to every types of growls from javascript
		 * 
		 * @param type
		 * @param header
		 * @param content
		 */
		public void growl(String type, String header, String content) {
			switch (type) {
			case "warning":
				Notifier.INSTANCE.notifyWarning(header, content);
				break;
			case "info":
				Notifier.INSTANCE.notifyInfo(header, content);
				break;
			case "success":
				Notifier.INSTANCE.notifySuccess(header, content);
				break;
			case "error":
				Notifier.INSTANCE.notifyError(header, content);
				break;
			default:
				Notifier.INSTANCE.notifyInfo(header, content);
				break;
			}
		}

		/**
		 * Gives syntactic scores (percent of correct syntactic functions when
		 * the head is correctly predicted)
		 * 
		 * @param deleteHeader
		 */
		public void getSynScores(String deleteHeader) {
			Element el = engine.getDocument().getElementById("alertfcerror");
			path1 = el.getTextContent();

			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					// makes the long-running API call
					System.out.println("synScores");
					if (deleteHeader.equals("true")) {
						path1 = Tools.tempFile("path1", ".temp",
								Format4MaltEval.removeFirstLine(Tools.readFile(path1)));
					}
					String malt = Format4MaltEval.convert2malt(maltFormat.map, path1);
					malt = Fixer.fixSyntaxDuplicataAndAnnotatorTag(malt, 6);
					malt = Fixer.fixSyntaxDuplicataAndAnnotatorTag(malt, 8);
					String scoresSyn = Error.getDeprelScores(Tools.tempFile("malt", ".malt", malt));
					String scoresPos = Error.getTagsAccuracy(Tools.tempFile("malt2", ".malt", malt), 3, 4);

					List<String> linesSyn = Tools.StringToList(scoresSyn);
					List<String> linesPos = Tools.StringToList(scoresPos);

					StringBuilder synSb = new StringBuilder();
					synSb.append(
							"<div style=\"overflow-y: scroll; height:400px;\"><table border=\"0\" style=\"width:100%\"> "
									+ "<th>Syntactic Function</th><th>Correct Syntactic Functions</th>" + "<tbody>");
					for (String line : linesSyn) {
						String[] cols = line.split("\t");
						synSb.append("<tr><td>" + cols[0] + "</td>" + "<td>" + cols[1] + "</td>" + "<td>" + "</tr>");

					}
					synSb.append("</tbody></table></div>");
					res1 = synSb.toString();

					StringBuilder posSb = new StringBuilder();
					posSb.append(
							"<div style=\"overflow-y: scroll; height:400px;\"><table border=\"0\" style=\"width:100%\"> "
									+ "<th>PoS tag</th><th>Correct PoS tags</th>" + "<tbody>");
					for (String line : linesPos) {
						String[] cols = line.split("\t");
						posSb.append(
								"<tr><td>" + cols[0] + "</td>" + "<td>" + cols[1] + " %" + "</td>" + "<td>" + "</tr>");
					}
					posSb.append("</tbody></table></div>");
					res2 = posSb.toString();
					return null;
				}
			};
			task.setOnRunning((WorkerStateEvent event) -> {
				// disable ui
				System.out.println("start");
				try {
					enterLoading();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			task.setOnCancelled((WorkerStateEvent event) -> {
				// reenable ui
				// handle cancel
			});
			task.setOnSucceeded((WorkerStateEvent event) -> {
				// reenable ui
				// handle succeed
				System.out.println("exit");
				executejQuery(engine, "$('#scores').html('" + res1 + "');");
				executejQuery(engine, "$('#synscorestab').html('" + res1 + "');");
				executejQuery(engine, "$('#posscorestab').html('" + res2 + "');");
				growl("success", "Scores Updated", "You can access scores in its tab.");
				exitLoading();
			});

			task.setOnFailed((WorkerStateEvent event) -> {
				// reenable ui
				// handle failed task e.g.:
				executejQuery(engine, "$.unblockUI(); ");
				executejQuery(engine, "alert('Oops, Error: " + task.getException() + "' );");
				System.err.println("Oops, Error:");
				task.getException().printStackTrace(System.err);
			});

			Thread t = new Thread(task);
			// thread shouldn't prevent program shutdown
			t.setDaemon(true);
			t.start();
		}

		public void openPdf(String path) throws Exception {

			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				InputStream is = getClass().getResourceAsStream(path);
				String tempFile = "file";
				File temp = File.createTempFile(tempFile, ".pdf");
//				String pathPdf = getClass().getResource("/html/test.pdf").toExternalForm().replaceFirst("file:", "");

				Thread t = new Thread("Pdf Thread") {
					public void run() {
						try {

							// Copy file
							OutputStream outputStream = new FileOutputStream(temp);
							byte[] buffer = new byte[1024];
							int length;
							while ((length = is.read(buffer)) > 0) {
								outputStream.write(buffer, 0, length);
							}
							outputStream.close();
							is.close();

							desktop.open(temp);
						} catch (Exception e) {
							e.printStackTrace();
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Documentation");
							alert.setHeaderText("Error");
							alert.setContentText("NO PDF READER INSTALLED");

							alert.showAndWait();
						}
					}
				};
				t.start();
				growl("info", "Documentation", "Opened with Default Application");
			}

		}

		public void showChart() {
			try {
				// Load the fxml file and create a new stage for the popup.
				FXMLLoader loader = new FXMLLoader();
				// FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
				// load fxml in maven architecture
				loader.setLocation(getClass().getResource("/StatusStatistics.fxml"));
				AnchorPane page = (AnchorPane) loader.load();

				Stage dialogStage = new Stage();

				dialogStage.setTitle("Statuses");
				dialogStage.initModality(Modality.WINDOW_MODAL);
				dialogStage.initOwner(stage);
				Scene scene = new Scene(page);
				dialogStage.setScene(scene);

				// Set the persons into the controller.
				StatusStatisticsController controller = loader.getController();
				controller.setTagData(synTagsScores);

				dialogStage.show();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * launch arborator browser with internet connection to the arborator 
		 * quick access page
		 * @param content2inject The content to inject (i.e. the sentence)
		 * @param id2retrieve (the sentence id in the data, to change in case of modifications)
		 * @deprecated better use the arboratorLocal method
		 */
		public void arborator(String content2inject, String id2retrieve) {

			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					// makes the long-running API call

					return null;
				}
			};
			task.setOnRunning((WorkerStateEvent event) -> {
				// disable ui
				System.out.println("start arborator");
				try {
					enterLoading();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			task.setOnCancelled((WorkerStateEvent event) -> {
				// reenable ui
				// handle cancel
			});
			task.setOnSucceeded((WorkerStateEvent event) -> {
				// reenable ui
				// handle succeed
				Lakme2Arborator arbo = new Lakme2Arborator();
				arbo.setContent(content2inject, id2retrieve);

				try {
					Stage popup = new Stage();
					popup.initOwner(stage);
					popup.initStyle(StageStyle.UTILITY);
					popup.initModality(Modality.WINDOW_MODAL);

					popup.setScene(arbo.getScene(popup));
					popup.show();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				exitLoading();
			});

			task.setOnFailed((WorkerStateEvent event) -> {
				// reenable ui
				// handle failed task e.g.:
				executejQuery(engine, "$.unblockUI(); ");
				executejQuery(engine, "alert('Oops, Error: " + task.getException() + "' );");
				System.err.println("Oops, Error:");
				task.getException().printStackTrace(System.err);
			});

			Thread t = new Thread(task);
			// thread shouldn't prevent program shutdown
			t.setDaemon(true);
			t.start();
		}

		
		/**
		 * launch arborator browser locally
		 * @param content2inject The content to inject (i.e. the sentence)
		 * @param id2retrieve (the sentence id in the data, to change in case of modifications)
		 */
		public void arboratorLocal(String content2inject, String id2retrieve) {

			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					// makes the long-running API call

					return null;
				}
			};
			task.setOnRunning((WorkerStateEvent event) -> {
				// disable ui
				System.out.println("start arborator");
				try {
					enterLoading();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			task.setOnCancelled((WorkerStateEvent event) -> {
				// reenable ui
				// handle cancel
			});
			task.setOnSucceeded((WorkerStateEvent event) -> {
				// reenable ui
				// handle succeed
				ArboratorQuickAccessLocal arbo = new ArboratorQuickAccessLocal();
				arbo.setContent(content2inject, id2retrieve);

				try {
					Stage popup = new Stage();
					popup.initOwner(stage);
					popup.initStyle(StageStyle.UTILITY);
					popup.initModality(Modality.WINDOW_MODAL);

					popup.setScene(arbo.getScene(popup));
					popup.show();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				exitLoading();
			});

			task.setOnFailed((WorkerStateEvent event) -> {
				// reenable ui
				// handle failed task e.g.:
				executejQuery(engine, "$.unblockUI(); ");
				executejQuery(engine, "alert('Oops, Error: " + task.getException() + "' );");
				System.err.println("Oops, Error:");
				task.getException().printStackTrace(System.err);
			});

			Thread t = new Thread(task);
			// thread shouldn't prevent program shutdown
			t.setDaemon(true);
			t.start();
		}
		
		
		/**
		 * refresh the sentences from the file, and show them in a table view
		 * 
		 * @param deleteHeader
		 * @throws Exception
		 */
		public void reloadSentences(String deleteHeader) throws Exception {
			Element el = engine.getDocument().getElementById("alertfcarbo");
			path1 = el.getTextContent();

			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					// makes the long-running API call
					
					if (deleteHeader.equals("true")) {
						path1 = Tools.tempFile("path1", ".temp",
								Format4MaltEval.removeFirstLine(Tools.readFile(path1)));
					}
					
					path1 = Tools.tempFile("path1", ".temp",
							Format4MaltEval.removeFalseEmptyLines(Tools.readFile(path1)));
					
					
					sentences = DataProcess.stockSentences(Corpus.getSentences(path1), maltFormat.map);
					var1 = DataProcess.getSentencesListView(sentences);

					return null;
				}
			};
			task.setOnRunning((WorkerStateEvent event) -> {
				// disable ui
				try {
					enterLoading();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			task.setOnCancelled((WorkerStateEvent event) -> {
				// reenable ui
				// handle cancel
			});
			task.setOnSucceeded((WorkerStateEvent event) -> {
				// reenable ui
				// handle succeed
				// executejQuery(engine, "$('#sentencesListView').html('"+
				// "<button id=\"sent3\" type=\"button\"
				// onclick=\"call.print(\'yahoo\')\" class=\"btn btn-info
				// btn-xl\" >View</button>" + "');");
				executejQuery(engine, "$('#sentencesListView').html('" + var1 + "');");
				growl("success", "Sentences updated", "You can see them below.");
				var1 = "";
				System.out.println("exit");
				exitLoading();

			});

			task.setOnFailed((WorkerStateEvent event) -> {
				// reenable ui
				// handle failed task e.g.:
				executejQuery(engine, "$.unblockUI(); ");
				executejQuery(engine, "alert('Oops, Error: " + task.getException() + "'); ");
				System.err.println("Oops, Error:");
				task.getException().printStackTrace(System.err);
			});

			Thread t = new Thread(task);
			// thread shouldn't prevent program shutdown
			t.setDaemon(true);
			t.start();
		}

		/**
		 * Edit the selected (by id) sentence by arborator Quick Access and
		 * retrieve modifications. Modifications are sent to the public static
		 * sentences from Browser
		 * 
		 * @param id
		 * @throws IOException
		 * @throws Exception
		 */
		public void editSent(String id) throws IOException, Exception {
//			sentences = DataProcess.stockSentences(Corpus.getSentences(path1), maltFormat.map);
			String content2inject = sentences.get(Integer.parseInt(id)).toMalt();
			arborator(content2inject.replaceAll("'", "&apos;"), id);
		}
		
		/**
		 * remove all sentences (will need to reload from file again again)
		 */
		public void deleteSentences(){
			sentences = new ArrayList<Sentence>();
			executejQuery(engine, "$('#sentencesListView').html('');");
		}
		
		/**
		 * show a save file chooser and will write the current sentences (the list) to the
		 * selected file in the same format as it was in entry.
		 * @throws Exception
		 */
		public void exportChanges() throws Exception {

			
			Element el = engine.getDocument().getElementById("alertfcarbo");
			path1 = el.getTextContent();
			
			FileChooser fileChooser = new FileChooser();

			// Define extension filter
			FileChooser.ExtensionFilter ALL = new FileChooser.ExtensionFilter("All files (*)", "*");

			fileChooser.getExtensionFilters().addAll(ALL);

			// Show save file dialog
			fileChooser.setTitle("Define your exported file !");
			File file = fileChooser.showSaveDialog(stage);
			
			if (file != null) {
			
			Task<Void> taskMaltEval = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					// makes the long-running API call
					int headCol = maltFormat.map.get("head");
					int deprelCol = maltFormat.map.get("deprel");

					
						String maltChanged = DataProcess.getSentencesMalt(sentences);
						List<String> originalText = Tools.path2liste(path1);
						List<String> changedText = Tools.StringToList(maltChanged);
//						System.out.println("ori " + originalText.size() + "\t new " + changedText.size());

						StringBuilder sb = new StringBuilder();
						Joiner joinerTab = Joiner.on("\t");
						for (int i = 0; i < originalText.size(); i++) {
							if (originalText.get(i).length() == 0) {
								sb.append("\n");
								continue;
							}
							String[] colsOrigin = originalText.get(i).split("\t");
							String[] colsNew = changedText.get(i).split("\t");
							colsOrigin[headCol] = colsNew[6];
							colsOrigin[deprelCol] = colsNew[7];
							sb.append(joinerTab.join(colsOrigin) + "\n");
						}
						Tools.ecrire(file.getAbsolutePath(), sb.toString());
					

					return null;
				}
			};
			taskMaltEval.setOnRunning((WorkerStateEvent event) -> {
				// disable ui
				try {
					enterLoading();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			taskMaltEval.setOnCancelled((WorkerStateEvent event) -> {
				// reenable ui
				// handle cancel
				exitLoading();
			});
			taskMaltEval.setOnSucceeded((WorkerStateEvent event) -> {
				// reenable ui
				// handle succeed
				exitLoading();
				growl("success", "Success", "Changes written in\n" + file.getAbsolutePath());
			});

			taskMaltEval.setOnFailed((WorkerStateEvent event) -> {
				// reenable ui
				// handle failed task e.g.:
				System.err.println("Oops, Error:");
				taskMaltEval.getException().printStackTrace(System.err);
			});
			Thread t = new Thread(taskMaltEval);
			// thread shouldn't prevent program shutdown
			t.setDaemon(true);
			t.start();
			}
			
			

			}

			
		

		/**
		 * simple javafx alert to inform about the author
		 */
		public void handleAbout() {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About");
			alert.setHeaderText("Author");
			alert.setContentText("Gaël Guibon\n\n" 
					+ "Lattice CNRS"
					+ "\n"
					+ "http://www.lattice.cnrs.fr/"
					+ "\n\n"
					+ "gael.guibon@gmail.com\n\n");
			alert.show();
			
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
		String script = new Tools().accessRessourceFile("/html/js/jquery-2.2.0.min.js");
		engine.executeScript(script);
		script = new Tools().accessRessourceFile("/html/js/bootstrap.min.js");
		engine.executeScript(script);
		script = new Tools().accessRessourceFile("/html/js/bootstrap-toggle.min.js");
		engine.executeScript(script);
		// script = new
		// Tools().accessRessourceFile("/html/js/jasny-bootstrap.min.js");
		// engine.executeScript(script);
		script = new Tools().accessRessourceFile("/html/js/jquery.blockUI.js");
		executejQuery(engine, DEFAULT_JQUERY_MIN_VERSION, JQUERY_LOCATION, script);
//		script = new Tools().accessRessourceFile("/html/js/jquery.slidereveal.min.js");
//		executejQuery(engine, DEFAULT_JQUERY_MIN_VERSION, JQUERY_LOCATION, script);
//		script = new Tools().accessRessourceFile("/html/js/loader.js");
//		engine.executeScript(script);
//		script = new Tools().accessRessourceFile("/html/js/jquery.media.js");
//		engine.executeScript(script);
		// script = new Tools().accessRessourceFile("/html/js/spin.min.js");
		// engine.executeScript(script);
		script = new Tools().accessRessourceFile("/html/js/srcmf-error.js");
		executejQuery(engine, DEFAULT_JQUERY_MIN_VERSION, JQUERY_LOCATION, script);
		script = new Tools().accessRessourceFile("/html/startbootstrap-creative-1.0.2/js/jquery.easing.min.js");
		engine.executeScript(script);
		script = new Tools().accessRessourceFile("/html/startbootstrap-creative-1.0.2/js/query.fittext.js");
		engine.executeScript(script);
		script = new Tools().accessRessourceFile("/html/startbootstrap-creative-1.0.2/js/wow.min.js");
		engine.executeScript(script);
		script = new Tools().accessRessourceFile("/html/startbootstrap-creative-1.0.2/js/creative.js");
		engine.executeScript(script);
	}
	
	private static class ExitTrappedException extends SecurityException { }

	  private static void forbidSystemExitCall() {
	    final SecurityManager securityManager = new SecurityManager() {
	      public void checkPermission( Permission permission ) {
	        if( "exitVM".equals( permission.getName() ) ) {
	          throw new ExitTrappedException() ;
	        }
	      }
	    } ;
	    System.setSecurityManager( securityManager ) ;
	  }

	  private static void enableSystemExitCall() {
	    System.setSecurityManager( null ) ;
	  }
}
