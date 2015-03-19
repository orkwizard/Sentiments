package com.sunset.sentiments.core;

import java.io.InputStream;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.decoder.adaptation.Stats;
import edu.cmu.sphinx.decoder.adaptation.Transform;
import edu.cmu.sphinx.result.WordResult;

public class Transcriber {

	private Configuration config;
	private StreamSpeechRecognizer recognizer;
	private InputStream stream;
	private SpeechResult result;
	
	public void loadConfig(String modelPath,String dictionary,String languageMode){
		System.out.println("Loading models ......");
		config = new Configuration();
		config.setAcousticModelPath(modelPath);
		config.setDictionaryPath(dictionary);
		config.setLanguageModelPath(languageMode);
		
	}
	
}
