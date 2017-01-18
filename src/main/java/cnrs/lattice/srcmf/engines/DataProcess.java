package cnrs.lattice.srcmf.engines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Joiner;

import cnrs.lattice.srcmf.model.Sentence;
import cnrs.lattice.srcmf.model.Word;
import cnrs.lattice.srcmf.utils.Tools;

public class DataProcess {

	/**
	 * parse a list of string content. each string content being a sentence. It will
	 * then stock datas of words and sentences into their relative models to be access
	 * easily afterwards.
	 * @param listStringSentences
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public static List<Sentence> stockSentences(List<String> listStringSentences, HashMap<String, Integer> map) throws IOException{
		List<Sentence> listSentences = new ArrayList<Sentence>();
		int i = 0;
		for(String s : listStringSentences){
			
			Sentence sent = new Sentence();
			List<String> linesSent = Tools.StringToList(s);
			
			for(String line : linesSent){
				
				Word word = new Word();
				String[] cols = line.split("\t");
				word.setId(cols[map.get("id")]);
				word.setForm(cols[map.get("form")]);
				word.setLemma(cols[map.get("lemma")]);
				word.setCPosTag(cols[map.get("cpostag")]);
				word.setPosTag(cols[map.get("postag")]);
				word.setFeats(cols[map.get("feats")]);
				word.setHead(cols[map.get("head")]);
				word.setDeprel(cols[map.get("deprel")]);
				word.setPHead(cols[map.get("phead")]);
				word.setPDeprel(cols[map.get("pdeprel")]);
				sent.addWord(word);
				
			}
			
			sent.setId(i);
			i++;
			listSentences.add(sent);
		}
		
		return listSentences;
	}
	
	/**
	 * return sentences embedded in html table with joined button with id corresponding to 
	 * "sent"+sent.getId()
	 * @param listSentences
	 * @return
	 */
	public static String getSentencesListView(List<Sentence> listSentences){
		StringBuilder sb = new StringBuilder();
//    	sb.append("<div style=\"overflow: auto; height:400px;\"><table border=\"0\" style=\"width:100%\" "
//    			+ "class=\"table table-condensed table-responsive\"> "
//    			+ "<th>Id</th><th>Sentence</th><th>View</th>"
//    			+ "<tbody>");
    	sb.append("<table><table><th>Id</th><th>Sentence</th><th>View</th></table>"
    			+ "<div style=\"overflow: auto; height:400px;\"><table>");
		for (Sentence sent : listSentences){
			sb.append("<tr><td>"
					+ sent.getId()
					+ "</td><td>"
					+ sent.getStringSentence().replaceAll("'", "&apos;")
					+ "</td><td>"
					+ "<button id=\"sent" + sent.getId() + "\" type=\"button\" onclick=\"call.editSent(&apos;"+ sent.getId() +"&apos;)\" class=\"btn btn-default btn-sm\" >"
							+ "View/Edit"
							+ "</button>"
					+ "</td></tr>");
		}
		sb.append("</table></div></table>");
//		sb.append("</tbody></table></div>");
		return sb.toString();
	}
	
	/**
	 * return the sentences into one String on Malt format
	 * @param listSentences
	 * @return
	 */
	public static String getSentencesMalt(List<Sentence> listSentences){
		Joiner joinerTab = Joiner.on("\t");
		Joiner joinerLn = Joiner.on("\n");
		Joiner joinerDoubleLn = Joiner.on("\n\n");
		List<String> resSentences = new ArrayList<String>();
		
		//recreate malt format from sentences
		for (Sentence s : listSentences){
			List<Word> words = s.getWords();
			List<String> resWords = new ArrayList<String>();
			
			for (Word w : words){
				List<String> cols = new ArrayList<String>();
				cols.add(w.getId());
				cols.add(w.getForm());
				cols.add(w.getLemma());
				cols.add(w.getCPosTag());
				cols.add(w.getPosTag());
				cols.add(w.getFeats());
				cols.add(w.getHead());
				cols.add(w.getDeprel());
				cols.add(w.getPHead());
				cols.add(w.getPDeprel());
				
				resWords.add( joinerTab.join(cols) );
			}
			
			resSentences.add(joinerLn.join(resWords));
		}
		return joinerDoubleLn.join(resSentences);
	}
}
