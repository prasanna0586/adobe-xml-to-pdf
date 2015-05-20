package com.xml.topdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.thoughtworks.xstream.XStream;
public class XmlToPdf {    
	public static void main(String[] args) {                  
		TransformerFactory tFactory = null;
		Transformer transformer = null;
		Document document = new Document();
		PdfWriter writer = null;
		StreamResult streamResult = null;
		try {
			tFactory = TransformerFactory.newInstance();
			transformer = tFactory.newTransformer();
			streamResult = new StreamResult(new FileOutputStream("html/TaxReportID64747.html"));
			transformer.transform(new StreamSource("xml/TaxReportID64747.xml"), streamResult);
			System.out.println("I am here....");
			System.out.println(streamResult.getWriter());
			writer = PdfWriter.getInstance(document, new FileOutputStream("pdf/TaxReportID64747.pdf"));
			document.open();
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream("html/TaxReportID64747.html"));              
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(document != null)
				document.close();
			if (writer != null)
				writer.close();
			
		}
	}
}

